package core.worker.responses;

import core.worker.WorkerRequestType;

public class WorkerResponse {

	public WorkerRequestType requestType;
	
	public String jsonData;
	
	public WorkerResponse(WorkerRequestType requestType, String jsonData) {
		this.requestType = requestType;
		this.jsonData = jsonData;
	}
}
