package webGui.models;

import java.util.Date;

import core.Customer.CustomerRequest;
import webGui.util.CustomerRequestFactory;
import webGui.util.MockWebClientConnectionManager;
import webGui.util.ServerMessageHandler;

public class OrderFullMonthlySubscriptionModel {
	
	private MockWebClientConnectionManager connectionManager;
	
	public OrderFullMonthlySubscriptionModel(ServerMessageHandler controller) {
		connectionManager = MockWebClientConnectionManager.getInstance();
		connectionManager.addServerMessageListener(controller);
	}
	
	public void SendFullMonthlySubcriptionRequestToServer(int customerID, int liscencePlate, String email, Date startingDate){
		CustomerRequest request = CustomerRequestFactory.CreateOrderFullMonthlySubscriptionRequest(customerID, liscencePlate, email, startingDate);
		connectionManager.sendMessageToServer(request);
	}
}
