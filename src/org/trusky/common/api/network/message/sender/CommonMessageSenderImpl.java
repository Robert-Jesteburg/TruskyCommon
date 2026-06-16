package org.trusky.common.api.network.message.sender;

import org.trusky.common.api.network.message.type.CommonMessage;
import org.trusky.common.api.network.message.type.CommonMessageHeader;
import org.trusky.common.impl.network.message.sender.CommonMessageSender;

import java.io.IOException;
import java.io.OutputStream;

public class CommonMessageSenderImpl implements CommonMessageSender {

	@Override
	public void writeMessage(CommonMessage msg, OutputStream outputStream) throws IOException {

		byte[] msgPayload = msg.serializePayload();
		CommonMessageHeader header = new CommonMessageHeader(msg.getType()
				.getCode(), msgPayload.length);

		header.writeTo(outputStream);
		outputStream.write(msgPayload);
	}


}
