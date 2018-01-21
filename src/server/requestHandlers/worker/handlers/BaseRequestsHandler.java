package server.requestHandlers.worker.handlers;

import java.sql.SQLException;

import com.google.gson.Gson;

import core.CpsGson;
import core.ResponseStatus;
import core.worker.WorkerRequestType;
import core.worker.requests.BaseRequest;
import core.worker.responses.BadResponse;
import core.worker.responses.WorkerBaseResponse;
import core.worker.responses.WorkerResponse;
import ocsf.server.ConnectionToClient;
import server.db.dbAPI.RegularDBAPI;
import server.db.dbAPI.ReportsDBAPI;
import server.db.dbAPI.WorkersDBAPI;
import server.parkingLot.ParkingLotsManager;
import server.rates.RatesManager;
import server.requestHandlers.worker.IProvideConnectionsToClient;

public abstract class BaseRequestsHandler implements IRequestsHandler {
	protected String USER_NAME = "userName";
	protected Gson gson = CpsGson.GetGson();
	protected RegularDBAPI regularDBAPI = RegularDBAPI.getInstance();
	protected WorkersDBAPI workersDBAPI = WorkersDBAPI.getInstance();
	protected ReportsDBAPI reportsDBAPI = ReportsDBAPI.getInstance();
	protected ParkingLotsManager parkingLotsManager = ParkingLotsManager.getInstance();
	protected RatesManager ratesManager = RatesManager.getInstance();
	protected IProvideConnectionsToClient connectionsToClientProvider;

	public BaseRequestsHandler(IProvideConnectionsToClient connectionsToClientProvider) {
		this.connectionsToClientProvider = connectionsToClientProvider;
	}
	
	@Override
	public WorkerResponse HandleRequest(BaseRequest request, ConnectionToClient client) throws SQLException {
		if (request.requestType != getHandlerRequestsType()) {
			return null;
		}
		
		return HandleSpecificRequest(request, client);
	}
	
	protected abstract WorkerRequestType getHandlerRequestsType();
	
	protected abstract WorkerResponse HandleSpecificRequest(BaseRequest specificRequest, ConnectionToClient client) throws SQLException;

	protected WorkerResponse CreateWorkerResponse(WorkerBaseResponse response) {
		return new WorkerResponse(getHandlerRequestsType(), gson.toJson(response));
	}
	
	protected WorkerResponse createUnsupportedFeatureResponse() {
		BadResponse badRequest = new BadResponse(ResponseStatus.UNSUPPORTED_FEATURE, getHandlerRequestsType() + " request type isn't supported yet");
		return new WorkerResponse(WorkerRequestType.BAD_REQUEST, gson.toJson(badRequest));
	}

	protected WorkerResponse createRequestDeniedResponse(String refusalReason) {
		BadResponse badRequest = new BadResponse(ResponseStatus.REQUEST_DENIED, getHandlerRequestsType() + ": " + refusalReason);
		return new WorkerResponse(WorkerRequestType.BAD_REQUEST, gson.toJson(badRequest));
	}
}
