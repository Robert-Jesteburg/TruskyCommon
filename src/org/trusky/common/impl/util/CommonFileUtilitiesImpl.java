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

import org.trusky.common.api.util.CommonFileUtilities;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Scanner;

public class CommonFileUtilitiesImpl implements CommonFileUtilities {

	@Override
	public void createNonExistingFile(String fullFileNameWithPath) throws IOException {

		// Check for directory with same name
		File newFile = new File(fullFileNameWithPath);
		if (newFile.isDirectory()) {
			throw new IOException("File \"" + fullFileNameWithPath + "\" exists, but is a directory.");
		}

		// File should not exist
		if (newFile.exists()) {
			throw new IOException("File \"" + fullFileNameWithPath + "\" already exists.");
		}

		// Create file
		try {
			newFile.createNewFile();

		} catch (IOException e) {

			IOException exception = new IOException("Unable to create file \"" + //
					fullFileNameWithPath + //
					"\" (for writing).");
			exception.initCause(e);

			throw exception;
		}

	}

	@Override
	public void createFileContentsFromTemplate(//
											   InputStream templateFileContent, //
											   String charsetName, //
											   Map<String, String> replacements, //
											   Persistor writer) //
	throws IOException {

		Scanner scanner = new Scanner(templateFileContent, charsetName);

		while (scanner.hasNextLine()) {

			String line = scanner.nextLine();

			for (String placeholder : replacements.keySet()) {
				line = line.replace(placeholder, replacements.get(placeholder));
			}

			writer.write(line);

		}

		writer.flush();

	}
}
