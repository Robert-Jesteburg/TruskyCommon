package org.trusky.common.impl.network.socket.factory;

import com.google.inject.AbstractModule;
import org.trusky.common.api.network.socket.factory.CommonKeyStoreFactory;
import org.trusky.common.api.network.socket.factory.CommonKeymanagerFactoryFactory;
import org.trusky.common.api.network.socket.factory.CommonKeystoreParametersFactory;
import org.trusky.common.impl.network.socket.factory.internal.CommonKeystoreCreator;
import org.trusky.common.impl.network.socket.factory.internal.CommonKeystoreCreatorImpl;

public class CommonNetworkFactoryModule extends AbstractModule {

	@Override
	protected void configure() {
		super.configure();

		bind(CommonKeymanagerFactoryFactory.class).to(CommonKeymanagerFactoryFactoryImpl.class);
		bind(CommonKeyStoreFactory.class).to(CommonKeyStoreFactoryImpl.class);
		bind(CommonKeystoreParametersFactory.class).to(CommonKeystoreParametersFactoryImpl.class);

		// Internal package
		bind(CommonKeystoreCreator.class).to(CommonKeystoreCreatorImpl.class);
	}
}
