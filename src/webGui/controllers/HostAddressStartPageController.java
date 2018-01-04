package webGui.controllers;

import core.GuiUtilities.UriDictionary;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import webGui.util.MockWebClientConnectionManager;
import webGui.util.WebGuiController;


public class HostAddressStartPageController extends WebGuiController{

	@FXML // fx:id="changeHostButton"
	private Button changeHostButton; // Value injected by FXMLLoader

	@FXML // fx:id="localhostButton"
	private Button localhostButton; // Value injected by FXMLLoader
	
	@FXML // fx:id="hostTF"
	private TextField hostTF; // Value injected by FXMLLoader

	@FXML
	protected void initialize() {
		changeHostButton.setOnAction(e -> changeHost(hostTF.getText()));
		localhostButton.setOnAction(e -> changeHost("localhost"));
	}

	private void changeHost(String host) {
		MockWebClientConnectionManager.alternativeHostAddress = host;
		NavigateTo(changeHostButton.getScene(), UriDictionary.WebGui.MockWebClientView);
	}
}
