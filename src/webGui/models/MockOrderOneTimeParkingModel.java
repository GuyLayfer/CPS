package webGui.models;

import core.WebCustomerRequest;
import webGui.MockWebClientConnectionManager;
import webGui.util.CustomerRequestFactory;
import webGui.util.ServerMessageHandler;

public class MockOrderOneTimeParkingModel {
	private MockWebClientConnectionManager connectionManager;
		
	public MockOrderOneTimeParkingModel (ServerMessageHandler controller) {
		connectionManager = MockWebClientConnectionManager.getInstance();
		connectionManager.addServerMessageListener(controller);
	}
	
	public void SendOrderOrderOneTimeParkingRequestToServer(String customerID,String liscencePlate, String email, String parkingLotID, String arrivalTime, 
			String estimatedDepartureTime){
		WebCustomerRequest request = CustomerRequestFactory.CreateOrderOneTimeParkingRequest(customerID, liscencePlate, email, parkingLotID, arrivalTime, 
				 estimatedDepartureTime);
		connectionManager.sendMessageToServer(request);
	}

}
