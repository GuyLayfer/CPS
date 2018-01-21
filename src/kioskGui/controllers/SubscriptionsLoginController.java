package kioskGui.controllers;

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
import kioskGui.util.KioskConnectionManager;
import kioskGui.util.KioskRequestsFactory;

// TODO: Auto-generated Javadoc
/**
 * The Class SubscriptionsLoginController.
 */
public class SubscriptionsLoginController {
	
	/** The validation. */
	private ValidationSupport validation = new ValidationSupport();
	
	/** The connection manager. */
	private KioskConnectionManager connectionManager;

	/**
	 * Instantiates a new subscriptions login controller.
	 */
	public SubscriptionsLoginController() {
		connectionManager = KioskConnectionManager.getInstance();
	}

	/**
	 * Initialize.
	 */
	@FXML
	protected void initialize() {
		submitBTN.disableProperty().bind(validation.invalidProperty());
		validation.registerValidator(CarIdField, Validator.createRegexValidator("Liscence plate is Required", CpsRegEx.LicencePlateLength, Severity.ERROR));
		validation.registerValidator(SubscriptionIdField, Validator.createRegexValidator("Subscription ID is Required", CpsRegEx.IntegerBetweenMinAndMaxLength, Severity.ERROR));
	}

	/** The Subscription id field. */
	@FXML
	private NumberTextField SubscriptionIdField;

	/** The Car id field. */
	@FXML
	private LicencePlateTextField CarIdField;

	/** The submit BTN. */
	@FXML
	private Button submitBTN;

	/**
	 * Submit subscription login request.
	 *
	 * @param event the event
	 */
	@FXML
	void SubmitSubscriptionLoginRequest(ActionEvent event) {
		CustomerRequest subscriberEntranceRequest = KioskRequestsFactory.CreateSubscriberEntranceRequest(CarIdField.getText(), SubscriptionIdField.getNumber());
		connectionManager.sendMessageToServer(subscriberEntranceRequest);
	}
}
