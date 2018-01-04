package server;

import ocsf.server.AbstractServer;
import ocsf.server.ConnectionToClient;

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
import db.DBAPI;
import db.SqlColumns;
import db.SqlColumns.ParkingTonnage;


public class WebCustomerRequestsHandler extends AbstractServer {
	final protected Gson gson = new CpsGson().GetGson();
	protected IDsGenerator idsGenerator;
	
	
	public WebCustomerRequestsHandler(int port, IDsGenerator idsGenerator) {
		super(port);
		this.idsGenerator = idsGenerator;
	}
	
	protected void ChargeAccount() {
		// TODO: implement
	}
	
	protected String orderOneTimeParking(CustomerRequest request) throws SQLException {
		int entranceId = idsGenerator.nextEntranceID();
		DBAPI.updateParkingReservaion(request.carID, request.customerID, entranceId, request.parkingLotID,
				request.arrivalTime, request.estimatedDepartureTime, new Date(0), new Date(0), 
				DBAPI.orderType.ORDER.getId());
		//TODO: calculate order price and update the account balance
		double price = 0.0;
		DBAPI.createNewAccount(request.customerID, request.email, request.carID, price, false);
		//TODO: update parking lots info
		return createOkResponse(request.requestType, gson.toJson(new IdPricePair(entranceId, price)));
	}
	
	protected String trackOrderStatus(CustomerRequest request) throws SQLException {
		ArrayList<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
		DBAPI.trackOrderStatus(request.orderID, resultList);
		if (resultList.isEmpty()) {
			return createRequestDeniedResponse(request.requestType, "Wrong Order ID");
		} else {
			Map<String, Object> result = resultList.iterator().next();
			TrackOrderResponseData response = new TrackOrderResponseData();
			response.orderID = (Integer) result.get(ParkingTonnage.ENTRANCE_ID);
			response.customerID = (Integer) result.get(SqlColumns.ParkingTonnage.ACCOUNT_ID);
			response.carID = result.get(SqlColumns.ParkingTonnage.CAR_ID).toString();
			response.parkingLotID = (Integer) result.get(SqlColumns.ParkingTonnage.LOT_ID);
			response.arrivalTime = (Date)result.get(SqlColumns.ParkingTonnage.ARRIVE_PREDICTION);
			response.estimatedDepartureTime = (Date)result.get(SqlColumns.ParkingTonnage.LEAVE_PREDICTION);
			return createOkResponse(request.requestType, gson.toJson(response));
		}
	}
	
	// returns the response json string
	protected String handleWebCustomerRequest(CustomerRequest request) throws SQLException {
		switch (request.requestType) {
		case ORDER_ONE_TIME_PARKING:
			return orderOneTimeParking(request);
		case CANCEL_ORDER: // TODO: implement
			return createUnsupportedFeatureResponse(request.requestType);
		case TRACK_ORDER_STATUS:
			return trackOrderStatus(request);
		case ORDER_ROUTINE_MONTHLY_SUBSCRIPTION: // TODO: implement
			return createUnsupportedFeatureResponse(request.requestType);
		case ORDER_FULL_MONTHLY_SUBSCRIPTION: // TODO: implement
			return createUnsupportedFeatureResponse(request.requestType);
		case SUBSCRIPTION_RENEWAL: // TODO: implement
			return createUnsupportedFeatureResponse(request.requestType);
		case OPEN_COMPLAINT: // TODO: implement
			return createUnsupportedFeatureResponse(request.requestType);
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
