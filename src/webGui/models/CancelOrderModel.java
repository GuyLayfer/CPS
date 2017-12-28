package webGui.models;

import core.Customer.CustomerRequest;
import webGui.MockWebClientConnectionManager;
import webGui.util.CustomerRequestFactory;
import webGui.util.ServerMessageHandler;

public class CancelOrderModel {
	private MockWebClientConnectionManager connectionManager;
	
	public CancelOrderModel(ServerMessageHandler controller) {
		connectionManager = MockWebClientConnectionManager.getInstance();
		connectionManager.addServerMessageListener(controller);
	}
	
	public void SendCancelRequestToServer(int orderId){
		CustomerRequest request = CustomerRequestFactory.CreateCancelRequest(orderId);
		connectionManager.sendMessageToServer(request);
	}
}
