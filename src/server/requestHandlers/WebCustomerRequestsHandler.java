package server.requestHandlers;

import ocsf.server.AbstractServer;
import ocsf.server.ConnectionToClient;
import server.db.DBConstants.OrderType;
import server.db.DBConstants.TrueFalse;
import server.db.SqlColumns;
import server.db.dbAPI.RegularDBAPI;
import server.parkingLot.ParkingLotsManager;
import server.rates.PriceCalculator;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

import core.CpsGson;
import core.ResponseStatus;
import core.ServerPorts;
import core.customer.CustomerRequest;
import core.customer.CustomerRequestType;
import core.customer.responses.BadCustomerResponse;
import core.customer.responses.CustomerBaseResponse;
import core.customer.responses.CustomerNotificationResponse;
import core.customer.responses.CustomerResponse;
import core.customer.responses.IdPricePairResponse;
import core.customer.responses.ParkingLotsNamesForCustomerResponse;
import core.customer.responses.TrackOrderResponse;

public class WebCustomerRequestsHandler extends AbstractServer {
	final protected Gson gson = CpsGson.GetGson();
	final protected RegularDBAPI regularDBAPI = RegularDBAPI.getInstance();
	final protected ParkingLotsManager parkingLotsManager = ParkingLotsManager.getInstance();
	final protected PriceCalculator priceCalculator = PriceCalculator.getInstance();
	
	public WebCustomerRequestsHandler(int port) {
		super(port);
	}
	
	protected void ChargeAccount() {
		// TODO: implement
	}
	
	protected String orderPreOrderedParking(CustomerRequest request) throws SQLException {
		int entranceID = regularDBAPI.insertParkingReservation(request.carID, request.customerID, request.parkingLotID,
				request.arrivalTime, request.estimatedDepartureTime, new Date(0), new Date(0), 
				OrderType.ORDER);
		regularDBAPI.insertNewAccount(request.customerID, request.email, request.carID, TrueFalse.FALSE);
		//calculate order price and update the account balance
		double price = priceCalculator.calculatePreOrdered(request.parkingLotID, request.arrivalTime, request.estimatedDepartureTime);
		regularDBAPI.updateCustomerBalance(request.customerID, price);
		//TODO: update parking lots info
		return createCustomerResponse(request.requestType, new IdPricePairResponse(entranceID, price));
	}

	protected String cancelOrder(CustomerRequest request) throws SQLException {
		ArrayList<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
		regularDBAPI.selectOrderStatus(request.orderID, resultList);
		if (resultList.isEmpty()) {
			return createRequestDeniedResponse(request.requestType, "Wrong Order ID");
		} else {
			Map<String, Object> result = resultList.iterator().next();
			double refund = priceCalculator.calculateCancelRefund(((int) result.get(SqlColumns.ParkingTonnage.LOT_ID)),
					((Date) result.get(SqlColumns.ParkingTonnage.ARRIVE_PREDICTION)), ((Date) result.get(SqlColumns.ParkingTonnage.LEAVE_PREDICTION)));
			regularDBAPI.cancelOrder(request.orderID, -refund);
			return createNotificationResponse(request.requestType, "You are acquited with " + refund + "NIS.");
		}
	}
	
	protected String trackOrderStatus(CustomerRequest request) throws SQLException {
		ArrayList<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
		regularDBAPI.selectOrderStatus(request.orderID, resultList);

		if (resultList.isEmpty()) {
			return createRequestDeniedResponse(request.requestType, "Wrong Order ID");
		} else {
			Map<String, Object> result = resultList.iterator().next();
			TrackOrderResponse response = new TrackOrderResponse();

			response.requestType = CustomerRequestType.TRACK_ORDER_STATUS;
			response.orderID = (Integer) result.get(SqlColumns.ParkingTonnage.ENTRANCE_ID);
			response.customerID = (Integer) result.get(SqlColumns.ParkingTonnage.ACCOUNT_ID);
			response.carID = result.get(SqlColumns.ParkingTonnage.CAR_ID).toString();
			response.parkingLotID = (Integer) result.get(SqlColumns.ParkingTonnage.LOT_ID);
			response.arrivalTime = (Date)result.get(SqlColumns.ParkingTonnage.ARRIVE_PREDICTION);
			response.estimatedDepartureTime = (Date)result.get(SqlColumns.ParkingTonnage.LEAVE_PREDICTION);
			return createCustomerResponse(request.requestType, response);
		}
	}
	
