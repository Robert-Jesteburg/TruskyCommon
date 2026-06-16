package org.trusky.common.impl.configuration;

import org.junit.jupiter.api.Test;
import org.trusky.common.api.configuration.CommonCredentialsStdResolver;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class CommonCredentialsStdResolverImplTest {


	private CommonCredentialsStdResolver getSut() {
		return new CommonCredentialsStdResolverImpl();
	}

	@Test
	void testResolveUserName() {

		// GIVEN
		final String name = "TestName";

		// WHEN
		String resolvedName = getSut().resolveUserName(null, name);

		// THEN
		assertAll( //
				() -> assertThat(resolvedName).isNotNull(), //
				() -> assertThat(resolvedName).isEqualTo(name) //
				 );
	}

	@Test
	void testResolvePassword() {

		// GIVEN
		final String pwd = "MyPassword";

		// WHEN
		String resolvedPwd = getSut().resolvePassword(null, pwd);

		// THEN
		assertAll( //
				() -> assertThat(resolvedPwd).isNotNull(), //
				() -> assertThat(resolvedPwd).isEqualTo(pwd) //
				 );
	}
}