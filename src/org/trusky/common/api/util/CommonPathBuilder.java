package org.trusky.common.api.util;

import java.util.function.Function;

public interface CommonPathBuilder {

	String createPathNameAsAbsolutePath(Function<String, String> fnGetSingleOption, String... optionNames);

	String createPathNameAsAbsolutePath(String... pathComponents);
}
