package org.trusky.common.impl.network.server;

import com.google.inject.AbstractModule;
import org.trusky.common.api.network.server.CommonSSLServerSocketFactory;

public class CommonNetworkServerModule extends AbstractModule {
	@Override
	protected void configure() {
		super.configure();

		bind(CommonSSLServerSocketFactory.class).to(CommonSSLServerSocketFactoryImpl.class);
	}
}
