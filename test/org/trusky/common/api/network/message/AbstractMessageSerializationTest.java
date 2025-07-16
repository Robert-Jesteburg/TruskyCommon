package org.trusky.common.api.network.message;

import org.trusky.common.api.network.message.parser.CommonMessageParser;
import org.trusky.common.api.network.message.type.CommonMessage;
import org.trusky.common.api.network.message.type.CommonMessageHeader;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

public abstract class AbstractMessageSerializationTest<MESSAGE_TYPE extends CommonMessage> {

	/**
	 * Serializes the given message and read the message back. Will return the read back copy. Will check the outcome
	 * of message.equals() called against the readback copy.
	 *
	 * @param message The message object to check for proper serialisation and deserialisation.
	 * @return The message that was read back.
	 * @throws IOException
	 */
	protected MESSAGE_TYPE doTestSerialization(MESSAGE_TYPE message) throws IOException {

		ByteArrayOutputStream out = new ByteArrayOutputStream();
		message.writeTo(out);

		byte[] writtenBytes = out.toByteArray();
		assertThat(writtenBytes.length) //
				.as("The count of bytes written does not matsch the expected byte count") //
				.isEqualTo(CommonMessageHeader.HEADER_SIZE + message.serializePayload().length);

		ByteArrayInputStream in = new ByteArrayInputStream(writtenBytes);

		CommonMessage copy = CommonMessageParser.readMessage(in);
		assertAll( //
				() -> assertThat(copy.getType()).as("Object types differ.")
						.isEqualTo(message.getType()), //
				() -> assertThat(copy).as("Objects differ")
						.isEqualTo(message) //
				 );


		return (MESSAGE_TYPE) copy;

	}
}
