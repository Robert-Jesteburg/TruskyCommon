package org.trusky.common.impl.network.socket;

import org.trusky.common.api.network.socket.CommonKeystoreParameters;

// FIXME Write tests
public class CommonKeystoreParametersImpl implements CommonKeystoreParameters {

	private final String pathToKeystore;
	private final String keystorePassword;
	private final String keystoreType;

	public CommonKeystoreParametersImpl(String pathToKeystore, String keystorePassword, String keystoreType) {
		this.pathToKeystore = pathToKeystore;
		this.keystorePassword = keystorePassword;
		this.keystoreType = keystoreType;
	}

	@Override
	public String getPathToKeystore() {
		return pathToKeystore;
	}

	@Override
	public String getKeystorePassword() {
		return keystorePassword;
	}

	@Override
	public String getKeystoreType() {
		return keystoreType;
	}
}
