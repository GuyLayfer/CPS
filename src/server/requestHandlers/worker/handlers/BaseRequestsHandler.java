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

// TODO: Auto-generated Javadoc
/**
 * The Class BaseRequestsHandler.
 */
public abstract class BaseRequestsHandler implements IRequestsHandler {
	
	/** The user name. */
	protected String USER_NAME = "userName";
	
	/** The gson. */
	protected Gson gson = CpsGson.GetGson();
	
	/** The regular DBAPI. */
	protected RegularDBAPI regularDBAPI = RegularDBAPI.getInstance();
	
	/** The workers DBAPI. */
	protected WorkersDBAPI workersDBAPI = WorkersDBAPI.getInstance();
	
	/** The reports DBAPI. */
	protected ReportsDBAPI reportsDBAPI = ReportsDBAPI.getInstance();
	
	/** The parking lots manager. */
	protected ParkingLotsManager parkingLotsManager = ParkingLotsManager.getInstance();
	
	/** The rates manager. */
	protected RatesManager ratesManager = RatesManager.getInstance();
	
	/** The connections to client provider. */
	protected IProvideConnectionsToClient connectionsToClientProvider;

	/**
	 * Instantiates a new base requests handler.
	 *
	 * @param connectionsToClientProvider the connections to client provider
	 */
	public BaseRequestsHandler(IProvideConnectionsToClient connectionsToClientProvider) {
		this.connectionsToClientProvider = connectionsToClientProvider;
	}
	
	/* (non-Javadoc)
	 * @see server.requestHandlers.worker.handlers.IRequestsHandler#HandleRequest(core.worker.requests.BaseRequest, ocsf.server.ConnectionToClient)
	 */
	@Override
	public WorkerResponse HandleRequest(BaseRequest request, ConnectionToClient client) throws SQLException {
		if (request.requestType != getHandlerRequestsType()) {
			return null;
		}
		
		return HandleSpecificRequest(request, client);
	}
	
	/**
	 * Gets the handler requests type.
	 *
	 * @return the handler requests type
	 */
	protected abstract WorkerRequestType getHandlerRequestsType();
	
	/**
	 * Handle specific request.
	 *
	 * @param specificRequest the specific request
	 * @param client the client
	 * @return the worker response
	 * @throws SQLException the SQL exception
	 */
	protected abstract WorkerResponse HandleSpecificRequest(BaseRequest specificRequest, ConnectionToClient client) throws SQLException;

	/**
	 * Creates the worker response.
	 *
	 * @param response the response
	 * @return the worker response
	 */
	protected WorkerResponse CreateWorkerResponse(WorkerBaseResponse response) {
		return new WorkerResponse(getHandlerRequestsType(), gson.toJson(response));
	}
	
	/**
	 * Creates the unsupported feature response.
	 *
	 * @return the worker response
	 */
	protected WorkerResponse createUnsupportedFeatureResponse() {
		BadResponse badRequest = new BadResponse(ResponseStatus.UNSUPPORTED_FEATURE, getHandlerRequestsType() + " request type isn't supported yet");
		return new WorkerResponse(WorkerRequestType.BAD_REQUEST, gson.toJson(badRequest));
	}

	/**
	 * Creates the request denied response.
	 *
	 * @param refusalReason the refusal reason
	 * @return the worker response
	 */
	protected WorkerResponse createRequestDeniedResponse(String refusalReason) {
		BadResponse badRequest = new BadResponse(ResponseStatus.REQUEST_DENIED, getHandlerRequestsType() + ": " + refusalReason);
		return new WorkerResponse(WorkerRequestType.BAD_REQUEST, gson.toJson(badRequest));
	}
}
