package kioskGui.controllers;

import core.guiUtilities.UriDictionary;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;

public class EntranceController extends KioskClientController {

	@FXML
	private Button subscriptionsLoginBTN;

	@FXML
	private Button orderOccasionalParkingBTN;

	@FXML
	private Button PreOrderedParkingLoginBTN;

	@FXML
	void orderOccasionalParking(ActionEvent event) {
		Scene scene = orderOccasionalParkingBTN.getScene();
		NavigateTo("Occasional Parking", scene, UriDictionary.Kiosk.OrderOccasionalParkingView);
	}

	@FXML
	void PreOrderedParkingLogin(ActionEvent event) {
		Scene scene = PreOrderedParkingLoginBTN.getScene();
		NavigateTo("Pre Ordered Parking Entrance", scene, UriDictionary.Kiosk.PreOrderedParkingLoginView);
	}

	@FXML
	void subscriptionsLogin(ActionEvent event) {
		Scene scene = subscriptionsLoginBTN.getScene();
		NavigateTo("Subscriber Entrance", scene, UriDictionary.Kiosk.SubscriptionsLoginView);
	}
}
