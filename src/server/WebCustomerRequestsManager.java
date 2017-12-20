package server;

import ocsf.server.AbstractServer;
import ocsf.server.ConnectionToClient;

import com.google.gson.Gson;

import com.google.gson.JsonSyntaxException;
import java.io.IOException;
import java.sql.SQLException;

import core.ServerBasicResponse;
import core.ServerGenericResponse;
import core.ServerResponseStatus;
import core.WebCustomerRequest;

/*
 * This is a temporary mock server which was created in order to simplify the implementation of the prototype.
 * This class and the whole server will be changed radically after the prototype submission. 
 */
public class WebCustomerRequestsManager extends AbstractServer {
	// TODO: move it to another place after the prototype submission
	final private Gson gson = new Gson();
	final public static int DEFAULT_PORT = 5555;
	
	// TODO: move it to another place after the prototype submission
	private int lastOrderId;
	
	public WebCustomerRequestsManager(int port) {
		super(port);
		// TODO: retrieve the last order id from DB and initialize lastOrderId.
	}
	
	public void ChargeAccount() {
		// TODO: implement
	}

	public void handleMessageFromClient(Object msg, ConnectionToClient client) {
		try {
			WebCustomerRequest request = gson.fromJson((String)msg, WebCustomerRequest.class);
			switch (request.webCustomerRequestType) {
			case ORDER_ONE_TIME_PARKING:
				++lastOrderId;
				// TODO: store the order data in the DB
				client.sendToClient(new ServerGenericResponse(String.valueOf(lastOrderId)));
				break;
			case CANCEL_ORDER:
				// TODO: implement
				break;
			case TRACK_ORDER_STATUS:
				// TODO: retrieve the order data from DB and send it to the client
				break;
			case ORDER_ROUTINE_MONTHLY_SUBSCRIPTION:
				// TODO: implement
				break;
			case ORDER_FULL_MONTHLY_SUBSCRIPTION:
				// TODO: implement
				break;
			case SUBSCRIPTION_RENEWAL:
				// TODO: implement
				break;
			case OPEN_COMPLAINT:
				// TODO: implement
				break;
			default:
				// TODO: implement
				break;
			}
		} catch (JsonSyntaxException e) {
			try {
				client.sendToClient(new ServerBasicResponse(ServerResponseStatus.BAD_REQUEST, "JsonSyntaxException"));
				e.printStackTrace();
			} catch (IOException e2) {
				e2.printStackTrace();
			}
			e.printStackTrace();
		} catch (SQLException e) { // compilation error will be fixed after adding the DB communication functions
			try {
				client.sendToClient(new ServerBasicResponse(ServerResponseStatus.SERVER_FAILLURE, "Server Failure"));
				e.printStackTrace();
			} catch (IOException e2) {
				e2.printStackTrace();
			}
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
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
