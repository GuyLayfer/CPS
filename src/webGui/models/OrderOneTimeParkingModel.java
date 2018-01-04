package webGui.models;

import java.util.Date;

import core.custome.CustomerRequest;
import webGui.util.CustomerRequestFactory;
import webGui.util.MockWebClientConnectionManager;
import webGui.util.ServerMessageHandler;

public class OrderOneTimeParkingModel {
	private MockWebClientConnectionManager connectionManager;
		
	public OrderOneTimeParkingModel (ServerMessageHandler controller) {
		connectionManager = MockWebClientConnectionManager.getInstance();
		connectionManager.addServerMessageListener(controller);
	}
	
	public void SendOrderOneTimeParkingRequestToServer(int customerID,int liscencePlate, String email, int parkingLotID, Date arrivalTime, 
			Date estimatedDepartureTime){
		CustomerRequest request = CustomerRequestFactory.CreateOrderOneTimeParkingRequest(customerID, liscencePlate, email, parkingLotID, arrivalTime, 
				 estimatedDepartureTime);
		connectionManager.sendMessageToServer(request);
	}

}
