package org.trusky.common.impl.configuration;

import org.trusky.common.api.configuration.CommonConfigurationIntReader;

import java.util.Optional;
import java.util.Properties;

public class CommonConfigurationIntReaderImpl implements CommonConfigurationIntReader {

	@Override
	public int readMandatoryIntValue(Properties props, String key) throws NumberFormatException,
																		  IllegalStateException {

		Optional<Integer> optValue = readOptionalIntValue(props, key);
		if (optValue.isEmpty()) {
			throw new IllegalStateException("No integer value found for key '" + key + "'.");
		}

		return optValue.get();
	}

	@Override
	public int readManadtoryPositiveIntValue(Properties props, String key) throws IllegalArgumentException {
		return readManadtoryPositiveIntValue(props, key, false);
	}

	@Override
	public int readManadtoryPositiveIntValue(Properties props, String key, boolean allowZero)
	throws IllegalArgumentException, IllegalStateException {

		int i = readMandatoryIntValue(props, key);

		if (i < 0) {
			throw new IllegalArgumentException( //
					"Integer value for '" + key + "' (actual value " + i + ") is not expected to be negative.");

		}
		if (i == 0 && !allowZero) {
			throw new IllegalArgumentException( //
					"Integer value for '" + key + "' (actual value " + i + ") is not expected to become zero.");
		}

		return i;
	}

	@Override
	public int readManadtoryNegativeIntValue(Properties props, String key) {
		return readManadtoryNegativeIntValue(props, key, false);
	}

	@Override
	public int readManadtoryNegativeIntValue(Properties props, String key, boolean allowZero)
	throws IllegalStateException, IllegalArgumentException {

		int i = readMandatoryIntValue(props, key);

		if (i > 0) {
			throw new IllegalArgumentException( //
					"Integer value for '" + key + "' (actual value " + i + ") is not expected to be positive.");
		}

		if (!allowZero && i == 0) {
			throw new IllegalArgumentException( //
					"Integer value for '" + key + "' (actual value " + i + ") is not expected to become zero.");
		}

		return i;
	}

	@Override
	public Optional<Integer> readOptionalIntValue(Properties props, String key) throws NumberFormatException {

		if (props.containsKey(key)) {

			String propertyValue = props.getProperty(key)
					.replace(" ", "");

			Integer i = null;
			try {
				i = Integer.parseInt(propertyValue);

			} catch (NumberFormatException nfe) {

				NumberFormatException numberFormatException = new NumberFormatException( //
						"Value '" + propertyValue + //
								"' under key '" + key + //
								"' is not a valid integer value.");
				numberFormatException.initCause(nfe);
				throw numberFormatException;
			}

			return Optional.of(i);

		}

		return Optional.empty();
	}

	@Override
	public int readOptionalIntValue(Properties props, String key, int defaultValue) throws NumberFormatException {

		if (props.containsKey(key)) {
			Optional<Integer> i = readOptionalIntValue(props, key);

			// i will always be present, but IDE complains about using get() without prior call to isPresent() if not
			// expressed this way.
			return i.orElse(defaultValue);
		}

		return defaultValue;
	}

	@Override
	public Optional<Integer> readOptionalPositveIntValueWithoutZero(Properties properties, String key)
	throws NumberFormatException, IllegalStateException, IllegalArgumentException {

		Optional<Integer> optValue = readOptionalIntValue(properties, key);
		if (optValue.isEmpty()) {
			return Optional.empty();
		}

		int value = readManadtoryPositiveIntValue(properties, key, false);
		return Optional.of(value);
	}
}
