package server.requestHandlers.worker;

import java.util.List;

import ocsf.server.ConnectionToClient;

public interface IProvideConnectionsToClient {
	
	public List<ConnectionToClient> getConnections();
}
