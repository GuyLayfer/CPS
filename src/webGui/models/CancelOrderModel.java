package webGui.models;

import core.customer.CustomerRequest;
import webGui.util.CustomerRequestFactory;
import webGui.util.MockWebClientConnectionManager;

// TODO: Auto-generated Javadoc
/**
 * The Class CancelOrderModel.
 */
public class CancelOrderModel {
	
	/** The connection manager. */
	private MockWebClientConnectionManager connectionManager;
	
	/**
	 * Instantiates a new cancel order model.
	 */
	public CancelOrderModel() {
		connectionManager = MockWebClientConnectionManager.getInstance();
	}
	
	/**
	 * Send cancel request to server.
	 *
	 * @param orderId the order id
	 */
	public void SendCancelRequestToServer(int orderId){
		CustomerRequest request = CustomerRequestFactory.createCancelRequest(orderId);
		connectionManager.sendMessageToServer(request);
	}
}
