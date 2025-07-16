package org.trusky.common.impl.util;

import org.trusky.common.api.util.CommonStringUtilities;

public class CommonStringUtilitiesImpl implements CommonStringUtilities {

	@Override
	public boolean isNullOrEmpty(String string) {

		if (string == null) {
			return true;
		}

		return string.isEmpty();
	}

	@Override
	public boolean isWhitespaceOnly(String string) {

		if (isNullOrEmpty(string)) {
			return false;
		}

		return string.isBlank();
	}
}
