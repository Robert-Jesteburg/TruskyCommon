package org.trusky.common.api.network.socket.factory;

import org.trusky.common.api.network.socket.CommonKeystoreParameters;

public interface CommonKeystoreParametersFactory {

	CommonKeystoreParameters create(String pathToKeystore, String keystorePwd, String instanceType);
}
