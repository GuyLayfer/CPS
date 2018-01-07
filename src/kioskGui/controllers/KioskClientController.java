package kioskGui.controllers;

import core.guiUtilities.UriDictionary;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import kioskGui.util.KioskConnectionManager;
import kioskGui.util.KioskGuiController;
import webGui.util.MockWebClientConnectionManager;

public class KioskClientController extends KioskGuiController{

	@FXML
	private Button EnterParkingLotButton;

	@FXML
	private Button OrderParkingButton;

	@FXML
	private Button LeaveParkingLotButton;

	@FXML
	void OpenLeaveParkingLotdialog(ActionEvent event) {
		Scene scene = OrderParkingButton.getScene();
		NavigateTo("Leave parking lot", scene, UriDictionary.Kiosk.LeaveParkingLotView);
	}

	@FXML
	void OpenWebGui(ActionEvent event) {
		Scene scene = OrderParkingButton.getScene();
		MockWebClientConnectionManager.alternativeHostAddress = KioskConnectionManager.getInstance().getHost();
//		Should be the host address. If not change to the static alternativeHostAddress 
		NavigateTo("Web Portal", scene, UriDictionary.WebGui.ClientView);
	}

	@FXML
	void OpenEnterParkingLotMenu(ActionEvent event) {
		Scene scene = EnterParkingLotButton.getScene();
		NavigateTo("Enter Parking Lot", scene, UriDictionary.Kiosk.EntranceView);
	}

}