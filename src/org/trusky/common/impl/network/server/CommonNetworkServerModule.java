package org.trusky.common.impl.network.server;

import com.google.inject.AbstractModule;
import org.trusky.common.api.network.server.CommonSSLServerAcceptLoopFactory;
import org.trusky.common.api.network.server.CommonSSLServerSocketFactory;
import org.trusky.common.api.network.server.CommonSSLServerSocketFactoryFactory;
import org.trusky.common.api.network.server.RunnableExecutor;

public class CommonNetworkServerModule extends AbstractModule {
	@Override
	protected void configure() {
		super.configure();

		bind(CommonSSLServerSocketFactory.class).to(CommonSSLServerSocketFactoryImpl.class);
		bind(CommonSSLServerSocketFactoryFactory.class).to(CommonSSLServerSocketFactoryFactoryImpl.class);
		bind(CommonSSLServerAcceptLoopFactory.class).to(CommonSSLServerAcceptLoopFactoryImpl.class);
		bind(RunnableExecutor.class).to(ThreadRunnableExecutor.class);
	}
}


