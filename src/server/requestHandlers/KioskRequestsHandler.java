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
import server.parkingLot.exceptions.DateIsNotWithinTheNext24Hours;
import server.parkingLot.exceptions.LotIdDoesntExistException;

public class KioskRequestsHandler extends WebCustomerRequestsHandler {

	public KioskRequestsHandler(int port) {
		super(port);
	}
	
	protected String orderOccasionalParking(CustomerRequest request) throws SQLException, LotIdDoesntExistException, DateIsNotWithinTheNext24Hours {
		//customerID = customerID
		//carID = licensePlate
		//estimatedDepartureTime = estimatedDepartureTime
		//email = email
		//parkingLotID = currentLotId
		
		if (!parkingLotsManager.reservePlace(request.parkingLotID, request.carID, new Date())) {
			return createRequestDeniedResponse(request.requestType, "Unfortunatly, this parking lot " + 
												"is full.\nPlease try another parking lot.");
		}
		String entranceResponse = parkingLotsManager.insertCar(request.parkingLotID, request.carID, 
																request.estimatedDepartureTime, false);
		if (entranceResponse != null) {
			return createRequestDeniedResponse(request.requestType, entranceResponse);
		}
		
		//check if the customerID exists already
		ArrayList<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
		regularDBAPI.selectCustomerAccountDetails(request.customerID, resultList);
		//if there is no customerID, create new one
		if (resultList.isEmpty())
			regularDBAPI.insertNewAccount(request.customerID, request.email, request.carID, TrueFalse.FALSE);
		
		Date rightNow = new Date();
		int entranceID = regularDBAPI.insertParkingReservation(request.carID, request.customerID, request.parkingLotID,
				rightNow, request.estimatedDepartureTime, rightNow, new Date(0), 
				OrderType.ORDER, request.email);
	
		//calculate order price and update the account balance
		double price = priceCalculator.calculateOccasional(request.parkingLotID, rightNow, request.estimatedDepartureTime);
		
		price = updatePriceWithBalance(request.customerID, price);
		
		return createCustomerResponse(request.requestType, new IdPricePairResponse(entranceID, price));
	}
	protected String enterParkingPreOrdered(CustomerRequest request) throws SQLException, LotIdDoesntExistException {
		//carID 
		//parkingLotID
		
		Date rightNow = new Date();
		ArrayList<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
		regularDBAPI.selectOrderByCarIdAndLotIdAndTime(request.carID, request.parkingLotID, rightNow, resultList);
		if (resultList.isEmpty())
			return createRequestDeniedResponse(request.requestType, "Sorry, there is no order for this car.");

		regularDBAPI.updateArriveTime(request.carID, rightNow);
		
		Date estimatedDepartureTime = (Date)resultList.get(0).get(SqlColumns.ParkingTonnage.LEAVE_PREDICTION);

		String entranceResponse = parkingLotsManager.insertCar(request.parkingLotID, request.carID, 
				estimatedDepartureTime, false);
		if (entranceResponse != null) {
			return createRequestDeniedResponse(request.requestType, entranceResponse);
		}
		
		return createNotificationResponse(request.requestType, "Your car has been entered successfuly.");
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
	protected String exitParking(CustomerRequest request) throws SQLException, LotIdDoesntExistException {
		//carID
		//parkingLotID
		
		String exitResponse = parkingLotsManager.removeCar(request.parkingLotID, request.carID);
		if (exitResponse != null) {
			return createRequestDeniedResponse(request.requestType, exitResponse);
		}
		
		Date rightNow = new Date();
		ArrayList<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
		regularDBAPI.selectOrderByCarIdAndLotIdAndTime(request.carID, request.parkingLotID, rightNow, resultList);
		
		//check if was late
		Date estimatedDepartureTime = (Date)resultList.get(0).get(SqlColumns.ParkingTonnage.LEAVE_PREDICTION);
		if (rightNow.compareTo(estimatedDepartureTime) <= 0) {
			regularDBAPI.carLeftParkingByCarId(request.carID, request.parkingLotID, rightNow);
			return createNotificationResponse(request.requestType, "Thank you, goodbye!");
		} else {
			OrderType orderType = (OrderType)resultList.get(0).get(SqlColumns.ParkingTonnage.ORDER_TYPE);
			long miliLate = rightNow.getTime() - estimatedDepartureTime.getTime();
			double fine = priceCalculator.calculateFine(orderType, request.parkingLotID, miliLate);
			return createNotificationResponse(request.requestType, "You are late! you are charged with " + fine + " NIS.");
		}

	}

	private String handleKioskRequest(CustomerRequest request) throws SQLException, LotIdDoesntExistException, DateIsNotWithinTheNext24Hours {
		switch (request.requestType) {
		case OCCASIONAL_PARKING:
			return orderOccasionalParking(request);
		case ENTER_PARKING_PRE_ORDERED: // TODO: implement
			return enterParkingPreOrdered(request);
		case ENTER_PARKING_SUBSCRIBER: // TODO: implement
			return createUnsupportedFeatureResponse(request.requestType);
		case EXIT_PARKING: // TODO: implement
			return exitParking(request);
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
			} catch (LotIdDoesntExistException e) {
				client.sendToClient(gson.toJson(new BadCustomerResponse(ResponseStatus.BAD_REQUEST, "LotIdDoesntExistException")));
				e.printStackTrace();
			} catch (SQLException | DateIsNotWithinTheNext24Hours e) {
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
