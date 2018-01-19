package server.requestHandlers.worker.handlers;

import java.sql.SQLException;

import core.worker.WorkerRequestType;
import core.worker.requests.BaseRequest;
import core.worker.requests.UpdateRatesRequest;
import core.worker.responses.WorkerBaseResponse;
import core.worker.responses.WorkerResponse;
import server.requestHandlers.worker.WorkerResponseFactory;
public class UpdateRatesRequestsHandler extends BaseRequestsHandler {

	@Override
	protected WorkerRequestType getHandlerRequestsType() {
		return WorkerRequestType.UPDATE_RATES;
	}

	@Override
	protected WorkerResponse HandleSpecificRequest(BaseRequest specificRequest) throws SQLException {
		UpdateRatesRequest updateRateRequest = (UpdateRatesRequest) specificRequest;
		workersDBAPI.insertRatesOfLotId(true, updateRateRequest.lotId, updateRateRequest.preOrderedRate
				, updateRateRequest.occasionalRate, updateRateRequest.rutineMonthlyRate
				, updateRateRequest.fullNonthlyField, updateRateRequest.rutineMonthlyMultipleRate);
		WorkerBaseResponse response = WorkerResponseFactory.CreateUpdateRatesResponse();
		return CreateWorkerResponse(response);
	}
}
