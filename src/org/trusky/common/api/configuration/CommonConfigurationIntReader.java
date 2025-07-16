package org.trusky.common.api.configuration;

import java.util.Optional;
import java.util.Properties;

public interface CommonConfigurationIntReader {

	int readMandatoryIntValue(Properties props, String key) throws NumberFormatException, IllegalStateException;


	/**
	 * Allows the mandatory value in the range 1..
	 *
	 * @param props
	 * @param key
	 * @return see above
	 */
	int readManadtoryPositiveIntValue(Properties props, String key)
	throws NumberFormatException, IllegalArgumentException;

	int readManadtoryPositiveIntValue(Properties props, String key, boolean allowZero)
	throws NumberFormatException, IllegalArgumentException, IllegalStateException;


	/**
	 * Allows values fom -1..
	 *
	 * @param props
	 * @param key
	 * @return see above
	 */
	int readManadtoryNegativeIntValue(Properties props, String key)
	throws NumberFormatException, IllegalArgumentException, IllegalStateException;

	int readManadtoryNegativeIntValue(Properties props, String key, boolean allowZero)
	throws NumberFormatException, IllegalStateException, IllegalArgumentException;


	// =======================================

	Optional<Integer> readOptionalIntValue(Properties props, String key) throws NumberFormatException;

	int readOptionalIntValue(Properties props, String key, int defaultValue);


	Optional<Integer> readOptionalPositveIntValueWithoutZero(Properties properties, String shutdownPort)
	throws NumberFormatException, IllegalStateException, IllegalArgumentException;

	;
}
