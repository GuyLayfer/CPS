package server.requestHandlers.worker.handlers;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;

import core.worker.WorkerRequestType;
import core.worker.requests.BaseRequest;
import core.worker.requests.ReserveParkingSpaceRequest;
import core.worker.responses.BadResponse;
import core.worker.responses.WorkerBaseResponse;
import core.worker.responses.WorkerResponse;
import ocsf.server.ConnectionToClient;
import server.db.DBConstants;
import server.db.DBConstants.TrueFalse;
import server.db.dbAPI.RegularDBAPI;
import server.parkingLot.ParkingLotsManager;
import server.parkingLot.exceptions.DateIsNotWithinTheNext24Hours;
import server.parkingLot.exceptions.LotIdDoesntExistException;
import server.requestHandlers.worker.IProvideConnectionsToClient;
import server.requestHandlers.worker.WorkerResponseFactory;
import server.rates.PriceCalculator;


// TODO: Auto-generated Javadoc
/**
 * The Class ReserveParkingSpaceRequestsHandler.
 */
public class ReserveParkingSpaceRequestsHandler extends BaseRequestsHandler {

	/**
	 * Instantiates a new reserve parking space requests handler.
	 *
	 * @param connectionsToClientProvider the connections to client provider
	 */
	public ReserveParkingSpaceRequestsHandler(IProvideConnectionsToClient connectionsToClientProvider) {
		super(connectionsToClientProvider);
	}
	
	/* (non-Javadoc)
	 * @see server.requestHandlers.worker.handlers.BaseRequestsHandler#getHandlerRequestsType()
	 */
	@Override
	protected WorkerRequestType getHandlerRequestsType() {
		return WorkerRequestType.RESERVE_PARKING_SPACE;
	}

	/* (non-Javadoc)
	 * @see server.requestHandlers.worker.handlers.BaseRequestsHandler#HandleSpecificRequest(core.worker.requests.BaseRequest, ocsf.server.ConnectionToClient)
	 */
	@Override
	protected WorkerResponse HandleSpecificRequest(BaseRequest specificRequest, ConnectionToClient client) throws SQLException {
		ReserveParkingSpaceRequest reserveParkingSpace = (ReserveParkingSpaceRequest) specificRequest;
		ArrayList<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
		try {
			if(ParkingLotsManager.getInstance().reservePlace(reserveParkingSpace.parkingLotID
					, reserveParkingSpace.licensePlate
					, reserveParkingSpace.arrivalTime)) {
				regularDBAPI.selectCustomerAccountDetails(reserveParkingSpace.customerID, resultList);
				if (resultList.isEmpty())
					regularDBAPI.insertNewAccount(reserveParkingSpace.customerID, reserveParkingSpace.email, reserveParkingSpace.licensePlate, TrueFalse.FALSE);
				int orderId = regularDBAPI.insertParkingReservation(reserveParkingSpace.licensePlate
						, reserveParkingSpace.customerID
						, reserveParkingSpace.parkingLotID, reserveParkingSpace.arrivalTime
						, reserveParkingSpace.estimatedDepartureTime
						, null, null, DBConstants.OrderType.ONE_TIME , reserveParkingSpace.email);
				double price = PriceCalculator.getInstance().calculatePreOrdered(reserveParkingSpace.parkingLotID, reserveParkingSpace.arrivalTime, reserveParkingSpace.estimatedDepartureTime);
				WorkerBaseResponse response = WorkerResponseFactory.CreateReserveParkingSpaceResponse(orderId, price);
				return CreateWorkerResponse(response);
			
			}
		} catch (LotIdDoesntExistException e) {
			// TODO Auto-generated catch block	
			e.printStackTrace();
		} catch (DateIsNotWithinTheNext24Hours e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String response = "your request was denied.\n"
				+ " Either the lot id you entered does not exist, or the reservation you asked is not within the next 24 hours."; 
		return createRequestDeniedResponse(response);
	}
}
