package org.trusky.common.api.network.implementators.factory;

import org.trusky.common.api.network.implementators.CommonConnectionProcessor;
import org.trusky.common.api.network.socket.CommonImmutableFlagContainer;
import org.trusky.common.api.network.socket.CommonSSLSocket;

public interface CommonConnectionProcessorFactory {

	/**
	 * Create a CommonConnectionProcessor to handle the communication with one client.
	 *
	 * <p>The CommonConnectionProcessor should use non-blocking read, checking the flag container on timeouts. If
	 * the flag is set, communication should be ended (eventually sending an appropriate closing  message) and
	 * start() method shoulld return.</p>
	 *
	 * <p>The start() method will be called on a new thread.</p>
	 *
	 * @param connection
	 * @param flagContainer
	 * @return
	 */
	CommonConnectionProcessor create(CommonSSLSocket connection, CommonImmutableFlagContainer flagContainer);
}
