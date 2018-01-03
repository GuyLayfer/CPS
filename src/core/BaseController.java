package core;

import java.io.IOException;
import java.net.URL;

import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.control.TabPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import webGui.MockWebClientApplicationStarter;

public abstract class BaseController {
	private static StackPane parent;
	private static Pane currentView;
	
	protected void NavigateTo(String page) {
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
