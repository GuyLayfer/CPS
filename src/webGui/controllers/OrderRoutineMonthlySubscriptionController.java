
package webGui.controllers;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.validator.routines.EmailValidator;
import org.controlsfx.validation.Severity;
import org.controlsfx.validation.ValidationSupport;
import org.controlsfx.validation.Validator;

import com.jfoenix.controls.JFXTimePicker;

import core.customer.CustomerRequest;
import core.customer.CustomerRequestType;
import core.customer.responses.CustomerBaseResponse;
import core.customer.responses.ParkingLotsNamesForCustomerResponse;
import core.guiUtilities.CpsRegEx;
import core.guiUtilities.IServerResponseHandler;
import core.guiUtilities.NumberTextField;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import webGui.util.CustomerRequestFactory;
import webGui.util.LocalTimeConverter;
import webGui.util.MockWebClientConnectionManager;
import webGui.util.MultipleCarsDialog;

// TODO: Auto-generated Javadoc
/**
 * The Class OrderRoutineMonthlySubscriptionController.
 */
public class OrderRoutineMonthlySubscriptionController implements IServerResponseHandler<CustomerBaseResponse>{
	
	/** The validation. */
	private ValidationSupport validation = new ValidationSupport();
	
	/** The email validator. */
	private EmailValidator emailValidator = EmailValidator.getInstance();
	
	/** The cars liscence plates. */
	private List<String> carsLiscencePlates = new ArrayList<String>();
	
	/** The connection manager. */
	private MockWebClientConnectionManager connectionManager;

	/**
	 * Instantiates a new order routine monthly subscription controller.
	 */
	public OrderRoutineMonthlySubscriptionController() {
		connectionManager = MockWebClientConnectionManager.getInstance();
		connectionManager.addServerMessageListener(this);
		connectionManager.sendMessageToServer(CustomerRequestFactory.createParkingLotNamesRequest());
	}

	/**
	 * Initialize.
	 */
	@FXML
	protected void initialize() {
		SetupClockField();
		liscencePlateTF.setEditable(false);
		validation.registerValidator(emailTF, Validator.createPredicateValidator((email) -> emailValidator.isValid((String) email), "Email is not valid"));
		validation.registerValidator(parkingLotIdComboBox, Validator.createEmptyValidator("Parking lot ID is Required"));
		validation.registerValidator(customerIDTF, Validator.createRegexValidator("Customer ID is Required", CpsRegEx.IntegerBetweenMinAndMaxLength, Severity.ERROR));
		validation.registerValidator(liscencePlateTF, Validator.createEmptyValidator("Liscence plate is Required"));
		validation.registerValidator(TimePickerHelper, Validator.createEmptyValidator("Departure time is Required"));
		validation.registerValidator(startingDateTF, Validator.createEmptyValidator("Starting date is Required"));
	}

	/** The Email LBL. */
	@FXML // fx:id="EmailLBL"
	private Label EmailLBL; // Value injected by FXMLLoader

	/** The routine departure time TF. */
	@FXML // fx:id="routineDepartureTimeTF"
	private JFXTimePicker routineDepartureTimeTF; // Value injected by FXMLLoader

	/** The Liscence plate LBL. */
	@FXML // fx:id="LiscencePlateLBL"
	private Label LiscencePlateLBL; // Value injected by FXMLLoader

	/** The liscence plate TF. */
	@FXML // fx:id="liscencePlateTF"
	private TextField liscencePlateTF; // Value injected by FXMLLoader

	/** The Routine departure time LBL. */
	@FXML // fx:id="RoutineDepartureTimeLBL"
	private Label RoutineDepartureTimeLBL; // Value injected by FXMLLoader

	/** The parking lot id combo box. */
	@FXML // fx:id="parkingLotIDTF"
	private ComboBox<Integer> parkingLotIdComboBox; // Value injected by FXMLLoader

	/** The customer IDTF. */
	@FXML // fx:id="customerIDTF"
	private NumberTextField customerIDTF; // Value injected by FXMLLoader

	/** The customer IDLBL. */
	@FXML // fx:id="customerIDLBL"
	private Label customerIDLBL; // Value injected by FXMLLoader

	/** The email TF. */
	@FXML // fx:id="emailTF"
	private TextField emailTF; // Value injected by FXMLLoader

	/** The Rot mon sub LBL. */
	@FXML // fx:id="RotMonSubLBL"
	private Label RotMonSubLBL; // Value injected by FXMLLoader

	/** The starting date TF. */
	@FXML // fx:id="startingDateTF"
	private DatePicker startingDateTF; // Value injected by FXMLLoader

	/** The Parking lot IDLBL. */
	@FXML // fx:id="ParkingLotIDLBL"
	private Label ParkingLotIDLBL; // Value injected by FXMLLoader

	/** The Starting date LBL. */
	@FXML // fx:id="StartingDateLBL"
	private Label StartingDateLBL; // Value injected by FXMLLoader

	/** The create subscription BTN. */
	@FXML // fx:id="createSubscriptionBTN"
	private Button createSubscriptionBTN; // Value injected by FXMLLoader

	/** The Configure cars BTN. */
	@FXML // fx:id="AddCarsBTN"
	private Button ConfigureCarsBTN; // Value injected by FXMLLoader

	/** The Time picker helper. */
	private TextField TimePickerHelper;

	/**
	 * Creates the subscription.
	 *
	 * @param event the event
	 */
	@FXML
	public void CreateSubscription(ActionEvent event) {
		CustomerRequest request = CustomerRequestFactory.createOrderRoutineMonthlySubscriptionRequest(
				customerIDTF.getNumber(),
				carsLiscencePlates, emailTF.getText(),
				parkingLotIdComboBox.getValue(),
				java.sql.Date.valueOf(startingDateTF.getValue()),
				routineDepartureTimeTF.getValue());
		connectionManager.sendMessageToServer(request);
	}

	/**
	 * Configure cars list.
	 *
	 * @param event the event
	 */
	@FXML
	public void ConfigureCarsList(ActionEvent event) {
		MultipleCarsDialog dialog = new MultipleCarsDialog(carsLiscencePlates);
		carsLiscencePlates = dialog.showAndWait().get();
		liscencePlateTF.setText(carsLiscencePlates.isEmpty() ? "" : carsLiscencePlates.toString());
	}

	/**
	 * Validate time picker helper.
	 *
	 * @param event the event
	 */
	public void ValidateTimePickerHelper(ActionEvent event) {
		LocalTime time = routineDepartureTimeTF.getValue();
		TimePickerHelper.setText(time == null ? "" : time.toString());
	}

	/**
	 * Setup clock field.
	 */
	private void SetupClockField() {
		TimePickerHelper = new TextField();
		routineDepartureTimeTF.setIs24HourView(true);
		routineDepartureTimeTF.setConverter(new LocalTimeConverter());
		routineDepartureTimeTF.setValue(LocalTime.of(0, 0));
		createSubscriptionBTN.disableProperty().bind(validation.invalidProperty());
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
}
