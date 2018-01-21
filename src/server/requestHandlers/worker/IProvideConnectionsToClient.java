package server.requestHandlers.worker;

import java.util.List;

import ocsf.server.ConnectionToClient;

// TODO: Auto-generated Javadoc
/**
 * The Interface IProvideConnectionsToClient.
 */
public interface IProvideConnectionsToClient {
	
	/**
	 * Gets the connections.
	 *
	 * @return the connections
	 */
	public List<ConnectionToClient> getConnections();
}
