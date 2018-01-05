package kioskGui;

import java.io.IOException;

import core.guiUtilities.UriDictionary;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class KioskClientApplicationStarter extends Application{
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws IOException {
		Region shell = FXMLLoader.load(UriDictionary.class.getResource(UriDictionary.Kiosk.OuterShell));
		Region hostAddressView = FXMLLoader.load(UriDictionary.class.getResource(UriDictionary.Kiosk.KioskStartPageView));
		Scene scene = new Scene(shell);
		Pane mainViewRegion = (Pane) scene.lookup(UriDictionary.Regions.kisokOuterShellRegion);
		mainViewRegion.getChildren().add(hostAddressView);

		primaryStage.setScene(scene);
		primaryStage.setTitle("Parking Lot Kiosk");
		primaryStage.show();
		scene.getWindow().sizeToScene();
	}
}
