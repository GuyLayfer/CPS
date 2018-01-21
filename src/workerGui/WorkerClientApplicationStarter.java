package workerGui;

import java.io.IOException;

import core.guiUtilities.UriDictionary;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import workerGui.util.WorkerConnectionManager;

public class WorkerClientApplicationStarter extends Application {
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws IOException {
		Region shell = FXMLLoader.load(UriDictionary.class.getResource(UriDictionary.WorkerGui.OuterShell));
		Region hostAddressView = FXMLLoader.load(UriDictionary.class.getResource(UriDictionary.WorkerGui.WorkerStartPageView));
		Scene scene = new Scene(shell);
		Pane mainViewRegion = (Pane) scene.lookup(UriDictionary.Regions.workerOuterShellRegion);
		mainViewRegion.getChildren().add(hostAddressView);

		primaryStage.setScene(scene);
		primaryStage.setTitle("CPS Work Manager");
		primaryStage.show();
		scene.getWindow().sizeToScene();
		primaryStage.getIcons().add(new Image(UriDictionary.class.getResourceAsStream(UriDictionary.Images.icon)));

		primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
			@Override
			public void handle(WindowEvent event) {
				Alert exitDialog = new Alert(AlertType.CONFIRMATION, "");
				exitDialog.setTitle("Confirm Exit");
				exitDialog.getDialogPane().setHeaderText("Are you sure you want to exit?");
				ButtonType result = exitDialog.showAndWait().get();
				if (result == ButtonType.OK) {
					WorkerConnectionManager.getInstance().closeServerConnection();
					System.exit(0);
				} else if (result == ButtonType.CANCEL) {
					event.consume();
				}
			}
		});
	}
}
