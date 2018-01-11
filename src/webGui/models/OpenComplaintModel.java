package webGui.models;

import core.customer.CustomerRequest;
import core.guiUtilities.ServerMessageHandler;
import webGui.util.CustomerRequestFactory;
import webGui.util.MockWebClientConnectionManager;

public class OpenComplaintModel {
	private MockWebClientConnectionManager connectionManager;
		
		public OpenComplaintModel(ServerMessageHandler controller) {
			connectionManager = MockWebClientConnectionManager.getInstance();
			connectionManager.addServerMessageListener(controller);
		}
		
		public void SendOpenComplaintRequestToServer(String complaint, int customerId){
			CustomerRequest request = CustomerRequestFactory.CreateOpenComplaintRequest(complaint, customerId);
			connectionManager.sendMessageToServer(request);
		}

}
