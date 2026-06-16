package org.trusky.common.impl.network.message.registry;

import org.trusky.common.api.network.message.type.CommonMessageType;
import org.trusky.common.api.network.message.type.registry.CommonCommonMessageTypeProvider;
import org.trusky.common.api.network.message.type.registry.CommonMessageRegistry;

import javax.inject.Inject;

public class CommonMessageRegistryImpl implements CommonMessageRegistry {

	private final CommonCommonMessageTypeProvider commonCommonMessageTypeProvider;

	// FIXME Factory erstellen, die die einzige Registry als static field im Bauch hat
	@Inject
	public CommonMessageRegistryImpl(CommonCommonMessageTypeProvider commonCommonMessageTypeProvider) {

		this.commonCommonMessageTypeProvider = commonCommonMessageTypeProvider;
	}

	@Override
	public CommonMessageType get(int messageType) throws IllegalArgumentException {
		return null;
	}
}
