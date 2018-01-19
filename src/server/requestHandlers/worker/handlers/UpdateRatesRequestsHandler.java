package server.requestHandlers.worker.handlers;

import java.sql.SQLException;

import core.worker.WorkerRequestType;
import core.worker.requests.BaseRequest;
import core.worker.responses.WorkerResponse;
import ocsf.server.ConnectionToClient;
import server.requestHandlers.worker.IProvideConnectionsToClient;

public class UpdateRatesRequestsHandler extends BaseRequestsHandler {

	public UpdateRatesRequestsHandler(IProvideConnectionsToClient connectionsToClientProvider) {
		super(connectionsToClientProvider);
	}
	
	@Override
	protected WorkerRequestType getHandlerRequestsType() {
		return WorkerRequestType.UPDATE_RATES;
	}

	@Override
	protected WorkerResponse HandleSpecificRequest(BaseRequest specificRequest, ConnectionToClient client) throws SQLException {
		return createUnsupportedFeatureResponse();
//		BaseResponse response = WorkerResponseFactory.CreateUpdateRatesResponse();
//		return CreateWorkerResponse(response);
	}
}
