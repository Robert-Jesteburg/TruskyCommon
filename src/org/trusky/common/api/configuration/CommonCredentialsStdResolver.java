package org.trusky.common.api.configuration;

/**
 * A credential resolver that simply returns the supplied values (rawUserSpec/rawPwdSpec) without any decoding
 */
public interface CommonCredentialsStdResolver extends CommonCredentialsResolver {
}
