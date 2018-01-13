package server.workerServer.workerRequestsHandlers;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;

import core.worker.Permissions;
import core.worker.WorkerRequestType;
import core.worker.WorkerRole;
import core.worker.requests.BaseRequest;
import core.worker.requests.PermissionsRequest;
import core.worker.responses.BaseResponse;
import core.worker.responses.WorkerResponse;
import server.db.SqlColumns;
import server.workerServer.WorkerResponseFactory;

public class PermissionsRequestsHandler extends BaseRequestsHandler {

	@Override
	protected WorkerRequestType getHandlerRequestsType() {
		return WorkerRequestType.REQUEST_PERMISSIONS;
	}

	@Override
	protected WorkerResponse HandleSpecificRequest(BaseRequest specificRequest) throws SQLException {
		PermissionsRequest permissionsRequest = (PermissionsRequest) specificRequest;
		ArrayList<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
		workersDBAPI.getWorkerByNameAndPassword(permissionsRequest.workerId, permissionsRequest.password, resultList);
		if (resultList.isEmpty()) {
			return createRequestDeniedResponse("Wrong ID or password");
		}

		Map<String, Object> result = resultList.iterator().next();
		WorkerRole workerRole = WorkerRole.valueOf((String) result.get(SqlColumns.Workers.ROLE_TYPE));
		int lotId = result.get(SqlColumns.Workers.LOT_ID) == null ? 0 : (Integer)result.get(SqlColumns.Workers.LOT_ID);
		BaseResponse response = WorkerResponseFactory.CreatePermissionsResponse(
				permissionsRequest.workerId,
				lotId,
				workerRole,
				new Permissions(workerRole));
		return CreateWorkerResponse(response);
	}
}
