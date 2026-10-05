package org.trusky.common.impl.network.server;

import org.junit.jupiter.api.Test;
import org.trusky.common.api.network.server.CommonClientConnectionHandlerFactory;
import org.trusky.common.api.network.server.CommonClientSSLConnectionHandler;
import org.trusky.common.api.network.socket.CommonSSLSocket;
import org.trusky.common.impl.network.socket.CommonFlagContainerFake;
import org.trusky.common.impl.network.socket.CommonSSLSocketFake;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Beispiel-Test, der zeigt, wie CommonClientConnectionHandlerFactoryMock verwendet wird.
 */
class CommonClientConnectionHandlerFactoryMockExampleTest {

	@Test
	void testCommonClientConnectionHandlerFactoryMock() {
		// GIVEN
		CommonClientSSLConnectionHandler mockHandler = new CommonClientSSLConnectionHandlerFake();
		CommonClientConnectionHandlerFactory factory = new CommonClientConnectionHandlerFactoryMock(mockHandler);
		CommonSSLSocket socket = new CommonSSLSocketFake();
		var flagContainer = new CommonFlagContainerFake();

		// WHEN
		CommonClientSSLConnectionHandler returnedHandler = factory.createClientConnectionHandler(socket, flagContainer);

		// THEN
		assertThat(returnedHandler).isSameAs(mockHandler);
	}

	@Test
	void testCommonClientConnectionHandlerFactoryMockAlwaysReturnsSameInstance() {
		// GIVEN
		CommonClientSSLConnectionHandler mockHandler = new CommonClientSSLConnectionHandlerFake();
		CommonClientConnectionHandlerFactory factory = new CommonClientConnectionHandlerFactoryMock(mockHandler);
		CommonSSLSocket socket1 = new CommonSSLSocketFake();
		CommonSSLSocket socket2 = new CommonSSLSocketFake();
		var flagContainer = new CommonFlagContainerFake();

		// WHEN
		CommonClientSSLConnectionHandler handler1 = factory.createClientConnectionHandler(socket1, flagContainer);
		CommonClientSSLConnectionHandler handler2 = factory.createClientConnectionHandler(socket2, flagContainer);

		// THEN
		assertThat(handler1).isSameAs(handler2).isSameAs(mockHandler);
	}
}

