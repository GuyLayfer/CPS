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

public class LeaveParkingLotController implements ServerMessageHandler {
	private ValidationSupport validation = new ValidationSupport();
	private KioskConnectionManager connectionManager;

	public LeaveParkingLotController() {
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
	void SubmitLeaveRequest(ActionEvent event) {
		CustomerRequest parkingLotExitRequest = KioskRequestsFactory.CreateParkingLotExitRequest(CarIdField.getText());
		connectionManager.sendMessageToServer(parkingLotExitRequest);
	}

	@Override
	public void handleServerMessage(String msg) {

	}
}
