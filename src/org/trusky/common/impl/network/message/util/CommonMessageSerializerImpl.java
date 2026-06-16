package org.trusky.common.impl.network.message.util;

import org.trusky.common.api.network.message.type.CommonMessageType;
import org.trusky.common.api.network.message.util.CommonIntSerializer;
import org.trusky.common.api.network.message.util.CommonMessageSerializer;

import javax.inject.Inject;
import java.io.IOException;
import java.io.InputStream;

public class CommonMessageSerializerImpl implements CommonMessageSerializer {

	private final CommonIntSerializer intSerializer;

	@Inject
	public CommonMessageSerializerImpl(CommonIntSerializer intSerializer) {
		this.intSerializer = intSerializer;
	}

	@Override
	public byte[] serializeMessage(byte[] messsageTypeBytes, byte[] payloadBytes) {


		int payloadLength = payloadBytes.length;
		byte[] lengthBytes = intSerializer.writeIntValue(payloadLength);

		byte[] result = new byte[messsageTypeBytes.length + lengthBytes.length + payloadLength];

		int offsetMessageType = 0;
		int offsetPayloadLength = messsageTypeBytes.length;
		int offsetPayload = offsetPayloadLength + lengthBytes.length;

		System.arraycopy(messsageTypeBytes, 0, result, offsetMessageType, messsageTypeBytes.length);
		System.arraycopy(lengthBytes, 0, result, offsetPayloadLength, lengthBytes.length);
		System.arraycopy(payloadBytes, 0, result, offsetPayload, payloadLength);

		return result;

	}

	@Override
	public MessageComponents deserializeMessage(InputStream in) throws IOException {

		CommonMessageType messageType = CommonMessageType.fromInputStream(in);

		int payloadLength = intSerializer.readIntValue(in);
		byte[] payload = in.readNBytes(payloadLength);

		return new MessageComponents(messageType, payload);
	}

}
