package org.trusky.common.impl.network.socket;

import org.junit.jupiter.api.Test;
import org.trusky.common.api.network.socket.CommonSSLSocket;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Beispiel-Test, der zeigt, wie CommonSSLServerSocketSpy verwendet wird.
 */
class CommonSSLServerSocketSpyExampleTest {

	@Test
	void testCommonSSLServerSocketSpy() throws IOException {
		// GIVEN
		CommonSSLSocket mockedSocket = new CommonSSLSocketFake();
		CommonSSLServerSocketSpy spy = new CommonSSLServerSocketSpy(mockedSocket);

		// WHEN
		CommonSSLSocket returnedSocket = spy.accept();
		spy.setSoTimeout(5000);
		spy.setSoTimeout(10000);

		// THEN
		assertThat(returnedSocket).isSameAs(mockedSocket);
		assertThat(spy.getSetSoTimeoutCalls()).hasSize(2);
		assertThat(spy.getSetSoTimeoutCalls()).containsExactly(new CommonSSLServerSocketSpy.SetSoTimeoutCall(5000),
				new CommonSSLServerSocketSpy.SetSoTimeoutCall(10000));
		assertThat(spy.getSetSoTimeoutCalls()
				.get(0)
				.getTimeoutInMilliSeconds()).isEqualTo(5000);
		assertThat(spy.getSetSoTimeoutCalls()
				.get(1)
				.getTimeoutInMilliSeconds()).isEqualTo(10000);
	}

	@Test
	void testCommonFlagContainerFake() {
		// GIVEN
		CommonFlagContainerFake fake = new CommonFlagContainerFake();

		// WHEN
		fake.setFlag(true);

		// THEN
		assertThat(fake.getFlag()).isTrue();
	}
}

