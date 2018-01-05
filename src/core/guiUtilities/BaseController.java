package core.guiUtilities;

import java.io.IOException;
import java.net.URL;

import javafx.animation.FadeTransition;
import javafx.animation.Interpolator;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;

public abstract class BaseController {

	protected void NavigateTo(Scene scene, String page) {
		NavigateTo(scene, page, GetMainViewRegion());
	}

	protected void NavigateTo(Scene scene, String page, String mainView) {
		Pane mainViewRegion = (Pane) scene.lookup(mainView);
		transitionInvisible(mainViewRegion.getChildren().get(0));
		
		URL uri = UriDictionary.class.getResource(page);
		Region region = null;
		try {
			region = FXMLLoader.load(uri);
			StackPane.setAlignment(region, Pos.TOP_LEFT);
			mainViewRegion.getChildren().clear();
			mainViewRegion.getChildren().add(region);
			transitionVisible(region);
			scene.getWindow().sizeToScene();
		} catch (IOException e) {
			e.printStackTrace();
		}

		scene.getWindow().sizeToScene();
		scene.getWindow().centerOnScreen();
	}

	protected abstract String GetMainViewRegion();

	private void transitionVisible(Node region) {
		FadeTransition fadeTransition = new FadeTransition(new Duration(350), region);
		fadeTransition.setFromValue(0);
		fadeTransition.setToValue(100);
		fadeTransition.setCycleCount(1);
		fadeTransition.setInterpolator(Interpolator.LINEAR);
		fadeTransition.play();
	}

	private void transitionInvisible(Node region) {
		if (region != null) {
			FadeTransition fadeTransition = new FadeTransition(new Duration(100), region);
			fadeTransition.setFromValue(100);
			fadeTransition.setToValue(0);
			fadeTransition.setCycleCount(1);
			fadeTransition.setInterpolator(Interpolator.LINEAR);
			fadeTransition.play();
		}
	}
}
