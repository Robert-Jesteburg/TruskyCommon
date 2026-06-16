package org.trusky.common.api.network.message;

import org.trusky.common.api.injection.InjectorFactory;
import org.trusky.common.api.network.message.parser.CommonMessageParser;
import org.trusky.common.api.network.message.type.CommonMessage;
import org.trusky.common.api.network.message.type.CommonMessageType;
import org.trusky.common.api.network.message.util.CommonMessageSerializer;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

public class ShutdownAckMessage extends CommonMessage {

	public static final CommonMessageType TYPE = new CommonMessageType(1, "SHUTOWN_ACK");
	public static final String EXPECTED_REPLY = "Shutdown submitted.";

	private final String reply;

	public ShutdownAckMessage(String reply) {
		this(InjectorFactory.getInstance(CommonMessageSerializer.class), reply);
	}

	public ShutdownAckMessage(CommonMessageSerializer serializer, String reply) {
		super(serializer, TYPE);
		this.reply = reply;
	}

	public String getReply() {
		return reply;
	}


	@Override
	public byte[] serializePayload() {
		return reply.getBytes(StandardCharsets.UTF_8);
	}

	public static ShutdownAckMessage parse(CommonMessageSerializer serializer, InputStream in) throws IOException {

		CommonMessageSerializer.MessageComponents messageComponents = serializer.deserializeMessage(in);
		return new ShutdownAckMessage(new String(messageComponents.payloadBytes(), StandardCharsets.UTF_8));
	}

	static {
		InjectorFactory.getInstance(CommonMessageParser.class)
				.register(TYPE, ShutdownAckMessage::parse);
	}

	@Override
	public boolean equals(Object obj) {

		if (!super.equals(obj)) {
			return false;
		}

		if (!(obj instanceof ShutdownAckMessage other)) {
			return false;
		}

		return super.equals(obj) && Objects.equals(this.getReply(), other.getReply());
	}
}
