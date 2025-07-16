package org.trusky.common.impl.network;

import com.google.inject.AbstractModule;
import org.trusky.common.api.network.implementators.CommonNetworkSocketHandler;
import org.trusky.common.impl.network.server.CommonNetworkServerModule;
import org.trusky.common.impl.network.socket.CommonNetworkSocketModule;

public class CommonNetworkModule extends AbstractModule {
	@Override
	protected void configure() {
		super.configure();

		install(new CommonNetworkServerModule());
		install(new CommonNetworkSocketModule());

		bind(CommonNetworkSocketHandler.class).to(CommonNetworkSocketHandlerImpl.class);
	}
}
