package webGui.models;

import core.customer.CustomerRequest;
import core.guiUtilities.ServerMessageHandler;
import webGui.util.CustomerRequestFactory;
import webGui.util.MockWebClientConnectionManager;

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
