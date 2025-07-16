package org.trusky.common.api.network.message.parser;

import org.trusky.common.api.network.message.type.CommonMessage;
import org.trusky.common.api.network.message.type.CommonMessageHeader;
import org.trusky.common.api.network.message.type.CommonMessageType;

import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class CommonMessageParser {
	public interface MessageFactory {
		CommonMessage parse(InputStream in, int payloadLength) throws IOException;
	}

	private static final ConcurrentMap<CommonMessageType, MessageFactory> factories = new ConcurrentHashMap<>();

	public static void register(CommonMessageType type, MessageFactory factory) {
		factories.put(type, factory);
	}

	public static CommonMessage readMessage(InputStream in) throws IOException {
		CommonMessageHeader header = CommonMessageHeader.readFrom(in);
		CommonMessageType type = CommonMessageType.fromCode(header.getTypeCode());
		if (type == null) {
			throw new IOException("Unknown message type code: " + header.getTypeCode());
		}

		MessageFactory factory = factories.get(type);
		if (factory == null) {
			throw new IOException("No factory registered for type: " + type);
		}

		return factory.parse(in, header.getPayloadLength());
	}
}
