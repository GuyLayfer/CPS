
package webGui.controllers;

import java.time.ZoneOffset;
import java.util.Date;

import org.apache.commons.validator.routines.EmailValidator;
import org.controlsfx.validation.Severity;
import org.controlsfx.validation.ValidationSupport;
import org.controlsfx.validation.Validator;

import core.guiUtilities.CpsRegEx;
import core.guiUtilities.LicencePlateTextField;
import core.guiUtilities.NumberTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import webGui.models.OrderFullMonthlySubscriptionModel;

public class OrderFullMonthlySubscriptionController {
	private OrderFullMonthlySubscriptionModel model;
	private ValidationSupport validation = new ValidationSupport();
	private EmailValidator emailValidator = EmailValidator.getInstance();

	public OrderFullMonthlySubscriptionController() {
		model = new OrderFullMonthlySubscriptionModel();
	}

	@FXML
	protected void initialize() {
		OrderFullMonthlySubscriptionBTN.disableProperty().bind(validation.invalidProperty());
		validation.registerValidator(emailTF, Validator.createPredicateValidator((email) -> emailValidator.isValid((String) email), "Email is not valid"));
		validation.registerValidator(customerIDTF, Validator.createRegexValidator("Customer ID is Required", CpsRegEx.IntegerBetweenMinAndMaxLength, Severity.ERROR));
		validation.registerValidator(liscencePlateTF, Validator.createRegexValidator("Liscence plate is Required", CpsRegEx.LicencePlateLength, Severity.ERROR));
		validation.registerValidator(startingDateTF, Validator.createEmptyValidator("Starting date is Required"));
	}

	@FXML // fx:id="EmailLBL"
	private Label EmailLBL; // Value injected by FXMLLoader

	@FXML // fx:id="OrderOneTimeParkingBTN"
	private Button OrderFullMonthlySubscriptionBTN; // Value injected by FXMLLoader

	@FXML // fx:id="LiscencePlateLBL"
	private Label LiscencePlateLBL; // Value injected by FXMLLoader

	@FXML // fx:id="liscencePlateTF"
	private LicencePlateTextField liscencePlateTF; // Value injected by FXMLLoader

	@FXML // fx:id="customerIDTF"
	private NumberTextField customerIDTF; // Value injected by FXMLLoader

	@FXML // fx:id="startingDateLBL"
	private Label startingDateLBL; // Value injected by FXMLLoader

	@FXML // fx:id="customerIDLBL"
	private Label customerIDLBL; // Value injected by FXMLLoader

	@FXML // fx:id="emailTF"
	private TextField emailTF; // Value injected by FXMLLoader

	@FXML // fx:id="startingDateTF"
	private DatePicker startingDateTF; // Value injected by FXMLLoader

	@FXML // fx:id="orderFullMonthlyLBL"
	private Label orderFullMonthlyLBL; // Value injected by FXMLLoader

	@FXML
	public void OrderFullMonthlySubscription(ActionEvent event) {
		model.SendFullMonthlySubcriptionRequestToServer(
				customerIDTF.getNumber(),
				liscencePlateTF.getText(),
				emailTF.getText(),
				Date.from(startingDateTF.getValue().atStartOfDay(ZoneOffset.UTC).toInstant()));
	}
}
