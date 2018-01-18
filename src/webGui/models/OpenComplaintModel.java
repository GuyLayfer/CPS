package webGui.models;

import core.customer.CustomerRequest;
import webGui.util.CustomerRequestFactory;
import webGui.util.MockWebClientConnectionManager;

public class OpenComplaintModel {
	private MockWebClientConnectionManager connectionManager;
		
		public OpenComplaintModel() {
			connectionManager = MockWebClientConnectionManager.getInstance();
		}
		
		public void SendOpenComplaintRequestToServer(String complaint, int customerId){
			CustomerRequest request = CustomerRequestFactory.createOpenComplaintRequest(complaint, customerId);
			connectionManager.sendMessageToServer(request);
		}

}
