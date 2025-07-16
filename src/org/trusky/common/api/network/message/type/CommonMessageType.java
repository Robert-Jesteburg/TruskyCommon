package org.trusky.common.api.network.message.type;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;


public class CommonMessageType {

	private static final ConcurrentMap<Integer, CommonMessageType> registry = new ConcurrentHashMap<>();

	private final int code;
	private final String name;

	public CommonMessageType(int code, String name) {
		this.code = code;
		this.name = name;
		if (registry.putIfAbsent(code, this) != null) {
			throw new IllegalArgumentException("MessageType code already registered: " + code);
		}
	}

	public int getCode() {
		return code;
	}

	public static CommonMessageType fromCode(int code) {
		return registry.get(code);
	}

	@Override
	public String toString() {
		return name + "(" + code + ")";
	}
}
