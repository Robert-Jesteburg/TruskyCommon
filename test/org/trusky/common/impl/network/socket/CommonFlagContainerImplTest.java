package org.trusky.common.impl.network.socket;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.trusky.common.api.network.socket.CommonFlagContainer;

import java.util.stream.Stream;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class CommonFlagContainerImplTest {

	@ParameterizedTest
	@MethodSource("provideTestData")
	void testFlagContainer(boolean flagValue) {

		// GIVEN
		CommonFlagContainer sut = new CommonFlagContainerImpl();

		// WHEN
		sut.setFlag(flagValue);

		// THEN
		assertAll( //
				() -> assertThat(sut.getFlag()).isEqualTo(flagValue),
				() -> assertThat(sut.getFlag()).isEqualTo(flagValue) // Should remain the same value!
				 );
	}

	private static Stream<Arguments> provideTestData() {
		return Stream.of(Arguments.of(true), Arguments.of(false));
	}
}