package org.trusky.common.api.network.message.type;

import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/**
 * This header should precede any message sent over the socket connections
 */
public class CommonMessageHeader {
	public static final int HEADER_SIZE = Integer.SIZE + Integer.SIZE; // 4 bytes type + 4 bytes payload length

	private final int typeCode;
	private final int payloadLength;

	public CommonMessageHeader(int typeCode, int payloadLength) {
		this.typeCode = typeCode;
		this.payloadLength = payloadLength;
	}

	public int getTypeCode() {
		return typeCode;
	}

	public int getPayloadLength() {
		return payloadLength;
	}

	public static CommonMessageHeader readFrom(InputStream in) throws IOException {
		byte[] buffer = in.readNBytes(HEADER_SIZE);
		if (buffer.length < HEADER_SIZE) {
			throw new EOFException("Incomplete header");
		}

		ByteBuffer bb = ByteBuffer.wrap(buffer)
				.order(ByteOrder.BIG_ENDIAN);
		return new CommonMessageHeader(bb.getInt(), bb.getInt());
	}

	public void writeTo(OutputStream out) throws IOException {
		ByteBuffer bb = ByteBuffer.allocate(HEADER_SIZE)
				.order(ByteOrder.BIG_ENDIAN);
		bb.putInt(typeCode);
		bb.putInt(payloadLength);
		out.write(bb.array());
	}
}
