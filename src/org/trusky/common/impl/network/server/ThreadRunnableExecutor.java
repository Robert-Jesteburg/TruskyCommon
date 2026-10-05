package org.trusky.common.impl.network.server;

import org.trusky.common.api.network.server.RunnableExecutor;

/**
 * Default implementation of RunnableExecutor that creates new threads for each runnable.
 */
public class ThreadRunnableExecutor implements RunnableExecutor {

	@Override
	public void execute(Runnable runnable) {
		new Thread(runnable).start();
	}
}

