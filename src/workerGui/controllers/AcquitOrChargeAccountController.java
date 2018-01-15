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
import javafx.scene.control.ComboBox;
import workerGui.util.WorkerConnectionManager;
import workerGui.util.WorkerRequestsFactory;

public class AcquitOrChargeAccountController {
	private ValidationSupport validation = new ValidationSupport();
	private WorkerConnectionManager connectionManager;
	private final String acquit = "Acquit";
	private final String charge = "Charge";

	public AcquitOrChargeAccountController() {
		connectionManager = WorkerConnectionManager.getInstance();
	}

	@FXML
	protected void initialize() {
		submitButton.setDisable(true);
		submitButton.disableProperty().bind(validation.invalidProperty());
		validation.registerValidator(AccountIdField, Validator.createRegexValidator("Account ID is Required", CpsRegEx.IntegerBetweenMinAndMaxLength, Severity.ERROR));
		validation.registerValidator(amountField, Validator.createRegexValidator("Field Amount is Required", CpsRegEx.FloatNumber, Severity.ERROR));
		validation.registerValidator(AcquitOrChargeComboBox, Validator.createEmptyValidator("Choose option"));
		AcquitOrChargeComboBox.getItems().addAll(acquit, charge);
	}

	@FXML
	private FloatNumberTextField amountField;

	@FXML
	private NumberTextField AccountIdField;

	@FXML
	private Button submitButton;

	@FXML
	private ComboBox<String> AcquitOrChargeComboBox;

	@FXML
	void SendTransaction(ActionEvent event) {
		double amount = (AcquitOrChargeComboBox.getValue() == acquit) ? amountField.getFloat() : -1 * amountField.getFloat();
		BaseRequest request = WorkerRequestsFactory.CreateAcquitOrChargeAccountRequest(AccountIdField.getNumber(), amount);
		connectionManager.sendMessageToServer(request);
	}
}
