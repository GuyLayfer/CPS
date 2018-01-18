package server;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;

import com.google.gson.JsonSyntaxException;

import core.IdPricePair;
import core.ResponseStatus;
import core.customer.CustomerRequest;
import core.customer.CustomerResponse;
import ocsf.server.ConnectionToClient;
import server.db.DBConstants.OrderType;
import server.db.DBConstants.TrueFalse;

public class KioskRequestsHandler extends WebCustomerRequestsHandler {

	public KioskRequestsHandler(int port) {
		super(port);
	}
	
	protected String orderPreOrderedParking(CustomerRequest request) throws SQLException {
		int entranceID = regularDBAPI.insertParkingReservation(request.carID, request.customerID, request.parkingLotID,
				request.arrivalTime, request.estimatedDepartureTime, new Date(0), new Date(0), 
				OrderType.ONE_TIME);
		regularDBAPI.insertNewAccount(request.customerID, request.email, request.carID, TrueFalse.FALSE);
		//calculate order price and update the account balance
		double price = priceCalculator.calculatePreOrdered(request.parkingLotID, request.arrivalTime, request.estimatedDepartureTime);
		regularDBAPI.updateCustomerBalance(request.customerID, price);
		//TODO: update parking lots info
		return createOkResponse(request.requestType, gson.toJson(new IdPricePair(entranceID, price)));
	}
	
	@Override
	protected void handleMessageFromClient(Object msg, ConnectionToClient client) {
		try {
			CustomerRequest request = gson.fromJson((String)msg, CustomerRequest.class);
			switch (request.requestType) {
			case OCCASIONAL_PARKING: // TODO: implement
				// orderOccasionalParking copied from WebCustomerHandler
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
