package webGui.models;

import core.customer.CustomerRequest;
import webGui.util.CustomerRequestFactory;
import webGui.util.MockWebClientConnectionManager;
import webGui.util.ServerMessageHandler;

public class TrackOrderStatusModel {
private MockWebClientConnectionManager connectionManager;
	
	public TrackOrderStatusModel(ServerMessageHandler controller) {
		connectionManager = MockWebClientConnectionManager.getInstance();
		connectionManager.addServerMessageListener(controller);
	}
	
	public void SendTrackOrderStatusRequestToServer(int orderId){
		CustomerRequest request = CustomerRequestFactory.CreateTrackOrderStatusRequest(orderId);
		connectionManager.sendMessageToServer(request);
	}
}
