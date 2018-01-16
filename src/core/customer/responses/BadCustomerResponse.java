package core.customer.responses;

import core.ResponseStatus;
import core.customer.CustomerRequestType;

public class BadCustomerResponse extends CustomerBaseResponse {
	public ResponseStatus status;
	public String statusDescription;

	public BadCustomerResponse(ResponseStatus status, String statusDescription){
		this.requestType = CustomerRequestType.BAD_REQUEST;
		this.status = status;
		this.statusDescription = statusDescription;
	}
	
	@Override
	public String toString() {
		return status + ": " + statusDescription;
	}
}
