package webGui;

import java.io.IOException;
import java.net.URL;

import core.gui.UriDictionary;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class MockWebClientApplicationStarter extends Application {

	public static void main(String[] args) {
		launch(args);
	}

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
	}
}
