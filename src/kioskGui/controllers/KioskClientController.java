package kioskGui.controllers;

import core.guiUtilities.UriDictionary;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import kioskGui.util.KioskConnectionManager;
import kioskGui.util.KioskGuiController;
import webGui.util.MockWebClientConnectionManager;

// TODO: Auto-generated Javadoc
/**
 * The Class KioskClientController.
 */
public class KioskClientController extends KioskGuiController{

	/** The Enter parking lot button. */
	@FXML
	private Button EnterParkingLotButton;

	/** The Order parking button. */
	@FXML
	private Button OrderParkingButton;

	/** The Leave parking lot button. */
	@FXML
	private Button LeaveParkingLotButton;

	/**
	 * Open leave parking lotdialog.
	 *
	 * @param event the event
	 */
	@FXML
	void OpenLeaveParkingLotdialog(ActionEvent event) {
		Scene scene = OrderParkingButton.getScene();
		NavigateTo("Leave parking lot", scene, UriDictionary.Kiosk.LeaveParkingLotView);
	}

	/**
	 * Open web gui.
	 *
	 * @param event the event
	 */
	@FXML
	void OpenWebGui(ActionEvent event) {
		Scene scene = OrderParkingButton.getScene();
		MockWebClientConnectionManager.alternativeHostAddress = KioskConnectionManager.getInstance().getHost();
//		Should be the host address. If not change to the static alternativeHostAddress 
		NavigateTo("Web Portal", scene, UriDictionary.WebGui.ClientView);
	}

	/**
	 * Open enter parking lot menu.
	 *
	 * @param event the event
	 */
	@FXML
	void OpenEnterParkingLotMenu(ActionEvent event) {
		Scene scene = EnterParkingLotButton.getScene();
		NavigateTo("Enter Parking Lot", scene, UriDictionary.Kiosk.EntranceView);
	}

}