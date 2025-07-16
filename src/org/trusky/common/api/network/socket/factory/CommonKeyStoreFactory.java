package org.trusky.common.api.network.socket.factory;

import org.trusky.common.api.network.socket.CommonKeyStore;
import org.trusky.common.api.network.socket.CommonKeystoreParameters;

import java.io.IOException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;

public interface CommonKeyStoreFactory {

	CommonKeyStore create(CommonKeystoreParameters parameters)
	throws KeyStoreException, IOException, CertificateException, NoSuchAlgorithmException;
}
