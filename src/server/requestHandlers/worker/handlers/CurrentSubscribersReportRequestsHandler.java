package server.requestHandlers.worker.handlers;

import java.sql.SQLException;

import core.worker.WorkerRequestType;
import core.worker.requests.BaseRequest;
import core.worker.responses.WorkerResponse;
import ocsf.server.ConnectionToClient;
import server.requestHandlers.worker.IProvideConnectionsToClient;

public class CurrentSubscribersReportRequestsHandler extends BaseRequestsHandler {

	public CurrentSubscribersReportRequestsHandler(IProvideConnectionsToClient connectionsToClientProvider) {
		super(connectionsToClientProvider);
	}

	@Override
	protected WorkerRequestType getHandlerRequestsType() {
		return WorkerRequestType.CURRENT_SUBSCRIBERS_REPORT;
	}

	@Override
	protected WorkerResponse HandleSpecificRequest(BaseRequest specificRequest, ConnectionToClient client) throws SQLException {
		return createUnsupportedFeatureResponse();

		// WorkerBaseResponse response = WorkerResponseFactory.CreateReportResponse(reportItems, getHandlerRequestsType());
		// return CreateWorkerResponse(response);
	}
}
