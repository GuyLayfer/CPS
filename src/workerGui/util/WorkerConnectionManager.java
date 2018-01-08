package workerGui.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import com.google.gson.Gson;

import core.CpsGson;
import core.ResponseStatus;
import core.ServerPorts;
import core.guiUtilities.ServerMessageHandler;
import core.worker.WorkerRequest;
import core.worker.WorkerRequestType;
import core.worker.WorkerResponse;
import ocsf.client.AbstractClient;

public class WorkerConnectionManager extends AbstractClient {

	private static WorkerConnectionManager instance;
	final private static int DEFAULT_PORT = ServerPorts.WORKER_PORT;
	final private static String DEFAULT_HOST = "localhost";
	final private Gson gson = new CpsGson().GetGson();
	private List<ServerMessageHandler> listeners = new ArrayList<ServerMessageHandler>();
	private Map<WorkerRequestType, Function<String, Object>> responseConverterMap;
	public static String alternativeHostAddress = null;

	private WorkerConnectionManager(String hostAddress) throws IOException {
		super(hostAddress == null ? DEFAULT_HOST : hostAddress, DEFAULT_PORT);
		openConnection();
		responseConverterMap = CreateResponseConverterMap();
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

	// Observer pattern
	public void addServerMessageListener(ServerMessageHandler listner) {
		listeners.add(listner);
	}

	public void sendMessageToServer(WorkerRequest order) {
		try {
			sendToServer(gson.toJson(order));
		} catch (IOException e) {
			System.out.println("Could not send message to server.\n" + e.getMessage() +  "\nTerminating client.");
			quit();
		}
	}

	@Override
	protected void handleMessageFromServer(Object arg0) {
		WorkerResponse response = gson.fromJson((String) arg0, WorkerResponse.class);
		if (response.status == ResponseStatus.OK) {
			String stringResponse = responseConverterMap.get(response.requestType).apply(response.jsonData).toString();
			notifyListeners(stringResponse);
		} else {
			notifyListeners(response.toString());
		}
	}

	private void quit() {
		try {
			closeConnection();
		} catch (IOException e) {

		}

		System.exit(0);
	}

	private void notifyListeners(String message) {
		// Observer pattern.
		for (ServerMessageHandler listener : listeners) {
			listener.handleServerMessage(message);
		}
	}

	private Map<WorkerRequestType, Function<String, Object>> CreateResponseConverterMap() {
		Map<WorkerRequestType, Function<String, Object>> converterMap = new HashMap<WorkerRequestType, Function<String, Object>>();
//		Add Relevant response converters
//		converterMap.put(CustomerRequestType.ORDER_ONE_TIME_PARKING, (gsonString) -> {
//			return gson.fromJson((String) gsonString, CustomerResponse.class);
//		});
//		converterMap.put(CustomerRequestType.CANCEL_ORDER, (gsonString) -> {
//			return gson.fromJson((String) gsonString, CustomerResponse.class);
//		});
//		converterMap.put(CustomerRequestType.TRACK_ORDER_STATUS, (gsonString) -> {
//			return gson.fromJson((String) gsonString, TrackOrderResponseData.class);
//		});
//		converterMap.put(CustomerRequestType.ORDER_ROUTINE_MONTHLY_SUBSCRIPTION, (gsonString) -> {
//			return gson.fromJson((String) gsonString, CustomerResponse.class);
//		});
//		converterMap.put(CustomerRequestType.ORDER_FULL_MONTHLY_SUBSCRIPTION, (gsonString) -> {
//			return gson.fromJson((String) gsonString, CustomerResponse.class);
//		});
//		converterMap.put(CustomerRequestType.SUBSCRIPTION_RENEWAL, (gsonString) -> {
//			return gson.fromJson((String) gsonString, CustomerResponse.class);
//		});
//		converterMap.put(CustomerRequestType.OPEN_COMPLAINT, (gsonString) -> {
//			return gson.fromJson((String) gsonString, CustomerResponse.class);
//		});

		return converterMap;
	};

}
