package server.requestHandlers.worker.handlers;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;

import core.worker.WorkerRequestType;
import core.worker.requests.BaseRequest;
import core.worker.responses.WorkerBaseResponse;
import core.worker.responses.WorkerResponse;
import ocsf.server.ConnectionToClient;
import server.requestHandlers.worker.IProvideConnectionsToClient;

import server.requestHandlers.worker.WorkerResponseFactory;
import core.worker.requests.DecideOnRateRequest;
public class DecideOnRatesRequestsHandler extends BaseRequestsHandler {

	public DecideOnRatesRequestsHandler(IProvideConnectionsToClient connectionsToClientProvider) {
		super(connectionsToClientProvider);
	}

	@Override
	protected WorkerRequestType getHandlerRequestsType() {
		return WorkerRequestType.DECIDE_ON_RATES;
	}

	@Override
	protected WorkerResponse HandleSpecificRequest(BaseRequest specificRequest, ConnectionToClient client) throws SQLException {
		DecideOnRateRequest decideOnRatesRequest = (DecideOnRateRequest) specificRequest;
		ArrayList<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
		
		workersDBAPI.selectPendingRatesDetails(decideOnRatesRequest.ratesToDecide.parkingLotId, resultList);
		if (resultList.isEmpty()) {
			return createRequestDeniedResponse("The Rate Request is no longer relevant.");
		}
		
		if(decideOnRatesRequest.isNewRateApproved) {
			ArrayList<Map<String, Object>> resultList2 = new ArrayList<Map<String, Object>>();
			workersDBAPI.selectRatesDetails(decideOnRatesRequest.ratesToDecide.parkingLotId, resultList2);
			if (resultList.isEmpty()) {
				workersDBAPI.insertRatesOfLotId(false, 
						decideOnRatesRequest.ratesToDecide.parkingLotId,
						decideOnRatesRequest.ratesToDecide.preOrderedParkingRate,
						decideOnRatesRequest.ratesToDecide.occasionalParkingRate, 
						decideOnRatesRequest.ratesToDecide.fullMonthlySubscription,
						decideOnRatesRequest.ratesToDecide.routineMonthlySubscription,
						decideOnRatesRequest.ratesToDecide.routineMonthlySubscriptionMultipleCars);
			} else {
				workersDBAPI.updateExistingRates(decideOnRatesRequest.ratesToDecide.parkingLotId,
				decideOnRatesRequest.ratesToDecide.preOrderedParkingRate,
				decideOnRatesRequest.ratesToDecide.occasionalParkingRate, 
				decideOnRatesRequest.ratesToDecide.routineMonthlySubscription,
				decideOnRatesRequest.ratesToDecide.routineMonthlySubscriptionMultipleCars);
			}
			
			workersDBAPI.updateFullSubscriptionRate(decideOnRatesRequest.ratesToDecide.fullMonthlySubscription);
		}
		
		workersDBAPI.deletePendingRate(decideOnRatesRequest.ratesToDecide.parkingLotId);
		WorkerBaseResponse reply = WorkerResponseFactory.CreateDecideOnRatesResponse(
				decideOnRatesRequest.ratesToDecide.parkingLotId,
				decideOnRatesRequest.isNewRateApproved);
		return CreateWorkerResponse(reply);
	}
}
