package webGui.models;

import core.customer.CustomerRequest;
import webGui.util.CustomerRequestFactory;
import webGui.util.MockWebClientConnectionManager;
import webGui.util.ServerMessageHandler;

public class OpenComplaintModel {
	private MockWebClientConnectionManager connectionManager;
		
		public OpenComplaintModel(ServerMessageHandler controller) {
			connectionManager = MockWebClientConnectionManager.getInstance();
			connectionManager.addServerMessageListener(controller);
		}
		
		public void SendOpenComplaintRequestToServer(String complaint){
			CustomerRequest request = CustomerRequestFactory.CreateOpenComplaintRequest(complaint);
			connectionManager.sendMessageToServer(request);
		}

}
