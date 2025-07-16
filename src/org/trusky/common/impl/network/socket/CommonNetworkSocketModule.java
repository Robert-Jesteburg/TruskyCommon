package org.trusky.common.impl.network.socket;

import com.google.inject.AbstractModule;
import org.trusky.common.impl.network.socket.factory.CommonNetworkFactoryModule;

public class CommonNetworkSocketModule extends AbstractModule {

	@Override
	protected void configure() {
		super.configure();

		install(new CommonNetworkFactoryModule());

	}
}
