open module org.trusky.common {

	requires java.base;
	requires com.google.guice;
	requires javafx.graphics;
	requires javax.inject;
	requires mockito.all;
	requires com.google.common;

	exports org.trusky.common.api.application;
	exports org.trusky.common.api.injection;
	exports org.trusky.common.api.startparameters;
	exports org.trusky.common.api.startparameters.builder;
	exports org.trusky.common.api.startparameters.builder.module;
	exports org.trusky.common.api.startparameters.exceptions;
	exports org.trusky.common.api.startparameters.module;
	exports org.trusky.common.api.startparameters.optionvalue;
	exports org.trusky.common.api.startparameters.parser;
	// exports org.trusky.common.api.startparameters.util; // Currently no classes inside it
	exports org.trusky.common.api.util;

}