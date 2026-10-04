package org.trusky.common.impl.database;

import org.trusky.common.api.database.CommonResultSet;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CommonResultSetImpl implements CommonResultSet {

	private final ResultSet resultSet;

	public CommonResultSetImpl(ResultSet resultSet) {
		this.resultSet = resultSet;
	}

	@Override
	public String getString(String columnName) throws SQLException {
		return resultSet.getString(columnName);
	}

	@Override
	public int getInt(String columnName) throws SQLException {
		return resultSet.getInt(columnName);
	}
}
