package org.trusky.common.api.configuration;

import java.util.Properties;


/**
 * This interface will be implemented by specific user code, not inside the common library.
 */
public interface CommonCredentialsResolver {

	/**
	 * Gets the raw user specification (may be in an encoded form, or a reference to another file/section to retrive
	 * the name from) and dellivers the name in clear text.
	 *
	 * @param props       A properties object the instances get informations from.
	 * @param rawUserSpec Raw spec
	 * @return User name in clear text
	 */
	String resolveUserName(Properties props, String rawUserSpec);

	/**
	 * Changes the raw user specification into a clear password text.
	 *
	 * @param props      A properties object the instances get informations from.
	 * @param rawPwdSpec Raw spec
	 * @return Password  in clear text
	 */
	String resolvePassword(Properties props, String rawPwdSpec);
}
