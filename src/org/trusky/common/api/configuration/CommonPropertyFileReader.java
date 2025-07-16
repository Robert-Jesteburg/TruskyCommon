package org.trusky.common.api.configuration;

import java.io.IOException;
import java.util.Properties;

/**
 * Reads a file and delivers it as property object
 */
public interface CommonPropertyFileReader {

	Properties readFromPropertyFile(String fullFileNameWithPath) throws IOException;
}
