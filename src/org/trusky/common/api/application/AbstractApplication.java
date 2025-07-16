/*
 * Copyright (c) 2025 by Robert Niemann, Rehkamp 21a; 12266 Jesteburg, Germany.
 * Diese Software unterliegt der GPL v3 vorbehaltlich jedweder böswilliger Veränderungen, eingeschlossen aber nicht
 * abschließend:
 * a) Ausspionieren von Gesundheitsdaten (bspw. durch Weiterleiten dieser Informationen oder Auswertung der Daten auf
 *  bereitgestellten Servern)
 * b) Ausspionieren von Beziehungen zwischen Personen oder deren zeitliche Verläufe
 * c) Irreführung durch Angabe bewusst falscher Daten
 */

package org.trusky.common.api.application;

import org.trusky.common.api.injection.InjectorFactory;
import org.trusky.common.api.logging.CommonLogger;
import org.trusky.common.api.logging.CommonLoggerFactory;
import org.trusky.common.api.startparameters.builder.Log4JOptionParserBuilder;
import org.trusky.common.api.startparameters.exceptions.StartParameterException;
import org.trusky.common.api.startparameters.parser.BaseDirOptionParser;
import org.trusky.common.api.startparameters.parser.Log4JOptionParser;
import org.trusky.common.api.startparameters.parser.ParamDirNameOptionParser;
import org.trusky.common.api.startparameters.parser.StartOptionParser;
import org.trusky.common.api.util.CommonFileUtilities;
import org.trusky.common.api.util.CommonLog4JConfigurationUtils;
import org.trusky.common.api.util.CommonStartparametersUtils;
import org.trusky.common.impl.application.StartparameterManager;
import org.trusky.common.impl.logging.CommonLoggerImpl;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public abstract class AbstractApplication implements CommonApplication {

	/**
	 * Pointer to the one and only instance.
	 */
	private static AbstractApplication thiz;
	private final StartparameterManager startparameterManager;
	private final CommonStartparametersUtils commonStartparametersUtils;
	private final CommonLog4JConfigurationUtils commonLog4JConfigurationUtils;
	private final CommonFileUtilities commonFileUtilities;
	private final CommonLogger logger;

	protected AbstractApplication() {


		/*
		 * As the start parameter manager is an internal class the derived class can't create an instance. Thus, the
		 * instance is created manually (without the use of @Inject).
		 */
		this.startparameterManager = InjectorFactory.getInstance(StartparameterManager.class);
		this.commonStartparametersUtils = InjectorFactory.getInstance(CommonStartparametersUtils.class);
		this.commonLog4JConfigurationUtils = InjectorFactory.getInstance(CommonLog4JConfigurationUtils.class);
		this.commonFileUtilities = InjectorFactory.getInstance(CommonFileUtilities.class);

		CommonLoggerFactory loggerFactory = InjectorFactory.getInstance(CommonLoggerFactory.class);
		this.logger = loggerFactory.getLogger(this.getClass());

		thiz = this;
	}


	protected void prepareStartparametersAndLogging(String[] args, List<StartOptionParser> optionParserBuilders)
	throws IOException {


		/*
		 * This includes setting the base dir as well as the config dir and supplying the logging configuration.
		 * Probably by using a predefines logger xml in this jar???
		 */
		prepareLoggingConfigurationIfNotPresent(optionParserBuilders);

		String log4JOptionName = computeLog4JOptionName(optionParserBuilders);
		String baseDirOptionName = computeBaseDirOptionName(optionParserBuilders);
		String appDirOptionName = computeParamDirName(optionParserBuilders);

		startparameterManager.prepareParameters(optionParserBuilders);
		try {
			startparameterManager.parseCommandLine(args);
		} catch (StartParameterException e) {


			throw new RuntimeException(e);
		}

		setupLogging(baseDirOptionName, appDirOptionName, log4JOptionName);
	}

	protected Function<String, String> getOptionFromNameFunction() {
		return o -> commonStartparametersUtils.getStringStartparameterWithDefault( //
				o, //
				name -> commonStartparametersUtils.toStringOptionList(startparameterManager.getOption(name)), //
				"");
	}

	/**
	 * Derived classes may overwrite this method to have their own configuration template file delivered. These files
	 * should contain the configurations made in log4J-template.xml file of this package (that is, the specific
	 * template
	 * should extend that file with additional lines).
	 *
	 * @return InputStream of the file.
	 */
	protected InputStream getLog4JConfigFileTemplate() {
		return getClass().getResourceAsStream("log4J-template.xml");
	}

	private String computeParamDirName(List<StartOptionParser> optionParserBuilders) throws IllegalArgumentException {

		List<StartOptionParser> paramDirBuilderList = optionParserBuilders.stream()
				.filter(builder -> (builder instanceof ParamDirNameOptionParser))
				.toList();

		if (paramDirBuilderList.isEmpty()) {
			return null;
		}

		if (paramDirBuilderList.size() > 1) {

			final String errString = "Please supply only one parser builder per type (here: paramDir).";
			throw new IllegalArgumentException(errString);
		}

		return paramDirBuilderList.get(0)
				.getOptionName();
	}

	private String computeBaseDirOptionName(List<StartOptionParser> optionParserBuilders)
	throws IllegalArgumentException {

		List<StartOptionParser> baseDirBuilders = optionParserBuilders.stream()
				.filter(builder -> (builder instanceof BaseDirOptionParser))
				.toList();

		if (baseDirBuilders.isEmpty()) {
			return null;
		}

		if (baseDirBuilders.size() > 1) {

			final String errString = "Please supply only one parser builder per type (here: baseDir).";
			throw new IllegalArgumentException(errString);
		}

		return baseDirBuilders.get(0)
				.getOptionName();
	}

	private String computeLog4JOptionName(List<StartOptionParser> optionParserBuilders)
	throws IllegalArgumentException {

		List<StartOptionParser> log4JBuilders = optionParserBuilders.stream()
				.filter(builder -> (builder instanceof Log4JOptionParser))
				.toList();

		if (log4JBuilders.size() != 1) {
			final String errString = "Please supply exactly one parser builder per type (here: Log4J).";
			throw new IllegalArgumentException(errString);
		}

		return log4JBuilders.get(0)
				.getOptionName();

	}

	/**
	 * Ensures the presence of a Log4J parser builder. Will add a parser builder to the list given if jo log4J
	 * builder is preset. In that case the file is assumed in the users home directory, named "log4J-template.xml".
	 *
	 * @param optionParserBuilders List of parser builders. Method may <b>MODIFIES</b> the list by adding a parser
	 *                             builder.
	 * @throws IllegalArgumentException if there are two or more builders for log4J.
	 */
	private void prepareLoggingConfigurationIfNotPresent(List<StartOptionParser> optionParserBuilders)
	throws IllegalArgumentException {

		List<StartOptionParser> log4JBuilders = optionParserBuilders.stream()
				.filter(builder -> (builder instanceof Log4JOptionParser))
				.toList();

		if (log4JBuilders.size() > 1) {

			final String errString = "Please supply only one parser builder per type (here: Log4J).";
			System.err.println(errString);
			throw new IllegalArgumentException(errString);

		} else if (log4JBuilders.isEmpty()) {

			Log4JOptionParserBuilder log4JOptionParserBuilder =
					InjectorFactory.getInstance(Log4JOptionParserBuilder.class);

			String expectedLocation = "~/" + Log4JOptionParserBuilder.DEFAULT_LOG4J_CONFIGURATION_NAME;
			Path path = Path.of(expectedLocation);
			log4JOptionParserBuilder.setDefaultParameterValue(path.toAbsolutePath()
					.toString());

			optionParserBuilders.add(log4JOptionParserBuilder.build());
		}

	}


	/**
	 * Sets up the logging from the values given in the start parameters.
	 */
	private void setupLogging(String baseDirOptionName, String appDirOptionName, String log4JOptionName)
	throws IOException {

		/*
		 * Scan the parser builder list in internal main and ask the correct option names. Supply this method with
		 * the correct option names, so it can ask for the values specified.
		 */

		String fullConfigurationFileNameWithPath =
				commonLog4JConfigurationUtils.getFullConfigurationFileNameWithPath( //
				baseDirOptionName, //
				appDirOptionName, //
				log4JOptionName, //
				o -> commonStartparametersUtils.toStringOptionList(startparameterManager.getOption(o)));


		// Check that the configuration file is present (create it, if not)
		File out = new File(fullConfigurationFileNameWithPath);
		if (!out.exists()) {
			Map<String, String> replacementMap = new HashMap<>();
			replacementMap.put( //
					"##BASE_PATH##", //
					commonLog4JConfigurationUtils.getLog4JBasePath( //
							baseDirOptionName, //
							appDirOptionName, //
							o -> commonStartparametersUtils.toStringOptionList(startparameterManager.getOption(o))) //
							  );

			commonFileUtilities.createFileContentsFromTemplate( //
					getLog4JConfigFileTemplate(), //
					replacementMap, //
					new MyFileWriter(fullConfigurationFileNameWithPath) //
															  );
		}

		System.setProperty("log4j2.configurationFile", fullConfigurationFileNameWithPath);
		logger.debug("Logging configuration file is {}.", fullConfigurationFileNameWithPath);
		CommonLoggerImpl.setIsLoggingPrepared(true);
	}

	private static class MyFileWriter implements CommonFileUtilities.Persistor {

		private final FileWriter fileWriter;

		MyFileWriter(String fullFileName) throws IOException {
			fileWriter = new FileWriter(fullFileName);
		}

		@Override
		public void write(String line) throws IOException {
			fileWriter.write(line);
		}

		@Override
		public void flush() throws IOException {
			fileWriter.flush();
		}

		@Override
		public void close() throws IOException {
			fileWriter.close();
		}


	}


}
