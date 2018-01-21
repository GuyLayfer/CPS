
package webGui.controllers;

import org.controlsfx.validation.Severity;
import org.controlsfx.validation.ValidationSupport;
import org.controlsfx.validation.Validator;

import core.guiUtilities.CpsRegEx;
import core.guiUtilities.NumberTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import webGui.models.RenewSubscriptionModel;

// TODO: Auto-generated Javadoc
/**
 * The Class RenewSubscriptionController.
 */
public class RenewSubscriptionController {
	
	/** The model. */
	private RenewSubscriptionModel model;
	
	/** The validation. */
	private ValidationSupport validation = new ValidationSupport();

	/**
	 * Instantiates a new renew subscription controller.
	 */
	public RenewSubscriptionController() {
		model = new RenewSubscriptionModel();
	}

	/**
	 * Initialize.
	 */
	@FXML
	protected void initialize() {
		renewSubscriptionBTN.disableProperty().bind(validation.invalidProperty());
		validation.registerValidator(customerIDTF, Validator.createRegexValidator("Customer ID is Required", CpsRegEx.IntegerBetweenMinAndMaxLength, Severity.ERROR));
		validation.registerValidator(subscriptionIDTF, Validator.createRegexValidator("Subscription ID is Required", CpsRegEx.IntegerBetweenMinAndMaxLength, Severity.ERROR));

	}

	/** The renew subscription BTN. */
	@FXML // fx:id="trackOrderStatusBTN"
	private Button renewSubscriptionBTN; // Value injected by FXMLLoader

	/** The customer IDTF. */
	@FXML // fx:id="customerIDTF"
	private NumberTextField customerIDTF; // Value injected by FXMLLoader

	/** The subscription IDTF. */
	@FXML // fx:id="orderIDTF"
	private NumberTextField subscriptionIDTF; // Value injected by FXMLLoader

	/**
	 * Renew subscription.
	 *
	 * @param event the event
	 */
	@FXML
	public void RenewSubscription(ActionEvent event) {
		model.SendRenewSubscriptionRequestToServer(customerIDTF.getNumber(), subscriptionIDTF.getNumber());
	}
}
