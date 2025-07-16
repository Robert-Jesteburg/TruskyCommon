package org.trusky.common.impl.util;

import org.trusky.common.api.logging.CommonLogger;
import org.trusky.common.api.logging.CommonLoggerFactory;
import org.trusky.common.api.util.CommonPathBuilder;
import org.trusky.common.api.util.CommonSystemSettings;

import javax.inject.Inject;
import java.util.function.Function;

public class CommonPathBuilderImpl implements CommonPathBuilder {

	private final CommonSystemSettings systemSettings;
	private final CommonLogger logger;

	@Inject
	public CommonPathBuilderImpl(CommonSystemSettings systemSettings, CommonLoggerFactory loggerFactory) {
		this.systemSettings = systemSettings;
		logger = loggerFactory.getLogger(this.getClass());
	}

	@Override
	public String createPathNameAsAbsolutePath(Function<String, String> fnGetSingleOption, String... optionNames) {

		int componentCount = optionNames.length;
		String[] pathComponents = new String[componentCount];

		for (int i = 0; i < componentCount; i++) {
			pathComponents[i] = fnGetSingleOption.apply(optionNames[i]);
		}

		return createPathNameAsAbsolutePath(pathComponents);
	}

	@Override
	public String createPathNameAsAbsolutePath(String... pathComponents) {

		int paramCount = 0;
		for (String pathPart : pathComponents) {
			if (pathPart != null && !pathPart.isBlank()) {
				paramCount++;
			} else {
				if (pathPart == null) {
					logger.warn("Array of path components contained NULL element.");
				} else {
					logger.warn("Array of path components contained empty path component!");
				}
			}
		}

		if (paramCount == 0) {
			return systemSettings.getPathSeparator();
		}

		String[] paramsWithoutEmptyStrings = new String[paramCount];
		int i = 0;
		for (String pathPart : pathComponents) {
			if (pathPart != null && !pathPart.isBlank()) {
				paramsWithoutEmptyStrings[i] = pathPart;
				i++;
			}
		}

		return createAbsolutePathName(paramsWithoutEmptyStrings);
	}

	private String createAbsolutePathName(String... pathComponents) {

		StringBuilder sb = new StringBuilder();

		boolean lastComponentHadPathSeparator = (pathComponents.length >= 1) ?
				pathComponents[0].startsWith(systemSettings.getPathSeparator()) : false;

		for (String pathPart : pathComponents) {

			if (!pathPart.isBlank()) {

				if (!lastComponentHadPathSeparator) {
					sb.append(systemSettings.getPathSeparator());
				}
				sb.append(pathPart);
				lastComponentHadPathSeparator = pathPart.endsWith(systemSettings.getPathSeparator());

			}
		}

		return sb.toString();
	}
}
