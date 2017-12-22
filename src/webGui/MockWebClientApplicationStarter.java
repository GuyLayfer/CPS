package webGui;

import java.io.IOException;
import java.net.URL;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.TabPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class MockWebClientApplicationStarter extends Application {
	private static StackPane stackPane;
	private static AnchorPane hostAddressPane;
	
	@Override
	public void start(Stage primaryStage) throws IOException {
		URL rootUri = getClass().getResource("views/Shell.fxml");
		URL uri = getClass().getResource("views/HostAddressStartPageView.fxml");
		AnchorPane pane = hostAddressPane = FXMLLoader.load(uri);
		stackPane = FXMLLoader.load(rootUri);
		stackPane.getChildren().add(pane);
		Scene scene = new Scene(stackPane);
		primaryStage.setScene(scene);
		primaryStage.setTitle("Web Client Mock");
		primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
	
	public static void navigateToWebClientView(){
		TabPane pane = null;
		URL uri = MockWebClientApplicationStarter.class.getResource("views/MockWebClientView.fxml");
		try {
			pane = FXMLLoader.load(uri);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		stackPane.getChildren().remove(hostAddressPane);
		StackPane.setAlignment(pane,Pos.TOP_LEFT);
		stackPane.getChildren().add(pane);
	}
}