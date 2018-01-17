package workerGui.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import com.google.gson.Gson;

import core.CpsGson;
import core.ServerPorts;
import core.guiUtilities.IServerResponseHandler;
import core.worker.requests.BaseRequest;
import core.worker.requests.WorkerRequest;
import core.worker.WorkerRequestType;
import core.worker.responses.WorkerBaseResponse;
import core.worker.responses.WorkerResponse;
import ocsf.client.AbstractClient;

public class WorkerConnectionManager extends AbstractClient {

	private static WorkerConnectionManager instance;
	final private static int DEFAULT_PORT = ServerPorts.WORKER_PORT;
	final private static String DEFAULT_HOST = "localhost";
	final private Gson gson = new CpsGson().GetGson();
	private List<IServerResponseHandler<WorkerBaseResponse>> listeners = new ArrayList<IServerResponseHandler<WorkerBaseResponse>>();
	private Map<WorkerRequestType, Function<String, WorkerBaseResponse>> responseConverterMap;
	public static String alternativeHostAddress = null;

	private WorkerConnectionManager(String hostAddress) throws IOException {
		super(hostAddress == null ? DEFAULT_HOST : hostAddress, DEFAULT_PORT);
		openConnection();
		responseConverterMap = ResponsesTypesMapper.CreateResponsesConverterMap();
	}

	public static WorkerConnectionManager getInstance() {
		if (instance == null) {
			try {
				instance = new WorkerConnectionManager(alternativeHostAddress);
				return instance;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return instance;
	}

	public void addServerMessageListener(IServerResponseHandler<WorkerBaseResponse> listner) {
		listeners.add(listner);
	}

	public void sendMessageToServer(BaseRequest specificRequest) {
		try {
			WorkerRequest workerRequest = new WorkerRequest();
			workerRequest.requestType = specificRequest.requestType;
			workerRequest.jsonData = gson.toJson(specificRequest);
			sendToServer(gson.toJson(workerRequest));
		} catch (IOException e) {
			System.out.println("Could not send message to server.\n" + e.getMessage() + "\nTerminating client.");
			e.printStackTrace();
			quit();
		}
	}

	@Override
	protected void handleMessageFromServer(Object arg0) {
		WorkerResponse response = gson.fromJson((String) arg0, WorkerResponse.class);

		WorkerBaseResponse specificResponse = responseConverterMap.get(response.requestType).apply(response.jsonData);
		notifyListeners(specificResponse);
	}

	private void quit() {
		try {
			closeConnection();
		} catch (IOException e) {

		}

		System.exit(0);
	}

	private void notifyListeners(WorkerBaseResponse message) {
		for (IServerResponseHandler<WorkerBaseResponse> listener : listeners) {
			listener.handleServerResponse(message);
		}
	}
}
