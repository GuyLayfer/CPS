package server.requestHandlers.worker.handlers;

import java.sql.SQLException;

import core.worker.WorkerRequestType;
import core.worker.requests.BaseRequest;
import core.worker.responses.WorkerResponse;
import ocsf.server.ConnectionToClient;
import server.requestHandlers.worker.IProvideConnectionsToClient;

public class ParkingLotFullRequestsHandler extends BaseRequestsHandler {

	public ParkingLotFullRequestsHandler(IProvideConnectionsToClient connectionsToClientProvider) {
		super(connectionsToClientProvider);
	}
	
	@Override
	protected WorkerRequestType getHandlerRequestsType() {
		return WorkerRequestType.PARKING_LOT_FULL;
	}

	@Override
	protected WorkerResponse HandleSpecificRequest(BaseRequest specificRequest, ConnectionToClient client) throws SQLException {
		return createUnsupportedFeatureResponse();
//		BaseResponse response = WorkerResponseFactory.CreateParkingLotFullResponse(parkingLotIsFullState, lotId);
//		return CreateWorkerResponse(response);
	}
}
