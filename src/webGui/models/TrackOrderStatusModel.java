package webGui.models;

import core.customer.CustomerRequest;
import core.guiUtilities.ServerMessageHandler;
import webGui.util.CustomerRequestFactory;
import webGui.util.MockWebClientConnectionManager;

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
