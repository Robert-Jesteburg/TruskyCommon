package org.trusky.common.api.network.message.util;

import java.io.IOException;
import java.io.InputStream;

public interface CommonIntSerializer {

	/**
	 * Convert an integer value to an array of bytes
	 *
	 * @param value
	 * @return Array of bytes.
	 */
	byte[] writeIntValue(int value);

	/**
	 *
	 * @param bytes array of bytes
	 * @return The integer value
	 * @throws ArrayIndexOutOfBoundsException if the length of the buffer is less than the neccessary length.
	 */
	int readIntValue(byte[] bytes) throws ArrayIndexOutOfBoundsException;

	/**
	 * Liest Reads an integer value at index offset.
	 *
	 * @param bytes  array of bytes
	 * @param offset Zero-based index into buffer
	 * @return The integer value
	 * @throws ArrayIndexOutOfBoundsException if the length is too short to contain the neccessary bytes starting
	 *                                        from offset
	 */
	int readIntValue(byte[] bytes, int offset) throws ArrayIndexOutOfBoundsException;

	/**
	 * Read integer from (socket) stream
	 *
	 * @param in
	 * @return
	 * @throws IOException
	 */
	int readIntValue(InputStream in) throws IOException;
}
