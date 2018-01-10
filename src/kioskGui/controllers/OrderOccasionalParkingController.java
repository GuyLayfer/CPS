package kioskGui.controllers;

import java.time.ZoneOffset;
import java.util.Date;

import org.apache.commons.validator.routines.EmailValidator;
import org.controlsfx.validation.Severity;
import org.controlsfx.validation.ValidationSupport;
import org.controlsfx.validation.Validator;

import core.customer.CustomerRequest;
import core.guiUtilities.CpsRegEx;
import core.guiUtilities.LicencePlateTextField;
import core.guiUtilities.NumberTextField;
import core.guiUtilities.ServerMessageHandler;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import kioskGui.util.KioskConnectionManager;
import kioskGui.util.KioskRequestsFactory;
import tornadofx.control.DateTimePicker;

public class OrderOccasionalParkingController implements ServerMessageHandler {
	private ValidationSupport validation = new ValidationSupport();
	private EmailValidator emailValidator = EmailValidator.getInstance();
	private KioskConnectionManager connectionManager;
	
	public OrderOccasionalParkingController() {
		connectionManager = KioskConnectionManager.getInstance();
		connectionManager.addServerMessageListener(this);
	}

	@FXML
	protected void initialize() {
		orderBTN.disableProperty().bind(validation.invalidProperty());
		validation.registerValidator(EmailField, Validator.createPredicateValidator((email) -> emailValidator.isValid((String) email), "Email is not valid"));
		validation.registerValidator(CustomerIdField, Validator.createRegexValidator("Customer ID is Required", CpsRegEx.IntegerBetweenMinAndMaxLength, Severity.ERROR));
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
		CustomerRequest occasionalParkingRequest = KioskRequestsFactory.CreateOccasionalParkingRequest(
				CustomerIdField.getNumber(),
				CarIdField.getText(),
				Date.from(DepartureDateField.getDateTimeValue().atOffset(ZoneOffset.UTC).toInstant()),
				EmailField.getText());
		
		connectionManager.sendMessageToServer(occasionalParkingRequest);
	}

	@Override
	public void handleServerMessage(String msg) {
		
	}

}
