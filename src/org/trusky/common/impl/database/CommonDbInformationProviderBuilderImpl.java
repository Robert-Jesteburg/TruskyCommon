package org.trusky.common.impl.database;

import org.trusky.common.api.database.CommonDbInformationProvider;
import org.trusky.common.api.database.CommonDbInformationProviderBuilder;

public class CommonDbInformationProviderBuilderImpl implements CommonDbInformationProviderBuilder {

	private String host;
	private int port;
	private String databaseName;

	@Override
	public CommonDbInformationProviderBuilder setHost(String host) {
		this.host = host;
		return this;
	}

	@Override
	public CommonDbInformationProviderBuilder setPort(int port) {
		this.port = port;
		return this;
	}

	@Override
	public CommonDbInformationProviderBuilder setDatabaseName(String databaseName) {
		this.databaseName = databaseName;
		return this;
	}

	@Override
	public CommonDbInformationProvider build() {
		if (host == null || host.isBlank()) {
			throw new IllegalStateException("host must be set");
		}
		if (port < 1 || port > 65535) {
			throw new IllegalStateException("port must be between 1 and 65535");
		}
		if (databaseName == null || databaseName.isBlank()) {
			throw new IllegalStateException("databaseName must be set");
		}
		return new CommonDbInformationProviderImpl(host, port, databaseName);
	}
}
