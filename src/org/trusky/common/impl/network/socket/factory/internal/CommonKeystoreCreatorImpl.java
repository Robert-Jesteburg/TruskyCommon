package org.trusky.common.impl.network.socket.factory.internal;

import org.trusky.common.api.network.socket.CommonKeyStore;
import org.trusky.common.impl.network.socket.CommonKeyStoreImpl;
import org.trusky.common.impl.network.socket.CommonKeystoreParametersImpl;

import java.io.FileInputStream;
import java.io.IOException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;

public class CommonKeystoreCreatorImpl implements CommonKeystoreCreator {
	
	@Override
	public CommonKeyStore createKeystore(CommonKeystoreParametersImpl parameters)
	throws KeyStoreException, CertificateException, IOException, NoSuchAlgorithmException {

		KeyStore keyStore = KeyStore.getInstance(parameters.getKeystoreType());
		FileInputStream fis = new FileInputStream(parameters.getPathToKeystore());
		keyStore.load(fis, parameters.getKeystorePassword()
				.toCharArray());

		return new CommonKeyStoreImpl(keyStore, parameters.getKeystorePassword());
	}
}
