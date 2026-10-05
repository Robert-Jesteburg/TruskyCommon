package org.trusky.common.api.network.server;

/**
 * Executes a Runnable. This abstraction allows for dependency injection of thread creation,
 * making the code more testable by allowing tests to execute runnables synchronously
 * or in a controlled manner instead of creating actual threads.
 */
public interface RunnableExecutor {

	/**
	 * Executes the given Runnable. Implementations may execute it in a new thread,
	 * a thread pool, or synchronously, depending on the use case.
	 *
	 * <p>Usage example: <br/>
	 * <code>runnableExecutor.execute(() -> handler.handleCommunication());</code></p>
	 *
	 * @param runnable the code to execute
	 */
	void execute(Runnable runnable);
}

