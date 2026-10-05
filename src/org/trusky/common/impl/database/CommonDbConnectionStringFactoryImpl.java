package org.trusky.common.impl.database;

import org.trusky.common.api.database.CommonDbConnectionStringFactory;
import org.trusky.common.api.database.CommonDbInformationProvider;
import org.trusky.common.api.database.ECommonDatabaseType;

import java.util.Objects;

public class CommonDbConnectionStringFactoryImpl implements CommonDbConnectionStringFactory {

	@Override
	public String createConnectionString(ECommonDatabaseType databaseType,
										 CommonDbInformationProvider dbInformationProvider) {
		Objects.requireNonNull(databaseType, "databaseType must not be null");
		Objects.requireNonNull(dbInformationProvider, "dbInformationProvider must not be null");

		return "jdbc:" + databaseType.getDriverType() + "://" + dbInformationProvider.getHost() + ":"
				+ dbInformationProvider.getPort() + "/" + dbInformationProvider.getDatabaseName();
	}
}
