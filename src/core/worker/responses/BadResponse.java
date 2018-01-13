package core.worker.responses;

import core.ResponseStatus;

public class BadResponse extends BaseResponse{

	public ResponseStatus status;

	public String statusDescription;
	
	public BadResponse(ResponseStatus status, String statusDescription){
		this.status = status;
		this.statusDescription = statusDescription;
	}
}
