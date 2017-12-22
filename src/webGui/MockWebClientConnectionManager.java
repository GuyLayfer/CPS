package webGui;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;

import core.*;
import ocsf.client.AbstractClient;
import webGui.util.ServerMessageHandler;

public class MockWebClientConnectionManager extends AbstractClient {
	private static MockWebClientConnectionManager instance;
	final private static int DEFAULT_PORT = 5555;
	final private static String DEFAULT_HOST = "localhost";
	final private Gson gson = new Gson();
	private List<ServerMessageHandler> listeners = new ArrayList<ServerMessageHandler>();
	private static List<ServerMessageHandler> startupListeners = new ArrayList<ServerMessageHandler>();
	// Had trouble registering the shell for messages because it starts on startup, before the connection.
	// It's not a good solution but it is the easiest. Other option is to access the shell via childViewAnchor.getParent().lookup("#lebelInParent"));
	
	public static String alternativeHostAddress = null;
	
	private MockWebClientConnectionManager(String hostAddress) throws IOException {
		super(hostAddress == null ? DEFAULT_HOST : hostAddress, DEFAULT_PORT);
		openConnection();
	}
	
	public static MockWebClientConnectionManager getInstance(){
		if(instance == null){
			try {
				MockWebClientConnectionManager instance = new MockWebClientConnectionManager(alternativeHostAddress);
				instance.listeners.addAll(startupListeners);
				return instance;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		return instance;
	}
	
//	Observer pattern
	public void addServerMessageListener(ServerMessageHandler listner){
		listeners.add(listner);
	}

	public void sendMessageToServer(WebCustomerRequest order) {
		try {
			sendToServer(gson.toJson(order));
		} catch (IOException e) {
			notifyListeners("Could not send message to server.  Terminating client.");
			quit();
		}
	}
	
	@Override
	protected void handleMessageFromServer(Object arg0) {
		notifyListeners(gson.fromJson((String)arg0, ServerBasicResponse.class).toString());
	}

	private void quit() {
		try {
			closeConnection();
		} catch (IOException e) {
			
		}
		
		System.exit(0);
	}
	
	private void notifyListeners(String message){
//		Observer pattern.
		for (ServerMessageHandler listener : listeners){
			listener.handleServerMessage(message);
		}
	}
	
	public static void registerStartupListeners(ServerMessageHandler listner){
		startupListeners.add(listner);
	}
}
