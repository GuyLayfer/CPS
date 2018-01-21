package workerGui.util;

import java.util.ArrayList;
import java.util.List;

import core.guiUtilities.IServerResponseHandler;
import core.worker.Permissions;
import core.worker.WorkerOperations;
import core.worker.WorkerRequestType;
import core.worker.WorkerRole;
import core.worker.responses.WorkerBaseResponse;
import core.worker.responses.PermissionsResponse;

// TODO: Auto-generated Javadoc
/**
 * The Class WorkerAccountManager.
 */
public class WorkerAccountManager implements IServerResponseHandler<WorkerBaseResponse> {
	
	/** The instance. */
	private static WorkerAccountManager instance;
	
	/** The connection manager. */
	private WorkerConnectionManager connectionManager;
	
	/** The listeners. */
	private List<ICareAboutLoginState> listeners = new ArrayList<ICareAboutLoginState>();
	
	/** The worker logged in. */
	private Boolean workerLoggedIn = false;
	
	/** The worker id. */
	private int workerId;
	
	/** The workerlot id. */
	private int workerlotId;
	
	/** The worker role. */
	private WorkerRole workerRole;
	
	/** The permissions. */
	private Permissions permissions;

	/**
	 * Instantiates a new worker account manager.
	 */
	private WorkerAccountManager() {
		connectionManager = WorkerConnectionManager.getInstance();
		connectionManager.addServerMessageListener(this);
	}

	/**
	 * Gets the single instance of WorkerAccountManager.
	 *
	 * @return single instance of WorkerAccountManager
	 */
	public static WorkerAccountManager getInstance() {
		if (instance == null) {
			instance = new WorkerAccountManager();
			return instance;
		}

		return instance;
	}

	/**
	 * Checks if is worker logged in.
	 *
	 * @return the boolean
	 */
	public Boolean isWorkerLoggedIn() {
		return workerLoggedIn;
	}

	/**
	 * Register login listener.
	 *
	 * @param controller the controller
	 */
	public void registerLoginListener(ICareAboutLoginState controller) {
		listeners.add(controller);
	}

	/**
	 * Checks if is operation allowed.
	 *
	 * @param operation the operation
	 * @return the boolean
	 */
	public Boolean isOperationAllowed(WorkerOperations operation) {
		return permissions.isOperationAllowed(operation);
	}

	/**
	 * Login.
	 *
	 * @param workerId the worker id
	 * @param password the password
	 */
	public void Login(int workerId, String password) {
		connectionManager.sendMessageToServer(WorkerRequestsFactory.CreatePermissionsRequest(workerId, password));
//		 debugMode();
	}

//	private void debugMode() {
//		this.workerId = 1;
//		this.workerlotId = 1;
//		this.workerRole = WorkerRole.FIRM_MANAGER;
//		this.permissions = new Permissions(WorkerRole.FIRM_MANAGER);
//		workerLoggedIn = true;
//		for (ICareAboutLoginState listener : listeners) {
//			listener.handleLogin();
//		}
//	}

	/**
 * Logout.
 */
public void Logout() {
		this.workerId = 0;
		this.workerlotId = 0;
		this.workerRole = null;
		this.permissions = null;
		workerLoggedIn = false;
		for (ICareAboutLoginState listener : listeners) {
			listener.handleLogout();
		}
	}

	/* (non-Javadoc)
	 * @see core.guiUtilities.IServerResponseHandler#handleServerResponse(java.lang.Object)
	 */
	@Override
	public void handleServerResponse(WorkerBaseResponse response) {
		if (response.requestType == WorkerRequestType.REQUEST_PERMISSIONS) {
			PermissionsResponse permissionsResponse = (PermissionsResponse) response;
			this.workerId = permissionsResponse.workerId;
			this.workerlotId = permissionsResponse.workerLotId;
			this.workerRole = permissionsResponse.workerRole;
			this.permissions = permissionsResponse.permissions;
			workerLoggedIn = true;
			for (ICareAboutLoginState listener : listeners) {
				listener.handleLogin();
			}
		}
	}

	/**
	 * Gets the worker id.
	 *
	 * @return the worker id
	 */
	public int getWorkerId() {
		return workerId;
	}

	/**
	 * Gets the workerlot id.
	 *
	 * @return the workerlot id
	 */
	public int getWorkerlotId() {
		return workerlotId;
	}

	/**
	 * Gets the worker role.
	 *
	 * @return the worker role
	 */
	public WorkerRole getWorkerRole() {
		return workerRole;
	}
}
