package org.trusky.common.api.network.server;

/**
 * Negative codes are fatal errors.
 */
public class ServerExitCodes {

	/**
	 * If using additional codes in derived/client classes please use this value. Define your codes
	 * like<br/><code>private static final int MY_ERROR_CODE = ServerExitCodes.USER_FATAL_ERROR_START;
	 * </code><br/><code>private static final int MY_SECOND_CODE = ServerExitCodes
	 * .USER_FATAL_ERROR_START-1;
	 * </code>
	 * Thus, even if the boundary has to be moved your code will still function correctly withaout the need to be
	 * adjusted.
	 */
	public static final int USER_FATAL_ERROR_START = -1000;

	public static final int SHUTDOWN_LISTENER_NOT_CREATABLE = -1;
	public static final int CONNECTION_LOOP_NOT_CREATEABLE = -2;

	private ServerExitCodes() {
		// Not to be instantiated
	}
}
