package server.requestHandlers.worker.handlers;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;

import core.worker.WorkerRequestType;
import core.worker.requests.BaseRequest;
import core.worker.responses.WorkerResponse;
import server.db.SqlColumns;
import server.requestHandlers.worker.WorkerResponseFactory;
import core.worker.responses.WorkerBaseResponse;
import core.worker.requests.DecideOnComplaintRequest;

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
			return createRequestDeniedResponse("Wrong ID");
		}
		regularDBAPI.updateComplaint(decideOnComplaintRequest.isComplaintApproved, resultList);
		
		if(decideOnComplaintRequest.isComplaintApproved) {
			regularDBAPI.updateCustomerBalance(decideOnComplaintRequest.complaintToDecide.getCustomerId(), decideOnComplaintRequest.amountToAcquit);
		};
		WorkerBaseResponse response = WorkerResponseFactory.CreateDecideOnComplaintsResponse();
		return CreateWorkerResponse(response);
	}
}
