package kioskGui.controllers;

import org.controlsfx.validation.Severity;
import org.controlsfx.validation.ValidationSupport;
import org.controlsfx.validation.Validator;

import core.customer.CustomerRequest;
import core.guiUtilities.CpsRegEx;
import core.guiUtilities.LicencePlateTextField;
import core.guiUtilities.ServerMessageHandler;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import kioskGui.util.KioskConnectionManager;
import kioskGui.util.KioskRequestsFactory;

public class PreOrderedParkingLoginController implements ServerMessageHandler {
	private ValidationSupport validation = new ValidationSupport();
	private KioskConnectionManager connectionManager;

	public PreOrderedParkingLoginController() {
		connectionManager = KioskConnectionManager.getInstance();
		connectionManager.addServerMessageListener(this);
	}

	@FXML
	protected void initialize() {
		submitBTN.disableProperty().bind(validation.invalidProperty());
		validation.registerValidator(CarIdField, Validator.createRegexValidator("Liscence plate is Required", CpsRegEx.LicencePlateLength, Severity.ERROR));
	}

	@FXML
	private Button submitBTN;

	@FXML
	private LicencePlateTextField CarIdField;

	@FXML
	void SubmitPreOrdered(ActionEvent event) {
		CustomerRequest preOrderedEntranceRequest = KioskRequestsFactory.CreatePreOrderedEntranceRequest(CarIdField.getText());
		connectionManager.sendMessageToServer(preOrderedEntranceRequest);
	}

	@Override
	public void handleServerMessage(String msg) {

	}
}
