/*
 * Copyright (c) 2025 by Robert Niemann, Rehkamp 21a; 12266 Jesteburg, Germany.
 * Diese Software unterliegt der GPL v3 vorbehaltlich jedweder böswilliger Veränderungen, eingeschlossen aber nicht
 * abschließend:
 * a) Ausspionieren von Gesundheitsdaten (bspw. durch Weiterleiten dieser Informationen oder Auswertung der Daten auf
 *  bereitgestellten Servern)
 * b) Ausspionieren von Beziehungen zwischen Personen oder deren zeitliche Verläufe
 * c) Irreführung durch Angabe bewusst falscher Daten
 */

package org.trusky.common.impl.util;

import org.trusky.common.api.startparameters.optionvalue.StringOptionValue;
import org.trusky.common.api.util.CommonLog4JConfigurationUtils;
import org.trusky.common.api.util.CommonStartparametersUtils;
import org.trusky.common.api.util.CommonSystemSettings;

import javax.inject.Inject;
import java.nio.file.Path;
import java.util.List;
import java.util.function.Function;

public class CommonLog4JConfigurationUtilsImpl implements CommonLog4JConfigurationUtils {

	private final CommonStartparametersUtils startparametersUtils;
	private final CommonSystemSettings commonSystemSettings;

	@Inject
	public CommonLog4JConfigurationUtilsImpl(CommonStartparametersUtils startparametersUtils,
											 CommonSystemSettings commonSystemSettings) {
		this.startparametersUtils = startparametersUtils;
		this.commonSystemSettings = commonSystemSettings;
	}

	@Override
	public String getLog4JBasePath(String baseDirOptionName, String appDirOptionName, Function<String,
			List<StringOptionValue>> parameterSupplier) {

		final String defaultBaseDir = Path.of(commonSystemSettings.getHomeDir())
				.toAbsolutePath()
				.toString();
		final String defaultAppDir = "";

		StringBuilder sbAbsolutePath = new StringBuilder();

		String baseDir = extractFromParameters(baseDirOptionName, parameterSupplier, defaultBaseDir);
		if (baseDir.startsWith("~")) {
			String homeDir = stripTrailingString(commonSystemSettings.getHomeDir(),
					commonSystemSettings.getPathSeparator());
			baseDir = baseDir.replace("~", homeDir);
		}


		String appDirName = extractFromParameters(appDirOptionName, parameterSupplier, defaultAppDir);

		if (appDirName.isBlank() || appDirName.equals(commonSystemSettings.getPathSeparator())) {

			if (baseDir.endsWith(commonSystemSettings.getPathSeparator())) {

				// FIXME das hier mit einem Testfall abdecken!
				baseDir = stripTrailingString(baseDir, commonSystemSettings.getPathSeparator());

				if (baseDir.isEmpty()) { // If root dir was specified, use it
					sbAbsolutePath.append(commonSystemSettings.getPathSeparator());
				} else {
					sbAbsolutePath.append(baseDir);
				}


			} else {
				sbAbsolutePath.append(baseDir);
			}

		} else {

			sbAbsolutePath.append(baseDir);
			if (!baseDir.endsWith(commonSystemSettings.getPathSeparator())) {
				sbAbsolutePath.append(commonSystemSettings.getPathSeparator());
			}

			sbAbsolutePath.append(stripTrailingString(appDirName, commonSystemSettings.getPathSeparator()));

		}


		return sbAbsolutePath.toString();
	}

	private String stripTrailingString(String string, String trailingPart) {

		return string.endsWith(trailingPart) ? string.substring(0, string.length() - trailingPart.length()) : string;
	}

	private String extractFromParameters(String optionName,
										 Function<String, List<StringOptionValue>> parameterSupplier,
										 String defaultAppDir) {
		String parameterValue;
		if (optionName != null && !optionName.isBlank()) {

			parameterValue = startparametersUtils.getStringStartparameterWithDefault(optionName, parameterSupplier,
					defaultAppDir);

		} else {
			parameterValue = defaultAppDir;
		}

		return parameterValue;
	}

	@Override
	public String getLog4JConfigurationFileName(String log4JFileNameOptionName, Function<String,
			List<StringOptionValue>> parameterSupplier) {

		final String log4JDefaultFileName = "log4J-template.xml";

		String fileName;

		if (log4JFileNameOptionName != null && !log4JFileNameOptionName.isBlank()) {
			fileName = startparametersUtils.getStringStartparameterWithDefault(log4JFileNameOptionName,
					parameterSupplier, log4JDefaultFileName);

		} else {
			fileName = log4JDefaultFileName;
		}

		return fileName;
	}

	@Override
	public String getFullConfigurationFileNameWithPath(String baseDirOptionName, String appDirOptionName,
													   String log4JFileNameOptionName, Function<String,
					List<StringOptionValue>> parameterSupplier) {

		StringBuilder sb = new StringBuilder(getLog4JBasePath(baseDirOptionName, appDirOptionName, parameterSupplier));
		sb //
				.append(commonSystemSettings.getPathSeparator())
				.append(getLog4JConfigurationFileName(log4JFileNameOptionName, parameterSupplier));

		return sb.toString();
	}

	@Override
	public void configureLog4J(String pathAndLocationOfConfigurationFile) {
		System.setProperty("log4j.configurationFile", pathAndLocationOfConfigurationFile);
	}
}
