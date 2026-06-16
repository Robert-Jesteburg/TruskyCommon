package org.trusky.common.api.network.message.type;

import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Objects;

/**
 * Base class of all messages sent over a socket connection.
 */
public abstract class CommonMessage {

	private final CommonMessageType type;

	protected CommonMessage(CommonMessageType type) {
		this.type = type;
	}

	public CommonMessageType getType() {
		return type;
	}

	public abstract byte[] serializePayload();

	public final void writeTo(OutputStream out) throws IOException {
		byte[] payload = serializePayload();
		new CommonMessageHeader(type.getCode(), payload.length).writeTo(out);
		out.write(payload);
	}

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

