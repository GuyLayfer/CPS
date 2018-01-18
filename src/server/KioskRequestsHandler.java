package server;

import java.io.IOException;
import java.sql.SQLException;

import com.google.gson.JsonSyntaxException;

import core.ResponseStatus;
import core.ServerPorts;
import core.customer.CustomerRequest;
import core.customer.responses.BadCustomerResponse;
import ocsf.server.ConnectionToClient;

public class KioskRequestsHandler extends WebCustomerRequestsHandler {

	public KioskRequestsHandler(int port) {
		super(port);
	}

	private String handleKioskRequest(CustomerRequest request) throws SQLException {
		switch (request.requestType) {
		case OCCASIONAL_PARKING: // TODO: implement
			return createUnsupportedFeatureResponse(request.requestType);
		case ENTER_PARKING_PRE_ORDERED: // TODO: implement
			return createUnsupportedFeatureResponse(request.requestType);
		case ENTER_PARKING_SUBSCRIBER: // TODO: implement
			return createUnsupportedFeatureResponse(request.requestType);
		case EXIT_PARKING: // TODO: implement
			return createUnsupportedFeatureResponse(request.requestType);
		case PARKING_LOT_NAMES:
			return parkingLotNames(request);
		default:
			if (getPort() == ServerPorts.KIOSK_PORT) {
				return gson.toJson(new BadCustomerResponse(ResponseStatus.BAD_REQUEST, request.requestType + " is illegal Web CustomerRequestType"));
			} else {
				return gson.toJson(new BadCustomerResponse(ResponseStatus.BAD_REQUEST, request.requestType + " is illegal CustomerRequestType"));
			}
		}
	}

	@Override
	protected void handleMessageFromClient(Object msg, ConnectionToClient client) {
		try {
			try {
				client.sendToClient(handleKioskRequest(gson.fromJson((String) msg, CustomerRequest.class)));

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
