package kioskGui.controllers;

import java.time.ZoneId;
import java.util.Date;

import org.apache.commons.validator.routines.EmailValidator;
import org.controlsfx.validation.Severity;
import org.controlsfx.validation.ValidationSupport;
import org.controlsfx.validation.Validator;

import core.customer.CustomerRequest;
import core.guiUtilities.CpsRegEx;
import core.guiUtilities.LicencePlateTextField;
import core.guiUtilities.NumberTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import kioskGui.util.KioskConnectionManager;
import kioskGui.util.KioskRequestsFactory;
import tornadofx.control.DateTimePicker;

// TODO: Auto-generated Javadoc
/**
 * The Class OrderOccasionalParkingController.
 */
public class OrderOccasionalParkingController {
	
	/** The validation. */
	private ValidationSupport validation = new ValidationSupport();
	
	/** The email validator. */
	private EmailValidator emailValidator = EmailValidator.getInstance();
	
	/** The connection manager. */
	private KioskConnectionManager connectionManager;
	
	/**
	 * Instantiates a new order occasional parking controller.
	 */
	public OrderOccasionalParkingController() {
		connectionManager = KioskConnectionManager.getInstance();
	}

	/**
	 * Initialize.
	 */
	@FXML
	protected void initialize() {
		orderBTN.disableProperty().bind(validation.invalidProperty());
		validation.registerValidator(EmailField, Validator.createPredicateValidator((email) -> emailValidator.isValid((String) email), "Email is not valid"));
		validation.registerValidator(CustomerIdField, Validator.createRegexValidator("Customer ID is Required", CpsRegEx.IntegerBetweenMinAndMaxLength, Severity.ERROR));
		validation.registerValidator(CarIdField, Validator.createRegexValidator("Liscence plate is Required", CpsRegEx.LicencePlateLength, Severity.ERROR));
		validation.registerValidator(DepartureDateField, Validator.createEmptyValidator("Departure time is Required"));
	}

	/** The Customer id field. */
	@FXML
	private NumberTextField CustomerIdField;

	/** The Car id field. */
	@FXML
	private LicencePlateTextField CarIdField;

	/** The Email field. */
	@FXML
	private TextField EmailField;

	/** The Departure date field. */
	@FXML
	private DateTimePicker DepartureDateField;

	/** The order BTN. */
	@FXML
	private Button orderBTN;

	/**
	 * Order occasional parking.
	 *
	 * @param event the event
	 */
	@FXML
	void orderOccasionalParking(ActionEvent event) {
		CustomerRequest occasionalParkingRequest = KioskRequestsFactory.CreateOccasionalParkingRequest(
				CustomerIdField.getNumber(),
				CarIdField.getText(),
				Date.from(DepartureDateField.getDateTimeValue().atZone(ZoneId.systemDefault()).toInstant()),
				EmailField.getText());
		
		connectionManager.sendMessageToServer(occasionalParkingRequest);
	}
}
