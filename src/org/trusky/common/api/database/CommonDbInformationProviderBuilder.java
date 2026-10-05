package org.trusky.common.api.database;

public interface CommonDbInformationProviderBuilder {

	CommonDbInformationProviderBuilder setHost(String host);

	CommonDbInformationProviderBuilder setPort(int port);

	CommonDbInformationProviderBuilder setDatabaseName(String databaseName);

	CommonDbInformationProvider build();
}
