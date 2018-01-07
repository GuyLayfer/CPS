
package webGui.controllers;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.validator.routines.EmailValidator;
import org.controlsfx.validation.Severity;
import org.controlsfx.validation.ValidationSupport;
import org.controlsfx.validation.Validator;

import com.jfoenix.controls.JFXTimePicker;

import core.guiUtilities.CpsRegEx;
import core.guiUtilities.NumberTextField;
import core.guiUtilities.ServerMessageHandler;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import webGui.models.OrderRoutineMonthlySubscriptionModel;
import webGui.util.LocalTimeConverter;
import webGui.util.MultipleCarsDialog;

public class OrderRoutineMonthlySubscriptionController implements ServerMessageHandler {
	private OrderRoutineMonthlySubscriptionModel model;
	private ValidationSupport validation = new ValidationSupport();
	private EmailValidator emailValidator = EmailValidator.getInstance();
	private List<String> carsLiscencePlates = new ArrayList<String>();

	public OrderRoutineMonthlySubscriptionController() {
		model = new OrderRoutineMonthlySubscriptionModel(this);
	}

	@FXML
	protected void initialize() {
		SetupClockField();
		liscencePlateTF.setEditable(false);
		validation.registerValidator(emailTF, Validator.createPredicateValidator((email) -> emailValidator.isValid((String) email), "Email is not valid"));
		validation.registerValidator(parkingLotIDTF, Validator.createRegexValidator("Parking lot ID is Required", CpsRegEx.OneOrMoreIntegers, Severity.ERROR));
		validation.registerValidator(customerIDTF, Validator.createRegexValidator("Customer ID is Required", CpsRegEx.OneOrMoreIntegers, Severity.ERROR));
		validation.registerValidator(liscencePlateTF, Validator.createEmptyValidator("Liscence plate is Required"));
		validation.registerValidator(TimePickerHelper, Validator.createEmptyValidator("Departure time is Required"));
		validation.registerValidator(startingDateTF, Validator.createEmptyValidator("Starting date is Required"));
	}

	@FXML // fx:id="EmailLBL"
	private Label EmailLBL; // Value injected by FXMLLoader

	@FXML // fx:id="routineDepartureTimeTF"
	private JFXTimePicker routineDepartureTimeTF; // Value injected by FXMLLoader

	@FXML // fx:id="LiscencePlateLBL"
	private Label LiscencePlateLBL; // Value injected by FXMLLoader

	@FXML // fx:id="liscencePlateTF"
	private TextField liscencePlateTF; // Value injected by FXMLLoader

	@FXML // fx:id="RoutineDepartureTimeLBL"
	private Label RoutineDepartureTimeLBL; // Value injected by FXMLLoader

	@FXML // fx:id="parkingLotIDTF"
	private NumberTextField parkingLotIDTF; // Value injected by FXMLLoader

	@FXML // fx:id="customerIDTF"
	private NumberTextField customerIDTF; // Value injected by FXMLLoader

	@FXML // fx:id="customerIDLBL"
	private Label customerIDLBL; // Value injected by FXMLLoader

	@FXML // fx:id="emailTF"
	private TextField emailTF; // Value injected by FXMLLoader

	@FXML // fx:id="RotMonSubLBL"
	private Label RotMonSubLBL; // Value injected by FXMLLoader

	@FXML // fx:id="startingDateTF"
	private DatePicker startingDateTF; // Value injected by FXMLLoader

	@FXML // fx:id="ParkingLotIDLBL"
	private Label ParkingLotIDLBL; // Value injected by FXMLLoader

	@FXML // fx:id="StartingDateLBL"
	private Label StartingDateLBL; // Value injected by FXMLLoader

	@FXML // fx:id="createSubscriptionBTN"
	private Button createSubscriptionBTN; // Value injected by FXMLLoader

	@FXML // fx:id="AddCarsBTN"
	private Button ConfigureCarsBTN; // Value injected by FXMLLoader

	private TextField TimePickerHelper;

	@FXML
	public void CreateSubscription(ActionEvent event) {
		// TODO: change liscencePlateTF to String when Raz changes it in the DB
		model.SendOrderRoutineMonthlySubscriptionRequestToServer(
				customerIDTF.getNumber(),
				carsLiscencePlates, emailTF.getText(),
				parkingLotIDTF.getNumber(),
				java.sql.Date.valueOf(startingDateTF.getValue()),
				routineDepartureTimeTF.getValue());
	}

	@FXML
	public void ConfigureCarsList(ActionEvent event) {
		MultipleCarsDialog dialog = new MultipleCarsDialog(carsLiscencePlates);
		carsLiscencePlates = dialog.showAndWait().get();
		liscencePlateTF.setText(carsLiscencePlates.isEmpty() ? "" : carsLiscencePlates.toString());
	}

	@Override
	public void handleServerMessage(String msg) {

	}

	public void ValidateTimePickerHelper(ActionEvent event) {
		LocalTime time = routineDepartureTimeTF.getValue();
		TimePickerHelper.setText(time == null ? "" : time.toString());
	}

	private void SetupClockField() {
		TimePickerHelper = new TextField();
		routineDepartureTimeTF.setIs24HourView(true);
		routineDepartureTimeTF.setConverter(new LocalTimeConverter());
		routineDepartureTimeTF.setValue(LocalTime.of(0, 0));
		createSubscriptionBTN.disableProperty().bind(validation.invalidProperty());
	}
}
