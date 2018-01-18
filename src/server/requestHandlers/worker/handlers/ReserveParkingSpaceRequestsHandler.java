package server.requestHandlers.worker.handlers;

import java.sql.SQLException;

import core.worker.WorkerRequestType;
import core.worker.requests.BaseRequest;
import core.worker.responses.WorkerResponse;
import core.worker.requests.*;
public class ReserveParkingSpaceRequestsHandler extends BaseRequestsHandler {

	@Override
	protected WorkerRequestType getHandlerRequestsType() {
		return WorkerRequestType.RESERVE_PARKING_SPACE;
	}

	@Override
	protected WorkerResponse HandleSpecificRequest(BaseRequest specificRequest) throws SQLException {
		return createUnsupportedFeatureResponse();
		// ReserveParkingSpaceRequest 
//		BaseResponse response = WorkerResponseFactory.CreateReserveParkingSpaceResponse(lotId, row, column, floor);
//		return CreateWorkerResponse(response);
	}
}
