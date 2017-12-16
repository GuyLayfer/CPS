package server;

import java.io.IOException;

import com.google.gson.Gson;

import core.CostumerOrder;
import ocsf.server.AbstractServer;
import ocsf.server.ConnectionToClient;

// Added Gson library to the project. It doesn't have to be our weapon of choice for JSONing stuff.
// I chose Gson only because it's my default since WEB course, though I believe it might be suitable
// for us. See reference at https://github.com/google/gson 
// You can also check if JSONing is needed with OCSF. As far as I can understand it, Java will serialize the object and send it
// as a new object, though it will cause problems if wi'll need to deep clone objects, which in this case Gson will
// solve that issue for us.
public class CustomerRequestsManager extends AbstractServer {
	// This is just a mock server for webGui testing
	final private Gson gson = new Gson();
	final public static int DEFAULT_PORT = 5555;
	
	public CustomerRequestsManager(int port) {
		super(port);
	}
	
	public void ChargeAccount() {
//		example method to implement
	}

	public void handleMessageFromClient(Object msg, ConnectionToClient client) {
		CostumerOrder costumerRequest = gson.fromJson((String)msg, CostumerOrder.class);
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
		CustomerRequestsManager customerRequestsManager = new CustomerRequestsManager(DEFAULT_PORT);

		try {
			customerRequestsManager.listen();
		} catch (Exception ex) {
			System.out.println("ERROR - Could not listen for clients!");
		}
	}
}
