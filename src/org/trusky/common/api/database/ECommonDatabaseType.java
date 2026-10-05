package org.trusky.common.api.database;

public enum ECommonDatabaseType {

	MYSQL("com.mysql.jdbc.Driver", "mysql");

	private final String driverName;
	private final String driverType;

	ECommonDatabaseType(String driverName, String type) {
		this.driverName = driverName;
		this.driverType = type;
	}

	public String getDriverName() {
		return driverName;
	}

	public String getDriverType() {
		return driverType;
	}
}
