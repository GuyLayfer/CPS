package webGui.models;

import core.custome.CustomerRequest;
import webGui.util.CustomerRequestFactory;
import webGui.util.MockWebClientConnectionManager;
import webGui.util.ServerMessageHandler;

public class RenewSubscriptionModel {
	
	private MockWebClientConnectionManager connectionManager;
	
	public RenewSubscriptionModel(ServerMessageHandler controller) {
		connectionManager = MockWebClientConnectionManager.getInstance();
		connectionManager.addServerMessageListener(controller);
	}
	
	public void SendRenewSubscriptionRequestToServer(int customerId, int subscriptionId){
		CustomerRequest request = CustomerRequestFactory.CreateSubscriptionRenewalRequest(customerId, subscriptionId);
		connectionManager.sendMessageToServer(request);
	}
}
