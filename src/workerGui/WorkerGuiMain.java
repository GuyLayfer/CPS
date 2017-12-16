package workerGui;

import java.io.IOException;
import java.net.URL;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class WorkerGuiMain extends Application{

	public void start(Stage primaryStage) throws IOException {
		URL uri = getClass().getResource("WebClientMockView.fxml");
		AnchorPane pane = FXMLLoader.load(uri);
		Scene scene = new Scene(pane);

		primaryStage.setScene(scene);
		primaryStage.setTitle("Web Client Mock");
		primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}

}