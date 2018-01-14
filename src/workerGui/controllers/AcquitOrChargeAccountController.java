package workerGui.controllers;

import org.controlsfx.validation.Severity;
import org.controlsfx.validation.ValidationSupport;
import org.controlsfx.validation.Validator;

import core.guiUtilities.CpsRegEx;
import core.guiUtilities.FloatNumberTextField;
import core.guiUtilities.NumberTextField;
import core.worker.requests.BaseRequest;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import workerGui.util.WorkerConnectionManager;
import workerGui.util.WorkerRequestsFactory;

public class AcquitOrChargeAccountController {
	private ValidationSupport validation = new ValidationSupport();
	private WorkerConnectionManager connectionManager;

	public AcquitOrChargeAccountController() {
		connectionManager = WorkerConnectionManager.getInstance();
	}

	@FXML
	protected void initialize() {
		submitButton.disableProperty().bind(validation.invalidProperty());
		validation.registerValidator(AccountIdField, Validator.createRegexValidator("Account ID is Required", CpsRegEx.IntegerBetweenMinAndMaxLength, Severity.ERROR));
		validation.registerValidator(AcquitAmountField, Validator.createPredicateValidator((acquitAmount) -> {
			return !((String) acquitAmount).isEmpty() || !chargeAmountField.getText().isEmpty();
		}, "Charge amount is Required"));
		validation.registerValidator(chargeAmountField, Validator.createPredicateValidator((chargeAmount) -> {
			return !((String) chargeAmount).isEmpty() || !AcquitAmountField.getText().isEmpty();
		}, "Acquit amount is Required"));
		validation.registerValidator(AcquitAmountField, Validator.createPredicateValidator((acquitAmount) -> isOnlyOneFieldFilled(), "Only one field is Required"));
		validation.registerValidator(chargeAmountField, Validator.createPredicateValidator((chargeAmount) -> isOnlyOneFieldFilled(), "Only one field is Required"));
	}

	@FXML
	private FloatNumberTextField chargeAmountField;

	@FXML
	private NumberTextField AccountIdField;

	@FXML
	private Button submitButton;

	@FXML
	private FloatNumberTextField AcquitAmountField;

	@FXML
	void SendTransaction(ActionEvent event) {
		double amount = AcquitAmountField.getText().isEmpty() ? chargeAmountField.getFloat() : AcquitAmountField.getFloat();
		BaseRequest request = WorkerRequestsFactory.CreateAcquitOrChargeAccountRequest(AccountIdField.getNumber(), amount);
		connectionManager.sendMessageToServer(request);
	}

	private Boolean isOnlyOneFieldFilled() {
		return (AcquitAmountField.getText().isEmpty() ? 0 : 1)
				+ (chargeAmountField.getText().isEmpty() ? 0 : 1)
				== 1;
	}
}
