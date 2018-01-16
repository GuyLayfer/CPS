package kioskGui.controllers;

import org.controlsfx.control.BreadCrumbBar;
import org.controlsfx.control.BreadCrumbBar.BreadCrumbActionEvent;

import core.customer.CustomerRequestType;
import core.customer.responses.BadCustomerResponse;
import core.customer.responses.CustomerBaseResponse;
import core.customer.responses.CustomerNotificationResponse;
import core.customer.responses.IdPricePairResponse;
import core.guiUtilities.IServerResponseHandler;
import core.guiUtilities.UriDictionary;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.TreeItem;
import javafx.scene.layout.AnchorPane;
import kioskGui.util.KioskConnectionManager;
import kioskGui.util.UriToString;

public class KioskGuiShellController extends KioskClientController implements IServerResponseHandler<CustomerBaseResponse> {
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
	public void handleServerResponse(CustomerBaseResponse response) {
		if (response instanceof CustomerNotificationResponse) {
			CustomerNotificationResponse notificationResponse = (CustomerNotificationResponse)response;
			showNotification(notificationResponse.message);
			return;
		}

		if (response.requestType == CustomerRequestType.BAD_REQUEST) {
			BadCustomerResponse badResponse = (BadCustomerResponse) response;
			showNotification(badResponse.toString());
			return;
		}
		
		if (response instanceof IdPricePairResponse) {
			IdPricePairResponse idPricePair = (IdPricePairResponse) response;
			showNotification(idPricePair.toString());
		}
	}
}
