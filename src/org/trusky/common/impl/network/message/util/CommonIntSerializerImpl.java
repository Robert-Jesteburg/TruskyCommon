package org.trusky.common.impl.network.message.util;

import org.trusky.common.api.network.message.util.CommonIntSerializer;

import java.io.IOException;
import java.io.InputStream;

public class CommonIntSerializerImpl implements CommonIntSerializer {


	@Override
	public byte[] writeIntValue(int value) {

		byte[] result = new byte[4];

		// Write code as 4 bytes (big-endian)
		result[0] = (byte) ((value >> 24) & 0xFF);
		result[1] = (byte) ((value >> 16) & 0xFF);
		result[2] = (byte) ((value >> 8) & 0xFF);
		result[3] = (byte) (value & 0xFF);

		return result;
	}

	@Override
	public int readIntValue(byte[] bytes) throws ArrayIndexOutOfBoundsException {
		return readIntValue(bytes, 0);
	}

	@Override
	public int readIntValue(byte[] bytes, int offset) throws ArrayIndexOutOfBoundsException {

		if (offset < 0 || offset + 3 >= bytes.length) {
			throw new ArrayIndexOutOfBoundsException("Offset is out of bounds");
		}

		return ((bytes[offset] & 0xFF) << 24) | ((bytes[offset + 1] & 0xFF) << 16) | ((bytes[offset + 2] & 0xFF) << 8) | (bytes[offset + 3] & 0xFF);
	}

	@Override
	public int readIntValue(InputStream in) throws IOException {

		byte[] bytes;
		try {
			bytes = in.readNBytes(4);
		} catch (IOException e) {
			throw new IOException("Input stream too short to read integer bytes", e);
		}

		return readIntValue(bytes);
	}
}
