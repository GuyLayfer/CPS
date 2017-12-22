package webGui.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import webGui.MockWebClientApplicationStarter;
import webGui.MockWebClientConnectionManager;


public class HostAddressStartPageController {

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
    	MockWebClientApplicationStarter.navigateToWebClientView();
    }
}
