package org.trusky.common.impl.startparameters.parser;

import org.trusky.common.api.startparameters.builder.AbstractOptionParserBuilder;
import org.trusky.common.api.startparameters.builder.BaseDirOptionParserBuilder;
import org.trusky.common.api.startparameters.parser.BaseDirOptionParser;

import javax.inject.Inject;
import java.nio.file.FileSystems;
import java.util.Optional;

/**
 * The method build() can be called directly without calling the setters, which will result in a standard name of
 * "--baseDir" that will be checked in a case-sensitive manner.
 */
public class BaseDirOptionParserBuilderImpl extends
		AbstractOptionParserBuilder<BaseDirOptionParserBuilder, String> implements BaseDirOptionParserBuilder {


	@Inject
	private BaseDirOptionParserBuilderImpl() {
		super("--baseDir", true, createDefaultParameter());
	}

	private static String createDefaultParameter() {

		// Get current directory
		String currentWorkingDir = System.getProperty("user.home");

		/*
		 * Don't use System.getProperty("file.separator") as this can be overwritten on the command line!
		 */
		String pathDelimiter = FileSystems.getDefault()
				.getSeparator();

		if (!currentWorkingDir.endsWith(pathDelimiter)) {
			currentWorkingDir += pathDelimiter;
		}

		return currentWorkingDir;
	}

	@Override
	public BaseDirOptionParserBuilder setDefaultOptionValue(String defaultOptionValue) {
		return super.setDefaultParameterValue(defaultOptionValue);
	}

	@Override
	public BaseDirOptionParser build() {
		Optional<String> defaultParameterValue = getDefaultParameterValue();
		return defaultParameterValue.isPresent() ? //
				new BaseDirOptionParserImpl(getOptionName(), isCaseSensitive(), defaultParameterValue.get()) : //
				new BaseDirOptionParserImpl(getOptionName(), isCaseSensitive());
	}
}
