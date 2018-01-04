package kioskGui.controllers;

import org.controlsfx.control.BreadCrumbBar;
import org.controlsfx.control.BreadCrumbBar.BreadCrumbActionEvent;

import core.guiUtilities.UriDictionary;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.TreeItem;
import javafx.scene.layout.AnchorPane;
import kioskGui.util.UriToString;

public class KioskGuiShellController extends KioskClientController {

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
}
