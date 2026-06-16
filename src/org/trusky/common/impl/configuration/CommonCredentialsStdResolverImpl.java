package org.trusky.common.impl.configuration;

import org.trusky.common.api.configuration.CommonCredentialsStdResolver;

import java.util.Properties;

/**
 * Resolves user names and passwords by simply returning the raw user spec/raw password spec .
 */
public class CommonCredentialsStdResolverImpl implements CommonCredentialsStdResolver {

	@Override
	public String resolveUserName(Properties props, String rawUserSpec) {
		return rawUserSpec;
	}

	@Override
	public String resolvePassword(Properties props, String rawPwdSpec) {
		return rawPwdSpec;
	}
}
