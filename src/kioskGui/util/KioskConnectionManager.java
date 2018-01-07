package kioskGui.util;

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
import core.customer.CustomerRequest;
import core.customer.CustomerRequestType;
import core.customer.CustomerResponse;
import core.guiUtilities.ServerMessageHandler;
import ocsf.client.AbstractClient;

public class KioskConnectionManager extends AbstractClient {

	private static KioskConnectionManager instance;
	final private static int DEFAULT_PORT = ServerPorts.KIOSK_PORT;
	final private static String DEFAULT_HOST = "localhost";
	final private Gson gson = new CpsGson().GetGson();
	private List<ServerMessageHandler> listeners = new ArrayList<ServerMessageHandler>();
	private Map<CustomerRequestType, Function<String, Object>> responseConverterMap;
	public static String alternativeHostAddress = null;

	private KioskConnectionManager(String hostAddress) throws IOException {
		super(hostAddress == null ? DEFAULT_HOST : hostAddress, DEFAULT_PORT);
		openConnection();
		responseConverterMap = CreateResponseConverterMap();
	}

	public static KioskConnectionManager getInstance() {
		if (instance == null) {
			try {
				instance = new KioskConnectionManager(alternativeHostAddress);
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

	public void sendMessageToServer(CustomerRequest order) {
		try {
			sendToServer(gson.toJson(order));
		} catch (IOException e) {
			System.out.println("Could not send message to server.\n" + e.getMessage() +  "\nTerminating client.");
			quit();
		}
	}

	@Override
	protected void handleMessageFromServer(Object arg0) {
		CustomerResponse response = gson.fromJson((String) arg0, CustomerResponse.class);
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

	private Map<CustomerRequestType, Function<String, Object>> CreateResponseConverterMap() {
		Map<CustomerRequestType, Function<String, Object>> converterMap = new HashMap<CustomerRequestType, Function<String, Object>>();
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
