package webGui.models;

import core.WebCustomerRequest;
import webGui.MockWebClientConnectionManager;
import webGui.util.CustomerRequestFactory;
import webGui.util.ServerMessageHandler;

public class MockTrackOrderStatusModel {
private MockWebClientConnectionManager connectionManager;
	
	public MockTrackOrderStatusModel(ServerMessageHandler controller) {
		connectionManager = MockWebClientConnectionManager.getInstance();
		connectionManager.addServerMessageListener(controller);
	}
	
	public void SendTrackOrderStatusRequestToServer(String orderId){
		WebCustomerRequest request = CustomerRequestFactory.CreateTrackOrderStatusRequest(orderId);
		connectionManager.sendMessageToServer(request);
	}

}
