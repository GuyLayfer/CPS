
package webGui.controllers;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import org.apache.commons.validator.routines.EmailValidator;
import org.controlsfx.validation.Severity;
import org.controlsfx.validation.ValidationSupport;
import org.controlsfx.validation.Validator;

import core.customer.CustomerRequest;
import core.customer.CustomerRequestType;
import core.customer.responses.CustomerBaseResponse;
import core.customer.responses.ParkingLotsNamesForCustomerResponse;
import core.guiUtilities.CpsRegEx;
import core.guiUtilities.IServerResponseHandler;
import core.guiUtilities.LicencePlateTextField;
import core.guiUtilities.NumberTextField;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import tornadofx.control.DateTimePicker;
import webGui.util.CustomerRequestFactory;
import webGui.util.MockWebClientConnectionManager;
import webGui.util.WebGuiController;

// TODO: Auto-generated Javadoc
/**
 * The Class PreOrderParkingController.
 */
public class PreOrderParkingController extends WebGuiController implements IServerResponseHandler<CustomerBaseResponse> {
	
	/** The validation. */
	private ValidationSupport validation = new ValidationSupport();
	
	/** The email validator. */
	private EmailValidator emailValidator = EmailValidator.getInstance();
	
	/** The connection manager. */
	private MockWebClientConnectionManager connectionManager;

	/**
	 * Instantiates a new pre order parking controller.
	 */
	public PreOrderParkingController() {
		connectionManager = MockWebClientConnectionManager.getInstance();
		connectionManager.addServerMessageListener(this);
	}

	/**
	 * Initialize.
	 */
	@FXML
	protected void initialize() {
		OrderOneTimeParkingBTN.disableProperty().bind(validation.invalidProperty());
		validation.registerValidator(emailTF, Validator.createPredicateValidator((email) -> emailValidator.isValid((String) email), "Email is not valid"));
		validation.registerValidator(parkingLotIdComboBox, Validator.createEmptyValidator("Parking lot ID is Required"));
		validation.registerValidator(customerIDTF, Validator.createRegexValidator("Customer ID is Required", CpsRegEx.IntegerBetweenMinAndMaxLength, Severity.ERROR));
		validation.registerValidator(liscencePlateTF, Validator.createRegexValidator("Liscence plate is Required", CpsRegEx.LicencePlateLength, Severity.ERROR));
		validation.registerValidator(estimatedDepartureTimeTF, Validator.createEmptyValidator("Departure time is Required"));
		validation.registerValidator(arrivalTimeTF, Validator.createEmptyValidator("Arrival time is Required"));
		connectionManager.sendMessageToServer(CustomerRequestFactory.createParkingLotNamesRequest());
	}

	/** The estimated departure time TF. */
	@FXML // fx:id="estimatedDepartureTimeTF"
	private DateTimePicker estimatedDepartureTimeTF; // Value injected by FXMLLoader

	/** The Order one time parking BTN. */
	@FXML // fx:id="OrderOneTimeParkingBTN"
	private Button OrderOneTimeParkingBTN; // Value injected by FXMLLoader

	/** The liscence plate TF. */
	@FXML // fx:id="liscencePlateTF"
	private LicencePlateTextField liscencePlateTF; // Value injected by FXMLLoader

	/** The parking lot id combo box. */
	@FXML // fx:id="parkingLotIDTF"
	private ComboBox<Integer> parkingLotIdComboBox; // Value injected by FXMLLoader

	/** The customer IDTF. */
	@FXML // fx:id="customerIDTF"
	private NumberTextField customerIDTF; // Value injected by FXMLLoader

	/** The email TF. */
	@FXML // fx:id="emailTF"
	private TextField emailTF; // Value injected by FXMLLoader

	/** The arrival time TF. */
	@FXML // fx:id="arrivalTimeTF"
	private DateTimePicker arrivalTimeTF; // Value injected by FXMLLoader

	/**
	 * Order one time parking.
	 *
	 * @param event the event
	 */
	@FXML
	public void OrderOneTimeParking(ActionEvent event) {
		if (validateDates()) {
			CustomerRequest request = CustomerRequestFactory.createOrderOneTimeParkingRequest(customerIDTF.getNumber(), liscencePlateTF.getText(), emailTF.getText(),
					parkingLotIdComboBox.getValue(), Date.from(arrivalTimeTF.getDateTimeValue().atZone(ZoneId.systemDefault()).toInstant()),
					Date.from(estimatedDepartureTimeTF.getDateTimeValue().atZone(ZoneId.systemDefault()).toInstant()));
			connectionManager.sendMessageToServer(request);
		} else {
			showError("Please check your dates are valid for the reservation.");
		}
	}

	/* (non-Javadoc)
	 * @see core.guiUtilities.IServerResponseHandler#handleServerResponse(java.lang.Object)
	 */
	@Override
	public void handleServerResponse(CustomerBaseResponse response) {
		if (response.requestType == CustomerRequestType.PARKING_LOT_NAMES) {
			Platform.runLater(() -> {
				ParkingLotsNamesForCustomerResponse parkingLotNames = (ParkingLotsNamesForCustomerResponse) response;
				parkingLotIdComboBox.getItems().clear();
				parkingLotIdComboBox.getItems().addAll(parkingLotNames.lotNames);
			});
		}
	}

	/**
	 * Validate dates.
	 *
	 * @return the boolean
	 */
	private Boolean validateDates() {
		if (arrivalTimeTF.getDateTimeValue() == null || estimatedDepartureTimeTF.getDateTimeValue() == null) {
			return false;
		}

		if (arrivalTimeTF.getDateTimeValue().isAfter(estimatedDepartureTimeTF.getDateTimeValue())) {
			return false;
		}

		if (arrivalTimeTF.getDateTimeValue().isBefore(LocalDateTime.now())) {
			return false;
		}

		return true;
	}
}
