package org.trusky.common.impl.configuration;

import com.google.inject.AbstractModule;
import org.trusky.common.api.configuration.CommonConfigurationIntReader;
import org.trusky.common.api.configuration.CommonPropertyFileReader;
import org.trusky.common.api.configuration.CommonCredentialsStdResolver;

public class CommonConfigurationModule extends AbstractModule {

	@Override
	protected void configure() {
		super.configure();

		bind(CommonCredentialsStdResolver.class).to(CommonCredentialsStdResolverImpl.class);
		bind(CommonConfigurationIntReader.class).to(CommonConfigurationIntReaderImpl.class);
		bind(CommonPropertyFileReader.class).to(CommonPropertyFileReaderImpl.class);
	}
}
