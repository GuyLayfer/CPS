package webGui.models;

import core.WebCustomerRequest;
import webGui.MockWebClientConnectionManager;
import webGui.util.CustomerRequestFactory;
import webGui.util.ServerMessageHandler;

public class OrderRoutineMonthlySubscriptionModel {
	private MockWebClientConnectionManager connectionManager;
		
	public OrderRoutineMonthlySubscriptionModel(ServerMessageHandler controller) {
		connectionManager = MockWebClientConnectionManager.getInstance();
		connectionManager.addServerMessageListener(controller);
	}

	// TODO: change liscencePlate to String when Raz changes it in the DB
	public void SendOrderRoutineMonthlySubscriptionRequestToServer(int customerID,int liscencePlate, String email, int parkingLotID, long startingDat, 
			long routineDepartureTime){
		WebCustomerRequest request = CustomerRequestFactory.CreateOrderRoutineMonthlySubscriptionRequest(customerID, liscencePlate, email, parkingLotID, startingDat, 
				 routineDepartureTime);
		connectionManager.sendMessageToServer(request);
	}

}
