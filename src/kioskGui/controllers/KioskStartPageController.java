package kioskGui.controllers;

import java.io.IOException;

import core.guiUtilities.UriDictionary;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import webGui.util.MockWebClientConnectionManager;

public class KioskStartPageController {

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
		Scene scene = changeHostButton.getScene();

		try {
			Region shell = FXMLLoader.load(UriDictionary.class.getResource(UriDictionary.Kiosk.Shell));
			Pane shellRegion = (Pane) scene.lookup(UriDictionary.Regions.kisokOuterShellRegion);
			shellRegion.getChildren().clear();
			shellRegion.getChildren().add(shell);

			Region kioskView = FXMLLoader.load(UriDictionary.class.getResource(UriDictionary.Kiosk.ClientView));
			Pane mainViewRegion = (Pane) scene.lookup(UriDictionary.Regions.kisokMainViewRegion);
			mainViewRegion.getChildren().add(kioskView);

			scene.getWindow().sizeToScene();
		} catch (IOException e) {
			e.printStackTrace();
		}

		scene.getWindow().sizeToScene();
		scene.getWindow().centerOnScreen();
	}
}
