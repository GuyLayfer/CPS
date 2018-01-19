package server.requestHandlers.worker.handlers;

import java.sql.SQLException;

import core.worker.WorkerRequestType;
import core.worker.requests.BaseRequest;
import core.worker.responses.WorkerResponse;
import ocsf.server.ConnectionToClient;
import server.requestHandlers.worker.IProvideConnectionsToClient;
public class ReserveParkingSpaceRequestsHandler extends BaseRequestsHandler {

	public ReserveParkingSpaceRequestsHandler(IProvideConnectionsToClient connectionsToClientProvider) {
		super(connectionsToClientProvider);
	}
	
	@Override
	protected WorkerRequestType getHandlerRequestsType() {
		return WorkerRequestType.RESERVE_PARKING_SPACE;
	}

	@Override
	protected WorkerResponse HandleSpecificRequest(BaseRequest specificRequest, ConnectionToClient client) throws SQLException {
		return createUnsupportedFeatureResponse();
		// ReserveParkingSpaceRequest 
//		BaseResponse response = WorkerResponseFactory.CreateReserveParkingSpaceResponse(lotId, row, column, floor);
//		return CreateWorkerResponse(response);
	}
}
