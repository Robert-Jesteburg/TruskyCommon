package org.trusky.common.api.startparameters;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.trusky.common.api.startparameters.optionvalue.StringOptionValue;
import org.trusky.common.impl.startparameters.commandlineprocessor.CommandLineProcessorImpl;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class AbstractStartOptionTest {


	private static final String OPTION_NAME = "--MyTestOption";
	private static final String DEFAULT_VALUE = "defaultValue";
	private static final Map<String, String> subOptions;

	private CommandLineProcessor sut;

	static {

		Map<String, String> map = new HashMap<>();
		map.put("-way", "North");
		map.put("-option", "on");
		map.put("-language", "en_en");
		map.put("-switch", null);

		subOptions = Collections.unmodifiableMap(map);
	}

	@BeforeEach
	void setUp() {
		sut = new CommandLineProcessorImpl();
	}

	@AfterEach
	void tearDown() {
	}

	/**
	 * Test that any given option can be retrieved (along with their values).
	 */
	@Test
	void testOptionWithSubOptions() {

		// GIVEN
		TestOption testOption = new TestOption(OPTION_NAME, DEFAULT_VALUE);
		for (String subOption : subOptions.keySet()) {

			String value = subOptions.get(subOption);
			if (value == null) {
				testOption.addSubOption(subOption);
			} else {
				testOption.addSubOption(subOption, value);
			}

		}

		// WHEN
		// Nothing to do

		// THEN
		assertThat(testOption.getOptionName()).isEqualTo(OPTION_NAME)
				.as("Invalid option name found.");
		assertThat(testOption.isDefaultValuePresent()).isTrue()
				.as("Default value is expected to be present");

		assertThat(testOption.getDefaultValue()
				.get()
				.getValue()
				.get()).isEqualTo(DEFAULT_VALUE)
				.as("Unexpected default value found");

		for (String o : subOptions.keySet()) {

			assertThat(testOption.isSubOptionPresent(o)).as("Suboption" + " " + o + " is expected to be present")
					.isTrue();

			String value;
			if ((value = subOptions.get(o)) != null) {

				assertThat(testOption.hasValue(o)).isTrue();

				assertThat(testOption.getValue(o)
						.size()).as("Expected count of values for suboption " + o + " is one.")
						.isEqualTo(1);

				assertThat(testOption.getValue(o)).as("Unexpected value for sub option " + o + (" found: Should be " + "\"" + value + "\""))
						.contains(new StringOptionValue(value));

			} else {
				assertThat(testOption.hasValue(o)).isFalse()
						.as("Sub option " + o + " is not expected to have a value " + "attached.");
			}
		}


	}

	/**
	 * Test a suboption with a list of values can be retrieved back and that the list will hold the same values as it
	 * should.
	 */
	@Test
	void testOptionWithSubOptionList() {

		final String subOptionName = "-option";

		// GIVEN
		TestOption testOption = new TestOption(OPTION_NAME, DEFAULT_VALUE);
		testOption.addSubOption(subOptionName, "eins");
		testOption.addSubOption(subOptionName, "zwei");

		assertThat(testOption.isSubOptionPresent(subOptionName)).as("Suboption" + " " + subOptionName + " is expected "
						+ "to be present")
				.isTrue();

		assertThat(testOption.getValue(subOptionName)
				.size()).isEqualTo(2);
		assertThat(testOption.getValue(subOptionName)).contains(new StringOptionValue("eins"));
		assertThat(testOption.getValue(subOptionName)).contains(new StringOptionValue("zwei"));

	}

	private static class TestOption extends AbstractStartOption<StringOptionValue, StringOptionValue> {


		public TestOption(String optionName) {
			super(optionName);
		}

		public TestOption(String optionName, String defaultValue) {
			super(optionName, new StringOptionValue(defaultValue));
		}

		public void addSubOption(String optionName) {
			super.addSubOption(optionName);
		}

		public void addSubOption(String subOptionName, String value) {
			super.addSubOption(subOptionName, new StringOptionValue(value));

		}
	}


}