package workerGui.controllers;

import org.controlsfx.validation.Severity;
import org.controlsfx.validation.ValidationSupport;
import org.controlsfx.validation.Validator;

import core.guiUtilities.CpsRegEx;
import core.guiUtilities.NumberTextField;
import core.worker.requests.BaseRequest;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import workerGui.util.WorkerConnectionManager;
import workerGui.util.WorkerRequestsFactory;

// TODO: Auto-generated Javadoc
/**
 * The Class CancelCustomerOrderController.
 */
public class CancelCustomerOrderController {
	
	/** The validation. */
	private ValidationSupport validation = new ValidationSupport();
	
	/** The connection manager. */
	private WorkerConnectionManager connectionManager;

	/**
	 * Instantiates a new cancel customer order controller.
	 */
	public CancelCustomerOrderController() {
		connectionManager = WorkerConnectionManager.getInstance();
	}

	/**
	 * Initialize.
	 */
	@FXML
	protected void initialize() {
		CancelOrderButton.disableProperty().bind(validation.invalidProperty());
		validation.registerValidator(OrderIdField, Validator.createRegexValidator("Order ID is Required", CpsRegEx.IntegerBetweenMinAndMaxLength, Severity.ERROR));
	}

	/** The Order id field. */
	@FXML
	private NumberTextField OrderIdField;

	/** The Cancel order button. */
	@FXML
	private Button CancelOrderButton;

	/**
	 * Cancel order.
	 *
	 * @param event the event
	 */
	@FXML
	void CancelOrder(ActionEvent event) {
		BaseRequest request = WorkerRequestsFactory.CreateCancelCustomerOrderRequest(OrderIdField.getNumber());
		connectionManager.sendMessageToServer(request);
	}
}
