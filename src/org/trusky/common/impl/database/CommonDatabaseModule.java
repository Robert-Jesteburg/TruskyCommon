package org.trusky.common.impl.database;

import com.google.inject.AbstractModule;
import org.trusky.common.api.database.CommonDbConnectionStringFactory;
import org.trusky.common.api.database.CommonDbInformationProviderBuilder;

public class CommonDatabaseModule extends AbstractModule {

	@Override
	protected void configure() {
		bind(CommonDbConnectionStringFactory.class).to(CommonDbConnectionStringFactoryImpl.class);
		bind(CommonDbInformationProviderBuilder.class).to(CommonDbInformationProviderBuilderImpl.class);
	}
}
