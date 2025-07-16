package org.trusky.common.impl.network.socket.factory.internal;

import org.trusky.common.api.network.socket.CommonKeyStore;
import org.trusky.common.impl.network.socket.CommonKeystoreParametersImpl;

import java.io.IOException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;

public interface CommonKeystoreCreator {

	CommonKeyStore createKeystore(CommonKeystoreParametersImpl parameters)
	throws KeyStoreException, CertificateException, IOException, NoSuchAlgorithmException;
}
