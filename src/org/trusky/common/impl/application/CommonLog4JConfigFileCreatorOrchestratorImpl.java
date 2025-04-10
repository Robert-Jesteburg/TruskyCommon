/*
 * Copyright (c) 2025 by Robert Niemann, Rehkamp 21a; 12266 Jesteburg, Germany.
 * Diese Software unterliegt der GPL v3 vorbehaltlich jedweder böswilliger Veränderungen, eingeschlossen aber nicht
 * abschließend:
 * a) Ausspionieren von Gesundheitsdaten (bspw. durch Weiterleiten dieser Informationen oder Auswertung der Daten auf
 *  bereitgestellten Servern)
 * b) Ausspionieren von Beziehungen zwischen Personen oder deren zeitliche Verläufe
 * c) Irreführung durch Angabe bewusst falscher Daten
 */

package org.trusky.common.impl.application;

import com.google.common.base.Supplier;
import org.trusky.common.api.startparameters.optionvalue.StringOptionValue;
import org.trusky.common.api.util.CommonFileUtilities;
import org.trusky.common.api.util.CommonLog4JConfigurationUtils;
import org.trusky.common.api.util.CommonSystemSettings;
import org.trusky.common.impl.util.CommonFileUtilitiesImpl;

import javax.inject.Inject;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class CommonLog4JConfigFileCreatorOrchestratorImpl implements CommonLog4JConfigFileCreatorOrchestrator {

	private final CommonSystemSettings commonSystemSettings;
	private final CommonLog4JConfigurationUtils commonLog4JConfigurationUtils;
	private final CommonFileUtilitiesImpl fileUtilities;

	@Inject
	public CommonLog4JConfigFileCreatorOrchestratorImpl(CommonSystemSettings commonSystemSettings,
														CommonLog4JConfigurationUtils commonLog4JConfigurationUtils,
														CommonFileUtilitiesImpl fileUtilities) {
		this.commonSystemSettings = commonSystemSettings;
		this.commonLog4JConfigurationUtils = commonLog4JConfigurationUtils;
		this.fileUtilities = fileUtilities;
	}

	@Override
	public String getLogPath(String baseDirOptionName, String appDirOptionName, Function<String,
			List<StringOptionValue>> parameterSupplier) {


		String appDir = commonLog4JConfigurationUtils.getLog4JBasePath(baseDirOptionName, appDirOptionName,
				parameterSupplier);

		StringBuilder sbLog4JLogDir = new StringBuilder(appDir);
		if (!appDir.endsWith(commonSystemSettings.getPathSeparator())) {
			sbLog4JLogDir.append(commonSystemSettings.getPathSeparator());
		}

		sbLog4JLogDir.append("log");

		return sbLog4JLogDir.toString();
	}

	@Override
	public void createFile( //
							String baseDirOptionName, //
							String appDirOptionName,//
							String log4JFileNameOptionName, //
							Function<String, List<StringOptionValue>> parameterSupplier, //
							Supplier<InputStream> logTemplateSupplier) throws IOException {

		String logFolder = getLogPath(baseDirOptionName, appDirOptionName, parameterSupplier);
		String fullConfigurationFileNameWithPath =
				commonLog4JConfigurationUtils.getFullConfigurationFileNameWithPath( //
				baseDirOptionName, //
				appDirOptionName, //
				log4JFileNameOptionName, parameterSupplier);

		fileUtilities.createNonExistingFile(fullConfigurationFileNameWithPath);
		createFileContentsFromTemplate(logTemplateSupplier, fullConfigurationFileNameWithPath, logFolder);

	}

	private void createFileContentsFromTemplate(Supplier<InputStream> logTemplateSupplier,
												String fullConfigurationFileNameWithPath, String logFolder)
	throws IOException {

		InternalWriter internalWriter = new InternalWriter(fullConfigurationFileNameWithPath);

		Map<String, String> replacements = new HashMap<>();
		replacements.put("##BASE_PATH##", logFolder);

		fileUtilities.createFileContentsFromTemplate(logTemplateSupplier.get(), replacements, internalWriter);

		internalWriter.close();
	}

	private static class InternalWriter implements CommonFileUtilities.Persistor {

		private final FileWriter fileWriter;

		InternalWriter(String fullPathAndName) throws IOException {
			fileWriter = new FileWriter(fullPathAndName);
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
