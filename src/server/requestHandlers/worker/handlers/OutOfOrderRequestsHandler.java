package server.requestHandlers.worker.handlers;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;

import core.worker.WorkerRequestType;
import core.worker.requests.BaseRequest;
import core.worker.requests.OutOfOrderRequest;
import core.worker.responses.WorkerBaseResponse;
import core.worker.responses.WorkerResponse;
import ocsf.server.ConnectionToClient;
import server.parkingLot.exceptions.LotIdDoesntExistException;
import server.requestHandlers.worker.IProvideConnectionsToClient;
import server.requestHandlers.worker.WorkerResponseFactory;

// TODO: Auto-generated Javadoc
/**
 * The Class OutOfOrderRequestsHandler.
 */
public class OutOfOrderRequestsHandler extends BaseRequestsHandler {

	/**
	 * Instantiates a new out of order requests handler.
	 *
	 * @param connectionsToClientProvider the connections to client provider
	 */
	public OutOfOrderRequestsHandler(IProvideConnectionsToClient connectionsToClientProvider) {
		super(connectionsToClientProvider);
	}

	/* (non-Javadoc)
	 * @see server.requestHandlers.worker.handlers.BaseRequestsHandler#getHandlerRequestsType()
	 */
	@Override
	protected WorkerRequestType getHandlerRequestsType() {
		return WorkerRequestType.OUT_OF_ORDER;
	}

	/* (non-Javadoc)
	 * @see server.requestHandlers.worker.handlers.BaseRequestsHandler#HandleSpecificRequest(core.worker.requests.BaseRequest, ocsf.server.ConnectionToClient)
	 */
	@Override
	protected WorkerResponse HandleSpecificRequest(BaseRequest specificRequest, ConnectionToClient client) throws SQLException {
		OutOfOrderRequest outOfOrder = (OutOfOrderRequest) specificRequest;
		try {
			if (outOfOrder.isOutOfOrder) {
				parkingLotsManager.setBrokenPlace(outOfOrder.lotId, outOfOrder.floor, outOfOrder.row, outOfOrder.column);
				reportsDBAPI.setBrokenParkingStatus(outOfOrder.isOutOfOrder, outOfOrder.lotId, outOfOrder.row, outOfOrder.column, outOfOrder.floor);
			} else {
				ArrayList<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
				reportsDBAPI.selectBrokenParkingStatus(outOfOrder.lotId, outOfOrder.row, outOfOrder.column, outOfOrder.floor, resultList);
				if(resultList.isEmpty()) {
					return createRequestDeniedResponse("This parking space wasn't broken");
				}
				
				parkingLotsManager.cancelBrokenPlaceSetting(outOfOrder.lotId, outOfOrder.floor, outOfOrder.row, outOfOrder.column);
				reportsDBAPI.updateBrokenParkingStatus(outOfOrder.lotId, outOfOrder.row, outOfOrder.column, outOfOrder.floor);
			}
			WorkerBaseResponse response = WorkerResponseFactory.CreateOutOfOrderResponse(
					outOfOrder.lotId,
					outOfOrder.row,
					outOfOrder.column,
					outOfOrder.floor,
					outOfOrder.isOutOfOrder);
			return CreateWorkerResponse(response);
		} catch (IndexOutOfBoundsException | LotIdDoesntExistException e) {
			return createRequestDeniedResponse("The request was denied. Either the parking lot does not exist, or parking position is out of bounds.");
		}
	}
}
