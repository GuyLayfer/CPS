package server.requestHandlers;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

import com.google.gson.JsonSyntaxException;

import core.ResponseStatus;
import core.ServerPorts;
import core.customer.CustomerRequest;
import core.customer.CustomerRequestType;
import core.customer.responses.BadCustomerResponse;
import core.customer.responses.CustomerBaseResponse;
import core.customer.responses.CustomerNotificationResponse;
import core.customer.responses.CustomerResponse;
import core.customer.responses.IdPricePairResponse;
import ocsf.server.ConnectionToClient;
import server.db.SqlColumns;
import server.db.DBConstants.OrderType;
import server.db.DBConstants.TrueFalse;
import server.db.dbAPI.RegularDBAPI;

public class KioskRequestsHandler extends WebCustomerRequestsHandler {
	final protected RegularDBAPI regularDBAPI = RegularDBAPI.getInstance();

	public KioskRequestsHandler(int port) {
		super(port);
	}
	
	protected String orderOccasionalParking(CustomerRequest request) throws SQLException {
		//customerID = customerID
		//carID = licensePlate
		//estimatedDepartureTime = estimatedDepartureTime
		//email = email
		//parkingLotID = currentLotId
		
		//check if the customerID exists already
		ArrayList<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
		regularDBAPI.selectCustomerAccountDetails(request.customerID, resultList);
		//if there is no customerID, create new one
		if (resultList.isEmpty())
			regularDBAPI.insertNewAccount(request.customerID, request.email, request.carID, TrueFalse.FALSE);
		
		Date rightNow = new Date();
		int entranceID = regularDBAPI.insertParkingReservation(request.carID, request.customerID, request.parkingLotID,
				rightNow, request.estimatedDepartureTime, rightNow, new Date(0), 
				OrderType.ONE_TIME);
	
		//calculate order price and update the account balance
		double price = priceCalculator.calculateOccasional(request.parkingLotID, rightNow, request.estimatedDepartureTime);
		
		price = updatePriceWithBalance(request.customerID, price);
		
		return createCustomerResponse(request.requestType, new IdPricePairResponse(entranceID, price));
	}
	protected void enterParkingPreOrdered(CustomerRequest request) throws SQLException {
		//carID 
		//parkingLotID
		
		Date rightNow = new Date();
		//TODO: selectOrderByCarIdAndLotIdAndTime  -  get the order details and check if exists + time are correct
	}
	protected String enterParkingSubscriber(CustomerRequest request) throws SQLException {
		//carID
		//subscriptionID
		//parkingLotID
		
		ArrayList<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
		subscriptionsDBAPI.selectSubscriptionDetails(request.subscriptionID, resultList);
		// check if subscription exists.
		if (resultList.isEmpty())
			return createRequestDeniedResponse(request.requestType, "Wrong Subscription ID");
		// check if the subscription fits to the current lot.
		if ( (int)resultList.get(0).get(SqlColumns.Subscriptions.LOT_ID) != request.parkingLotID )
			return createRequestDeniedResponse(request.requestType, "Your subscription is not for this parking lot");
		// check if the subscription fits the current time.
		Date expireDate = (Date)resultList.get(0).get(SqlColumns.Subscriptions.EXPIRED_DATE);
		Date startDate = (Date)resultList.get(0).get(SqlColumns.Subscriptions.START_DATE);
		Date rightNow = new Date();
		if (rightNow.compareTo(expireDate) > 0)
			return createRequestDeniedResponse(request.requestType, "Your subscription has expired");
		if (rightNow.compareTo(startDate) < 0)
			return createRequestDeniedResponse(request.requestType, "Your subscription has not started yet");
		
		regularDBAPI.updateArriveTime(request.carID, rightNow);
		return createNotificationResponse(request.requestType, "Welcome to our amazing parking lot!");
	}
	protected String exitParking(CustomerRequest request) throws SQLException {
		//carID
		//parkingLotID
		
		Date rightNow = new Date();
		//TODO: selectOrderByCarIdAndLotIdAndTime -  to compare the exit time with estimated exit time
		// and fine if need
		regularDBAPI.carLeftParkingByCarId(request.carID, request.parkingLotID, rightNow);
		return createNotificationResponse(request.requestType, "Thank you, goodbye!");
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
	
	protected String createRequestDeniedResponse(CustomerRequestType requestType, String refusalReason) {
		BadCustomerResponse badRequest = new BadCustomerResponse(ResponseStatus.REQUEST_DENIED, refusalReason);
		return gson.toJson(new CustomerResponse(CustomerRequestType.BAD_REQUEST, gson.toJson(badRequest)));
	}
	
	protected String createNotificationResponse(CustomerRequestType requestType, String message) {
		CustomerNotificationResponse response = new CustomerNotificationResponse(requestType, message);
		return gson.toJson(new CustomerResponse(requestType, gson.toJson(response)));
	}

	protected String createCustomerResponse(CustomerRequestType requestType, CustomerBaseResponse response) {
		return gson.toJson(new CustomerResponse(requestType, gson.toJson(response)));
	}
}
