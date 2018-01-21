package core.customer.responses;

import core.ResponseStatus;
import core.customer.CustomerRequestType;

// TODO: Auto-generated Javadoc
/**
 * The Class BadCustomerResponse.
 */
public class BadCustomerResponse extends CustomerBaseResponse {
	
	/** The status. */
	public ResponseStatus status;
	
	/** The status description. */
	public String statusDescription;

	/**
	 * Instantiates a new bad customer response.
	 *
	 * @param status the status
	 * @param statusDescription the status description
	 */
	public BadCustomerResponse(ResponseStatus status, String statusDescription){
		this.requestType = CustomerRequestType.BAD_REQUEST;
		this.status = status;
		this.statusDescription = statusDescription;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return status + ": " + statusDescription;
	}
}
