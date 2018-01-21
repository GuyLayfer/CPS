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

// TODO: Auto-generated Javadoc
/**
 * The Class AcquitOrChargeAccountController.
 */
public class AcquitOrChargeAccountController {
	
	/** The validation. */
	private ValidationSupport validation = new ValidationSupport();
	
	/** The connection manager. */
	private WorkerConnectionManager connectionManager;
	
	/** The acquit. */
	private final String acquit = "Acquit";
	
	/** The charge. */
	private final String charge = "Charge";

	/**
	 * Instantiates a new acquit or charge account controller.
	 */
	public AcquitOrChargeAccountController() {
		connectionManager = WorkerConnectionManager.getInstance();
	}

	/**
	 * Initialize.
	 */
	@FXML
	protected void initialize() {
		submitButton.setDisable(true);
		submitButton.disableProperty().bind(validation.invalidProperty());
		validation.registerValidator(AccountIdField, Validator.createRegexValidator("Account ID is Required", CpsRegEx.IntegerBetweenMinAndMaxLength, Severity.ERROR));
		validation.registerValidator(amountField, Validator.createRegexValidator("Field Amount is Required", CpsRegEx.FloatNumber, Severity.ERROR));
		validation.registerValidator(AcquitOrChargeComboBox, Validator.createEmptyValidator("Choose option"));
		AcquitOrChargeComboBox.getItems().addAll(acquit, charge);
	}

	/** The amount field. */
	@FXML
	private FloatNumberTextField amountField;

	/** The Account id field. */
	@FXML
	private NumberTextField AccountIdField;

	/** The submit button. */
	@FXML
	private Button submitButton;

	/** The Acquit or charge combo box. */
	@FXML
	private ComboBox<String> AcquitOrChargeComboBox;

	/**
	 * Send transaction.
	 *
	 * @param event the event
	 */
	@FXML
	void SendTransaction(ActionEvent event) {
		double amount = (AcquitOrChargeComboBox.getValue() == acquit) ? amountField.getFloat() : -1 * amountField.getFloat();
		BaseRequest request = WorkerRequestsFactory.CreateAcquitOrChargeAccountRequest(AccountIdField.getNumber(), amount);
		connectionManager.sendMessageToServer(request);
	}
}
