package webGui.models;

import core.customer.CustomerRequest;
import webGui.util.CustomerRequestFactory;
import webGui.util.MockWebClientConnectionManager;

// TODO: Auto-generated Javadoc
/**
 * The Class OpenComplaintModel.
 */
public class OpenComplaintModel {
	
	/** The connection manager. */
	private MockWebClientConnectionManager connectionManager;
		
		/**
		 * Instantiates a new open complaint model.
		 */
		public OpenComplaintModel() {
			connectionManager = MockWebClientConnectionManager.getInstance();
		}
		
		/**
		 * Send open complaint request to server.
		 *
		 * @param complaint the complaint
		 * @param customerId the customer id
		 */
		public void SendOpenComplaintRequestToServer(String complaint, int customerId){
			CustomerRequest request = CustomerRequestFactory.createOpenComplaintRequest(complaint, customerId);
			connectionManager.sendMessageToServer(request);
		}

}
