package server.workerServer.workerRequestsHandlers;

import java.sql.SQLException;

import core.worker.requests.BaseRequest;
import core.worker.responses.WorkerResponse;

public interface IRequestsHandler {
	
	WorkerResponse HandleRequest(BaseRequest request) throws SQLException;
}
