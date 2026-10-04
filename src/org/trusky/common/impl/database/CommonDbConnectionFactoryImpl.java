package org.trusky.common.impl.database;

import org.trusky.common.api.database.CommonDbConnection;
import org.trusky.common.api.database.CommonDbConnectionFactory;
import org.trusky.common.api.database.ECommonDatabaseType;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class CommonDbConnectionFactoryImpl implements CommonDbConnectionFactory {

	@Override
	public CommonDbConnection createConnection(ECommonDatabaseType dbType, String connectionString, String dbUser,
											   String dbPassword) {

		try {
			// Ensure driver can be loaded
			Class.forName(dbType.getDriverName());
		} catch (ClassNotFoundException e) {

			// FIXME Aussagefähige Meldung in's Log schreiben: Treiber für xyz nicht da
			return null;
		}

		Connection con = null;
		try {
			con = DriverManager.getConnection(connectionString, dbUser, dbPassword);
		} catch (SQLException e) {

			// FIXME Aussagefähige Meldung in's Log schreiben: Verbindung zu ... nicht möglich
			return null;
		}

		return (con == null) ? null : new CommonDbConnectionImpl(con);
	}

}
