package org.trusky.common.api.database;

/**
 *
 */
public interface CommonDbConnectionFactory {

	CommonDbConnection createConnection( //
										 ECommonDatabaseType dbType, //
										 String connectionString, //
										 String dbUser, //
										 String dbPassword //
									   );
}
