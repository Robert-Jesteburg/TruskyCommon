package org.trusky.common.impl.configuration;

import org.trusky.common.api.configuration.CommonPropertyFileReader;
import org.trusky.common.api.logging.CommonLogger;
import org.trusky.common.api.logging.CommonLoggerFactory;

import javax.inject.Inject;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class CommonPropertyFileReaderImpl implements CommonPropertyFileReader {

	private final CommonLogger LOGGER;

	@Inject
	public CommonPropertyFileReaderImpl(CommonLoggerFactory loggerFactory) {
		LOGGER = loggerFactory.getLogger(this.getClass());
	}

	@Override
	public Properties readFromPropertyFile(String fullFileNameWithPath) throws IOException {

		try (FileInputStream inputStream = new FileInputStream(fullFileNameWithPath)) {

			Properties props = new Properties();
			props.load(inputStream);

			return props;

		} catch (IOException e) {

			LOGGER.error("Unable to read server properties from file \"" + fullFileNameWithPath + "\".", e);
			throw new IOException("Unable to read server properties from file \"" + fullFileNameWithPath + "\".", e);
		}

	}
}
