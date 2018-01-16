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

public class SubscriptionsLoginController {
	private ValidationSupport validation = new ValidationSupport();
	private KioskConnectionManager connectionManager;

	public SubscriptionsLoginController() {
		connectionManager = KioskConnectionManager.getInstance();
	}

	@FXML
	protected void initialize() {
		submitBTN.disableProperty().bind(validation.invalidProperty());
		validation.registerValidator(CarIdField, Validator.createRegexValidator("Liscence plate is Required", CpsRegEx.LicencePlateLength, Severity.ERROR));
		validation.registerValidator(SubscriptionIdField, Validator.createRegexValidator("Subscription ID is Required", CpsRegEx.IntegerBetweenMinAndMaxLength, Severity.ERROR));
	}

	@FXML
	private NumberTextField SubscriptionIdField;

	@FXML
	private LicencePlateTextField CarIdField;

	@FXML
	private Button submitBTN;

	@FXML
	void SubmitSubscriptionLoginRequest(ActionEvent event) {
		CustomerRequest subscriberEntranceRequest = KioskRequestsFactory.CreateSubscriberEntranceRequest(CarIdField.getText(), SubscriptionIdField.getNumber());
		connectionManager.sendMessageToServer(subscriberEntranceRequest);
	}
}
