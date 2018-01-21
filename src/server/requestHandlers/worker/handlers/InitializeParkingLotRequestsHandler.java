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

// TODO: Auto-generated Javadoc
/**
 * The Class InitializeParkingLotRequestsHandler.
 */
public class InitializeParkingLotRequestsHandler extends BaseRequestsHandler {

	/**
	 * Instantiates a new initialize parking lot requests handler.
	 *
	 * @param connectionsToClientProvider the connections to client provider
	 */
	public InitializeParkingLotRequestsHandler(IProvideConnectionsToClient connectionsToClientProvider) {
		super(connectionsToClientProvider);
	}

	/* (non-Javadoc)
	 * @see server.requestHandlers.worker.handlers.BaseRequestsHandler#getHandlerRequestsType()
	 */
	@Override
	protected WorkerRequestType getHandlerRequestsType() {
		return WorkerRequestType.INITIALIZE_PARKING_LOT;
	}

	/* (non-Javadoc)
	 * @see server.requestHandlers.worker.handlers.BaseRequestsHandler#HandleSpecificRequest(core.worker.requests.BaseRequest, ocsf.server.ConnectionToClient)
	 */
	@Override
	protected WorkerResponse HandleSpecificRequest(BaseRequest specificRequest, ConnectionToClient client) throws SQLException {
		InitializeParkingLotRequest initializeRequest = (InitializeParkingLotRequest) specificRequest;
		int newParkingLotId = parkingLotsManager.addParkingLot(3, 3, initializeRequest.numberOfCoulmns);
		ratesManager.addRates(newParkingLotId);
		WorkerBaseResponse response = WorkerResponseFactory.CreateInitializeParkingLotResponse(newParkingLotId);
		return CreateWorkerResponse(response);
	}
}
