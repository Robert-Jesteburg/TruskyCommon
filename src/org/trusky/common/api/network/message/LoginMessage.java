package org.trusky.common.api.network.message;

import org.trusky.common.api.injection.InjectorFactory;
import org.trusky.common.api.network.message.parser.CommonMessageParser;
import org.trusky.common.api.network.message.type.CommonMessage;
import org.trusky.common.api.network.message.type.CommonMessageType;
import org.trusky.common.api.network.message.util.CommonMessageSerializer;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class LoginMessage extends CommonMessage {

	private final String username;

	public static final CommonMessageType TYPE = new CommonMessageType(2, "LOGIN");

	public LoginMessage(String username) {
		this(InjectorFactory.getInstance(CommonMessageSerializer.class), username);
	}

	public LoginMessage(CommonMessageSerializer messageSerializer, String username) {
		super(messageSerializer, TYPE);
		this.username = username;
	}

	private LoginMessage(byte[] payload) {

		super(InjectorFactory.getInstance(CommonMessageSerializer.class), TYPE);
		this.username = new String(payload, StandardCharsets.UTF_8);
	}

	public String getUsername() {
		return username;
	}

	@Override
	public byte[] serializePayload() {
		return username.getBytes(StandardCharsets.UTF_8);
	}

	public static LoginMessage parse(CommonMessageSerializer serializer, InputStream in) throws IOException {

		CommonMessageSerializer.MessageComponents messageComponents = serializer.deserializeMessage(in);
		return new LoginMessage(messageComponents.payloadBytes());
	}

	static {
		InjectorFactory.getInstance(CommonMessageParser.class)
				.register(TYPE, LoginMessage::parse);
	}
}

