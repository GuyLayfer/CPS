package kioskGui.controllers;

import org.controlsfx.control.BreadCrumbBar;
import org.controlsfx.control.BreadCrumbBar.BreadCrumbActionEvent;

import core.customer.CustomerRequestType;
import core.customer.responses.BadCustomerResponse;
import core.customer.responses.CustomerBaseResponse;
import core.customer.responses.CustomerNotificationResponse;
import core.customer.responses.IdPricePairResponse;
import core.customer.responses.ParkingLotsNamesForCustomerResponse;
import core.guiUtilities.IServerResponseHandler;
import core.guiUtilities.UriDictionary;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TreeItem;
import javafx.scene.layout.AnchorPane;
import kioskGui.util.KioskConnectionManager;
import kioskGui.util.KioskRequestsFactory;
import kioskGui.util.UriToString;

// TODO: Auto-generated Javadoc
/**
 * The Class KioskGuiShellController.
 */
public class KioskGuiShellController extends KioskClientController implements IServerResponseHandler<CustomerBaseResponse> {
	
	/** The connection manager. */
	private KioskConnectionManager connectionManager;

	/**
	 * Instantiates a new kiosk gui shell controller.
	 */
	public KioskGuiShellController() {
		connectionManager = KioskConnectionManager.getInstance();
		connectionManager.addServerMessageListener(this);
		connectionManager.sendMessageToServer(KioskRequestsFactory.CreateParkingLotNamesRequest());
	}

	/** The bread crumb bar. */
	@FXML
	private BreadCrumbBar<UriToString> breadCrumbBar;

	/** The parking lot id combo box. */
	@FXML
	private ComboBox<Integer> parkingLotIdComboBox;

	/** The kisok main view region. */
	@FXML
	private AnchorPane kisokMainViewRegion;

	/**
	 * Initialize.
	 */
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

		parkingLotIdComboBox.valueProperty().addListener(new ChangeListener<Integer>() {
			@Override
			public void changed(ObservableValue<? extends Integer> observable, Integer oldValue, Integer newValue) {
				KioskRequestsFactory.currentLotId = newValue;
			}
		});
	}

	/* (non-Javadoc)
	 * @see core.guiUtilities.IServerResponseHandler#handleServerResponse(java.lang.Object)
	 */
	@Override
	public void handleServerResponse(CustomerBaseResponse response) {
		if (response instanceof CustomerNotificationResponse) {
			CustomerNotificationResponse notificationResponse = (CustomerNotificationResponse) response;
			showNotification(notificationResponse.message);
			return;
		}

		if (response.requestType == CustomerRequestType.BAD_REQUEST) {
			BadCustomerResponse badResponse = (BadCustomerResponse) response;
			showError(badResponse.toString());
			return;
		}

		if (response.requestType == CustomerRequestType.PARKING_LOT_NAMES) {
			Platform.runLater(() -> {
				ParkingLotsNamesForCustomerResponse parkingLotNames = (ParkingLotsNamesForCustomerResponse) response;
				parkingLotIdComboBox.getItems().clear();
				parkingLotIdComboBox.getItems().addAll(parkingLotNames.lotNames);
				if (!parkingLotNames.lotNames.isEmpty()) {
					parkingLotIdComboBox.setValue(parkingLotNames.lotNames.get(0));
				}
			});
			return;
		}

		if (response instanceof IdPricePairResponse) {
			IdPricePairResponse idPricePair = (IdPricePairResponse) response;
			showNotification(idPricePair.toString());
		}
	}
}
