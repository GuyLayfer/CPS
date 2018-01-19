package server.requestHandlers.worker.handlers;

import java.sql.SQLException;

import core.worker.requests.BaseRequest;
import core.worker.responses.WorkerResponse;
import ocsf.server.ConnectionToClient;

public interface IRequestsHandler {
	
	WorkerResponse HandleRequest(BaseRequest request, ConnectionToClient client) throws SQLException;
}
