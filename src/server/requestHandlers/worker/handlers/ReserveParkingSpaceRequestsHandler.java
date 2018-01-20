package server.requestHandlers.worker.handlers;

import java.sql.SQLException;

import core.worker.WorkerRequestType;
import core.worker.requests.BaseRequest;
import core.worker.requests.ReserveParkingSpaceRequest;
import core.worker.responses.WorkerBaseResponse;
import core.worker.responses.WorkerResponse;
import ocsf.server.ConnectionToClient;
import server.db.DBConstants;
import server.db.dbAPI.RegularDBAPI;
import server.parkingLot.ParkingLotsManager;
import server.requestHandlers.worker.IProvideConnectionsToClient;
import server.requestHandlers.worker.WorkerResponseFactory;
public class ReserveParkingSpaceRequestsHandler extends BaseRequestsHandler {

	public ReserveParkingSpaceRequestsHandler(IProvideConnectionsToClient connectionsToClientProvider) {
		super(connectionsToClientProvider);
	}
	
	@Override
	protected WorkerRequestType getHandlerRequestsType() {
		return WorkerRequestType.RESERVE_PARKING_SPACE;
	}

	@Override
	protected WorkerResponse HandleSpecificRequest(BaseRequest specificRequest, ConnectionToClient client) throws SQLException {
		ReserveParkingSpaceRequest reserveParkingSpace = (ReserveParkingSpaceRequest) specificRequest;
		if(ParkingLotsManager.getInstance().reservePlace(reserveParkingSpace.parkingLotID, reserveParkingSpace.licensePlate, reserveParkingSpace.arrivalTime) {
		int orderId = regularDBAPI.insertParkingReservation(reserveParkingSpace.licensePlate, reserveParkingSpace.customerID
				, reserveParkingSpace.parkingLotID, reserveParkingSpace.arrivalTime, reserveParkingSpace.estimatedDepartureTime
				, null, null, DBConstants.OrderType.ONE_TIME , reserveParkingSpace.email);
	//	return createUnsupportedFeatureResponse();
		WorkerBaseResponse response = WorkerResponseFactory.CreateReserveParkingSpaceResponse(orderId);
		return CreateWorkerResponse(response);
		}
	}
}
