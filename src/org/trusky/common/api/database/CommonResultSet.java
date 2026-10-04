package org.trusky.common.api.database;

import java.sql.SQLException;

public interface CommonResultSet {
	String getString(String columnName) throws SQLException;

	int getInt(String columnName) throws SQLException;
}
