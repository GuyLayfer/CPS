package server.requestHandlers.worker.handlers;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;

import core.worker.WorkerRequestType;
import core.worker.requests.BaseRequest;
import core.worker.requests.UpdateRatesRequest;
import core.worker.responses.WorkerBaseResponse;
import core.worker.responses.WorkerResponse;
import ocsf.server.ConnectionToClient;
import server.requestHandlers.worker.IProvideConnectionsToClient;

import server.requestHandlers.worker.WorkerResponseFactory;
public class UpdateRatesRequestsHandler extends BaseRequestsHandler {

	public UpdateRatesRequestsHandler(IProvideConnectionsToClient connectionsToClientProvider) {
		super(connectionsToClientProvider);
	}
	
	@Override
	protected WorkerRequestType getHandlerRequestsType() {
		return WorkerRequestType.UPDATE_RATES;
	}

	@Override
	protected WorkerResponse HandleSpecificRequest(BaseRequest specificRequest, ConnectionToClient client) throws SQLException {
		UpdateRatesRequest updateRateRequest = (UpdateRatesRequest) specificRequest;
		ArrayList<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
		workersDBAPI.selectPendingRatesDetails(updateRateRequest.lotId, resultList);
		if (!resultList.isEmpty()) {
			return createRequestDeniedResponse("There's Rate Request already pending. Please wait for Firm Manager approval.");
		}
		
		workersDBAPI.insertRatesOfLotId(true, updateRateRequest.lotId, updateRateRequest.preOrderedRate,
				updateRateRequest.occasionalRate, updateRateRequest.fullNonthlyField,
				updateRateRequest.rutineMonthlyRate, updateRateRequest.rutineMonthlyMultipleRate);
		WorkerBaseResponse response = WorkerResponseFactory.CreateUpdateRatesResponse();
		return CreateWorkerResponse(response);
	}
}
