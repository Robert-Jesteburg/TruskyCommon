package org.trusky.common.api.database;

import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;

public interface CommonDbConnection {

	CommonPreparedStatement prepareStatement(String s) throws SQLException;

	/**
	 * Creates a default {@code PreparedStatement} object capable
	 * of returning the auto-generated keys designated by the given array.
	 * This array contains the names of the columns in the target
	 * table that contain the auto-generated keys that should be returned.
	 * The driver will ignore the array if the SQL statement
	 * is not an {@code INSERT} statement, or an SQL statement able to return
	 * auto-generated keys (the list of such statements is vendor-specific).
	 * <p>
	 * An SQL statement with or without IN parameters can be
	 * pre-compiled and stored in a {@code PreparedStatement} object. This
	 * object can then be used to efficiently execute this statement
	 * multiple times.
	 * <p>
	 * <B>Note:</B> This method is optimized for handling
	 * parametric SQL statements that benefit from precompilation. If
	 * the driver supports precompilation,
	 * the method {@code prepareStatement} will send
	 * the statement to the database for precompilation. Some drivers
	 * may not support precompilation. In this case, the statement may
	 * not be sent to the database until the {@code PreparedStatement}
	 * object is executed.  This has no direct effect on users; however, it does
	 * affect which methods throw certain SQLExceptions.
	 * <p>
	 * Result sets created using the returned {@code PreparedStatement}
	 * object will by default be type {@code TYPE_FORWARD_ONLY}
	 * and have a concurrency level of {@code CONCUR_READ_ONLY}.
	 * The holdability of the created result sets can be determined by
	 * calling {@link #getHoldability}.
	 *
	 * @param sql         an SQL statement that may contain one or more '?' IN
	 *                    parameter placeholders
	 * @param columnNames an array of column names indicating the columns
	 *                    that should be returned from the inserted row or rows
	 * @return a new {@code PreparedStatement} object, containing the
	 * pre-compiled statement, that is capable of returning the
	 * auto-generated keys designated by the given array of column
	 * names
	 * @throws SQLException                    if a database access error occurs
	 *                                         or this method is called on a closed connection
	 * @throws SQLFeatureNotSupportedException if the JDBC driver does not support
	 *                                         this method
	 * @since 1.4
	 */
	CommonPreparedStatement prepareStatement(String sql, String columnNames[]) throws SQLException;
}
