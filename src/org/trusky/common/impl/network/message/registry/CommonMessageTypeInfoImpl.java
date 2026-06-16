package org.trusky.common.impl.network.message.registry;

import org.trusky.common.api.network.message.type.registry.CommonMessageTypeInfo;

public class CommonMessageTypeInfoImpl implements CommonMessageTypeInfo {

	private final int messageTypeId;
	private final String messageTypeName;

	public CommonMessageTypeInfoImpl(int messageTypeId, String messageTypeName) {
		this.messageTypeId = messageTypeId;
		this.messageTypeName = messageTypeName;
	}

	@Override
	public int getMessageTypeId() {
		return messageTypeId;
	}

	@Override
	public String getMessageTypeName() {
		return messageTypeName;
	}
}
