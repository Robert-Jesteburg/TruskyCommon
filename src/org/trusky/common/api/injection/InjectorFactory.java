package org.trusky.common.api.injection;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;

/**
 * Factory that stores the injector instance.
 */
public class InjectorFactory {

	private static Injector INJECTOR = null;
	private static AbstractModule configModule = null;

	public static Injector getInjector() {

		if (INJECTOR == null) {
			INJECTOR = (configModule == null) ? Guice.createInjector() : Guice.createInjector(configModule);
		}

		return INJECTOR;
	}

	public static <T> T getInstance(Class<T> cls) {
		return getInjector().getInstance(cls);

	}

	public static void setModule(AbstractModule configurationModule) {

		if (configurationModule == null) {
			throw new IllegalArgumentException("Configuration module must not be NULL.");
		}

		configModule = configurationModule;
	}
}