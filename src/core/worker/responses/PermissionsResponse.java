package core.worker.responses;

import core.worker.Permissions;
import core.worker.WorkerRole;

public class PermissionsResponse extends WorkerBaseResponse {
	
	public Permissions permissions;
	
	public int workerId;
	
	public WorkerRole workerRole;
	
	public int workerLotId;
}
