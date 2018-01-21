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

// TODO: Auto-generated Javadoc
/**
 * The Class PermissionsRequestsHandler.
 */
public class PermissionsRequestsHandler extends BaseRequestsHandler {

	/**
	 * Instantiates a new permissions requests handler.
	 *
	 * @param connectionsToClientProvider the connections to client provider
	 */
	public PermissionsRequestsHandler(IProvideConnectionsToClient connectionsToClientProvider) {
		super(connectionsToClientProvider);
	}

	/* (non-Javadoc)
	 * @see server.requestHandlers.worker.handlers.BaseRequestsHandler#getHandlerRequestsType()
	 */
	@Override
	protected WorkerRequestType getHandlerRequestsType() {
		return WorkerRequestType.REQUEST_PERMISSIONS;
	}

	/* (non-Javadoc)
	 * @see server.requestHandlers.worker.handlers.BaseRequestsHandler#HandleSpecificRequest(core.worker.requests.BaseRequest, ocsf.server.ConnectionToClient)
	 */
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
	
	/**
	 * Checks if is worker already connected.
	 *
	 * @param workerId the worker id
	 * @return the boolean
	 */
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
	
	/**
	 * Creates the permissions.
	 *
	 * @param result the result
	 * @param workerId the worker id
	 * @param client the client
	 * @return the worker response
	 */
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
