package org.trusky.common.api.network.message;

import org.trusky.common.api.network.message.parser.CommonMessageParser;
import org.trusky.common.api.network.message.type.CommonMessage;
import org.trusky.common.api.network.message.type.CommonMessageType;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class LoginMessage extends CommonMessage {
	private final String username;

	public static final CommonMessageType TYPE = new CommonMessageType(2, "LOGIN");

	public LoginMessage(String username) {
		super(TYPE);
		this.username = username;
	}

	public String getUsername() {
		return username;
	}

	@Override
	public byte[] serializePayload() {
		return username.getBytes(StandardCharsets.UTF_8);
	}

	public static LoginMessage parse(InputStream in, int length) throws IOException {
		byte[] buf = CommonMessage.readPayload(in, length);
		return new LoginMessage(new String(buf, StandardCharsets.UTF_8));
	}

	static {
		CommonMessageParser.register(TYPE, LoginMessage::parse);
	}
}

