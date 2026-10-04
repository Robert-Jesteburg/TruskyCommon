package org.trusky.common.impl.database;

import org.trusky.common.api.database.CommonDbConnection;
import org.trusky.common.api.database.CommonPreparedStatement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CommonDbConnectionImpl implements CommonDbConnection {

	private final Connection connection;

	CommonDbConnectionImpl(Connection connection) {
		this.connection = connection;
	}

	/**
	 *
	 * @param s the SQL command that may contains '?' for placeholders. These placeholders can be substituted at the
	 *          statement level by using setString(int index, String actualValue) or similar methods
	 * @return A prepared statement
	 * @throws SQLException
	 */
	@Override
	public CommonPreparedStatement prepareStatement(String s) throws SQLException {

		PreparedStatement preparedStatement = connection.prepareStatement(s);
		return new CommonPreparedStatementImpl(preparedStatement);

	}

	@Override
	public CommonPreparedStatement prepareStatement(String sql, String columnNames[]) throws SQLException {
		
		PreparedStatement preparedStatement = connection.prepareStatement(sql, columnNames);
		return new CommonPreparedStatementImpl(preparedStatement);
	}
}
