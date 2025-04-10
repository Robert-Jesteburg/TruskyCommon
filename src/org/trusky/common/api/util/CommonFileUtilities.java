/*
 * Copyright (c) 2025 by Robert Niemann, Rehkamp 21a; 12266 Jesteburg, Germany.
 * Diese Software unterliegt der GPL v3 vorbehaltlich jedweder böswilliger Veränderungen, eingeschlossen aber nicht
 * abschließend:
 * a) Ausspionieren von Gesundheitsdaten (bspw. durch Weiterleiten dieser Informationen oder Auswertung der Daten auf
 *  bereitgestellten Servern)
 * b) Ausspionieren von Beziehungen zwischen Personen oder deren zeitliche Verläufe
 * c) Irreführung durch Angabe bewusst falscher Daten
 */

package org.trusky.common.api.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

public interface CommonFileUtilities {

	void createNonExistingFile(String fullFileNameWithPath) throws IOException;

	/**
	 * @param templateFileContent The template as input stream. There may be placeholders, that have
	 *                            to be replaced
	 *                            (see parameter replacements).
	 * @param charsetName         The name of the charset of the contents of the template file,
	 *                            normally utf-8
	 * @param replacements        A map consisting of a placeholder string and it's actual value
	 *                            (that is, the placeholder
	 *                            is the key of the map)
	 * @param writer              something to actually write the contents
	 * @throws IOException On any file errors.
	 */
	void createFileContentsFromTemplate( //
										 InputStream templateFileContent, //
										 String charsetName, //
										 Map<String, String> replacements, //
										 Persistor writer) throws IOException;

	/**
	 * Convenient method that assumes the contents of the input stream is in utf-8
	 *
	 * @param templateFileContent The template as input stream. There may be placeholders, that have
	 *                            to be replaced
	 *                            (see parameter replacements).
	 * @param replacements        A map consisting of a place holder string and it's actual value
	 *                            (that is, the placeholder
	 *                            is the key of the map)
	 * @param writer              something to actually write the contents
	 * @throws IOException On any file errors.
	 */
	default void createFileContentsFromTemplate( //
												 InputStream templateFileContent, //
												 Map<String, String> replacements, //
												 Persistor writer) throws IOException {

		createFileContentsFromTemplate(templateFileContent, "utf-8", replacements, writer);
	}

	/**
	 * Something that will transport the line into a file on disk (or a string buffer in memory or anything else).
	 */
	interface Persistor {

		void write(String line) throws IOException;

		void flush() throws IOException;

		void close() throws IOException;
	}

}
