package webGui.models;

import core.WebCustomerRequest;
import webGui.MockWebClientConnectionManager;
import webGui.util.CustomerRequestFactory;
import webGui.util.ServerMessageHandler;

public class MockOrderRoutineMonthlySubscriptionModel {
	private MockWebClientConnectionManager connectionManager;
		
	public MockOrderRoutineMonthlySubscriptionModel(ServerMessageHandler controller) {
		connectionManager = MockWebClientConnectionManager.getInstance();
		connectionManager.addServerMessageListener(controller);
	}

	public void SendOrderRoutineMonthlySubscriptionRequestToServer(String customerID,String liscencePlate, String email, String parkingLotID, String startingDat, 
			String routineDepartureTime){
		WebCustomerRequest request = CustomerRequestFactory.CreateOrderRoutineMonthlySubscriptionRequest(customerID, liscencePlate, email, parkingLotID, startingDat, 
				 routineDepartureTime);
		connectionManager.sendMessageToServer(request);
	}

}
