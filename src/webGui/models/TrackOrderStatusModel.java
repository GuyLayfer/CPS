package webGui.models;

import core.customer.CustomerRequest;
import core.customer.responses.CustomerBaseResponse;
import core.guiUtilities.IServerResponseHandler;
import webGui.util.CustomerRequestFactory;
import webGui.util.MockWebClientConnectionManager;

// TODO: Auto-generated Javadoc
/**
 * The Class TrackOrderStatusModel.
 */
public class TrackOrderStatusModel {

/** The connection manager. */
private MockWebClientConnectionManager connectionManager;
	
	/**
	 * Instantiates a new track order status model.
	 *
	 * @param controller the controller
	 */
	public TrackOrderStatusModel(IServerResponseHandler<CustomerBaseResponse> controller) {
		connectionManager = MockWebClientConnectionManager.getInstance();
		connectionManager.addServerMessageListener(controller);
	}
	
	/**
	 * Send track order status request to server.
	 *
	 * @param orderId the order id
	 */
	public void SendTrackOrderStatusRequestToServer(int orderId){
		CustomerRequest request = CustomerRequestFactory.createTrackOrderStatusRequest(orderId);
		connectionManager.sendMessageToServer(request);
	}
}
