package core.worker;

import core.AbstractResponse;
import core.ResponseStatus;
import core.Customer.CustomerRequestType;

//The usage is similar to the usage of CustomerResponse
//TODO: add responseType to jsonData-type map as in CustomerResponse
public class WorkerResponse extends AbstractResponse {
	// requestType has a valid value only if status is OK or REQUEST_DENIED or UNSUPPORTED_FEATURE
	public WorkerRequestType requestType;
	
	public WorkerResponse(ResponseStatus status, String statusDescription, 
							WorkerRequestType requestType, String jsonData) {
		this.status = status;
		this.statusDescription = statusDescription;
		this.requestType = requestType;
		this.jsonData = jsonData;
	}
	
	// Used in cases in which status is OK
	public WorkerResponse(WorkerRequestType requestType, String jsonData) {
		this.status = ResponseStatus.OK;
		this.statusDescription = null;
		this.requestType = requestType;
		this.jsonData = jsonData;
	}
	
	// used in cases in which status is BAD_REQUEST or SERVER_FAILLURE
	public WorkerResponse(ResponseStatus status, String statusDescription) {
		this.status = status;
		this.statusDescription = statusDescription;
	}
}
