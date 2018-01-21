package workerGui.controllers;

import java.io.IOException;

import core.guiUtilities.UriDictionary;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import workerGui.util.WorkerConnectionManager;

// TODO: Auto-generated Javadoc
/**
 * The Class WorkerStartPageController.
 */
public class WorkerStartPageController {

	/** The change host button. */
	@FXML
	private Button changeHostButton;

	/** The localhost button. */
	@FXML
	private Button localhostButton;

	/** The host TF. */
	@FXML
	private TextField hostTF;

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
		WorkerConnectionManager.alternativeHostAddress = host;
		Scene scene = changeHostButton.getScene();

		try {
			Region shell = FXMLLoader.load(UriDictionary.class.getResource(UriDictionary.WorkerGui.Shell));
			Pane shellRegion = (Pane) scene.lookup(UriDictionary.Regions.workerOuterShellRegion);
			shellRegion.getChildren().clear();
			shellRegion.getChildren().add(shell);

			Region workerView = FXMLLoader.load(UriDictionary.class.getResource(UriDictionary.WorkerGui.ClientView));
			Pane mainViewRegion = (Pane) scene.lookup(UriDictionary.Regions.workerMainViewRegion);
			mainViewRegion.getChildren().add(workerView);

			scene.getWindow().sizeToScene();
		} catch (IOException e) {
			e.printStackTrace();
		}

		scene.getWindow().sizeToScene();
		scene.getWindow().centerOnScreen();
	}
}
