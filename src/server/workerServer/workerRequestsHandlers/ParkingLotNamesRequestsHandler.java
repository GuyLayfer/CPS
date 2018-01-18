package server.workerServer.workerRequestsHandlers;

import java.sql.SQLException;

import core.worker.WorkerRequestType;
import core.worker.requests.BaseRequest;
import core.worker.responses.WorkerBaseResponse;
import core.worker.responses.WorkerResponse;
import server.ParkingLotsManager;
import server.workerServer.WorkerResponseFactory;

public class ParkingLotNamesRequestsHandler extends BaseRequestsHandler {
	private ParkingLotsManager parkingLotsManager = ParkingLotsManager.getInstance();

	@Override
	protected WorkerRequestType getHandlerRequestsType() {
		return WorkerRequestType.PARKING_LOT_NAMES;
	}

	@Override
	protected WorkerResponse HandleSpecificRequest(BaseRequest specificRequest) throws SQLException {
		WorkerBaseResponse response = WorkerResponseFactory.CreateParkingLotNamesResponse(parkingLotsManager.getAllIds());
		return CreateWorkerResponse(response);
	}
}
