package server.requestHandlers.worker.handlers;

import java.sql.SQLException;

import core.worker.WorkerRequestType;
import core.worker.requests.BaseRequest;
import core.worker.requests.OutOfOrderRequest;
import core.worker.responses.WorkerBaseResponse;
import core.worker.responses.WorkerResponse;
import ocsf.server.ConnectionToClient;
import server.db.dbAPI.ReportsDBAPI;
import server.parkingLot.ParkingLotsManager;
import server.parkingLot.exceptions.LotIdDoesntExistException;
import server.requestHandlers.worker.IProvideConnectionsToClient;
import server.requestHandlers.worker.WorkerResponseFactory;

public class OutOfOrderRequestsHandler extends BaseRequestsHandler {

	public OutOfOrderRequestsHandler(IProvideConnectionsToClient connectionsToClientProvider) {
		super(connectionsToClientProvider);
	}

	@Override
	protected WorkerRequestType getHandlerRequestsType() {
		return WorkerRequestType.OUT_OF_ORDER;
	}

	@Override
	protected WorkerResponse HandleSpecificRequest(BaseRequest specificRequest, ConnectionToClient client) throws SQLException {
		OutOfOrderRequest outOfOrder = (OutOfOrderRequest) specificRequest;
		if(outOfOrder.isOutOfOrder) {
			try {
				ParkingLotsManager.getInstance().setBrokenPlace(outOfOrder.lotId, outOfOrder.floor, outOfOrder.row, outOfOrder.column );
				ReportsDBAPI.getInstance().updateBrokenParkingStatus(outOfOrder.isOutOfOrder, outOfOrder.lotId, outOfOrder.row, outOfOrder.column, outOfOrder.floor);
				WorkerBaseResponse response = WorkerResponseFactory.CreateOutOfOrderResponse(outOfOrder.lotId, outOfOrder.row, outOfOrder.column, outOfOrder.floor, outOfOrder.isOutOfOrder);
				 return CreateWorkerResponse(response);
			} catch (IndexOutOfBoundsException | LotIdDoesntExistException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else {
			try {
				parkingLotsManager.cancelBrokenPlaceSetting(outOfOrder.lotId, outOfOrder.floor, outOfOrder.row, outOfOrder.column);
				ReportsDBAPI.getInstance().updateBrokenParkingStatus(outOfOrder.isOutOfOrder, outOfOrder.lotId, outOfOrder.row, outOfOrder.column, outOfOrder.floor);
				WorkerBaseResponse response = WorkerResponseFactory.CreateOutOfOrderResponse(outOfOrder.lotId, outOfOrder.row, outOfOrder.column, outOfOrder.floor, outOfOrder.isOutOfOrder);
				 return CreateWorkerResponse(response);
			} catch (IndexOutOfBoundsException | LotIdDoesntExistException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		String response = "The request was denied. Either the parking lot does not exist, or parking position is out of pounds.";
		return createRequestDeniedResponse(response);
	}
}
