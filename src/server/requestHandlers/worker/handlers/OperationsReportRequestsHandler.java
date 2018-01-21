package server.requestHandlers.worker.handlers;

import java.sql.SQLException;

import core.worker.WorkerRequestType;
import core.worker.requests.BaseRequest;
import core.worker.responses.WorkerResponse;
import ocsf.server.ConnectionToClient;
import server.requestHandlers.worker.IProvideConnectionsToClient;

// TODO: Auto-generated Javadoc
/**
 * The Class OperationsReportRequestsHandler.
 */
public class OperationsReportRequestsHandler extends BaseRequestsHandler {

	/**
	 * Instantiates a new operations report requests handler.
	 *
	 * @param connectionsToClientProvider the connections to client provider
	 */
	public OperationsReportRequestsHandler(IProvideConnectionsToClient connectionsToClientProvider) {
		super(connectionsToClientProvider);
	}

	/* (non-Javadoc)
	 * @see server.requestHandlers.worker.handlers.BaseRequestsHandler#getHandlerRequestsType()
	 */
	@Override
	protected WorkerRequestType getHandlerRequestsType() {
		return WorkerRequestType.OPERATIONS_REPORT;
	}

	/* (non-Javadoc)
	 * @see server.requestHandlers.worker.handlers.BaseRequestsHandler#HandleSpecificRequest(core.worker.requests.BaseRequest, ocsf.server.ConnectionToClient)
	 */
	@Override
	protected WorkerResponse HandleSpecificRequest(BaseRequest specificRequest, ConnectionToClient client) throws SQLException {
		return createUnsupportedFeatureResponse();

		// WorkerBaseResponse response = WorkerResponseFactory.CreateReportResponse(reportItems, getHandlerRequestsType());
		// return CreateWorkerResponse(response);
	}
}
