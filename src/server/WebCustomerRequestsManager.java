package server;

import ocsf.server.AbstractServer;
import ocsf.server.ConnectionToClient;

import com.google.gson.Gson;

import com.google.gson.JsonSyntaxException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;

import core.ServerBasicResponse;
import core.ServerGenericResponse;
import core.ServerResponseStatus;
import core.ServerTrackOrderResponse;
import core.WebCustomerRequest;
import db.DBAPI;
import db.SqlColumns;

/*
 * This is a temporary mock server which was created in order to simplify the implementation of the prototype.
 * This class and the whole server will be changed radically after the prototype submission. 
 */
public class WebCustomerRequestsManager extends AbstractServer {
	// TODO: move it to another place after the prototype submission
	final private Gson gson = new Gson();
	final public static int DEFAULT_PORT = 5555;
	
	// TODO: move it to another place after the prototype submission
	private int lastOrderId;
	
	public WebCustomerRequestsManager(int port) {
		super(port);
		// TODO: retrieve the last order id from DB and initialize lastOrderId 
		// (not necessary for the prototype, but the tables in the DB must be empty before running the prototype).
	}
	
	public void ChargeAccount() {
		// TODO: implement after the prototype submission
	}

	public void handleMessageFromClient(Object msg, ConnectionToClient client) {
		try {
			WebCustomerRequest request = gson.fromJson((String)msg, WebCustomerRequest.class);
			switch (request.webCustomerRequestType) {
			case ORDER_ONE_TIME_PARKING:
				++lastOrderId;
				DBAPI.updateParkingReservaion(request.carID, request.customerID, lastOrderId, request.parkingLotID,
						new Date(request.arrivalTime), new Date(request.estimatedDepartureTime), new Date(0), new Date(0), 
						DBAPI.orderType.ORDER.getId());
				DBAPI.createNewAccount(request.customerID, request.email, request.carID, false);
				client.sendToClient(gson.toJson(new ServerGenericResponse(String.valueOf(lastOrderId))));
				break;
			case CANCEL_ORDER:
				client.sendToClient(new ServerBasicResponse(ServerResponseStatus.UNSUPPORTED_FEATURE, 
						"this request type isn't supported yet"));
				// TODO: implement after the prototype submission
				break;
			case TRACK_ORDER_STATUS:
				ArrayList<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
				DBAPI.trackOrderStatus(request.orderID, resultList);
				if (resultList.isEmpty()) {
					client.sendToClient(gson.toJson(new ServerBasicResponse(ServerResponseStatus.REQUEST_DENIED, 
							"Wrong Order ID")));
				} else {
					Map<String, Object> result = resultList.iterator().next();
					ServerTrackOrderResponse response = new ServerTrackOrderResponse();
					response.orderID = (Integer) result.get(SqlColumns.ENTRANCE_ID);
					response.customerID = (Integer) result.get(SqlColumns.ACCOUNT_ID);
					response.carID = result.get(SqlColumns.CAR_ID).toString();
					response.parkingLotID = (Integer) result.get(SqlColumns.LOT_ID);
					response.arrivalTime = ((Date) result.get(SqlColumns.ARRIVE_PREDICTION)).getTime();
					response.estimatedDepartureTime = ((Date) result.get(SqlColumns.LEAVE_PREDICTION)).getTime();
					client.sendToClient(gson.toJson(response));
				}
				break;
			case ORDER_ROUTINE_MONTHLY_SUBSCRIPTION:
				client.sendToClient(new ServerBasicResponse(ServerResponseStatus.UNSUPPORTED_FEATURE, 
						"this request type isn't supported yet"));
				// TODO: implement after the prototype submission
				break;
			case ORDER_FULL_MONTHLY_SUBSCRIPTION:
				client.sendToClient(new ServerBasicResponse(ServerResponseStatus.UNSUPPORTED_FEATURE, 
						"this request type isn't supported yet"));
				// TODO: implement after the prototype submission
				break;
			case SUBSCRIPTION_RENEWAL:
				client.sendToClient(new ServerBasicResponse(ServerResponseStatus.UNSUPPORTED_FEATURE, 
						"this request type isn't supported yet"));
				// TODO: implement after the prototype submission
				break;
			case OPEN_COMPLAINT:
				client.sendToClient(new ServerBasicResponse(ServerResponseStatus.UNSUPPORTED_FEATURE, 
						"this request type isn't supported yet"));
				// TODO: implement after the prototype submission
				break;
			default:
				client.sendToClient(new ServerBasicResponse(ServerResponseStatus.BAD_REQUEST, 
						"Illegal webCustomerRequestType"));
				break;
			}
		} catch (JsonSyntaxException e) {
			try {
				client.sendToClient(new ServerBasicResponse(ServerResponseStatus.BAD_REQUEST, "JsonSyntaxException"));
				e.printStackTrace();
			} catch (IOException e2) {
				e2.printStackTrace();
			}
			e.printStackTrace();
		} catch (SQLException e) { // compilation error will be fixed after adding the DB communication functions
			try {
				client.sendToClient(new ServerBasicResponse(ServerResponseStatus.SERVER_FAILLURE, "Server Failure"));
				e.printStackTrace();
			} catch (IOException e2) {
				e2.printStackTrace();
			}
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	protected void serverStarted() {
		System.out.println("Server listening for connections on port " + getPort());
	}

	protected void serverStopped() {
		System.out.println("Server has stopped listening for connections.");
	}

// Mock main. Should be deleted and code moved to the initialization of the whole server.
	public static void main(String[] args) {
		WebCustomerRequestsManager customerRequestsManager = new WebCustomerRequestsManager(DEFAULT_PORT);

		try {
			customerRequestsManager.listen();
		} catch (Exception ex) {
			System.out.println("ERROR - Could not listen for clients!");
		}
	}
}
