package org.trusky.common.api.database;

public enum ECommonDatabaseType {

	MYSQL("com.mysql.jdbc.Driver");

	private final String driverName;

	ECommonDatabaseType(String driverName) {
		this.driverName = driverName;
	}

	public String getDriverName() {
		return driverName;
	}
}
