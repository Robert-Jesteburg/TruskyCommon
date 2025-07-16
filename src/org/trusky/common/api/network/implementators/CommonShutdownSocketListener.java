package org.trusky.common.api.network.implementators;

import org.trusky.common.api.network.implementators.factory.CommonShutdownSocketFactory;

/**
 * Listen to the shutdown command: This interface must be implemented by the client code to handle the request
 * correctly (for example, check authorisation first before allowing to shut down).
 *
 * <p>The shutdown command will be on a specific port; on the process of shutdown
 * authorisation may be checked. The inherited {@link CommonSocketListener#start()} method will be called on a
 * separate thread. After successfully setting the shutdown flag in the container (see
 * {@link CommonShutdownSocketFactory}) simply return from the start()
 * method.
 * </p>
 */
public interface CommonShutdownSocketListener extends CommonSocketListener {
}
