package webGui.models;

import core.customer.CustomerRequest;
import webGui.util.CustomerRequestFactory;
import webGui.util.MockWebClientConnectionManager;

// TODO: Auto-generated Javadoc
/**
 * The Class RenewSubscriptionModel.
 */
public class RenewSubscriptionModel {
	
	/** The connection manager. */
	private MockWebClientConnectionManager connectionManager;
	
	/**
	 * Instantiates a new renew subscription model.
	 */
	public RenewSubscriptionModel() {
		connectionManager = MockWebClientConnectionManager.getInstance();
	}
	
	/**
	 * Send renew subscription request to server.
	 *
	 * @param customerId the customer id
	 * @param subscriptionId the subscription id
	 */
	public void SendRenewSubscriptionRequestToServer(int customerId, int subscriptionId){
		CustomerRequest request = CustomerRequestFactory.createSubscriptionRenewalRequest(customerId, subscriptionId);
		connectionManager.sendMessageToServer(request);
	}
}
