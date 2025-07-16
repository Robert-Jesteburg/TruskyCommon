package org.trusky.common.impl.network.socket;

import org.junit.jupiter.api.Test;
import org.trusky.common.api.network.socket.CommonKeystoreParameters;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class CommonKeystoreParametersImplTest {

	@Test
	void testKeystoreParameters() {

		// GIVEN
		final String pathToKestore = "/etc/appname/config/kesytore.ks";
		final String pwd4Keystore = "SecretPassword";
		final String keystoreType = "JKS";

		// WHEN
		CommonKeystoreParameters parameter = new CommonKeystoreParametersImpl(pathToKestore, pwd4Keystore,
				keystoreType);

		// THEN
		assertAll( //
				() -> assertThat(parameter.getPathToKeystore()).as("Keystore path differs")
						.isEqualTo(pathToKestore), () -> assertThat(parameter.getKeystorePassword()).as("Keystore " +
								"password differs")
						.isEqualTo(pwd4Keystore), () -> assertThat(parameter.getKeystoreType()).as("Keystore type " +
								"differs")
						.isEqualTo(keystoreType));

	}

}