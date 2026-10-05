package org.trusky.common.mock.network.server;

import org.trusky.common.api.network.server.RunnableExecutor;

/**
 * Fake implementation of RunnableExecutor for testing purposes.
 * Executes the provided Runnable synchronously in the current thread.
 */
public class RunnableExecutorFake implements RunnableExecutor {

	/**
	 * Executes the given Runnable immediately in the current thread.
	 *
	 * @param runnable the code to execute
	 */
	@Override
	public void execute(Runnable runnable) {
		runnable.run();
	}
}

