package kiosk.controllers;

import org.apache.commons.validator.routines.EmailValidator;
import org.controlsfx.validation.Severity;
import org.controlsfx.validation.ValidationSupport;
import org.controlsfx.validation.Validator;

import core.gui.CpsRegEx;
import core.gui.LicencePlateTextField;
import core.gui.NumberTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import tornadofx.control.DateTimePicker;

public class OrderOccasionalParkingController {
	private ValidationSupport validation = new ValidationSupport();
	private EmailValidator emailValidator = EmailValidator.getInstance();

	@FXML
	protected void initialize() {
		orderBTN.disableProperty().bind(validation.invalidProperty());
		validation.registerValidator(EmailField, Validator.createPredicateValidator((email) -> emailValidator.isValid((String) email), "Email is not valid"));
		validation.registerValidator(CustomerIdField, Validator.createRegexValidator("Customer ID is Required", CpsRegEx.OneOrMoreIntegers, Severity.ERROR));
		validation.registerValidator(CarIdField, Validator.createRegexValidator("Liscence plate is Required", CpsRegEx.LicencePlateLength, Severity.ERROR));
		validation.registerValidator(DepartureDateField, Validator.createEmptyValidator("Departure time is Required"));
	}

	@FXML
	private NumberTextField CustomerIdField;

	@FXML
	private LicencePlateTextField CarIdField;

	@FXML
	private TextField EmailField;

	@FXML
	private DateTimePicker DepartureDateField;

	@FXML
	private Button orderBTN;

	@FXML
	void orderOccasionalParking(ActionEvent event) {

	}

}
