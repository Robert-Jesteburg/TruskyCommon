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
import org.trusky.common.api.util.CommonStartparametersUtils;
import org.trusky.common.api.util.CommonSystemSettings;
import org.trusky.common.impl.application.StartparameterManager;

import java.nio.file.Path;
import java.util.List;
import java.util.Optional;

public abstract class AbstractApplication implements CommonApplication {

	private final StartparameterManager startparameterManager;
	private final CommonSystemSettings commonSystemSettings;
	private final CommonStartparametersUtils commonStartparametersUtils;
	/**
	 * Pointer to the one and only instance.
	 */
	private AbstractApplication thiz;

	protected AbstractApplication() {

		/*
		 * As the start parameter manager is an internal class the derived class can't create an instance. Thus, the
		 * instance is created manually (without the use of @Inject).
		 */
		this.startparameterManager = InjectorFactory.getInstance(StartparameterManager.class);
		this.commonSystemSettings = InjectorFactory.getInstance(CommonSystemSettings.class);
		this.commonStartparametersUtils = InjectorFactory.getInstance(CommonStartparametersUtils.class);
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

	public void main(String[] args) {

		thiz = createApplicationObject();
		internalMain(args);

	}

	protected void internalMain(String[] args) {

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
	 * builder is preset. In that case the file is assumed in the users home directory, named "log4J.xml".
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
	private void setupLogging(String baseDirOptionName, String appDirOptionName, String log4JOptionName) {

		/*
		 * Scan the parser builder list in internal main and ask the correct option names. Supply this method with
		 * the correct option names, so it can ask for the values specified.
		 */

		// Get log4J file name
		List<StartOption<? extends OptionValue<?>, ? extends OptionValue<?>>> log4JOptionList =
				startparameterManager.getOption(log4JOptionName);
		if (log4JOptionList.size() != 1) {
			throw new IllegalArgumentException("Log4J option missing or specified more than once.");
		}

		Optional<? extends OptionValue<?>> optConfigFileName = log4JOptionList.get(0)
				.getDefaultValue();
		if (optConfigFileName.isEmpty()) {
			throw new IllegalArgumentException("Log4J configuration file wasn't specified.");
		}

		OptionValue<String> fileNameOptionValue = getOptionValueAsStringOptionValue(optConfigFileName);
		if (fileNameOptionValue.isEmpty()) {

			System.err.println("WARN: Configuration file name for Log4J not given. Logging wil not be configured.");
			return;
		}

		Optional<String> optLog4JFileName = fileNameOptionValue.getValue();
		String log4JFileName = "";

		/*
		 * The value is always present (because this is already checked in the call of fileNameOptionValue). As the
		 * code analysis does not know about that it would emit a warning about accessing an optional without
		 * previous call to isPresent(). To prevent this warning the unnecessary check to optLog4JFileName.isPresent()
		 * is done below.
		 */
		if (optLog4JFileName.isPresent()) {
			log4JFileName = optLog4JFileName.get();
		}

		if (log4JFileName.isBlank()) {
			System.err.println("WARN: Empty configuration file name given. Logging will not be configured.");
		}

		// FIXME Build full file name
		String absoluteLog4JConfigurationFilenameWithPath = //
				computeLog4JConfigurationFileLocation( //
						baseDirOptionName, //
						appDirOptionName, //
						log4JFileName //;
													 );

		// FIXME Check that the configuration file is present (create it, if not)


		// FIXME setup Log4J
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


}
