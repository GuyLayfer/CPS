package webGui.models;

import java.util.Date;

import core.customer.CustomerRequest;
import webGui.util.CustomerRequestFactory;
import webGui.util.MockWebClientConnectionManager;

public class OrderFullMonthlySubscriptionModel {
	
	private MockWebClientConnectionManager connectionManager;
	
	public OrderFullMonthlySubscriptionModel() {
		connectionManager = MockWebClientConnectionManager.getInstance();
	}
	
	public void SendFullMonthlySubcriptionRequestToServer(int customerID, String liscencePlate, String email, Date startingDate){
		CustomerRequest request = CustomerRequestFactory.createOrderFullMonthlySubscriptionRequest(customerID, liscencePlate, email, startingDate);
		connectionManager.sendMessageToServer(request);
	}
}
