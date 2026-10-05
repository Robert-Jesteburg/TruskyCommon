package org.trusky.common.impl.database;

import org.junit.jupiter.api.Test;
import org.trusky.common.api.database.CommonDbInformationProvider;
import org.trusky.common.api.database.CommonDbInformationProviderBuilder;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

class CommonDbInformationProviderImplTest {

	@Test
	void builderCreatesProviderWithConfiguredDatabaseInformation() {
		CommonDbInformationProvider provider = new CommonDbInformationProviderBuilderImpl()
				.setHost("localhost")
				.setPort(3306)
				.setDatabaseName("trusky")
				.build();

		assertAll(
				() -> assertThat(provider.getHost()).isEqualTo("localhost"),
				() -> assertThat(provider.getPort()).isEqualTo(3306),
				() -> assertThat(provider.getDatabaseName()).isEqualTo("trusky")
		);
	}

	@Test
	void buildCreatesIndependentProviderSnapshots() {
		CommonDbInformationProviderBuilder builder = new CommonDbInformationProviderBuilderImpl()
				.setHost("localhost")
				.setPort(3306)
				.setDatabaseName("trusky");

		CommonDbInformationProvider first = builder.build();
		CommonDbInformationProvider second = builder.setDatabaseName("other").build();

		assertThat(first.getDatabaseName()).isEqualTo("trusky");
		assertThat(second.getDatabaseName()).isEqualTo("other");
	}

	@Test
	void buildRejectsMissingConnectionInformation() {
		assertThatThrownBy(() -> new CommonDbInformationProviderBuilderImpl().build())
				.isInstanceOf(IllegalStateException.class)
				.hasMessage("host must be set");
	}
}
