package core.worker.responses;

import core.ResponseStatus;
import core.worker.WorkerRequestType;

public class BadResponse extends WorkerBaseResponse{

	public ResponseStatus status;

	public String statusDescription;
	
	public BadResponse(ResponseStatus status, String statusDescription){
		this.requestType = WorkerRequestType.BAD_REQUEST;
		this.status = status;
		this.statusDescription = statusDescription;
	}
	
	@Override
	public String toString(){
		return statusDescription;
	}
}
