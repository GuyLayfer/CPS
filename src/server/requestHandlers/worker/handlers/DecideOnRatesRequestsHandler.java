package server.requestHandlers.worker.handlers;

import java.sql.SQLException;

import core.worker.WorkerRequestType;
import core.worker.requests.BaseRequest;
import core.worker.responses.WorkerResponse;
import ocsf.server.ConnectionToClient;
import server.requestHandlers.worker.IProvideConnectionsToClient;

public class DecideOnRatesRequestsHandler extends BaseRequestsHandler {

	public DecideOnRatesRequestsHandler(IProvideConnectionsToClient connectionsToClientProvider) {
		super(connectionsToClientProvider);
	}

	@Override
	protected WorkerRequestType getHandlerRequestsType() {
		return WorkerRequestType.DECIDE_ON_RATES;
	}

	@Override
	protected WorkerResponse HandleSpecificRequest(BaseRequest specificRequest, ConnectionToClient client) throws SQLException {
		return createUnsupportedFeatureResponse();
		// BaseResponse response = WorkerResponseFactory.CreateDecideOnRatesResponse();
		// return CreateWorkerResponse(response);
	}
}
