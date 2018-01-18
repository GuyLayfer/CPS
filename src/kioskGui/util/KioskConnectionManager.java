package kioskGui.util;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Function;

import org.controlsfx.control.Notifications;

import com.google.gson.Gson;

import core.CpsGson;
import core.ServerPorts;
import core.customer.CustomerRequest;
import core.customer.CustomerRequestType;
import core.customer.CustomerResponsesTypesMapper;
import core.customer.responses.CustomerBaseResponse;
import core.customer.responses.CustomerResponse;
import core.guiUtilities.IServerResponseHandler;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.util.Duration;
import ocsf.client.AbstractClient;

public class KioskConnectionManager extends AbstractClient {

	private static KioskConnectionManager instance;
	final private static int DEFAULT_PORT = ServerPorts.KIOSK_PORT;
	final private static String DEFAULT_HOST = "localhost";
	final private Gson gson = CpsGson.GetGson();
	private List<IServerResponseHandler<CustomerBaseResponse>> listeners = new CopyOnWriteArrayList<IServerResponseHandler<CustomerBaseResponse>>();
	private Map<CustomerRequestType, Function<String, CustomerBaseResponse>> responseConverterMap;
	public static String alternativeHostAddress = null;

	private KioskConnectionManager(String hostAddress) throws IOException {
		super(hostAddress == null ? DEFAULT_HOST : hostAddress, DEFAULT_PORT);
		openConnection();
		responseConverterMap = CustomerResponsesTypesMapper.CreateResponseConverterMap();
	}

	public static KioskConnectionManager getInstance() {
		if (instance == null) {
			try {
				instance = new KioskConnectionManager(alternativeHostAddress);
				return instance;
			} catch (IOException e) {
				e.printStackTrace();
				showNotification("Could not connect to server.");
			}
		}

		return instance;
	}

	public void addServerMessageListener(IServerResponseHandler<CustomerBaseResponse> listner) {
		listeners.add(listner);
	}

	public void sendMessageToServer(CustomerRequest order) {
		try {
			sendToServer(gson.toJson(order));
		} catch (IOException e) {
			showNotification("Could not send message to server.\n" + e.getMessage());
			e.printStackTrace();
		}
	}

	@Override
	protected void handleMessageFromServer(Object arg0) {
		CustomerResponse response = gson.fromJson((String) arg0, CustomerResponse.class);
		CustomerBaseResponse specificResponse = responseConverterMap.get(response.requestType).apply(response.jsonData);
		notifyListeners(specificResponse);
	}

	public void closeServerConnection() {
		try {
			closeConnection();
		} catch (IOException e) {

		}
	}

	private void notifyListeners(CustomerBaseResponse message) {
		for (IServerResponseHandler<CustomerBaseResponse> listener : listeners) {
			listener.handleServerResponse(message);
		}
	}
	
	private static void showNotification(String msg) {
		Platform.runLater(() -> {
			Notifications notificationBuilder = Notifications.create()
				.title("Connection Error:")
				.text(msg)
				.hideAfter(Duration.seconds(10))
				.position(Pos.BOTTOM_RIGHT)
				.onAction(new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent arg0) {
						
					}
				});
		
		notificationBuilder.showError();
		});
	}
}
