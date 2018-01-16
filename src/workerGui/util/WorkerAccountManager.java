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

public class WorkerAccountManager implements IServerResponseHandler<WorkerBaseResponse> {
	private static WorkerAccountManager instance;
	private WorkerConnectionManager connectionManager;
	private List<ICareAboutLoginState> listeners = new ArrayList<ICareAboutLoginState>();
	private Boolean workerLoggedIn = false;
	private int workerId;
	private int workerlotId;
	private WorkerRole workerRole;
	private Permissions permissions;

	private WorkerAccountManager() {
		connectionManager = WorkerConnectionManager.getInstance();
		connectionManager.addServerMessageListener(this);
	}

	public static WorkerAccountManager getInstance() {
		if (instance == null) {
			instance = new WorkerAccountManager();
			return instance;
		}

		return instance;
	}

	public Boolean isWorkerLoggedIn() {
		return workerLoggedIn;
	}

	public void registerLoginListener(ICareAboutLoginState controller) {
		listeners.add(controller);
	}

	public Boolean isOperationAllowed(WorkerOperations operation) {
		return permissions.isOperationAllowed(operation);
	}

	public void Login(int workerId, String password) {
		connectionManager.sendMessageToServer(WorkerRequestsFactory.CreatePermissionsRequest(workerId, password));
		 debugMode(); // Enable when someone is connected to DB via MySql and you need to work on the GUI.
	}

	private void debugMode() {
		this.workerId = 1;
		this.workerlotId = 1;
		this.workerRole = WorkerRole.FIRM_MANAGER;
		this.permissions = new Permissions(WorkerRole.FIRM_MANAGER);
		workerLoggedIn = true;
		for (ICareAboutLoginState listener : listeners) {
			listener.handleLogin();
		}
	}

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

	public int getWorkerId() {
		return workerId;
	}

	public int getWorkerlotId() {
		return workerlotId;
	}

	public WorkerRole getWorkerRole() {
		return workerRole;
	}
}
