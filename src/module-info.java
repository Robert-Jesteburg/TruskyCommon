open module org.trusky.common {

	requires com.google.guice;
	requires javafx.graphics;
	requires javax.inject;
	requires mockito.all;
	requires com.google.common;
	requires org.apache.logging.log4j;
	requires org.jetbrains.annotations;

	exports org.trusky.common.api.application;
	exports org.trusky.common.api.injection;
	exports org.trusky.common.api.startparameters;
	exports org.trusky.common.api.startparameters.builder;
	exports org.trusky.common.api.startparameters.builder.module;
	exports org.trusky.common.api.startparameters.exceptions;
	exports org.trusky.common.api.logging;
	exports org.trusky.common.api.startparameters.module;
	exports org.trusky.common.api.startparameters.optionvalue;
	exports org.trusky.common.api.startparameters.parser;
	// exports org.trusky.common.api.startparameters.util; // Currently no classes inside it
	exports org.trusky.common.api.util;
	exports org.trusky.common.api.configuration;

}