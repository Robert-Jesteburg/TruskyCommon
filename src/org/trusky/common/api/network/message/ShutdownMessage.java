package org.trusky.common.api.network.message;

import org.trusky.common.api.network.message.parser.CommonMessageParser;
import org.trusky.common.api.network.message.type.CommonMessage;
import org.trusky.common.api.network.message.type.CommonMessageType;

import java.io.IOException;
import java.io.InputStream;

public class ShutdownMessage extends CommonMessage {

	public static final CommonMessageType TYPE = new CommonMessageType(0, "SHUTDOWN");

	protected ShutdownMessage() {
		super(TYPE);
	}

	@Override
	public byte[] serializePayload() {
		return new byte[0];
	}

	public static ShutdownMessage parse(InputStream in, int length) throws IOException {
		byte[] buf = CommonMessage.readPayload(in, length);
		return new ShutdownMessage();
	}

	static {
		CommonMessageParser.register(TYPE, ShutdownMessage::parse);
	}

}
