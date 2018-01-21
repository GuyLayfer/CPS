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

// TODO: Auto-generated Javadoc
/**
 * The Class KioskConnectionManager.
 */
public class KioskConnectionManager extends AbstractClient {

	/** The instance. */
	private static KioskConnectionManager instance;
	
	/** The Constant DEFAULT_PORT. */
	final private static int DEFAULT_PORT = ServerPorts.KIOSK_PORT;
	
	/** The Constant DEFAULT_HOST. */
	final private static String DEFAULT_HOST = "localhost";
	
	/** The gson. */
	final private Gson gson = CpsGson.GetGson();
	
	/** The listeners. */
	private List<IServerResponseHandler<CustomerBaseResponse>> listeners = new CopyOnWriteArrayList<IServerResponseHandler<CustomerBaseResponse>>();
	
	/** The response converter map. */
	private Map<CustomerRequestType, Function<String, CustomerBaseResponse>> responseConverterMap;
	
	/** The alternative host address. */
	public static String alternativeHostAddress = null;

	/**
	 * Instantiates a new kiosk connection manager.
	 *
	 * @param hostAddress the host address
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	private KioskConnectionManager(String hostAddress) throws IOException {
		super(hostAddress == null ? DEFAULT_HOST : hostAddress, DEFAULT_PORT);
		openConnection();
		responseConverterMap = CustomerResponsesTypesMapper.CreateResponseConverterMap();
	}

	/**
	 * Gets the single instance of KioskConnectionManager.
	 *
	 * @return single instance of KioskConnectionManager
	 */
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

	/**
	 * Adds the server message listener.
	 *
	 * @param listner the listner
	 */
	public void addServerMessageListener(IServerResponseHandler<CustomerBaseResponse> listner) {
		listeners.add(listner);
	}

	/**
	 * Send message to server.
	 *
	 * @param order the order
	 */
	public void sendMessageToServer(CustomerRequest order) {
		try {
			sendToServer(gson.toJson(order));
		} catch (IOException e) {
			showNotification("Could not send message to server.\n" + e.getMessage());
			e.printStackTrace();
		}
	}

	/* (non-Javadoc)
	 * @see ocsf.client.AbstractClient#handleMessageFromServer(java.lang.Object)
	 */
	@Override
	protected void handleMessageFromServer(Object arg0) {
		CustomerResponse response = gson.fromJson((String) arg0, CustomerResponse.class);
		CustomerBaseResponse specificResponse = responseConverterMap.get(response.requestType).apply(response.jsonData);
		notifyListeners(specificResponse);
	}

	/**
	 * Close server connection.
	 */
	public void closeServerConnection() {
		try {
			closeConnection();
		} catch (IOException e) {

		}
	}

	/**
	 * Notify listeners.
	 *
	 * @param message the message
	 */
	private void notifyListeners(CustomerBaseResponse message) {
		for (IServerResponseHandler<CustomerBaseResponse> listener : listeners) {
			listener.handleServerResponse(message);
		}
	}
	
	/**
	 * Show notification.
	 *
	 * @param msg the msg
	 */
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
