package kioskGui.controllers;

import core.guiUtilities.UriDictionary;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;

// TODO: Auto-generated Javadoc
/**
 * The Class EntranceController.
 */
public class EntranceController extends KioskClientController {

	/** The subscriptions login BTN. */
	@FXML
	private Button subscriptionsLoginBTN;

	/** The order occasional parking BTN. */
	@FXML
	private Button orderOccasionalParkingBTN;

	/** The Pre ordered parking login BTN. */
	@FXML
	private Button PreOrderedParkingLoginBTN;

	/**
	 * Order occasional parking.
	 *
	 * @param event the event
	 */
	@FXML
	void orderOccasionalParking(ActionEvent event) {
		Scene scene = orderOccasionalParkingBTN.getScene();
		NavigateTo("Occasional Parking", scene, UriDictionary.Kiosk.OrderOccasionalParkingView);
	}

	/**
	 * Pre ordered parking login.
	 *
	 * @param event the event
	 */
	@FXML
	void PreOrderedParkingLogin(ActionEvent event) {
		Scene scene = PreOrderedParkingLoginBTN.getScene();
		NavigateTo("Pre Ordered Parking Entrance", scene, UriDictionary.Kiosk.PreOrderedParkingLoginView);
	}

	/**
	 * Subscriptions login.
	 *
	 * @param event the event
	 */
	@FXML
	void subscriptionsLogin(ActionEvent event) {
		Scene scene = subscriptionsLoginBTN.getScene();
		NavigateTo("Subscriber Entrance", scene, UriDictionary.Kiosk.SubscriptionsLoginView);
	}
}
