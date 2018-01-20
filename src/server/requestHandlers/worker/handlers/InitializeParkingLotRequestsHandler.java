package server.requestHandlers.worker.handlers;

import java.sql.SQLException;

import core.worker.WorkerRequestType;
import core.worker.requests.BaseRequest;
import core.worker.requests.InitializeParkingLotRequest;
import core.worker.responses.WorkerBaseResponse;
import core.worker.responses.WorkerResponse;
import ocsf.server.ConnectionToClient;
import server.requestHandlers.worker.IProvideConnectionsToClient;
import server.requestHandlers.worker.WorkerResponseFactory;

public class InitializeParkingLotRequestsHandler extends BaseRequestsHandler {

	public InitializeParkingLotRequestsHandler(IProvideConnectionsToClient connectionsToClientProvider) {
		super(connectionsToClientProvider);
	}

	@Override
	protected WorkerRequestType getHandlerRequestsType() {
		return WorkerRequestType.INITIALIZE_PARKING_LOT;
	}

	@Override
	protected WorkerResponse HandleSpecificRequest(BaseRequest specificRequest, ConnectionToClient client) throws SQLException {
		InitializeParkingLotRequest initializeRequest = (InitializeParkingLotRequest) specificRequest;
		int newParkingLotId = parkingLotsManager.addParkingLot(3, 3, initializeRequest.numberOfCoulmns);
		ratesManager.addRates(newParkingLotId);
		WorkerBaseResponse response = WorkerResponseFactory.CreateInitializeParkingLotResponse(newParkingLotId);
		return CreateWorkerResponse(response);
	}
}
