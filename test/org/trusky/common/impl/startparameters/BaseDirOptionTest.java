package org.trusky.common.impl.startparameters;

import org.junit.jupiter.api.Test;
import org.trusky.common.api.injection.InjectorFactory;
import org.trusky.common.api.startparameters.builder.BaseDirOptionParserBuilder;
import org.trusky.common.api.startparameters.parser.BaseDirOptionParser;
import org.trusky.common.impl.startparameters.parser.StartparameterParserContainerImpl;

class BaseDirOptionTest {

	@org.junit.jupiter.api.BeforeEach
	void setUp() {
	}

	@org.junit.jupiter.api.AfterEach
	void tearDown() {
	}

	@Test
	void defaultNamesWithoutOptionLeadsToUserHomeDirectory() {

		// GIVEN
		BaseDirOptionParserBuilder builder = InjectorFactory.getInjector()
				.getInstance(BaseDirOptionParserBuilder.class);

		BaseDirOptionParser parser = builder.build();
		StartparameterParserContainerImpl parserContainer = new StartparameterParserContainerImpl();
		parserContainer.addParser(parser);

		// WHEN
		String commandLine = "";

		// THEN


	}
}