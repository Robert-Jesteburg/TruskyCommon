package org.trusky.common.api.network.implementators;

/**
 * The processor will be given a CommonSSLSocket object to handle the communication with that client. After
 * finishing, simply return from the {@link CommonSocketListener#start() start()} method.
 *
 * <p>The connection proocessor's strat() method will be called on a new thread; on closing the connection (on
 * behalf of the client oor the server, that's up to the implementer) simply return from the start() method.</p>
 */
public interface CommonConnectionProcessor extends CommonSocketListener {
}
