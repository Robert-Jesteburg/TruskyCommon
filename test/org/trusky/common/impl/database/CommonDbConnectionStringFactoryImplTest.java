package org.trusky.common.impl.database;

import com.google.inject.Guice;
import com.google.inject.Injector;
import org.junit.jupiter.api.Test;
import org.trusky.common.api.database.CommonDbConnectionStringFactory;
import org.trusky.common.api.database.CommonDbInformationProvider;
import org.trusky.common.api.database.CommonDbInformationProviderBuilder;
import org.trusky.common.api.database.ECommonDatabaseType;

import static org.assertj.core.api.Assertions.assertThat;

class CommonDbConnectionStringFactoryImplTest {

	@Test
	void createsMysqlConnectionString() {
		CommonDbInformationProvider provider = new CommonDbInformationProviderBuilderImpl()
				.setHost("localhost")
				.setPort(3306)
				.setDatabaseName("trusky")
				.build();

		String connectionString = new CommonDbConnectionStringFactoryImpl()
				.createConnectionString(ECommonDatabaseType.MYSQL, provider);

		assertThat(connectionString).isEqualTo("jdbc:mysql://localhost:3306/trusky");
	}

	@Test
	void databaseModuleBindsFactoryAndBuilder() {
		Injector injector = Guice.createInjector(new CommonDatabaseModule());

		assertThat(injector.getInstance(CommonDbConnectionStringFactory.class))
				.isInstanceOf(CommonDbConnectionStringFactoryImpl.class);
		assertThat(injector.getInstance(CommonDbInformationProviderBuilder.class))
				.isInstanceOf(CommonDbInformationProviderBuilderImpl.class);
	}
}
