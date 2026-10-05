package org.trusky.common.api.database;

public interface CommonDbConnectionStringFactory {

	String createConnectionString(ECommonDatabaseType databaseType,
								  CommonDbInformationProvider dbInformationProvider);
}
