package org.trusky.common.impl.network.message.parser;

import org.trusky.common.api.injection.InjectorFactory;
import org.trusky.common.api.network.message.parser.CommonMessageParser;
import org.trusky.common.api.network.message.type.CommonMessage;
import org.trusky.common.api.network.message.type.CommonMessageType;
import org.trusky.common.api.network.message.util.CommonMessageSerializer;

import javax.inject.Inject;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class CommonMessageParserImpl implements CommonMessageParser {

	public interface MessageFactory {
		CommonMessage parse(CommonMessageSerializer messageSerializer, InputStream in) throws IOException;
	}

	private static final ConcurrentMap<CommonMessageType, MessageFactory> factories = new ConcurrentHashMap<>();

	private final CommonMessageSerializer messageSerializer;

	@Inject
	private CommonMessageParserImpl() {
		this(InjectorFactory.getInstance(CommonMessageSerializer.class));
	}

	public CommonMessageParserImpl(CommonMessageSerializer messageSerializer) {
		this.messageSerializer = messageSerializer;
	}

	@Override
	public void register(CommonMessageType type, CommonMessageParserImpl.MessageFactory factory) {
		CommonMessageParserImpl.factories.put(type, factory);
	}

	@Override
	public <T extends CommonMessage> T readMessage(InputStream in) throws IOException {

		CommonMessageSerializer.MessageComponents messageComponents = messageSerializer.deserializeMessage(in);
		CommonMessageType commonMessageType = messageComponents.messageType();

		CommonMessageParserImpl.MessageFactory factory = CommonMessageParserImpl.factories.get(commonMessageType);
		if (factory == null) {
			throw new IOException("No factory registered for type: " + commonMessageType.getCode());
		}


		CommonMessage message = factory.parse(messageSerializer, in);

		// Cannot use `instanceof` with a type parameter (T) because of type erasure.
		// Perform an unchecked cast and translate a ClassCastException to IOException
		// so the method signature remains unchanged and the code is compilable.
		try {
			@SuppressWarnings("unchecked")
			T casted = (T) message;
			return casted;
		} catch (ClassCastException e) {
			throw new IOException("Message type not supported: " + message.getClass().getName(), e);
		}
	}
}
