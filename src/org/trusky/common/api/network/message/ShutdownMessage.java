package org.trusky.common.api.network.message;

import org.trusky.common.api.injection.InjectorFactory;
import org.trusky.common.api.network.message.parser.CommonMessageParser;
import org.trusky.common.api.network.message.type.CommonMessage;
import org.trusky.common.api.network.message.type.CommonMessageType;
import org.trusky.common.api.network.message.util.CommonMessageSerializer;

import java.io.IOException;
import java.io.InputStream;

public class ShutdownMessage extends CommonMessage {

	public static final CommonMessageType TYPE = new CommonMessageType(0, "SHUTDOWN");

	protected ShutdownMessage() {
		this(InjectorFactory.getInstance(CommonMessageSerializer.class));
	}

	protected ShutdownMessage(CommonMessageSerializer serializer) {
		super(serializer, TYPE);
	}

	@Override
	public byte[] serializePayload() {
		return new byte[0];
	}

	public static ShutdownMessage parse(CommonMessageSerializer serializer, InputStream in) throws IOException {

		// Shutdown message has no payload, so serializer is not used
		return new ShutdownMessage(serializer);
	}

	static {
		InjectorFactory.getInstance(CommonMessageParser.class)
				.register(TYPE, ShutdownMessage::parse);
	}

}
