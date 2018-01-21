package core.worker.responses;

import core.worker.Permissions;
import core.worker.WorkerRole;

// TODO: Auto-generated Javadoc
/**
 * The Class PermissionsResponse.
 */
public class PermissionsResponse extends WorkerBaseResponse {
	
	/** The permissions. */
	public Permissions permissions;
	
	/** The worker id. */
	public int workerId;
	
	/** The worker role. */
	public WorkerRole workerRole;
	
	/** The worker lot id. */
	public int workerLotId;
}
