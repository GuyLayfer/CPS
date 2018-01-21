package server.requestHandlers.worker.handlers;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;

import core.worker.WorkerRequestType;
import core.worker.requests.BaseRequest;
import core.worker.responses.WorkerResponse;
import ocsf.server.ConnectionToClient;
import server.requestHandlers.worker.IProvideConnectionsToClient;
import server.requestHandlers.worker.WorkerResponseFactory;
import core.worker.responses.WorkerBaseResponse;
import core.worker.requests.DecideOnComplaintRequest;

// TODO: Auto-generated Javadoc
/**
 * The Class DecideOnComplaintsRequestsHandler.
 */
public class DecideOnComplaintsRequestsHandler extends BaseRequestsHandler {

	/**
	 * Instantiates a new decide on complaints requests handler.
	 *
	 * @param connectionsToClientProvider the connections to client provider
	 */
	public DecideOnComplaintsRequestsHandler(IProvideConnectionsToClient connectionsToClientProvider) {
		super(connectionsToClientProvider);
	}

	/* (non-Javadoc)
	 * @see server.requestHandlers.worker.handlers.BaseRequestsHandler#getHandlerRequestsType()
	 */
	@Override
	protected WorkerRequestType getHandlerRequestsType() {
		return WorkerRequestType.DECIDE_ON_COMPLAINTS;
	}

	/* (non-Javadoc)
	 * @see server.requestHandlers.worker.handlers.BaseRequestsHandler#HandleSpecificRequest(core.worker.requests.BaseRequest, ocsf.server.ConnectionToClient)
	 */
	@Override
	protected WorkerResponse HandleSpecificRequest(BaseRequest specificRequest, ConnectionToClient client) throws SQLException {
		DecideOnComplaintRequest decideOnComplaintRequest = (DecideOnComplaintRequest) specificRequest;
		ArrayList<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
		regularDBAPI.selectComplaintDetails(decideOnComplaintRequest.complaintToDecide.getComplaintId(), resultList);

		if (resultList.isEmpty()) {
			return createRequestDeniedResponse("The complaint is no longer relevant.");
		}

		String customerServiceResponse = "";
		if (decideOnComplaintRequest.isComplaintApproved) {
			customerServiceResponse = "Complaint was approved";
		} else {
			customerServiceResponse = "Complaint was declined";
		}

		if (decideOnComplaintRequest.isComplaintApproved && decideOnComplaintRequest.amountToAcquit > 0) {
			customerServiceResponse = customerServiceResponse.concat(" and acquited with the amount: " + decideOnComplaintRequest.amountToAcquit);
			
			ArrayList<Map<String, Object>> resultList2 = new ArrayList<Map<String, Object>>();
			regularDBAPI.selectCustomerAccountDetails(decideOnComplaintRequest.complaintToDecide.getCustomerId(), resultList2);
			if (!resultList2.isEmpty()) {
				regularDBAPI.updateCustomerBalance(decideOnComplaintRequest.complaintToDecide.getCustomerId(), decideOnComplaintRequest.amountToAcquit);
			}
		}

		regularDBAPI.updateComplaint(decideOnComplaintRequest.complaintToDecide, decideOnComplaintRequest.isComplaintApproved, customerServiceResponse);

		WorkerBaseResponse response = WorkerResponseFactory.CreateDecideOnComplaintsResponse(customerServiceResponse);
		return CreateWorkerResponse(response);
	}
}
