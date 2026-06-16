package org.trusky.common.impl.network.server;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.trusky.common.api.injection.CommonGuiceModule;
import org.trusky.common.api.injection.InjectorFactory;
import org.trusky.common.api.network.message.LoginMessage;
import org.trusky.common.api.network.message.util.CommonMessageSerializer;
import org.trusky.common.api.network.server.CommonSSLServerAcceptLoop;
import org.trusky.common.api.network.server.RunnableExecutor;
import org.trusky.common.api.network.socket.CommonFlagContainer;
import org.trusky.common.impl.network.socket.CommonFlagContainerFake;
import org.trusky.common.mock.network.server.RunnableExecutorFake;
import org.trusky.common.mock.network.socket.CommonSSLServerSocketSpy;
import org.trusky.common.mock.network.socket.CommonSSLSocketFake;

import java.io.*;
import java.util.concurrent.atomic.AtomicBoolean;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class CommonSSLServerAcceptLoopTest {

	private InputStream socketInputStream;
	private OutputStream socketOutputStream;
	// Writer used to inject data into the socket input stream (simulates client -> server)
	private PipedOutputStream socketInputWriter;
	// Reader used to read what the socket output stream produced (simulates server -> client)
	private PipedInputStream socketOutputReader;

	private CommonMessageSerializer messageSerializer;

	private CommonSSLSocketFake sslSocketFake;
	private CommonSSLServerSocketSpy sslServerSocketSpy;

	private CommonClientConnectionHandlerFactoryMock clientConnectionHandlerFactoryFactoryMock;
	private CommonFlagContainer shutdownFlag;
	private RunnableExecutor runnableExecuter;

	private CommonSSLServerAcceptLoop sut;

	@BeforeEach
	void setUp() throws IOException {

		MockitoAnnotations.initMocks(this);
		InjectorFactory.setModule(new CommonGuiceModule());

		setUpSocketFake();

		messageSerializer = InjectorFactory.getInstance(CommonMessageSerializer.class);

		sslServerSocketSpy = new CommonSSLServerSocketSpy().withAcceptReturn(sslSocketFake);

		shutdownFlag = new CommonFlagContainerFake();
		shutdownFlag.setFlag(true);

		runnableExecuter = new RunnableExecutorFake();

		clientConnectionHandlerFactoryFactoryMock = new CommonClientConnectionHandlerFactoryMock();

		sut = new CommonSSLServerAcceptLoopImpl(sslServerSocketSpy, clientConnectionHandlerFactoryFactoryMock,
				shutdownFlag, runnableExecuter);

	}

	private void setUpSocketFake() throws IOException {
		// Create piped streams so tests can inject messages into the socket input stream and
		// read messages written to the socket output stream.
		this.socketInputWriter = new PipedOutputStream();
		this.socketInputStream = new PipedInputStream(this.socketInputWriter);

		PipedOutputStream pipedOut = new PipedOutputStream();
		PipedInputStream pipedOutReader = new PipedInputStream(pipedOut);
		this.socketOutputStream = new PipedOutputStream();

		this.socketOutputReader = pipedOutReader;

		sslSocketFake = new CommonSSLSocketFake().withInputStream(this.socketInputStream)
				.withOutputStream(this.socketOutputStream);
	}

	@Test
	void loopAcceptsMessageAndTerminates() {

		AtomicBoolean providedHandlerHasBeenCalled = new AtomicBoolean(false);

		// GIVEN
		LoginMessage login = new LoginMessage(messageSerializer, "Monkey");
		assertDoesNotThrow( //
				() -> socketInputWriter.write(login.toByteArray()), //
				"Should not throw an IO-exception durling preparation");


		CommonClientSSLConnectionHandlerFake providedHandler = new CommonClientSSLConnectionHandlerFake();

		// Let the loop terminate on the first call
		providedHandler.setCallback(() -> {
			shutdownFlag.setFlag(true);
			providedHandlerHasBeenCalled.set(true);
		});
		clientConnectionHandlerFactoryFactoryMock.setProvidedHandler(providedHandler);

		shutdownFlag.setFlag(false); // No shutdown requested by now

		// WHEN
		assertDoesNotThrow(() -> sut.startLoop());

		// THEN
		assertAll(() -> assertThat(providedHandler.isCommunicationHandled()).isTrue(),
				() -> assertThat(providedHandlerHasBeenCalled.get()).isTrue());
	}

}