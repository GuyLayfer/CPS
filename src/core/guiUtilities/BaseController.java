package core.guiUtilities;

import java.io.IOException;
import java.net.URL;

import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;

public abstract class BaseController {

	protected void NavigateTo(Scene scene, String page) {
		NavigateTo(scene, page, GetMainViewRegion());
	}

	protected void NavigateTo(Scene scene, String page, String mainView) {
		Pane mainViewRegion = (Pane) scene.lookup(mainView);
		mainViewRegion.getChildren().clear();
		URL uri = UriDictionary.class.getResource(page);
		Region region = null;
		try {
			region = FXMLLoader.load(uri);
			StackPane.setAlignment(region, Pos.TOP_LEFT);
			mainViewRegion.getChildren().add(region);
			scene.getWindow().sizeToScene();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		scene.getWindow().sizeToScene();
	}

	protected abstract String GetMainViewRegion();

}
