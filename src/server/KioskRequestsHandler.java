package server;

import java.io.IOException;
import java.sql.SQLException;

import com.google.gson.JsonSyntaxException;

import core.ResponseStatus;
import core.customer.CustomerRequest;
import core.customer.responses.BadCustomerResponse;
import ocsf.server.ConnectionToClient;

public class KioskRequestsHandler extends WebCustomerRequestsHandler {

	public KioskRequestsHandler(int port) {
		super(port);
	}
	
	@Override
	protected void handleMessageFromClient(Object msg, ConnectionToClient client) {
		try {
			try {
				CustomerRequest request = gson.fromJson((String) msg, CustomerRequest.class);
				switch (request.requestType) {
				case OCCASIONAL_PARKING: // TODO: implement
					client.sendToClient(createUnsupportedFeatureResponse(request.requestType));
				case ENTER_PARKING_PRE_ORDERED: // TODO: implement
					client.sendToClient(createUnsupportedFeatureResponse(request.requestType));
				case ENTER_PARKING_SUBSCRIBER: // TODO: implement
					client.sendToClient(createUnsupportedFeatureResponse(request.requestType));
				case EXIT_PARKING: // TODO: implement
					client.sendToClient(createUnsupportedFeatureResponse(request.requestType));
				default:
					client.sendToClient(handleWebCustomerRequest(request));
				}
			} catch (JsonSyntaxException e) {
				client.sendToClient(gson.toJson(new BadCustomerResponse(ResponseStatus.BAD_REQUEST, "JsonSyntaxException")));
				e.printStackTrace();
			} catch (SQLException e) {
				client.sendToClient(gson.toJson(new BadCustomerResponse(ResponseStatus.SERVER_FAILLURE, "Server Failure")));
				e.printStackTrace();
			}
		} catch (IOException e) {
			System.out.println("Could not send message to client.\nPlease check your connection.");
			e.printStackTrace();
		}
	}

	@Override
	protected void serverStarted() {
		System.out.println("Kiosk Server is listening for connections on port " + getPort());
	}

	@Override
	protected void serverStopped() {
		System.out.println("Kiosk Server has stopped listening for connections.");
	}
}
