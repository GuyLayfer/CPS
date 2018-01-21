package core.worker.responses;

import core.worker.WorkerRequestType;

// TODO: Auto-generated Javadoc
/**
 * The Class WorkerResponse.
 */
public class WorkerResponse {

	/** The request type. */
	public WorkerRequestType requestType;
	
	/** The json data. */
	public String jsonData;
	
	/**
	 * Instantiates a new worker response.
	 *
	 * @param requestType the request type
	 * @param jsonData the json data
	 */
	public WorkerResponse(WorkerRequestType requestType, String jsonData) {
		this.requestType = requestType;
		this.jsonData = jsonData;
	}
}
