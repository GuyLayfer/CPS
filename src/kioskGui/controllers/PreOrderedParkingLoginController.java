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
 * The Class PreOrderedParkingLoginController.
 */
public class PreOrderedParkingLoginController {
	
	/** The validation. */
	private ValidationSupport validation = new ValidationSupport();
	
	/** The connection manager. */
	private KioskConnectionManager connectionManager;

	/**
	 * Instantiates a new pre ordered parking login controller.
	 */
	public PreOrderedParkingLoginController() {
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
	 * Submit pre ordered.
	 *
	 * @param event the event
	 */
	@FXML
	void SubmitPreOrdered(ActionEvent event) {
		CustomerRequest preOrderedEntranceRequest = KioskRequestsFactory.CreatePreOrderedEntranceRequest(CarIdField.getText());
		connectionManager.sendMessageToServer(preOrderedEntranceRequest);
	}
}
