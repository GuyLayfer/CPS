package server.requestHandlers.worker.handlers;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import core.worker.Permissions;
import core.worker.WorkerRequestType;
import core.worker.WorkerRole;
import core.worker.requests.BaseRequest;
import core.worker.requests.PermissionsRequest;
import core.worker.responses.WorkerBaseResponse;
import core.worker.responses.WorkerResponse;
import ocsf.server.ConnectionToClient;
import server.db.SqlColumns;
import server.requestHandlers.worker.IProvideConnectionsToClient;
import server.requestHandlers.worker.WorkerResponseFactory;

public class PermissionsRequestsHandler extends BaseRequestsHandler {

	public PermissionsRequestsHandler(IProvideConnectionsToClient connectionsToClientProvider) {
		super(connectionsToClientProvider);
	}

	@Override
	protected WorkerRequestType getHandlerRequestsType() {
		return WorkerRequestType.REQUEST_PERMISSIONS;
	}

	@Override
	protected WorkerResponse HandleSpecificRequest(BaseRequest specificRequest, ConnectionToClient client) throws SQLException {
		PermissionsRequest permissionsRequest = (PermissionsRequest) specificRequest;
		ArrayList<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
		workersDBAPI.getWorkerByNameAndPassword(permissionsRequest.workerId, permissionsRequest.password, resultList);
		if (resultList.isEmpty()) {
			return createRequestDeniedResponse("Wrong ID or password");
		}

		if (isWorkerAlreadyConnected(permissionsRequest.workerId)) {
			return createRequestDeniedResponse("User already logged in.");
		}
		
		return createPermissions(resultList.iterator().next(), permissionsRequest.workerId, client);
	}
	
	private Boolean isWorkerAlreadyConnected(int workerId) {
		List<ConnectionToClient> connections = connectionsToClientProvider.getConnections();
		for (ConnectionToClient client : connections) {
			Object infoObject = client.getInfo(USER_NAME);
			if (infoObject != null && workerId == (int)infoObject) {
				return true;
			}
		}
		
		return false;
	}
	
	private WorkerResponse createPermissions(Map<String, Object> result, int workerId, ConnectionToClient client) {
		WorkerRole workerRole = WorkerRole.valueOf((String) result.get(SqlColumns.Workers.ROLE_TYPE));
		int lotId = result.get(SqlColumns.Workers.LOT_ID) == null ? 0 : (Integer)result.get(SqlColumns.Workers.LOT_ID);
		WorkerBaseResponse response = WorkerResponseFactory.CreatePermissionsResponse(
				workerId,
				lotId,
				workerRole,
				new Permissions(workerRole));
		client.setInfo(USER_NAME, workerId);
		return CreateWorkerResponse(response);
	}
}
