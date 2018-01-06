package kioskGui.controllers;

import org.controlsfx.control.BreadCrumbBar;
import org.controlsfx.control.BreadCrumbBar.BreadCrumbActionEvent;
import org.controlsfx.control.Notifications;

import core.guiUtilities.ServerMessageHandler;
import core.guiUtilities.UriDictionary;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.TreeItem;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import kioskGui.util.KioskConnectionManager;
import kioskGui.util.UriToString;

public class KioskGuiShellController extends KioskClientController implements ServerMessageHandler {
	private KioskConnectionManager connectionManager;

	public KioskGuiShellController() {
		connectionManager = KioskConnectionManager.getInstance();
		connectionManager.addServerMessageListener(this);
	}

	@FXML
	private BreadCrumbBar<UriToString> breadCrumbBar;

	@FXML
	private AnchorPane kisokMainViewRegion;

	@FXML
	protected void initialize() {
		TreeItem<UriToString> initialPath = BreadCrumbBar.buildTreeModel(new UriToString(UriDictionary.Kiosk.ClientView, "Home"));
		breadCrumbBar.setSelectedCrumb(initialPath);

		breadCrumbBar.setOnCrumbAction(new EventHandler<BreadCrumbBar.BreadCrumbActionEvent<UriToString>>() {
			@Override
			public void handle(BreadCrumbActionEvent<UriToString> bce) {
				TreeItem<UriToString> selectedCrumb = bce.getSelectedCrumb();
				selectedCrumb.getChildren().clear();
				NavigateTo(breadCrumbBar.getScene(), selectedCrumb.getValue().Uri);
			}
		});
	}

	@Override
	public void handleServerMessage(String msg) {
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
