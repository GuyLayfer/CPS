package server.requestHandlers.worker.handlers;

import java.sql.SQLException;

import core.worker.WorkerRequestType;
import core.worker.requests.BaseRequest;
import core.worker.requests.ParkingLotFullRequest;
import core.worker.responses.WorkerBaseResponse;
import core.worker.responses.WorkerResponse;
import ocsf.server.ConnectionToClient;
import server.requestHandlers.worker.IProvideConnectionsToClient;
import server.requestHandlers.worker.WorkerResponseFactory;

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
		ParkingLotFullRequest request = (ParkingLotFullRequest) specificRequest;
		WorkerBaseResponse response = WorkerResponseFactory.CreateParkingLotFullResponse(request.setParkingLotIsFull, request.parkingLotId);
		return CreateWorkerResponse(response);
	}
}
