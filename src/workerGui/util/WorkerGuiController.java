package workerGui.util;

import org.controlsfx.control.Notifications;

import core.guiUtilities.BaseController;
import core.guiUtilities.UriDictionary;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.util.Duration;

public abstract class WorkerGuiController extends BaseController {

	@Override
	protected String GetMainViewRegion() {
		return UriDictionary.Regions.workerMainViewRegion;
	}
	
	protected void showNotification(String msg) {
		Platform.runLater(() -> {
			Notifications notificationBuilder = Notifications.create()
				.title("Message from server:")
				.text(msg)
				.hideAfter(Duration.seconds(10))
				.position(Pos.BOTTOM_RIGHT)
				.onAction(new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent arg0) {
						
					}
				});
		
		notificationBuilder.showInformation();
		});
	}
}
