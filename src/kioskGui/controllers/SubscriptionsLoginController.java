package kioskGui.controllers;

import org.controlsfx.validation.Severity;
import org.controlsfx.validation.ValidationSupport;
import org.controlsfx.validation.Validator;

import core.guiUtilities.CpsRegEx;
import core.guiUtilities.LicencePlateTextField;
import core.guiUtilities.NumberTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class SubscriptionsLoginController {
	private ValidationSupport validation = new ValidationSupport();

	@FXML
	protected void initialize() {
		submitBTN.disableProperty().bind(validation.invalidProperty());
		validation.registerValidator(CarIdField, Validator.createRegexValidator("Liscence plate is Required", CpsRegEx.LicencePlateLength, Severity.ERROR));
		validation.registerValidator(SubscriptionIdField, Validator.createRegexValidator("Subscription ID is Required", CpsRegEx.OneOrMoreIntegers, Severity.ERROR));
	}

	@FXML
	private NumberTextField SubscriptionIdField;

	@FXML
	private LicencePlateTextField CarIdField;

	@FXML
	private Button submitBTN;
	
	@FXML
	void SubmitSubscriptionLoginRequest(ActionEvent event) {

	}
}
