package server;

import com.google.gson.Gson;

import ocsf.server.AbstractServer;
import ocsf.server.ConnectionToClient;

public class WorkerRequestsHandler extends AbstractServer {
	final protected Gson gson = new Gson();
	//TODO: add required properties
	
	public WorkerRequestsHandler(int port) {
		super(port);
	}

	@Override
	protected void handleMessageFromClient(Object msg, ConnectionToClient client) {
		// TODO: implement

	}
	
	@Override
	protected void serverStarted() {
		System.out.println("Worker Server is listening for connections on port " + getPort());
	}

	@Override
	protected void serverStopped() {
		System.out.println("Worker Server has stopped listening for connections.");
	}

}