	protected String orderRoutineMonthlySubscription(CustomerRequest request) throws SQLException {
		// the request contains:
		//customerID
		//List<String> liscencePlates
		//email
		//parkingLotID
		//startingDate
		//routineDepartureTime
		
		//calculate price (check if there are multiple cars or just one)
		double price;
		if (request.liscencePlates.size() == 1)
			price = priceCalculator.calculateMonthly(request.parkingLotID);
		else
			price = priceCalculator.calculateMonthlyMultipleCars(request.parkingLotID, request.liscencePlates.size());
		
		//check if the customerID exists already
		ArrayList<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
		regularDBAPI.selectCustomerAccountDetails(request.customerID, resultList);
		if (resultList.isEmpty()) {
			//if there is no customerID, create new one and add the subscription
			regularDBAPI.insertNewAccount(request.customerID, request.email, request.carID, TrueFalse.TRUE);
			//TODO add subscription to the new account
			//TODO subscriptionID = something from DB
		}
		else {
			//else, add monthly subscription to the existing one
			//TODO add subscription to the new account
			//TODO subscriptionID = something from DB
		}
		return createUnsupportedFeatureResponse(request.requestType);
		//return createOkResponse(request.requestType, gson.toJson(new IdPricePair(subscriptionID, price)));
	}
	
	protected String orderFullMonthlySubscription(CustomerRequest request) throws SQLException {
		// the request contains:
		//customerID
		//carID
		//email
		//startingDate
		
		//calculate price
		double price = priceCalculator.calculateFullMonthly();
		
		//check if the customerID exists already
		ArrayList<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
		regularDBAPI.selectCustomerAccountDetails(request.customerID, resultList);
		if (resultList.isEmpty()) {
			//if there is no customerID, create new one and add the subscription
			regularDBAPI.insertNewAccount(request.customerID, request.email, request.carID, TrueFalse.TRUE);
			//TODO add subscription to the new account
			//TODO subscriptionID = something from DB
		}
		else {
			//else, add monthly subscription to the existing one
			//TODO add subscription to the new account
			//TODO subscriptionID = something from DB
		}
		return createUnsupportedFeatureResponse(request.requestType);
		//return createOkResponse(request.requestType, gson.toJson(new IdPricePair(subscriptionID, price)));
	}
	
	protected String subscriptionRenweal(CustomerRequest request) throws SQLException {
		//int customerID
		//int subscriptionId -> request.subscriptionID;
		ArrayList<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
		regularDBAPI.selectCustomerAccountDetails(request.customerID, resultList);
		// TODO what function should I use to renew the subscription?
		double price = 0.0; // TODO calculate price (don't know how to get subscription type with subscriptionID)
		return createUnsupportedFeatureResponse(request.requestType);
		//return createOkResponse(request.requestType, gson.toJson(new IdPricePair(subscriptionID, price)));
	}
	
	protected String openComplaint(CustomerRequest request) throws SQLException {
		int complaintID = regularDBAPI.insertComplaint(request.customerID, request.complaint, new Date() ); 
		return createNotificationResponse(request.requestType, gson.toJson(complaintID));
	}
	
	protected String parkingLotNames(CustomerRequest request) throws SQLException {
		ParkingLotsNamesForCustomerResponse response = new ParkingLotsNamesForCustomerResponse();
		response.requestType = CustomerRequestType.PARKING_LOT_NAMES;
		response.lotNames = parkingLotsManager.getAllIds();
		return createCustomerResponse(CustomerRequestType.PARKING_LOT_NAMES, response);
	}
	
	// returns the response json string
	protected String handleWebCustomerRequest(CustomerRequest request) throws SQLException {
		switch (request.requestType) {
		case PRE_ORDERED_PARKING:
			return orderPreOrderedParking(request);
		case CANCEL_ORDER:
			return cancelOrder(request);
		case TRACK_ORDER_STATUS:
			return trackOrderStatus(request);
		case ORDER_ROUTINE_MONTHLY_SUBSCRIPTION:
			return orderRoutineMonthlySubscription(request);
		case ORDER_FULL_MONTHLY_SUBSCRIPTION:
			return orderFullMonthlySubscription(request);
		case SUBSCRIPTION_RENEWAL:
			return subscriptionRenweal(request);
		case OPEN_COMPLAINT:
			return openComplaint(request);
		case PARKING_LOT_NAMES:
			return parkingLotNames(request);
		default:
			if (getPort() == ServerPorts.WEB_CUSTOMER_PORT) {
				return gson.toJson(new BadCustomerResponse(ResponseStatus.BAD_REQUEST, 
						request.requestType + " is illegal Web CustomerRequestType"));
			} else {
				return gson.toJson(new BadCustomerResponse(ResponseStatus.BAD_REQUEST, 
						request.requestType + " is illegal CustomerRequestType"));
			}
		}
	}
	
	@Override
	protected void handleMessageFromClient(Object msg, ConnectionToClient client) {
		try {
			try {
				client.sendToClient(handleWebCustomerRequest(gson.fromJson((String) msg, CustomerRequest.class)));
				
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
		System.out.println("WebCustomer Server is listening for connections on port " + getPort());
	}

	@Override
	protected void serverStopped() {
		System.out.println("WebCustomer Server has stopped listening for connections.");
	}

	protected String createUnsupportedFeatureResponse(CustomerRequestType requestType) {
		BadCustomerResponse badRequest = new BadCustomerResponse(ResponseStatus.UNSUPPORTED_FEATURE, requestType + " request type isn't supported yet");
		return gson.toJson(new CustomerResponse(CustomerRequestType.BAD_REQUEST, gson.toJson(badRequest)));
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
