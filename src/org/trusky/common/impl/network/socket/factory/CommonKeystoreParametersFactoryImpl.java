package org.trusky.common.impl.network.socket.factory;

import org.trusky.common.api.network.socket.CommonKeystoreParameters;
import org.trusky.common.api.network.socket.factory.CommonKeystoreParametersFactory;
import org.trusky.common.impl.network.socket.CommonKeystoreParametersImpl;

public class CommonKeystoreParametersFactoryImpl implements CommonKeystoreParametersFactory {

	@Override
	public CommonKeystoreParameters create(String pathToKeystore, String keystorePwd, String instanceType) {
		return new CommonKeystoreParametersImpl(pathToKeystore, keystorePwd, instanceType);
	}
}
