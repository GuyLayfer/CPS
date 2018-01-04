package kiosk.controllers;

import org.controlsfx.validation.Severity;
import org.controlsfx.validation.ValidationSupport;
import org.controlsfx.validation.Validator;

import core.gui.CpsRegEx;
import core.gui.LicencePlateTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class PreOrderedParkingLoginController {
	private ValidationSupport validation = new ValidationSupport();

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

	}
}
