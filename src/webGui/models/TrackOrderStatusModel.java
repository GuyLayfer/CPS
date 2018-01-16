package webGui.models;

import core.customer.CustomerRequest;
import core.customer.responses.CustomerBaseResponse;
import core.guiUtilities.IServerResponseHandler;
import webGui.util.CustomerRequestFactory;
import webGui.util.MockWebClientConnectionManager;

public class TrackOrderStatusModel {
private MockWebClientConnectionManager connectionManager;
	
	public TrackOrderStatusModel(IServerResponseHandler<CustomerBaseResponse> controller) {
		connectionManager = MockWebClientConnectionManager.getInstance();
		connectionManager.addServerMessageListener(controller);
	}
	
	public void SendTrackOrderStatusRequestToServer(int orderId){
		CustomerRequest request = CustomerRequestFactory.CreateTrackOrderStatusRequest(orderId);
		connectionManager.sendMessageToServer(request);
	}
}
