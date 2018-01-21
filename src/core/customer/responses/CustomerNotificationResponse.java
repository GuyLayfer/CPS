package core.customer.responses;

import core.customer.CustomerRequestType;

// TODO: Auto-generated Javadoc
/**
 * The Class CustomerNotificationResponse.
 */
public class CustomerNotificationResponse extends CustomerBaseResponse {

	/** The message. */
	public String message;
	
	/**
	 * Instantiates a new customer notification response.
	 *
	 * @param requestType the request type
	 * @param message the message
	 */
	public CustomerNotificationResponse(CustomerRequestType requestType, String message) {
		this.requestType = requestType;
		this.message = message;
	}
}
