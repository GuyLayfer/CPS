
package webGui.controllers;

import java.time.ZoneId;
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

// TODO: Auto-generated Javadoc
/**
 * The Class OrderFullMonthlySubscriptionController.
 */
public class OrderFullMonthlySubscriptionController {
	
	/** The model. */
	private OrderFullMonthlySubscriptionModel model;
	
	/** The validation. */
	private ValidationSupport validation = new ValidationSupport();
	
	/** The email validator. */
	private EmailValidator emailValidator = EmailValidator.getInstance();

	/**
	 * Instantiates a new order full monthly subscription controller.
	 */
	public OrderFullMonthlySubscriptionController() {
		model = new OrderFullMonthlySubscriptionModel();
	}

	/**
	 * Initialize.
	 */
	@FXML
	protected void initialize() {
		OrderFullMonthlySubscriptionBTN.disableProperty().bind(validation.invalidProperty());
		validation.registerValidator(emailTF, Validator.createPredicateValidator((email) -> emailValidator.isValid((String) email), "Email is not valid"));
		validation.registerValidator(customerIDTF, Validator.createRegexValidator("Customer ID is Required", CpsRegEx.IntegerBetweenMinAndMaxLength, Severity.ERROR));
		validation.registerValidator(liscencePlateTF, Validator.createRegexValidator("Liscence plate is Required", CpsRegEx.LicencePlateLength, Severity.ERROR));
		validation.registerValidator(startingDateTF, Validator.createEmptyValidator("Starting date is Required"));
	}

	/** The Email LBL. */
	@FXML // fx:id="EmailLBL"
	private Label EmailLBL; // Value injected by FXMLLoader

	/** The Order full monthly subscription BTN. */
	@FXML // fx:id="OrderOneTimeParkingBTN"
	private Button OrderFullMonthlySubscriptionBTN; // Value injected by FXMLLoader

	/** The Liscence plate LBL. */
	@FXML // fx:id="LiscencePlateLBL"
	private Label LiscencePlateLBL; // Value injected by FXMLLoader

	/** The liscence plate TF. */
	@FXML // fx:id="liscencePlateTF"
	private LicencePlateTextField liscencePlateTF; // Value injected by FXMLLoader

	/** The customer IDTF. */
	@FXML // fx:id="customerIDTF"
	private NumberTextField customerIDTF; // Value injected by FXMLLoader

	/** The starting date LBL. */
	@FXML // fx:id="startingDateLBL"
	private Label startingDateLBL; // Value injected by FXMLLoader

	/** The customer IDLBL. */
	@FXML // fx:id="customerIDLBL"
	private Label customerIDLBL; // Value injected by FXMLLoader

	/** The email TF. */
	@FXML // fx:id="emailTF"
	private TextField emailTF; // Value injected by FXMLLoader

	/** The starting date TF. */
	@FXML // fx:id="startingDateTF"
	private DatePicker startingDateTF; // Value injected by FXMLLoader

	/** The order full monthly LBL. */
	@FXML // fx:id="orderFullMonthlyLBL"
	private Label orderFullMonthlyLBL; // Value injected by FXMLLoader

	/**
	 * Order full monthly subscription.
	 *
	 * @param event the event
	 */
	@FXML
	public void OrderFullMonthlySubscription(ActionEvent event) {
		model.SendFullMonthlySubcriptionRequestToServer(
				customerIDTF.getNumber(),
				liscencePlateTF.getText(),
				emailTF.getText(),
				Date.from(startingDateTF.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant()));
	}
}
