package webGui.models;

import core.WebCustomerRequest;
import webGui.MockWebClientConnectionManager;
import webGui.util.CustomerRequestFactory;
import webGui.util.ServerMessageHandler;

public class MockOpenComplaintModel {
	private MockWebClientConnectionManager connectionManager;
		
		public MockOpenComplaintModel(ServerMessageHandler controller) {
			connectionManager = MockWebClientConnectionManager.getInstance();
			connectionManager.addServerMessageListener(controller);
		}
		
		public void SendOpenComplaintRequestToServer(){
			WebCustomerRequest request = CustomerRequestFactory.CreateOpenComplaintRequest();
			connectionManager.sendMessageToServer(request);
		}

}
