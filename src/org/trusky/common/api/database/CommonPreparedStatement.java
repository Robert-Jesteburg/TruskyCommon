package org.trusky.common.api.database;

import java.sql.SQLException;

public interface CommonPreparedStatement {

	void setString(int index, String value) throws SQLException;

	void setInt(int index, int value) throws SQLException;

	CommonResultSet executeQuery() throws SQLException;
}
