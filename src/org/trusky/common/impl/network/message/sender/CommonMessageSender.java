package org.trusky.common.impl.network.message.sender;

import org.trusky.common.api.network.message.type.CommonMessage;

import java.io.IOException;
import java.io.OutputStream;

public interface CommonMessageSender {

	void writeMessage(CommonMessage msg, OutputStream outputStream) throws IOException;
}
