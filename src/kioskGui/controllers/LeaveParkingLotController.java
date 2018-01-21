package kioskGui.controllers;

import org.controlsfx.validation.Severity;
import org.controlsfx.validation.ValidationSupport;
import org.controlsfx.validation.Validator;

import core.customer.CustomerRequest;
import core.guiUtilities.CpsRegEx;
import core.guiUtilities.LicencePlateTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import kioskGui.util.KioskConnectionManager;
import kioskGui.util.KioskRequestsFactory;

// TODO: Auto-generated Javadoc
/**
 * The Class LeaveParkingLotController.
 */
public class LeaveParkingLotController {
	
	/** The validation. */
	private ValidationSupport validation = new ValidationSupport();
	
	/** The connection manager. */
	private KioskConnectionManager connectionManager;

	/**
	 * Instantiates a new leave parking lot controller.
	 */
	public LeaveParkingLotController() {
		connectionManager = KioskConnectionManager.getInstance();
	}

	/**
	 * Initialize.
	 */
	@FXML
	protected void initialize() {
		submitBTN.disableProperty().bind(validation.invalidProperty());
		validation.registerValidator(CarIdField, Validator.createRegexValidator("Liscence plate is Required", CpsRegEx.LicencePlateLength, Severity.ERROR));
	}

	/** The submit BTN. */
	@FXML
	private Button submitBTN;

	/** The Car id field. */
	@FXML
	private LicencePlateTextField CarIdField;

	/**
	 * Submit leave request.
	 *
	 * @param event the event
	 */
	@FXML
	void SubmitLeaveRequest(ActionEvent event) {
		CustomerRequest parkingLotExitRequest = KioskRequestsFactory.CreateParkingLotExitRequest(CarIdField.getText());
		connectionManager.sendMessageToServer(parkingLotExitRequest);
	}
}
