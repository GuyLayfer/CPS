package server.workerServer.workerRequestsHandlers;

import java.sql.SQLException;

import com.google.gson.Gson;

import core.CpsGson;
import core.ResponseStatus;
import core.worker.WorkerRequestType;
import core.worker.requests.BaseRequest;
import core.worker.responses.BadResponse;
import core.worker.responses.WorkerBaseResponse;
import core.worker.responses.WorkerResponse;
import server.db.dbAPI.RegularDBAPI;
import server.db.dbAPI.WorkersDBAPI;

public abstract class BaseRequestsHandler implements IRequestsHandler {
	protected Gson gson = CpsGson.GetGson();
	protected RegularDBAPI regularDBAPI = RegularDBAPI.getInstance();
	protected WorkersDBAPI workersDBAPI = WorkersDBAPI.getInstance();

	@Override
	public WorkerResponse HandleRequest(BaseRequest request) throws SQLException {
		if (request.requestType != getHandlerRequestsType()) {
			return null;
		}
		
		return HandleSpecificRequest(request);
	}
	
	protected abstract WorkerRequestType getHandlerRequestsType();
	
	protected abstract WorkerResponse HandleSpecificRequest(BaseRequest specificRequest) throws SQLException;

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
