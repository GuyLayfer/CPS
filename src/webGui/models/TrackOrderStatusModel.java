package webGui.models;

import core.Customer.CustomerRequest;
import webGui.MockWebClientConnectionManager;
import webGui.util.CustomerRequestFactory;
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
