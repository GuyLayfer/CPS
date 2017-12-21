package webGui.models;

import core.WebCustomerRequest;
import webGui.MockWebClientConnectionManager;
import webGui.util.CustomerRequestFactory;
import webGui.util.ServerMessageHandler;

public class OrderOneTimeParkingModel {
	private MockWebClientConnectionManager connectionManager;
		
	public OrderOneTimeParkingModel (ServerMessageHandler controller) {
		connectionManager = MockWebClientConnectionManager.getInstance();
		connectionManager.addServerMessageListener(controller);
	}
	
	public void SendOrderOneTimeParkingRequestToServer(int customerID,int liscencePlate, String email, int parkingLotID, long arrivalTime, 
			long estimatedDepartureTime){
		WebCustomerRequest request = CustomerRequestFactory.CreateOrderOneTimeParkingRequest(customerID, liscencePlate, email, parkingLotID, arrivalTime, 
				 estimatedDepartureTime);
		connectionManager.sendMessageToServer(request);
	}

}
