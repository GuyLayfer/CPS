package workerGui.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import org.controlsfx.control.Notifications;

import com.google.gson.Gson;

import core.CpsGson;
import core.ServerPorts;
import core.guiUtilities.IServerResponseHandler;
import core.worker.requests.BaseRequest;
import core.worker.requests.WorkerRequest;
import core.worker.WorkerRequestType;
import core.worker.responses.WorkerBaseResponse;
import core.worker.responses.WorkerResponse;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.util.Duration;
import ocsf.client.AbstractClient;

// TODO: Auto-generated Javadoc
/**
 * The Class WorkerConnectionManager.
 */
public class WorkerConnectionManager extends AbstractClient {

	/** The instance. */
	private static WorkerConnectionManager instance;
	
	/** The Constant DEFAULT_PORT. */
	final private static int DEFAULT_PORT = ServerPorts.WORKER_PORT;
	
	/** The Constant DEFAULT_HOST. */
	final private static String DEFAULT_HOST = "localhost";
	
	/** The gson. */
	final private Gson gson = CpsGson.GetGson();
	
	/** The listeners. */
	private List<IServerResponseHandler<WorkerBaseResponse>> listeners = new ArrayList<IServerResponseHandler<WorkerBaseResponse>>();
	
	/** The response converter map. */
	private Map<WorkerRequestType, Function<String, WorkerBaseResponse>> responseConverterMap;
	
	/** The alternative host address. */
	public static String alternativeHostAddress = null;

	/**
	 * Instantiates a new worker connection manager.
	 *
	 * @param hostAddress the host address
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	private WorkerConnectionManager(String hostAddress) throws IOException {
		super(hostAddress == null ? DEFAULT_HOST : hostAddress, DEFAULT_PORT);
		openConnection();
		responseConverterMap = ResponsesTypesMapper.CreateResponsesConverterMap();
	}

	/**
	 * Gets the single instance of WorkerConnectionManager.
	 *
	 * @return single instance of WorkerConnectionManager
	 */
	public static WorkerConnectionManager getInstance() {
		if (instance == null) {
			try {
				instance = new WorkerConnectionManager(alternativeHostAddress);
				return instance;
			} catch (IOException e) {
				showNotification("Could not connect to server.");
				e.printStackTrace();
			}
		}

		return instance;
	}

	/**
	 * Adds the server message listener.
	 *
	 * @param listner the listner
	 */
	public void addServerMessageListener(IServerResponseHandler<WorkerBaseResponse> listner) {
		listeners.add(listner);
	}

	/**
	 * Send message to server.
	 *
	 * @param specificRequest the specific request
	 */
	public void sendMessageToServer(BaseRequest specificRequest) {
		try {
			WorkerRequest workerRequest = new WorkerRequest();
			workerRequest.requestType = specificRequest.requestType;
			workerRequest.jsonData = gson.toJson(specificRequest);
			sendToServer(gson.toJson(workerRequest));
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
		WorkerResponse response = gson.fromJson((String) arg0, WorkerResponse.class);

		WorkerBaseResponse specificResponse = responseConverterMap.get(response.requestType).apply(response.jsonData);
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
	private void notifyListeners(WorkerBaseResponse message) {
		for (IServerResponseHandler<WorkerBaseResponse> listener : listeners) {
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
