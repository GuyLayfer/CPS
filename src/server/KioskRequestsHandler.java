package server;

import java.io.IOException;
import java.sql.SQLException;

import com.google.gson.JsonSyntaxException;

import core.ResponseStatus;
import core.custome.CustomerRequest;
import core.custome.CustomerResponse;
import ocsf.server.ConnectionToClient;

public class KioskRequestsHandler extends WebCustomerRequestsHandler {

	public KioskRequestsHandler(int port, IDsGenerator idsGenerator) {
		super(port, idsGenerator);
	}
	
	@Override
	protected void handleMessageFromClient(Object msg, ConnectionToClient client) {
		try {
			CustomerRequest request = gson.fromJson((String)msg, CustomerRequest.class);
			switch (request.requestType) {
			case OCCASIONAL_PARKING: // TODO: implement
				client.sendToClient(createUnsupportedFeatureResponse(request.requestType));
			case ENTER_PARKING: // TODO: implement
				client.sendToClient(createUnsupportedFeatureResponse(request.requestType));
			case EXIT_PARKING: // TODO: implement
				client.sendToClient(createUnsupportedFeatureResponse(request.requestType));
			default:
				client.sendToClient(handleWebCustomerRequest(request));
			}
		} catch (JsonSyntaxException e) {
			try {
				client.sendToClient(gson.toJson(new CustomerResponse(ResponseStatus.BAD_REQUEST, "JsonSyntaxException")));
				e.printStackTrace();
			} catch (IOException e2) {
				e2.printStackTrace();
			}
			e.printStackTrace();
		} catch (SQLException e) {
			try {
				client.sendToClient(gson.toJson(new CustomerResponse(ResponseStatus.SERVER_FAILLURE, "Server Failure")));
				e.printStackTrace();
			} catch (IOException e2) {
				e2.printStackTrace();
			}
			e.printStackTrace();
		} catch (IOException e) {
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
