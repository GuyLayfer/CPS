package webGui.controllers;

import core.guiUtilities.UriDictionary;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import webGui.util.MockWebClientConnectionManager;
import webGui.util.WebGuiController;


// TODO: Auto-generated Javadoc
/**
 * The Class HostAddressStartPageController.
 */
public class HostAddressStartPageController extends WebGuiController{

	/** The change host button. */
	@FXML // fx:id="changeHostButton"
	private Button changeHostButton; // Value injected by FXMLLoader

	/** The localhost button. */
	@FXML // fx:id="localhostButton"
	private Button localhostButton; // Value injected by FXMLLoader
	
	/** The host TF. */
	@FXML // fx:id="hostTF"
	private TextField hostTF; // Value injected by FXMLLoader

	/**
	 * Initialize.
	 */
	@FXML
	protected void initialize() {
		changeHostButton.setOnAction(e -> changeHost(hostTF.getText()));
		localhostButton.setOnAction(e -> changeHost("localhost"));
	}

	/**
	 * Change host.
	 *
	 * @param host the host
	 */
	private void changeHost(String host) {
		MockWebClientConnectionManager.alternativeHostAddress = host;
		NavigateTo(changeHostButton.getScene(), UriDictionary.WebGui.ClientView);
	}
}
