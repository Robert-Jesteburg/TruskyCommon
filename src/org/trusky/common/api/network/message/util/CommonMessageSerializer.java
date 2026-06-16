package org.trusky.common.api.network.message.util;

import org.trusky.common.api.network.message.type.CommonMessageType;

import java.io.IOException;
import java.io.InputStream;

public interface CommonMessageSerializer {
	/**
	 * Puts the two parts into a bigger byte array and enables for reading it back.
	 *
	 * @param messsageTypeBytes
	 * @param payloadBytes
	 * @return
	 */
	byte[] serializeMessage(byte[] messsageTypeBytes, byte[] payloadBytes);

	/**
	 * Reading back a message, by returning the messageType object as well as the serialized payload.
	 * Converting the pyaload must be done in the concrete message class.
	 *
	 * @param in
	 * @return
	 * @throws IOException
	 */
	MessageComponents deserializeMessage(InputStream in) throws IOException;

	record MessageComponents(CommonMessageType messageType, byte[] payloadBytes) {
	}
}
