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

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.trusky.common.impl.util.CommonFileUtilitiesImpl;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class CommonFileUtilitiesTest {

	// Eine Zeile mit nur einer Ersetzung
	// Eine Zeile mit mehrfach derselben Ersetzung
	// Eine Zeile ohne Ersetzung
	// Kompletter text mit Ersetzungen

	private static Stream<Arguments> provideContents() {


		return Stream.of( //
				// One line with exactly one replacement
				Arguments.of("replacementfiles/OneLineOneReplacementTemplate.txt", //
						"replacementfiles/OneLineOneReplacementMap.csv", //
						"replacementfiles/OneLineOneReplacementResult.txt"), //

				// Two replacements on one line with the same replacement
				Arguments.of("replacementfiles/OneLineTwiceTheSameReplacementTemplate.txt", //
						"replacementfiles/OneLineTwiceTheSameReplacementMap.csv", //
						"replacementfiles/OneLineTwiceTheSameReplacementResult.txt"), //

				// Without any replacement at all
				Arguments.of("replacementfiles/WithoutReplacementTemplate.txt", //
						"replacementfiles/WithoutReplacementMap.csv", //
						"replacementfiles/WithoutReplacementResult.txt"), //

				// A multiline text with some replacements
				Arguments.of("replacementfiles/CompleteTextWithReplacementsTemplate.txt", //
						"replacementfiles/CompleteTextWithReplacementsMap.csv", //
						"replacementfiles/CompleteTextWithReplacementsResult.txt") //
						);
	}


	private CommonFileUtilities getSut() {
		return new CommonFileUtilitiesImpl();
	}

	@ParameterizedTest
	@MethodSource("provideContents")
	void checkReplacements(String templateFileName, String replacementFileName, String resultFileName)
	throws IOException {

		MyFileWriter fileWriter = new MyFileWriter();
		Map<String, String> replacements = readReplacementMap(replacementFileName);
		InputStream inputStream = CommonFileUtilitiesTest.class.getResourceAsStream(templateFileName);

		CommonFileUtilities sut = getSut();
		sut.createFileContentsFromTemplate(inputStream, replacements, fileWriter);


		List<String> expectedResult = readFile(resultFileName);
		List<String> generatedLines = fileWriter.getGeneratedines();

		assertThat(expectedResult.size()).isEqualTo(generatedLines.size());

		// Check the lines, one by one
		for (int i = 0; i < expectedResult.size(); i++) {

			String expectedLine = expectedResult.get(i);
			String generatedLine = generatedLines.get(i);

			assertThat(generatedLine).as("Line " + i + " mismatched.")
					.isEqualTo(expectedLine);

		}
	}

	private List<String> readFile(String fileName) {

		InputStream fileStream = CommonFileUtilitiesTest.class.getResourceAsStream(fileName);
		Scanner scanner = new Scanner(fileStream, "utf-8");

		List<String> contents = new LinkedList<>();
		while (scanner.hasNextLine()) {
			contents.add(scanner.nextLine());
		}

		scanner.close();

		return contents;
	}

	private Map<String, String> readReplacementMap(String fileName) {

		InputStream mapFile = CommonFileUtilitiesTest.class.getResourceAsStream(fileName);
		Scanner scanner = new Scanner(mapFile, "utf-8");

		Map<String, String> result = new HashMap<>();
		while (scanner.hasNextLine()) {

			String line = scanner.nextLine();
			String[] mapContents = line.split(";");
			assertThat(mapContents.length).as("Must be exactly two parameters")
					.isEqualTo(2);
			result.put(mapContents[0], mapContents[1]);
		}

		scanner.close();

		return result;
	}

	@BeforeEach
	void setUp() {
	}

	@AfterEach
	void tearDown() {
	}

	private static class MyFileWriter implements CommonFileUtilities.Persistor {

		private final List<String> contents = new LinkedList<>();

		@Override
		public void write(String line) throws IOException {
			contents.add(line);
		}

		@Override
		public void flush() throws IOException {
			// Do nothing
		}

		@Override
		public void close() throws IOException {
			// Do nothing
		}

		List<String> getGeneratedines() {
			return new LinkedList<>(contents);
		}

	}
}