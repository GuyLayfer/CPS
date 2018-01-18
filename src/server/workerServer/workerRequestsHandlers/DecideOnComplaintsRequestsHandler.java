package server.workerServer.workerRequestsHandlers;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;

import core.worker.WorkerRequestType;
import core.worker.requests.BaseRequest;
import core.worker.responses.WorkerResponse;
import core.worker.responses.WorkerBaseResponse;
import core.worker.requests.DecideOnComplaintRequest;
import server.workerServer.WorkerResponseFactory;

public class DecideOnComplaintsRequestsHandler extends BaseRequestsHandler {

	@Override
	protected WorkerRequestType getHandlerRequestsType() {
		return WorkerRequestType.DECIDE_ON_COMPLAINTS;
	}

	@Override
	protected WorkerResponse HandleSpecificRequest(BaseRequest specificRequest) throws SQLException {
		DecideOnComplaintRequest decideOnComplaintRequest = (DecideOnComplaintRequest) specificRequest;
		ArrayList<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
		regularDBAPI.selectComplaintDetails(decideOnComplaintRequest.complaintToDecide.getComplaintId(), resultList);
		if (resultList.isEmpty()) {
			return createRequestDeniedResponse("Wrong ID or password");
		}
		WorkerBaseResponse response = WorkerResponseFactory.CreateDecideOnComplaintsResponse();
		return CreateWorkerResponse(response);
	}
}
