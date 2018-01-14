package core.worker;

import java.util.HashMap;
import java.util.Map;

public class Permissions {
	private Map<WorkerOperations, Boolean> permissionsForOperation;
	private WorkerRole workerRole;

	public Permissions(WorkerRole workerRole) {
		this.workerRole = workerRole;
		Map<WorkerRole, Boolean> permissionsForRole = new HashMap<WorkerRole, Boolean>();
		for (WorkerRole role : WorkerRole.values()) {
			permissionsForRole.put(role, role == workerRole);
		}

		permissionsForOperation = new HashMap<WorkerOperations, Boolean>();
		setPermissionsForOperations(permissionsForRole);
	}

	public Boolean isOperationAllowed(WorkerOperations operation) {
		return permissionsForOperation.get(operation);
	}

	public WorkerRole getWorkerRole() {
		return workerRole;
	}

	private void setPermissionsForOperations(Map<WorkerRole, Boolean> permissionsForRole) {
		permissionsForOperation.put(WorkerOperations.ACQUIT_OR_CHARGE_ACCOUNT, 
				permissionsForRole.get(WorkerRole.CUSTOMER_SERVICE) || permissionsForRole.get(WorkerRole.LOT_MANAGER)
				|| permissionsForRole.get(WorkerRole.FIRM_MANAGER));
		permissionsForOperation.put(WorkerOperations.CANCEL_CUSTOMER_ORDER,
				permissionsForRole.get(WorkerRole.CUSTOMER_SERVICE) || permissionsForRole.get(WorkerRole.FIRM_MANAGER));
		permissionsForOperation.put(WorkerOperations.DECIDE_ON_COMPLAINTS, 
				permissionsForRole.get(WorkerRole.CUSTOMER_SERVICE) || permissionsForRole.get(WorkerRole.FIRM_MANAGER));
		permissionsForOperation.put(WorkerOperations.DECIDE_ON_RATES, permissionsForRole.get(WorkerRole.FIRM_MANAGER));
		permissionsForOperation.put(WorkerOperations.INITIALIZE_PARKING_LOT, 
				permissionsForRole.get(WorkerRole.LOT_WORKER) || permissionsForRole.get(WorkerRole.LOT_MANAGER)
				|| permissionsForRole.get(WorkerRole.FIRM_MANAGER));
		permissionsForOperation.put(WorkerOperations.OPERATION_REPORT, 
				permissionsForRole.get(WorkerRole.LOT_MANAGER) || permissionsForRole.get(WorkerRole.FIRM_MANAGER));
		permissionsForOperation.put(WorkerOperations.OUT_OF_ORDER, 
				permissionsForRole.get(WorkerRole.LOT_WORKER) || permissionsForRole.get(WorkerRole.LOT_MANAGER)
				|| permissionsForRole.get(WorkerRole.FIRM_MANAGER));
		permissionsForOperation.put(WorkerOperations.PARKING_LOT_FULL, 
				permissionsForRole.get(WorkerRole.LOT_WORKER) || permissionsForRole.get(WorkerRole.LOT_MANAGER)
				|| permissionsForRole.get(WorkerRole.FIRM_MANAGER));
		permissionsForOperation.put(WorkerOperations.RESERVE_PARKING_SPACE, 
				permissionsForRole.get(WorkerRole.CUSTOMER_SERVICE) || permissionsForRole.get(WorkerRole.FIRM_MANAGER));
		permissionsForOperation.put(WorkerOperations.RESERVE_PARKING_SPACE, 
				permissionsForRole.get(WorkerRole.CUSTOMER_SERVICE) || permissionsForRole.get(WorkerRole.LOT_MANAGER)
				|| permissionsForRole.get(WorkerRole.FIRM_MANAGER));
		permissionsForOperation.put(WorkerOperations.STATISTICS_REPORT, 
				permissionsForRole.get(WorkerRole.LOT_MANAGER) || permissionsForRole.get(WorkerRole.FIRM_MANAGER));
		permissionsForOperation.put(WorkerOperations.UPDATE_RATES, 
				permissionsForRole.get(WorkerRole.LOT_MANAGER) || permissionsForRole.get(WorkerRole.FIRM_MANAGER));
	}
}
