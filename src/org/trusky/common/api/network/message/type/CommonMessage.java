package org.trusky.common.api.network.message.type;

import org.trusky.common.api.network.message.util.CommonMessageSerializer;

import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Objects;

/**
 * Base class of all messages sent over a socket connection.
 */
public abstract class CommonMessage {

	private final CommonMessageSerializer messageSerializer;
	private final CommonMessageType type;

//	protected CommonMessage(CommonMessageType type) {
//		this(InjectorFactory.getInstance(CommonMessageSerializer.class), type);
//	}

	protected CommonMessage(CommonMessageSerializer messageSerializer, CommonMessageType type) {
		this.type = type;
		this.messageSerializer = messageSerializer;
	}

	public CommonMessageType getType() {
		return type;
	}

	public void writeTo(OutputStream outputStream) {
		// FIXME entfernen, dann den Test anpassen
	}

	/**
	 * Serializes this CommonMessage to a byte array.
	 * Format: CommonMessageType bytes + Payload bytes
	 *
	 * @return the serialized byte array
	 */
	public byte[] toByteArray() {
		byte[] typeBytes = type.toByteArray();
		byte[] payloadBytes = serializePayload();

		byte[] result = new byte[typeBytes.length + payloadBytes.length];
		System.arraycopy(typeBytes, 0, result, 0, typeBytes.length);
		System.arraycopy(payloadBytes, 0, result, typeBytes.length, payloadBytes.length);

		return result;
	}

	public abstract byte[] serializePayload();

	protected static byte[] readPayload(InputStream in, int length) throws IOException {
		byte[] buf = in.readNBytes(length);
		if (buf.length < length) {
			throw new EOFException("Payload incomplete");
		}
		return buf;
	}

	@Override
	public boolean equals(Object obj) {

		if (obj == null) {
			return false;
		}

		if (!(obj instanceof CommonMessage)) {
			return false;
		}

		CommonMessage other = (CommonMessage) obj;

		return Objects.equals(this.getType(), other.getType());
	}


}

