package server.requestHandlers.worker.handlers;

import java.sql.SQLException;

import core.worker.WorkerRequestType;
import core.worker.requests.BaseRequest;
import core.worker.responses.WorkerBaseResponse;
import core.worker.responses.WorkerResponse;
import ocsf.server.ConnectionToClient;
import server.parkingLot.ParkingLotsManager;
import server.requestHandlers.worker.IProvideConnectionsToClient;
import server.requestHandlers.worker.WorkerResponseFactory;

public class ParkingLotNamesRequestsHandler extends BaseRequestsHandler {
	private ParkingLotsManager parkingLotsManager = ParkingLotsManager.getInstance();

	public ParkingLotNamesRequestsHandler(IProvideConnectionsToClient connectionsToClientProvider) {
		super(connectionsToClientProvider);
	}

	@Override
	protected WorkerRequestType getHandlerRequestsType() {
		return WorkerRequestType.PARKING_LOT_NAMES;
	}

	@Override
	protected WorkerResponse HandleSpecificRequest(BaseRequest specificRequest, ConnectionToClient client) throws SQLException {
		WorkerBaseResponse response = WorkerResponseFactory.CreateParkingLotNamesResponse(parkingLotsManager.getAllIds());
		return CreateWorkerResponse(response);
	}
}
