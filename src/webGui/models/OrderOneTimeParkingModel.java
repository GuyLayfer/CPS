package webGui.models;

import java.util.Date;

import core.customer.CustomerRequest;
import webGui.util.CustomerRequestFactory;
import webGui.util.MockWebClientConnectionManager;

public class OrderOneTimeParkingModel {
	private MockWebClientConnectionManager connectionManager;
		
	public OrderOneTimeParkingModel () {
		connectionManager = MockWebClientConnectionManager.getInstance();
	}
	
	public void SendOrderOneTimeParkingRequestToServer(int customerID, String liscencePlate, String email, int parkingLotID, Date arrivalTime, 
			Date estimatedDepartureTime){
		CustomerRequest request = CustomerRequestFactory.CreateOrderOneTimeParkingRequest(customerID, liscencePlate, email, parkingLotID, arrivalTime, 
				 estimatedDepartureTime);
		connectionManager.sendMessageToServer(request);
	}
}
