package org.trusky.common.api.network.message.type;

import org.trusky.common.api.injection.InjectorFactory;
import org.trusky.common.api.network.message.util.CommonIntSerializer;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * As this is a general type in common library not all types of messages may be known. As a result, this can't be
 * implemented as an enum (because an enum is not extensible).
 */
public class CommonMessageType {

	/**
	 * To prevent a clash between message types from the common system user message types should be defined as follows:
	 * <ul>
	 *     <li>First message uses CommonMessageType(CommonMessageType.FIRST_USER_MESSAGE_CODE, "MyMessageName")</li>
	 *     <li>Next message would be CommonMessageType(CommonMessageType.FIRST_USER_MESSAGE_CODE<b>+1</b>,
	 *     "MySecondMessage")
	 *     </li>
	 *     <li>Third message would be CommonMessageType(CommonMessageType.FIRST_USER_MESSAGE_CODE<b>+2</b>,
	 *     "MyTHirdMessage")
	 *     <li>...and so on.</li>
	 * </ul>
	 * By using this numbering scheme of FIRST_USER_MESSAGE_CODE+<i><b>n</b></i> the code will still work if new
	 * common message types are 'invented'.
	 */
	private static final int FIRST_USER_MESSAGE_CODE = 10;

	// FIXME Das muss eine eigene Klasse sein, die alle Typen statisch kennt - auch die aus dem Client-Code!
	private static final ConcurrentMap<Integer, CommonMessageType> registry = new ConcurrentHashMap<>();

	private final int code;
	private final String name;

	private final CommonIntSerializer integerSerializer;

	public CommonMessageType(int code, String name) {
		this(InjectorFactory.getInstance(CommonIntSerializer.class), code, name);
	}

	public CommonMessageType(CommonIntSerializer integerSerializer, int code, String name) {

		this.integerSerializer = integerSerializer;

		this.code = code;
		this.name = name;

		registry.putIfAbsent(code, this);
	}


	public int getCode() {
		return code;
	}

	public static CommonMessageType fromCode(int code) {
		return registry.get(code);
	}

	@Override
	public String toString() {
		return name + "(" + code + ")";
	}

	/**
	 * Serializes this CommonMessageType to a byte array.
	 * Format: 4 bytes (code) + 2 bytes (name length) + name bytes (UTF-8)
	 *
	 * @return the serialized byte array
	 */
	public byte[] toByteArray() {

		byte[] typeCodeAsBytes = integerSerializer.writeIntValue(code);

		byte[] nameBytes = name.getBytes(StandardCharsets.UTF_8);
		byte[] result = new byte[typeCodeAsBytes.length + 2 + nameBytes.length];

		final int codeOffset = 0;
		final int nameLengthOffset = typeCodeAsBytes.length;
		final int nameOffset = nameLengthOffset + 2;

		// Put code
		System.arraycopy(typeCodeAsBytes, 0, result, codeOffset, typeCodeAsBytes.length);

		// Write name length as 2 bytes (big-endian)
		result[nameLengthOffset] = (byte) ((nameBytes.length >> 8) & 0xFF);
		result[nameLengthOffset + 1] = (byte) (nameBytes.length & 0xFF);

		// Write name bytes
		System.arraycopy(nameBytes, 0, result, nameOffset, nameBytes.length);

		return result;
	}

	/**
	 * Deserializes a CommonMessageType from an InputStream.
	 * Format: 4 bytes (code) + 2 bytes (name length) + name bytes (UTF-8)
	 *
	 * @param in the input stream to read from
	 * @return the deserialized CommonMessageType
	 * @throws IOException if an I/O error occurs or the data is malformed
	 */
	public static CommonMessageType fromInputStream(InputStream in) throws IOException {

		// Read code (4 bytes, big-endian)
		CommonIntSerializer integerSerializer = InjectorFactory.getInstance(CommonIntSerializer.class);
		int code = integerSerializer.readIntValue(in);

		// Read name length (2 bytes, big-endian)
		byte[] lengthBytes = in.readNBytes(2);
		if (lengthBytes.length < 2) {
			throw new IOException("Not enough bytes to read name length");
		}
		int nameLength = ((lengthBytes[0] & 0xFF) << 8) | (lengthBytes[1] & 0xFF);

		// Read name bytes
		byte[] nameBytes = in.readNBytes(nameLength);
		if (nameBytes.length < nameLength) {
			throw new IOException("Not enough bytes to read name");
		}
		String name = new String(nameBytes, StandardCharsets.UTF_8);

		return new CommonMessageType(code, name);
	}
}
