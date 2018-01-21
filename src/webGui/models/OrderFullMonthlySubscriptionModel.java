package webGui.models;

import java.util.Date;

import core.customer.CustomerRequest;
import webGui.util.CustomerRequestFactory;
import webGui.util.MockWebClientConnectionManager;

// TODO: Auto-generated Javadoc
/**
 * The Class OrderFullMonthlySubscriptionModel.
 */
public class OrderFullMonthlySubscriptionModel {
	
	/** The connection manager. */
	private MockWebClientConnectionManager connectionManager;
	
	/**
	 * Instantiates a new order full monthly subscription model.
	 */
	public OrderFullMonthlySubscriptionModel() {
		connectionManager = MockWebClientConnectionManager.getInstance();
	}
	
	/**
	 * Send full monthly subcription request to server.
	 *
	 * @param customerID the customer ID
	 * @param liscencePlate the liscence plate
	 * @param email the email
	 * @param startingDate the starting date
	 */
	public void SendFullMonthlySubcriptionRequestToServer(int customerID, String liscencePlate, String email, Date startingDate){
		CustomerRequest request = CustomerRequestFactory.createOrderFullMonthlySubscriptionRequest(customerID, liscencePlate, email, startingDate);
		connectionManager.sendMessageToServer(request);
	}
}
