package server.requestHandlers.worker;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import core.CpsGson;
import core.ResponseStatus;
import core.worker.WorkerRequestType;
import core.worker.requests.BaseRequest;
import core.worker.requests.WorkerRequest;
import core.worker.responses.BadResponse;
import core.worker.responses.WorkerResponse;
import ocsf.server.AbstractServer;
import ocsf.server.ConnectionToClient;
import server.requestHandlers.worker.handlers.IRequestsHandler;

public class WorkerRequestsHandler extends AbstractServer implements IProvideConnectionsToClient {
	private Gson gson = CpsGson.GetGson();
	private Map<WorkerRequestType, Function<String, BaseRequest>> responseConverterMap;
	Set<IRequestsHandler> requestsHandlers;

	public WorkerRequestsHandler(int port) {
		super(port);
		responseConverterMap = WorkerRequestsTypesMapper.CreateRequestsConverterMap();
		requestsHandlers = WorkerRequestsTypesMapper.CreateRequestsHandlers(this);
	}
 
	@Override
	protected void handleMessageFromClient(Object msg, ConnectionToClient client) {
		try {
			try {
				WorkerRequest request = gson.fromJson((String) msg, WorkerRequest.class);
				BaseRequest specificRequest = responseConverterMap.get(request.requestType).apply(request.jsonData);
				WorkerResponse response = null;
				// Iterate through all the response handlers. The one that care about the response should handle it.
				for (IRequestsHandler handler : requestsHandlers) {
					WorkerResponse handlerResponse = handler.HandleRequest(specificRequest, client);
					response = handlerResponse == null ? response : handlerResponse;
				}

				client.sendToClient(gson.toJson(response));

			} catch (JsonSyntaxException e) {
				client.sendToClient(gson.toJson(new BadResponse(ResponseStatus.BAD_REQUEST, "Json Syntax Exception. This feature might not be implemented.")));
				e.printStackTrace();
			} catch (SQLException e) {
				client.sendToClient(gson.toJson(new BadResponse(ResponseStatus.SERVER_FAILLURE, "Server Failure")));
				e.printStackTrace();
			}
		} catch (IOException e) {
			System.out.println("Could not send message to client.\nPlease check your connection.");
			e.printStackTrace();
		}
	}

	@Override
	protected void serverStarted() {
		System.out.println("Worker Server is listening for connections on port " + getPort());
	}

	@Override
	protected void serverStopped() {
		System.out.println("Worker Server has stopped listening for connections.");
	}

	@Override
	public List<ConnectionToClient> getConnections() {
		return Arrays.stream(this.getClientConnections()).map(x -> (ConnectionToClient)x).collect(Collectors.toList());
	}
}
