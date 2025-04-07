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

import com.google.inject.AbstractModule;
import org.trusky.common.api.injection.InjectorFactory;
import org.trusky.common.api.startparameters.StartOption;
import org.trusky.common.api.startparameters.builder.BaseDirOptionParserBuilder;
import org.trusky.common.api.startparameters.builder.Log4JOptionParserBuilder;
import org.trusky.common.api.startparameters.builder.OptionParserBuilder;
import org.trusky.common.api.startparameters.builder.ParamDirNameOptionParserBuilder;
import org.trusky.common.api.startparameters.exceptions.StartParameterException;
import org.trusky.common.api.startparameters.optionvalue.OptionValue;
import org.trusky.common.api.startparameters.optionvalue.StringOptionValue;
import org.trusky.common.api.util.CommonFileUtilities;
import org.trusky.common.api.util.CommonLog4JConfigurationUtils;
import org.trusky.common.api.util.CommonStartparametersUtils;
import org.trusky.common.api.util.CommonSystemSettings;
import org.trusky.common.impl.application.StartparameterManager;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.util.*;

public abstract class AbstractApplication implements CommonApplication {

	private final StartparameterManager startparameterManager;
	private final CommonSystemSettings commonSystemSettings;
	private final CommonStartparametersUtils commonStartparametersUtils;
	private final CommonLog4JConfigurationUtils commonLog4JConfigurationUtils;
	private final CommonFileUtilities commonFileUtilities;
	/**
	 * Pointer to the one and only instance.
	 */
	private AbstractApplication thiz;

	protected AbstractApplication(CommonFileUtilities commonFileUtilities) {


		/*
		 * As the start parameter manager is an internal class the derived class can't create an instance. Thus, the
		 * instance is created manually (without the use of @Inject).
		 */
		this.startparameterManager = InjectorFactory.getInstance(StartparameterManager.class);
		this.commonSystemSettings = InjectorFactory.getInstance(CommonSystemSettings.class);
		this.commonStartparametersUtils = InjectorFactory.getInstance(CommonStartparametersUtils.class);
		this.commonLog4JConfigurationUtils = InjectorFactory.getInstance(CommonLog4JConfigurationUtils.class);
		this.commonFileUtilities = InjectorFactory.getInstance(CommonFileUtilities.class);
	}

	/**
	 * This client method ist responsible for creating the application object.
	 * <p>
	 * As the abstract application object uses injection the first thing to do for the concrete class is to configure
	 * the injection framework by
	 * calling {@link org.trusky.common.api.injection.InjectorFactory#setModule(AbstractModule)}. The module can be
	 * constructed the same way as {@link org.trusky.common.api.injection.CommonGuiceModule}; the first thing to do
	 * after the call to super should be <code>install(new CommonGuiceModule());</code>. After that further modules
	 * and/or bind commands can be emitted.<br/>
	 * If the application itself does not use injection the injector can be initialized by simply call
	 * <code>InjectorFactory.setModule(new CommonGuiceModule);</code>
	 * </p>
	 *
	 * <p>
	 * After setting up injection simply create an instance of your class.
	 * </p>
	 *
	 * @return the instance of the Application object
	 */
	abstract AbstractApplication createApplicationObject();

	public void main(String[] args) throws IOException {

		thiz = createApplicationObject();
		internalMain(args);

	}

