package server.requestHandlers;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;

import com.google.gson.JsonSyntaxException;

import core.ResponseStatus;
import core.ServerPorts;
import core.customer.CustomerRequest;
import core.customer.responses.BadCustomerResponse;
import core.customer.responses.IdPricePairResponse;
import ocsf.server.ConnectionToClient;
import server.db.DBConstants.OrderType;
import server.db.DBConstants.TrueFalse;
import server.db.dbAPI.RegularDBAPI;

public class KioskRequestsHandler extends WebCustomerRequestsHandler {
	final protected RegularDBAPI regularDBAPI = RegularDBAPI.getInstance();

	public KioskRequestsHandler(int port) {
		super(port);
	}
	
	protected String orderOccasionalParking(CustomerRequest request) throws SQLException {
		Date rightNow = new Date();
		int entranceID = regularDBAPI.insertParkingReservation(request.carID, request.customerID, request.parkingLotID,
				rightNow, request.estimatedDepartureTime, rightNow, new Date(0), 
				OrderType.ONE_TIME);
		regularDBAPI.insertNewAccount(request.customerID, request.email, request.carID, TrueFalse.FALSE);
		//calculate order price and update the account balance
		double price = priceCalculator.calculateOccasional(request.parkingLotID, rightNow, request.estimatedDepartureTime);
		//TODO: update parking lots info
		return createCustomerResponse(request.requestType, new IdPricePairResponse(entranceID, price));
	}
	protected void enterParkingPreOrdered(CustomerRequest request) {
		//carID 
		//parkingLotID
		Date rightNow = new Date();
		
	}
	protected void enterParkingSubscriber(CustomerRequest request) {
		//carID
		//subscriptionID
		//parkingLotID
		Date rightNow = new Date();
		//server.db.dbAPI.SubscriptionsDBAPI.selectSubscriptionDetails(int, ArrayList<Map<String, Object>>)
		
	}
	protected void exitParking(CustomerRequest request) {
		//carID
		//parkingLotID
		Date rightNow = new Date();
		//TODO carLeftParking get entranceId not carID+parkingLotID
		//regularDBAPI.carLeftParking(int entranceId, rightNow);
		
	}

	private String handleKioskRequest(CustomerRequest request) throws SQLException {
		switch (request.requestType) {
		case OCCASIONAL_PARKING:
			return orderOccasionalParking(request);
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
