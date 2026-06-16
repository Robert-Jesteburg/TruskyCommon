package org.trusky.common.api.network.message.parser;

import org.trusky.common.api.network.message.type.CommonMessage;
import org.trusky.common.api.network.message.type.CommonMessageType;
import org.trusky.common.impl.network.message.parser.CommonMessageParserImpl;

import java.io.IOException;
import java.io.InputStream;

public interface CommonMessageParser {

	void register(CommonMessageType type, CommonMessageParserImpl.MessageFactory factory);

	/**
	 *
	 * @param in
	 * @param <T>
	 * @return
	 * @throws IOException Fur usual reasons as well as if the messsage read isn't of type T. So, if it's not
	 *                     completely sure the next message is T, simple read a Common Message and convert it lateron.
	 */
	<T extends CommonMessage> T readMessage(InputStream in) throws IOException;
}
