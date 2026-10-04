package org.trusky.common.impl.database;

import org.trusky.common.api.database.CommonPreparedStatement;
import org.trusky.common.api.database.CommonResultSet;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CommonPreparedStatementImpl implements CommonPreparedStatement {

	private final PreparedStatement preparedStatement;

	public CommonPreparedStatementImpl(PreparedStatement preparedStatement) {
		this.preparedStatement = preparedStatement;
	}

	@Override
	public void setString(int index, String value) throws SQLException {
		preparedStatement.setString(index, value);
	}

	@Override
	public void setInt(int index, int value) throws SQLException {
		preparedStatement.setInt(index, value);
	}

	@Override
	public CommonResultSet executeQuery() throws SQLException {
		ResultSet resultSet = preparedStatement.executeQuery();
		return new CommonResultSetImpl(resultSet);
	}
}
