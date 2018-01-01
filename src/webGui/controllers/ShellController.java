package webGui.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import webGui.util.MockWebClientConnectionManager;
import webGui.util.ServerMessageHandler;

public class ShellController implements ServerMessageHandler {

	@FXML
	private TextArea responseTextArea;

	public ShellController() {
		MockWebClientConnectionManager.registerStartupListeners(this);
	}

	@Override
	public void handleServerMessage(String msg) {
		responseTextArea.setText(msg);
	}
}
