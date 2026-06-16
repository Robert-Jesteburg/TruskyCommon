package org.trusky.common.impl.network.message.registry;

import org.trusky.common.api.network.message.type.registry.CommonCommonMessageTypeProvider;
import org.trusky.common.api.network.message.type.registry.CommonMessageTypeInfo;

import java.util.List;

public class CommonCommonMessageTypeProviderImpl implements CommonCommonMessageTypeProvider {
	@Override
	public List<CommonMessageTypeInfo> getMessageTypes() {
		return List.of( //
				new CommonMessageTypeInfoImpl(0, "SHUTDOWN"), //
				new CommonMessageTypeInfoImpl(1, "SHUTOWN_ACK"), //
				new CommonMessageTypeInfoImpl(2, "LOGIN"));
	}
}
