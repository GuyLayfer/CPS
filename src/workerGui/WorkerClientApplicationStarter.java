package workerGui;

import java.io.IOException;
import java.net.URL;

import core.guiUtilities.UriDictionary;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.stage.Stage;

public class WorkerClientApplicationStarter extends Application{
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws IOException {
		URL shellUri = UriDictionary.class.getResource(UriDictionary.WorkerGui.Shell);
		URL mainView = UriDictionary.class.getResource(UriDictionary.WorkerGui.ClientView);
		Region kioskView = FXMLLoader.load(mainView);
		Region shell = FXMLLoader.load(shellUri);
		Scene scene = new Scene(shell);

		Pane mainViewRegion = (Pane) scene.lookup(UriDictionary.Regions.workerMainViewRegion);
		mainViewRegion.getChildren().add(kioskView);

		primaryStage.setScene(scene);
		primaryStage.setTitle("CPS Work Manager");
		primaryStage.show();
		scene.getWindow().sizeToScene();
	}
}