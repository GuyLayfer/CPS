package server;

import ocsf.server.AbstractServer;
import ocsf.server.ConnectionToClient;
import server.db.DBConstants.OrderType;
import server.db.DBConstants.TrueFalse;
import server.db.SqlColumns;
import server.db.dbAPI.RegularDBAPI;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

import core.CpsGson;
import core.IdPricePair;
import core.ResponseStatus;
import core.ServerPorts;
import core.customer.CustomerRequest;
import core.customer.CustomerRequestType;
import core.customer.CustomerResponse;
import core.customer.TrackOrderResponseData;


public class WebCustomerRequestsHandler extends AbstractServer {
	final protected Gson gson = new CpsGson().GetGson();
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
		return createOkResponse(request.requestType, gson.toJson(new IdPricePair(entranceID, price)));
	}
	
	protected String cancelOrder(CustomerRequest request) throws SQLException {
		// get order details from DB
		ArrayList<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
		regularDBAPI.selectOrderStatus(request.orderID, resultList);
		if (resultList.isEmpty()) {
			return createRequestDeniedResponse(request.requestType, "Wrong Order ID");
		} else {
			Map<String, Object> result = resultList.iterator().next();
			double refund = priceCalculator.calculateCancelRefund(((int)result.get(SqlColumns.ParkingTonnage.LOT_ID)), ((Date)result.get(SqlColumns.ParkingTonnage.ARRIVE_PREDICTION)), ((Date)result.get(SqlColumns.ParkingTonnage.LEAVE_PREDICTION)));
			regularDBAPI.cancelOrder(request.orderID ,-refund);
			return createOkResponse(request.requestType, gson.toJson(refund));
		}
	}
	
	protected String trackOrderStatus(CustomerRequest request) throws SQLException {
		ArrayList<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
		regularDBAPI.selectOrderStatus(request.orderID, resultList);

		if (resultList.isEmpty()) {
			return createRequestDeniedResponse(request.requestType, "Wrong Order ID");
		} else {
			Map<String, Object> result = resultList.iterator().next();
			TrackOrderResponseData response = new TrackOrderResponseData();

			response.orderID = (Integer) result.get(SqlColumns.ParkingTonnage.ENTRANCE_ID);
			response.customerID = (Integer) result.get(SqlColumns.ParkingTonnage.ACCOUNT_ID);
			response.carID = result.get(SqlColumns.ParkingTonnage.CAR_ID).toString();
			response.parkingLotID = (Integer) result.get(SqlColumns.ParkingTonnage.LOT_ID);
			response.arrivalTime = (Date)result.get(SqlColumns.ParkingTonnage.ARRIVE_PREDICTION);
			response.estimatedDepartureTime = (Date)result.get(SqlColumns.ParkingTonnage.LEAVE_PREDICTION);
			return createOkResponse(request.requestType, gson.toJson(response));
		}
	}
	
	protected String orderRoutineMonthlySubscription(CustomerRequest request) throws SQLException {
		// the request contains:
		//customerID
		//carID
		//email
		//parkingLotID
		//startingDate
		//routineDepartureTime
		
		//first check if there is a customerID already
		ArrayList<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
		regularDBAPI.selectCustomerAccountDetails(request.customerID, resultList);
		if (resultList.isEmpty()) {
			//if there is no customerID, create new one and add monthly subscription
			regularDBAPI.insertNewAccount(request.customerID, request.email, request.carID, TrueFalse.TRUE);
			//TODO add subscription to the new account
		}
		else {
			//else, add monthly subscription to the existing one
			//TODO add subscription to the new account
		}
		double price = 0.0; // TODO calculate price
		int subscriptionID = 1234567; //TODO calculate subscriptionID
		return createUnsupportedFeatureResponse(request.requestType);
		//return createOkResponse(request.requestType, gson.toJson(new IdPricePair(subscriptionID, price)));
	}
	
	protected String orderFullMonthlySubscription(CustomerRequest request) throws SQLException {
		// the request contains:
		//customerID
		//carID
		//email
		//startingDate
		double price = 0.0; // TODO calculate price
		int subscriptionID = 1234567; //TODO calculate subscriptionID
		return createUnsupportedFeatureResponse(request.requestType);
		//return createOkResponse(request.requestType, gson.toJson(new IdPricePair(subscriptionID, price)));
	}
	
	protected String subscriptionRenweal(CustomerRequest request) throws SQLException {
		int subscriptionID = request.subscriptionID;
		// TODO what function should I use to renew the subscription?
		double price = 0.0; // TODO calculate price
		return createUnsupportedFeatureResponse(request.requestType);
		//return createOkResponse(request.requestType, gson.toJson(new IdPricePair(subscriptionID, price)));
	}
	
	protected String openComplaint(CustomerRequest request) throws SQLException {
		int complaintID = 1234567; //TODO calculate complaintID
		// TODO What function should I use to open complaint
		return createUnsupportedFeatureResponse(request.requestType);
		//return createOkResponse(request.requestType, gson.toJson(complaintID));
	}
	
	// returns the response json string
	protected String handleWebCustomerRequest(CustomerRequest request) throws SQLException {
		switch (request.requestType) {
		case PRE_ORDERED_PARKING:
			return orderPreOrderedParking(request);
		case CANCEL_ORDER: // TODO: implement
			return cancelOrder(request);
		case TRACK_ORDER_STATUS:
			return trackOrderStatus(request);
		case ORDER_ROUTINE_MONTHLY_SUBSCRIPTION: // TODO: implement
			return orderRoutineMonthlySubscription(request);
		case ORDER_FULL_MONTHLY_SUBSCRIPTION: // TODO: implement
			return orderFullMonthlySubscription(request);
		case SUBSCRIPTION_RENEWAL: // TODO: implement
			return subscriptionRenweal(request);
		case OPEN_COMPLAINT: // TODO: implement
			return openComplaint(request);
		default:
			if (getPort() == ServerPorts.WEB_CUSTOMER_PORT) {
				return gson.toJson(new CustomerResponse(ResponseStatus.BAD_REQUEST, 
						request.requestType + " is illegal Web CustomerRequestType"));
			} else {
				return gson.toJson(new CustomerResponse(ResponseStatus.BAD_REQUEST, 
						request.requestType + " is illegal CustomerRequestType"));
			}
		}
	}
	
	@Override
	protected void handleMessageFromClient(Object msg, ConnectionToClient client) {
		try {
			client.sendToClient(handleWebCustomerRequest(gson.fromJson((String)msg, CustomerRequest.class)));
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
		System.out.println("WebCustomer Server is listening for connections on port " + getPort());
	}

	@Override
	protected void serverStopped() {
		System.out.println("WebCustomer Server has stopped listening for connections.");
	}

	
	protected String createUnsupportedFeatureResponse(CustomerRequestType requestType){
		return gson.toJson(new CustomerResponse(ResponseStatus.UNSUPPORTED_FEATURE, 
				requestType + " request type isn't supported yet", requestType, null));
	}
	
	protected String createOkResponse(CustomerRequestType requestType, String responseData){
		return gson.toJson(new CustomerResponse(requestType, responseData));
	}
	
	protected String createRequestDeniedResponse(CustomerRequestType requestType, String refusalReason){
		return gson.toJson(new CustomerResponse(ResponseStatus.REQUEST_DENIED, refusalReason, requestType, null));
	}
}
