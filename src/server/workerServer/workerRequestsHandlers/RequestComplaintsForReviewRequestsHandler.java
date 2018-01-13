package server.workerServer.workerRequestsHandlers;

import java.sql.SQLException;

import core.worker.WorkerRequestType;
import core.worker.requests.BaseRequest;
import core.worker.responses.WorkerResponse;

public class RequestComplaintsForReviewRequestsHandler extends BaseRequestsHandler {

	@Override
	protected WorkerRequestType getHandlerRequestsType() {
		return WorkerRequestType.REQUEST_COMPLAINTS_FOR_REVIEW;
	}

	@Override
	protected WorkerResponse HandleSpecificRequest(BaseRequest specificRequest) throws SQLException {
		return createUnsupportedFeatureResponse();
//		BaseResponse response = WorkerResponseFactory.CreateRequestComplaintsForReviewResponse(complaintsToReview);
//		return CreateWorkerResponse(response);
	}
}
