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

public class CancelCustomerOrderController {
	private ValidationSupport validation = new ValidationSupport();
	private WorkerConnectionManager connectionManager;

	public CancelCustomerOrderController() {
		connectionManager = WorkerConnectionManager.getInstance();
	}

	@FXML
	protected void initialize() {
		CancelOrderButton.disableProperty().bind(validation.invalidProperty());
		validation.registerValidator(OrderIdField, Validator.createRegexValidator("Order ID is Required", CpsRegEx.IntegerBetweenMinAndMaxLength, Severity.ERROR));
	}

	@FXML
	private NumberTextField OrderIdField;

	@FXML
	private Button CancelOrderButton;

	@FXML
	void CancelOrder(ActionEvent event) {
		BaseRequest request = WorkerRequestsFactory.CreateCancelCustomerOrderRequest(OrderIdField.getNumber());
		connectionManager.sendMessageToServer(request);
	}
}
