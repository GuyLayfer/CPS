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

// TODO: Auto-generated Javadoc
/**
 * The Class ParkingLotFullRequestsHandler.
 */
public class ParkingLotFullRequestsHandler extends BaseRequestsHandler {

	/**
	 * Instantiates a new parking lot full requests handler.
	 *
	 * @param connectionsToClientProvider the connections to client provider
	 */
	public ParkingLotFullRequestsHandler(IProvideConnectionsToClient connectionsToClientProvider) {
		super(connectionsToClientProvider);
	}
	
	/* (non-Javadoc)
	 * @see server.requestHandlers.worker.handlers.BaseRequestsHandler#getHandlerRequestsType()
	 */
	@Override
	protected WorkerRequestType getHandlerRequestsType() {
		return WorkerRequestType.PARKING_LOT_FULL;
	}

	/* (non-Javadoc)
	 * @see server.requestHandlers.worker.handlers.BaseRequestsHandler#HandleSpecificRequest(core.worker.requests.BaseRequest, ocsf.server.ConnectionToClient)
	 */
	@Override
	protected WorkerResponse HandleSpecificRequest(BaseRequest specificRequest, ConnectionToClient client) throws SQLException {
		ParkingLotFullRequest request = (ParkingLotFullRequest) specificRequest;
		WorkerBaseResponse response = WorkerResponseFactory.CreateParkingLotFullResponse(request.setParkingLotIsFull, request.parkingLotId);
		return CreateWorkerResponse(response);
	}
}
