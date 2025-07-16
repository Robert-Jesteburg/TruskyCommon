package org.trusky.common.api.network.implementators;

import org.trusky.common.api.configuration.Configuration;
import org.trusky.common.api.network.implementators.factory.CommonConnectionProcessorFactory;
import org.trusky.common.api.network.implementators.factory.CommonShutdownSocketFactory;

public interface CommonNetworkSocketHandler {

	/**
	 * Will start the shutdown listener as well as the connection loop in separate threads. Client code must
	 * initialize itself to be ready to handle requests before calling this method.
	 *
	 * @param configuration               Configuration store
	 * @param connectionProcessorFactory  Factory to build the client code to handle all kinds of requests
	 * @param commonShutdownSocketFactory Factory to create client shutdown code that is executed (can do additional
	 *                                    checks before actually shutting down by setting the shutdown flag).
	 * @throws Exception There are plenty of socket and TLS related exceptions that can occur.
	 */
	void initForTLSServer(Configuration configuration, CommonConnectionProcessorFactory connectionProcessorFactory,
						  CommonShutdownSocketFactory commonShutdownSocketFactory)
	throws Exception;
}
