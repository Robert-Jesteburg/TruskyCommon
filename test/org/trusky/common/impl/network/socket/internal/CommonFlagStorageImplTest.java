package org.trusky.common.impl.network.socket.internal;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class CommonFlagStorageImplTest {


	@ParameterizedTest
	@MethodSource("provideTestData")
	void testFlagStorage(boolean flagvalue) {

		// GIVEN
		CommonFlagStorageImpl sut = new CommonFlagStorageImpl();

		// WHEN
		sut.setFlag(flagvalue);

		// THEN
		assertThat(sut.getFlag()).isEqualTo(flagvalue);

	}

	private static Stream<Arguments> provideTestData() {

		return Stream.of(Arguments.of(true), Arguments.of(false));
	}
}