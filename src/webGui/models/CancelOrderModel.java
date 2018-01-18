package webGui.models;

import core.customer.CustomerRequest;
import webGui.util.CustomerRequestFactory;
import webGui.util.MockWebClientConnectionManager;

public class CancelOrderModel {
	private MockWebClientConnectionManager connectionManager;
	
	public CancelOrderModel() {
		connectionManager = MockWebClientConnectionManager.getInstance();
	}
	
	public void SendCancelRequestToServer(int orderId){
		CustomerRequest request = CustomerRequestFactory.createCancelRequest(orderId);
		connectionManager.sendMessageToServer(request);
	}
}
