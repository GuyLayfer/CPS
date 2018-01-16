package core.customer.responses;

import core.customer.CustomerRequestType;

public class CustomerNotificationResponse extends CustomerBaseResponse {

	public String message;
	
	public CustomerNotificationResponse(CustomerRequestType requestType, String message) {
		this.requestType = requestType;
		this.message = message;
	}
}
