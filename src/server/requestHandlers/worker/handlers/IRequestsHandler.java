package server.requestHandlers.worker.handlers;

import java.sql.SQLException;

import core.worker.requests.BaseRequest;
import core.worker.responses.WorkerResponse;
import ocsf.server.ConnectionToClient;

// TODO: Auto-generated Javadoc
/**
 * The Interface IRequestsHandler.
 */
public interface IRequestsHandler {
	
	/**
	 * Handle request.
	 *
	 * @param request the request
	 * @param client the client
	 * @return the worker response
	 * @throws SQLException the SQL exception
	 */
	WorkerResponse HandleRequest(BaseRequest request, ConnectionToClient client) throws SQLException;
}
