package org.trusky.common.api.network.implementators.factory;

import org.trusky.common.api.network.implementators.CommonShutdownSocketListener;
import org.trusky.common.api.network.socket.CommonFlagContainer;
import org.trusky.common.api.network.socket.CommonSSLServerSocket;

/**
 * Factory to create a {@link CommonShutdownSocketListener}. The CommonFlagContainer should be passed to the new
 * object: If the shutdown is successfully requested, simply set the flag to true and terminate the listener.
 */
public interface CommonShutdownSocketFactory {

	CommonShutdownSocketListener create(CommonFlagContainer shutdownFlag, CommonSSLServerSocket socket);
}
