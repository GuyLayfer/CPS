package core.worker.responses;

import core.ResponseStatus;
import core.worker.WorkerRequestType;

// TODO: Auto-generated Javadoc
/**
 * The Class BadResponse.
 */
public class BadResponse extends WorkerBaseResponse{

	/** The status. */
	public ResponseStatus status;

	/** The status description. */
	public String statusDescription;
	
	/**
	 * Instantiates a new bad response.
	 *
	 * @param status the status
	 * @param statusDescription the status description
	 */
	public BadResponse(ResponseStatus status, String statusDescription){
		this.requestType = WorkerRequestType.BAD_REQUEST;
		this.status = status;
		this.statusDescription = statusDescription;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString(){
		return statusDescription;
	}
}