	protected void internalMain(String[] args) throws IOException {

		List<OptionParserBuilder> optionParserBuilders = prepareStartParameters();

		// FIXME If no Log4J logging parser builder is present, create and add one.
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

	private String computeParamDirName(List<OptionParserBuilder> optionParserBuilders) throws IllegalArgumentException {

		List<OptionParserBuilder> paramDirBuilderList = optionParserBuilders.stream()
				.filter(builder -> (builder instanceof ParamDirNameOptionParserBuilder))
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

	private String computeBaseDirOptionName(List<OptionParserBuilder> optionParserBuilders)
	throws IllegalArgumentException {

		List<OptionParserBuilder> baseDirBuilders = optionParserBuilders.stream()
				.filter(builder -> (builder instanceof BaseDirOptionParserBuilder))
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

	private String computeLog4JOptionName(List<OptionParserBuilder> optionParserBuilders)
	throws IllegalArgumentException {

		List<OptionParserBuilder> log4JBuilders = optionParserBuilders.stream()
				.filter(builder -> (builder instanceof Log4JOptionParserBuilder))
				.toList();

		if (log4JBuilders.size() != 1) {
			final String errString = "Please supply only one parser builder per type (here: Log4J).";
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
	private void prepareLoggingConfigurationIfNotPresent(List<OptionParserBuilder> optionParserBuilders)
	throws IllegalArgumentException {

		List<OptionParserBuilder> log4JBuilders = optionParserBuilders.stream()
				.filter(builder -> (builder instanceof Log4JOptionParserBuilder))
				.toList();

		if (log4JBuilders.size() == 1) {
			return;
		} else if (log4JBuilders.size() > 1) {

			final String errString = "Please supply only one parser builder per type (here: Log4J).";
			System.err.println(errString);
			throw new IllegalArgumentException(errString);

		} else {

			Log4JOptionParserBuilder log4JOptionParserBuilder =
					InjectorFactory.getInstance(Log4JOptionParserBuilder.class);

			Path path = Path.of("~/log4J.xml");
			log4JOptionParserBuilder.setDefaultParameterValue(path.toAbsolutePath()
					.toString());

			optionParserBuilders.add(log4JOptionParserBuilder);
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
				o -> toStringOptionList(startparameterManager.getOption(o)));


		// Check that the configuration file is present (create it, if not)
		File out = new File(fullConfigurationFileNameWithPath);
		if (!out.exists()) {
			Map<String, String> replacementMap = new HashMap<>();
			replacementMap.put( //
					"##BASE_PATH##", //
					commonLog4JConfigurationUtils.getLog4JBasePath( //
							baseDirOptionName, //
							appDirOptionName, //
							o -> toStringOptionList(startparameterManager.getOption(o))) //
							  );

			commonFileUtilities.createFileContentsFromTemplate( //
					getLog4JConfigFileTemplate(), //
					replacementMap, //
					new MyFileWriter(fullConfigurationFileNameWithPath) //
															  );
		}

		System.setProperty("log4j2.configurationFile", fullConfigurationFileNameWithPath);
	}

	/**
	 * Convert a list of OptionValue<?,?> to a list containing StringOptionValues.
	 *
	 * @param list A list of untyped OptionValues. Any of the element of this list MUST be a StringOptionValue, else
	 *             an exception will be thrown.
	 * @return The converted list
	 * @throws IllegalArgumentException if any of the elements in the incoming list is not a StringOptionValue
	 */
	private List<StringOptionValue> toStringOptionList(List<StartOption<? extends OptionValue<?>, ?
			extends OptionValue<?>>> list)
	throws IllegalArgumentException {

		List<StringOptionValue> returnList = new ArrayList<>();


		for (StartOption<? extends OptionValue<?>, ? extends OptionValue<?>> startOption : list) {

			Optional<? extends OptionValue<?>> defaultValue = startOption.getDefaultValue();
			if (defaultValue.isEmpty()) {
				returnList.add(new StringOptionValue());

			} else {
				if (!(defaultValue.get() instanceof StringOptionValue)) {
					throw new IllegalArgumentException("Der Wert in der Liste ist kein StringOptionValue.");
				}

				returnList.add((StringOptionValue) defaultValue.get());
			}
		}

		return returnList;

	}


	private String computeLog4JConfigurationFileLocation(String baseDirOptionName, String appDirOptionName,
														 String log4JFileNameOptionName) {

		final String defaultBaseDir = Path.of(commonSystemSettings.getHomeDir())
				.toAbsolutePath()
				.toString();
		final String defaultAppDir = "";
		final String defaultConfigFile = "log4J.properties";


		StringBuilder sbAbsolutePath = new StringBuilder();
		if (baseDirOptionName != null && !baseDirOptionName.isBlank()) {

			List<StartOption<? extends OptionValue<?>, ? extends OptionValue<?>>> baseDirSettings =
					startparameterManager.getOption(baseDirOptionName);

			if (!baseDirSettings.isEmpty()) {

				if (baseDirSettings.size() > 1) {
					throw new IllegalArgumentException("More than one base directory given.");
				}

				Optional<String> optBaseDir = getOptionValueAsStringOptionValue(baseDirSettings.get(0)
						.getDefaultValue()).getValue();

				if (optBaseDir.isPresent()) {

					String base = optBaseDir.get();
					sbAbsolutePath.append(base);
					if (!base.endsWith(commonSystemSettings.getPathSeparator())) {
						sbAbsolutePath.append(commonSystemSettings.getPathSeparator());
					}

				} else {
					sbAbsolutePath.append(defaultBaseDir);
					if (!defaultBaseDir.endsWith(commonSystemSettings.getPathSeparator())) {
						sbAbsolutePath.append(commonSystemSettings.getPathSeparator());
					}
				}

			} else {

				sbAbsolutePath.append(defaultBaseDir);
				if (!defaultBaseDir.endsWith(commonSystemSettings.getPathSeparator())) {
					sbAbsolutePath.append(commonSystemSettings.getPathSeparator());
				}

			}

		} else {
			String basePath = Path.of("./")
					.toString();
			sbAbsolutePath.append(basePath);

			if (!basePath.endsWith(commonSystemSettings.getPathSeparator())) {
				sbAbsolutePath.append(commonSystemSettings.getPathSeparator());
			}

		}

		if (appDirOptionName != null && !appDirOptionName.isBlank()) {

			List<StartOption<? extends OptionValue<?>, ? extends OptionValue<?>>> paramDirSettings =
					startparameterManager.getOption(appDirOptionName);

		}

		return ""; // FIXME korrekt implememntieren
	}

	@SuppressWarnings("unchecked")
	private OptionValue<String> getOptionValueAsStringOptionValue(Optional<? extends OptionValue<?>> optValue) {
		return (OptionValue<String>) optValue.get();
	}

	/**
	 * Configure expected parameters by supplying a list of parser builders.
	 *
	 * @return The list of OptionParserBuilders. It may be empty but must not be NULL.
	 */
	protected abstract List<OptionParserBuilder> prepareStartParameters();

	private class MyFileWriter implements CommonFileUtilities.Persistor {

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
