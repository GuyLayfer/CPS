package server.requestHandlers;

import ocsf.server.AbstractServer;
import ocsf.server.ConnectionToClient;
import server.db.DBConstants.OrderType;
import server.db.DBConstants.TrueFalse;
import server.db.DBConstants.SubscriptionType;
import server.db.SqlColumns;
import server.db.dbAPI.RegularDBAPI;
import server.db.dbAPI.SubscriptionsDBAPI;
import server.parkingLot.ParkingLotsManager;
import server.rates.PriceCalculator;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.List;

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
	final protected SubscriptionsDBAPI subscriptionsDBAPI = SubscriptionsDBAPI.getInstance();
	final protected ParkingLotsManager parkingLotsManager = ParkingLotsManager.getInstance();
	final protected PriceCalculator priceCalculator = PriceCalculator.getInstance();
	
	public WebCustomerRequestsHandler(int port) {
		super(port);
	}
	
	protected void ChargeAccount() {
		// TODO: implement
	}
	
	protected double updatePriceWithBalance(int customerID, double currentRequestPrice) throws SQLException {
		ArrayList<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
		regularDBAPI.selectCustomerAccountDetails(customerID, resultList);
		double customersBalance = (double)resultList.get(0).get(SqlColumns.Account.BALANCE);
		if (customersBalance == 0)
			return currentRequestPrice;
		if (customersBalance >= currentRequestPrice) {
			customersBalance -= currentRequestPrice;
			//update balance with customersBalance:
			regularDBAPI.updateCustomerBalance(customerID, customersBalance);
			currentRequestPrice = 0.0;
		}
		if (customersBalance < currentRequestPrice) {
			currentRequestPrice -= customersBalance;
			customersBalance = 0.0;
			//update balance with customersBalance:
			regularDBAPI.updateCustomerBalance(customerID, customersBalance);
		}
		return currentRequestPrice;
	}
	
	protected String orderPreOrderedParking(CustomerRequest request) throws SQLException {
		//customerID = customerID
		//carID = licensePlate
		//email = email
		//parkingLotID = parkingLotID
		//arrivalTime = arrivalTime
		//estimatedDepartureTime = estimatedDepartureTime
		
		//check if the customerID exists already
		ArrayList<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
		regularDBAPI.selectCustomerAccountDetails(request.customerID, resultList);
		//if there is no customerID, create new one
		if (resultList.isEmpty())
			regularDBAPI.insertNewAccount(request.customerID, request.email, request.carID, TrueFalse.FALSE);
		
		int entranceID = regularDBAPI.insertParkingReservation(request.carID, request.customerID, request.parkingLotID,
				request.arrivalTime, request.estimatedDepartureTime, new Date(0), new Date(0), 
				OrderType.ORDER);

		//calculate order price and update the account balance
		double price = priceCalculator.calculatePreOrdered(request.parkingLotID, request.arrivalTime, request.estimatedDepartureTime);
		
		price = updatePriceWithBalance(request.customerID, price);
		
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
			regularDBAPI.cancelOrder(request.orderID, refund);
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
		
		//check if the customerID exists already
		ArrayList<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
		regularDBAPI.selectCustomerAccountDetails(request.customerID, resultList);
		//if there is no customerID, create new one
		if (resultList.isEmpty())
			regularDBAPI.insertNewAccount(request.customerID, request.email, request.liscencePlates.get(0), TrueFalse.TRUE);
		
		//calculate price (check if there are multiple cars or just one)
		double price;
		if (request.liscencePlates.size() == 1)
			price = priceCalculator.calculateMonthly(request.parkingLotID);
		else
			price = priceCalculator.calculateMonthlyMultipleCars(request.parkingLotID, request.liscencePlates.size());
		
		price = updatePriceWithBalance(request.customerID, price);
		
		
		Date newExpireDate = new Date();
		Date newStartDate = request.startingDate;
		Calendar calendar = Calendar.getInstance();
		
		calendar.setTime(newStartDate);
        calendar.add(Calendar.DATE, 28);
        newExpireDate = calendar.getTime();
        
        java.sql.Date sqlExpireDate = new java.sql.Date(newExpireDate.getTime());
        java.sql.Date sqlStartDate = new java.sql.Date(newStartDate.getTime());
        java.sql.Date sqlRoutineDepartureTime = new java.sql.Date(request.estimatedDepartureTime.getTime());
        
        int subscriptionID;
        if (request.liscencePlates.size() == 1)
        	subscriptionID = subscriptionsDBAPI.insertNewSubscription(request.customerID, request.parkingLotID, SubscriptionType.ROUTINE, sqlStartDate, sqlExpireDate, sqlRoutineDepartureTime, request.liscencePlates, request.liscencePlates.size());
        else
        	subscriptionID = subscriptionsDBAPI.insertNewSubscription(request.customerID, request.parkingLotID, SubscriptionType.ROUTINE_MULTIPLE_CARS, sqlStartDate, sqlExpireDate, sqlRoutineDepartureTime, request.liscencePlates, request.liscencePlates.size());
		return createCustomerResponse(request.requestType, new IdPricePairResponse(subscriptionID, price));
	}
	
	protected String orderFullMonthlySubscription(CustomerRequest request) throws SQLException {
		//the request contains:
		//customerID
		//carID
		//email
		//startingDate
		
		//check if the customerID exists already
		ArrayList<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
		regularDBAPI.selectCustomerAccountDetails(request.customerID, resultList);
		//if there is no customerID, create new one
		if (resultList.isEmpty())
			regularDBAPI.insertNewAccount(request.customerID, request.email, request.carID, TrueFalse.TRUE);
		
		//calculate price
		double price = priceCalculator.calculateFullMonthly();
		
		price = updatePriceWithBalance(request.customerID, price);
		
		//set start and expire dates
		Date startDate = request.startingDate;
		Date expireDate = new Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(startDate);
        calendar.add(Calendar.DATE, 28);
        expireDate = calendar.getTime();
		java.sql.Date sqlStartDate = new java.sql.Date(startDate.getTime());
		java.sql.Date sqlExpireDate = new java.sql.Date(expireDate.getTime());
        
        //create a list that contains the only car
        List<String> carsList = new ArrayList<String>();
        carsList.add(request.carID);
        
		int subscriptionID = subscriptionsDBAPI.insertNewSubscription(request.customerID, 0, SubscriptionType.FULL, sqlStartDate, sqlExpireDate, sqlExpireDate, carsList, 1);
		return createCustomerResponse(request.requestType, new IdPricePairResponse(subscriptionID, price));
	}
	
	protected String subscriptionRenweal(CustomerRequest request) throws SQLException {
		//int customerID
		//int subscriptionId -> request.subscriptionID;
		
		// check if customer exists.
		ArrayList<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
		regularDBAPI.selectCustomerAccountDetails(request.customerID, resultList);
		if (resultList.isEmpty())
			return createRequestDeniedResponse(request.requestType, "Wrong Customer ID");
		// check if subscription exists.
		ArrayList<Map<String, Object>> resultList2 = new ArrayList<Map<String, Object>>();
		subscriptionsDBAPI.selectSubscriptionDetails(request.subscriptionID, resultList2);
		if (resultList2.isEmpty())
			return createRequestDeniedResponse(request.requestType, "Wrong Subscription ID");
		
		Date rightNow = new Date();
		Date newExpireDate = new Date();
		Date newStartDate = new Date();
		Date currentStartDate = (Date)resultList2.get(0).get(SqlColumns.Subscriptions.START_DATE);
		Date currentExpireDate = (Date)resultList2.get(0).get(SqlColumns.Subscriptions.EXPIRED_DATE);
		Calendar calendar = Calendar.getInstance();
		
		// check if the subscription is active
		if (subscriptionsDBAPI.isSubscriptionActive(request.subscriptionID)) {
			// add 28 days to newExpireDate
	        calendar.setTime(currentExpireDate);
	        calendar.add(Calendar.DATE, 28);
	        newExpireDate = calendar.getTime();
	        newStartDate = currentStartDate;
		} else {
			// subscription is not active but it is possible that there is a active subscription soon
			if (rightNow.compareTo(currentStartDate) < 0) {
				calendar.setTime(currentExpireDate);
		        calendar.add(Calendar.DATE, 28);
		        newExpireDate = calendar.getTime();
		        newStartDate = currentStartDate;
			} else {
			calendar.setTime(rightNow);
	        calendar.add(Calendar.DATE, 30);
	        newExpireDate = calendar.getTime();
	        
	        calendar.setTime(rightNow);
	        calendar.add(Calendar.DATE, 2);
	        newStartDate = calendar.getTime();
			}
		}
		java.sql.Date sqlExpireDate = new java.sql.Date(newExpireDate.getTime());
		java.sql.Date sqlStartDate = new java.sql.Date(newStartDate.getTime());
		subscriptionsDBAPI.updateSubscriptionExpiredDate(request.subscriptionID, sqlExpireDate, sqlStartDate);
		
		// check what is the rate for the specific parking type and set price
		double price = 0.0;
		ArrayList<Map<String, Object>> resultList3 = new ArrayList<Map<String, Object>>();
		subscriptionsDBAPI.selectSubscriptionDetails(request.subscriptionID, resultList3);
		SubscriptionType type = (SubscriptionType)resultList3.get(0).get(SqlColumns.Subscriptions.SUBSCRIPTION_TYPE);
		// if it is a full subscription - no need for lotId
		switch (type) {
		case FULL:
			price = priceCalculator.calculateFullMonthly();
			break;
		case ROUTINE:
			int lotId = (int)resultList3.get(0).get(SqlColumns.Subscriptions.LOT_ID);
			price = priceCalculator.calculateMonthly(lotId);
			break;
		case ROUTINE_MULTIPLE_CARS:
			int lotId1 = (int)resultList3.get(0).get(SqlColumns.Subscriptions.LOT_ID);
			int carsNum = (int)resultList3.get(0).get(SqlColumns.Subscriptions.CARS_NUM);
			price = priceCalculator.calculateMonthlyMultipleCars(lotId1, carsNum);
			break;
		}
		
		price = updatePriceWithBalance(request.customerID, price);
		
		return createCustomerResponse(request.requestType, new IdPricePairResponse(request.subscriptionID, price));
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
