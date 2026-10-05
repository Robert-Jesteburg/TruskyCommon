package org.trusky.common.impl.network.util;

import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.trusky.common.api.injection.CommonGuiceModule;
import org.trusky.common.api.injection.InjectorFactory;
import org.trusky.common.api.network.message.LoginMessage;
import org.trusky.common.api.network.message.ShutdownAckMessage;
import org.trusky.common.api.network.message.ShutdownMessage;
import org.trusky.common.api.network.message.type.CommonMessage;
import org.trusky.common.api.network.message.util.CommonIntSerializer;
import org.trusky.common.api.network.message.util.CommonMessageSerializer;
import org.trusky.common.impl.network.message.util.CommonIntSerializerImpl;
import org.trusky.common.impl.network.message.util.CommonMessageSerializerImpl;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.lang.reflect.Constructor;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class CommonMessageSerializerTest {

	private CommonMessageSerializer sut;

	@BeforeAll
	static void setUpBeforeClass() {
		InjectorFactory.setModule(new CommonGuiceModule());
	}

	@BeforeEach
	void setUp() {
		CommonIntSerializer intSerializer = new CommonIntSerializerImpl();
		sut = new CommonMessageSerializerImpl(intSerializer);
	}

	@Test
	void testSerializeDeserializeLoginMessage() throws IOException {

		// Arrange: Create a LoginMessage
		String testUsername = "testuser";
		LoginMessage originalMessage = new LoginMessage(testUsername);

		byte[] writtenBytes = convertMessageToByteArray(originalMessage);

		// Create ByteArrayInputStream for deserialization
		ByteArrayInputStream in = new ByteArrayInputStream(writtenBytes);

		// Deserialize the message using the parse method
		LoginMessage deserializedMessage = LoginMessage.parse(sut, in);

		// Assert: Verify that the deserialized message equals the original
		assertAll(() -> assertThat(deserializedMessage).as("LoginMessage should be equal after serialization and " +
						"deserialization")
				.isEqualTo(originalMessage),
				() -> assertThat(deserializedMessage.getUsername()).as("Username should " + "be preserved")
				.isEqualTo(testUsername));
	}

	private byte @NotNull [] convertMessageToByteArray(CommonMessage originalMessage) throws IOException {

		// Act: Convert message to byte arrays
		byte[] messageTypeBytes = originalMessage.getType()
				.toByteArray();
		byte[] payloadBytes = originalMessage.serializePayload();

		// Serialize the message
		byte[] serializedMessage = sut.serializeMessage(messageTypeBytes, payloadBytes);

		// Write serialized bytes to ByteArrayOutputStream
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		out.write(serializedMessage);
		byte[] writtenBytes = out.toByteArray();
		return writtenBytes;
	}

	@Test
	void testSerializeDeserializeShutdownMessage() throws IOException, ReflectiveOperationException {

		// Arrange: Create a ShutdownMessage using Reflection (protected constructor)
		Constructor<ShutdownMessage> constructor = ShutdownMessage.class.getDeclaredConstructor();
		constructor.setAccessible(true);
		ShutdownMessage originalMessage = constructor.newInstance();

		byte[] writtenBytes = convertMessageToByteArray(originalMessage);

		// Create ByteArrayInputStream for deserialization
		ByteArrayInputStream in = new ByteArrayInputStream(writtenBytes);

		// Deserialize the message using the parse method
		ShutdownMessage deserializedMessage = ShutdownMessage.parse(sut, in);

		// Assert: Verify that the deserialized message equals the original
		assertAll(() -> assertThat(deserializedMessage).as("ShutdownMessage should be equal after serialization and " + "deserialization")
				.isEqualTo(originalMessage),
				() -> assertThat(sut.deserializeMessage(new ByteArrayInputStream(writtenBytes))
				.payloadBytes().length).as("Payload should be empty")
				.isEqualTo(0));
	}

	@Test
	void testSerializeDeserializeShutdownAckMessage() throws IOException {
		// Arrange: Create a ShutdownAckMessage
		String testReply = "Shutdown submitted.";
		ShutdownAckMessage originalMessage = new ShutdownAckMessage(testReply);

		byte[] writtenBytes = convertMessageToByteArray(originalMessage);

		// Create ByteArrayInputStream for deserialization
		ByteArrayInputStream in = new ByteArrayInputStream(writtenBytes);

		// Deserialize the message using the parse method
		ShutdownAckMessage deserializedMessage = ShutdownAckMessage.parse(sut, in);

		// Assert: Verify that the deserialized message equals the original
		assertAll(() -> assertThat(deserializedMessage).as("ShutdownAckMessage should be equal after serialization " + "and" + " deserialization")
				.isEqualTo(originalMessage), () -> assertThat(deserializedMessage.getReply()).as("Reply should be " +
						"preserved")
				.isEqualTo(testReply));
	}
}









