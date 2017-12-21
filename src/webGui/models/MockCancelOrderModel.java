package webGui.models;

import core.WebCustomerRequest;
import webGui.MockWebClientConnectionManager;
import webGui.util.CustomerRequestFactory;
import webGui.util.ServerMessageHandler;

public class MockCancelOrderModel {
	private MockWebClientConnectionManager connectionManager;
	
	public MockCancelOrderModel(ServerMessageHandler controller) {
		connectionManager = MockWebClientConnectionManager.getInstance();
		connectionManager.addServerMessageListener(controller);
	}
	
	public void SendCancelRequestToServer(String orderId){
		WebCustomerRequest request = CustomerRequestFactory.CreateCancelRequest(orderId);
		connectionManager.sendMessageToServer(request);
	}
}
