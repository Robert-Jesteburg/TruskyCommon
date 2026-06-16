package org.trusky.common.api.network.message.type.registry;

import org.trusky.common.api.network.message.type.CommonMessageType;

public interface CommonMessageRegistry {

	CommonMessageType get(int messageType) throws IllegalArgumentException;
}
