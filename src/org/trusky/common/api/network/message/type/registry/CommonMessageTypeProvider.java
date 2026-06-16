package org.trusky.common.api.network.message.type.registry;

import java.util.List;

/**
 * Provides a list of all Message types. There are two implementations: One (implemented) for the common messages and
 * one that must be implemented by the client code.
 */
public interface CommonMessageTypeProvider {

	/**
	 * The list returned may be empty, but never NULL. Also, identifiers are listed only once.
	 *
	 * @return See above
	 */
	List<CommonMessageTypeInfo> getMessageTypes();
}
