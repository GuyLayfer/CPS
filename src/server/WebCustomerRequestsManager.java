package server;

import java.io.IOException;

import com.google.gson.Gson;

import core.WebCustomerRequest;
import ocsf.server.AbstractServer;
import ocsf.server.ConnectionToClient;

public class WebCustomerRequestsManager extends AbstractServer {
	// This is just a mock server for webGui testing
	final private Gson gson = new Gson();
	final public static int DEFAULT_PORT = 5555;
	
	public WebCustomerRequestsManager(int port) {
		super(port);
	}
	
	public void ChargeAccount() {
//		example method to implement
	}

	public void handleMessageFromClient(Object msg, ConnectionToClient client) {
		WebCustomerRequest costumerRequest = gson.fromJson((String)msg, WebCustomerRequest.class);
		System.out.println("Request received:\n" + costumerRequest.toString() + "\nfrom " + client);
		try {
			client.sendToClient("Got the message dear mister " + client + "!");
		} catch (IOException e) {
			e.printStackTrace();
		}
		
//		Do stuff or something
	}

	protected void serverStarted() {
		System.out.println("Server listening for connections on port " + getPort());
	}

	protected void serverStopped() {
		System.out.println("Server has stopped listening for connections.");
	}

// Mock main. Should be deleted and code moved to the initialization of the whole server.
	public static void main(String[] args) {
		WebCustomerRequestsManager customerRequestsManager = new WebCustomerRequestsManager(DEFAULT_PORT);

		try {
			customerRequestsManager.listen();
		} catch (Exception ex) {
			System.out.println("ERROR - Could not listen for clients!");
		}
	}
}
