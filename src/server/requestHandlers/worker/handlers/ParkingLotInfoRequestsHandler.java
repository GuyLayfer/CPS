package server.requestHandlers.worker.handlers;

import java.sql.SQLException;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import core.parkingLot.ParkingLotInfo;
import core.worker.WorkerRequestType;
import core.worker.requests.BaseRequest;
import core.worker.requests.ParkingLotInfoRequest;
import core.worker.responses.WorkerBaseResponse;
import core.worker.responses.WorkerResponse;
import ocsf.server.ConnectionToClient;
import server.parkingLot.exceptions.LotIdDoesntExistException;
import server.requestHandlers.worker.IProvideConnectionsToClient;
import server.requestHandlers.worker.WorkerResponseFactory;

public class ParkingLotInfoRequestsHandler extends BaseRequestsHandler {
	private Gson gson = new Gson();
	
	public ParkingLotInfoRequestsHandler(IProvideConnectionsToClient connectionsToClientProvider) {
		super(connectionsToClientProvider);
	}

	@Override
	protected WorkerRequestType getHandlerRequestsType() {
		return WorkerRequestType.PARKING_LOT_INFO;
	}

	@Override
	protected WorkerResponse HandleSpecificRequest(BaseRequest specificRequest, ConnectionToClient client) throws SQLException {
		ParkingLotInfoRequest request = (ParkingLotInfoRequest) specificRequest;
		ParkingLotInfo parkingLotInfo;
		try {
			parkingLotInfo = gson.fromJson(parkingLotsManager.parkingLotInfoToJson(request.parkingLotId), ParkingLotInfo.class);
		} catch (JsonSyntaxException | LotIdDoesntExistException e) {
			return createRequestDeniedResponse("Parking lot ID doesn't exists anymore");
		}
		
		WorkerBaseResponse response = WorkerResponseFactory.CreateParkingLotInfoResponse(parkingLotInfo);
		return CreateWorkerResponse(response);
	}

}
