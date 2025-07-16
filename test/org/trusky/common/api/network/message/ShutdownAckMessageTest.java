package org.trusky.common.api.network.message;

import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class ShutdownAckMessageTest extends AbstractMessageSerializationTest<ShutdownAckMessage> {
	
	@Test
	void testSerialize() throws IOException {

		ShutdownAckMessage original = new ShutdownAckMessage(ShutdownAckMessage.EXPECTED_REPLY);
		ShutdownAckMessage readMessage = doTestSerialization(original);

		// Special checks for the specific message
		assertAll(() -> assertThat(readMessage.getReply()).isEqualTo(original.getReply()));

	}
}