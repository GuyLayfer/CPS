package webGui.models;

import core.customer.CustomerRequest;
import webGui.util.CustomerRequestFactory;
import webGui.util.MockWebClientConnectionManager;

public class RenewSubscriptionModel {
	
	private MockWebClientConnectionManager connectionManager;
	
	public RenewSubscriptionModel() {
		connectionManager = MockWebClientConnectionManager.getInstance();
	}
	
	public void SendRenewSubscriptionRequestToServer(int customerId, int subscriptionId){
		CustomerRequest request = CustomerRequestFactory.CreateSubscriptionRenewalRequest(customerId, subscriptionId);
		connectionManager.sendMessageToServer(request);
	}
}
