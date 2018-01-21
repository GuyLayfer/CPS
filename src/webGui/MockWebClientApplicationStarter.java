package webGui;

import java.io.IOException;
import java.net.URL;

import core.guiUtilities.UriDictionary;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import webGui.util.MockWebClientConnectionManager;

// TODO: Auto-generated Javadoc
/**
 * The Class MockWebClientApplicationStarter.
 */
public class MockWebClientApplicationStarter extends Application {

	/**
	 * The main method.
	 *
	 * @param args the arguments
	 */
	public static void main(String[] args) {
		launch(args);
	}

	/* (non-Javadoc)
	 * @see javafx.application.Application#start(javafx.stage.Stage)
	 */
	@Override
	public void start(Stage primaryStage) throws IOException {
		URL shellUri = UriDictionary.class.getResource(UriDictionary.WebGui.Shell);
		URL uri = UriDictionary.class.getResource(UriDictionary.WebGui.HostAddressStartPageView);
		AnchorPane hostAddress = FXMLLoader.load(uri);
		StackPane stackPane = FXMLLoader.load(shellUri);
		Scene scene = new Scene(stackPane);

		Pane mainViewRegion = (Pane) scene.lookup(UriDictionary.Regions.webGuiMainViewRegion);
		mainViewRegion.getChildren().add(hostAddress);

		primaryStage.setScene(scene);
		primaryStage.setTitle("Web Client Mock");
		primaryStage.show();
		
		primaryStage.getIcons().add(new Image(UriDictionary.class.getResourceAsStream(UriDictionary.Images.icon)));

		primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
			@Override
			public void handle(WindowEvent event) {
				Alert exitDialog = new Alert(AlertType.CONFIRMATION, "");
				exitDialog.setTitle("Confirm Exit");
				exitDialog.getDialogPane().setHeaderText("Are you sure you want to exit?");
				ButtonType result = exitDialog.showAndWait().get();
				if (result == ButtonType.OK) {
					MockWebClientConnectionManager.getInstance().closeServerConnection();
					System.exit(0);
				} else if (result == ButtonType.CANCEL) {
					event.consume();
				}
			}
		});
	}
}
