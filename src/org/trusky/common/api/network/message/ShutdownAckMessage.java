package org.trusky.common.api.network.message;

import org.trusky.common.api.network.message.parser.CommonMessageParser;
import org.trusky.common.api.network.message.type.CommonMessage;
import org.trusky.common.api.network.message.type.CommonMessageType;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

public class ShutdownAckMessage extends CommonMessage {

	public static final CommonMessageType TYPE = new CommonMessageType(1, "SHUTOWN_ACK");
	public static final String EXPECTED_REPLY = "Shutdown submitted.";

	private final String reply;

	public ShutdownAckMessage(String reply) {
		super(TYPE);
		this.reply = reply;
	}

	public String getReply() {
		return reply;
	}


	@Override
	public byte[] serializePayload() {
		return reply.getBytes(StandardCharsets.UTF_8);
	}

	public static ShutdownAckMessage parse(InputStream in, int length) throws IOException {
		byte[] buf = CommonMessage.readPayload(in, length);
		return new ShutdownAckMessage(new String(buf, StandardCharsets.UTF_8));
	}

	static {
		CommonMessageParser.register(TYPE, ShutdownAckMessage::parse);
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
