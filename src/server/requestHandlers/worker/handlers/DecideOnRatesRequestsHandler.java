package server.requestHandlers.worker.handlers;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;

import core.worker.WorkerRequestType;
import core.worker.requests.BaseRequest;
import core.worker.requests.DecideOnComplaintRequest;
import core.worker.responses.WorkerBaseResponse;
import core.worker.responses.WorkerResponse;
import ocsf.server.ConnectionToClient;
import server.requestHandlers.worker.IProvideConnectionsToClient;

import server.requestHandlers.worker.WorkerResponseFactory;
import server.db.dbAPI.WorkersDBAPI;
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
//		DecideOnRateRequest decideOnRatesRequest = (DecideOnRateRequest) specificRequest;
//		ArrayList<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
//		if(decideOnRatesRequest.isNewRateApproved) {
//			workersDBAPI.selectAllLotsRates(decideOnRatesRequest.isNewRateApproved, resultList);
//			workersDBAPI.insertRatesOfLotId(false, decideOnRatesRequest.ratesToDecide.parkingLotId
//					, decideOnRatesRequest.ratesToDecide.preOrderedParkingRate,decideOnRatesRequest.ratesToDecide.occasionalParkingRate , subscriptionFull, subscriptionOccasional, subscriptionMultipleCarsPrice);
//
//		};
//		
//		workersDBAPI.insertRatesOfLotId(insertIntoPending, lotId, oneTimeParking, order, subscriptionFull, subscriptionOccasional, subscriptionMultipleCarsPrice);
////		regularDBAPI.updateComplaint(decideOnComplaintRequest.isComplaintApproved, resultList);
////		
////		if(decideOnComplaintRequest.isComplaintApproved) {
////			regularDBAPI.updateCustomerBalance(decideOnComplaintRequest.complaintToDecide.getCustomerId(), decideOnComplaintRequest.amountToAcquit);
//		};
////		WorkerBaseResponse response = WorkerResponseFactory.CreateDecideOnComplaintsResponse();
////		return CreateWorkerResponse(response);
//	}
		return createUnsupportedFeatureResponse();
		// BaseResponse response = WorkerResponseFactory.CreateDecideOnRatesResponse();
		// return CreateWorkerResponse(response);
	}
}
