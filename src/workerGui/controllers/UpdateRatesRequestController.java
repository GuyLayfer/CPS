package workerGui.controllers;

import org.controlsfx.validation.Severity;
import org.controlsfx.validation.ValidationSupport;
import org.controlsfx.validation.Validator;

import core.guiUtilities.CpsRegEx;
import core.guiUtilities.FloatNumberTextField;
import core.worker.requests.BaseRequest;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import workerGui.util.WorkerConnectionManager;
import workerGui.util.WorkerRequestsFactory;

public class UpdateRatesRequestController {
	private ValidationSupport validation = new ValidationSupport();
	private WorkerConnectionManager connectionManager;

	public UpdateRatesRequestController() {
		connectionManager = WorkerConnectionManager.getInstance();
	}

	@FXML
	protected void initialize() {
		orderButton.disableProperty().bind(validation.invalidProperty());
		validation.registerValidator(ParkingLotIdField, Validator.createRegexValidator("Parking lot ID is Required", CpsRegEx.IntegerBetweenMinAndMaxLength, Severity.ERROR));
		validation.registerValidator(RutineMonthlyField, Validator.createRegexValidator("Rate is Required", CpsRegEx.FloatNumber, Severity.ERROR));
		validation.registerValidator(OccasionalRateField, Validator.createRegexValidator("Rate is Required", CpsRegEx.FloatNumber, Severity.ERROR));
		validation.registerValidator(RutineMonthlyMultipleField, Validator.createRegexValidator("Rate is Required", CpsRegEx.FloatNumber, Severity.ERROR));
		validation.registerValidator(FullNonthlyField, Validator.createRegexValidator("Rate is Required", CpsRegEx.FloatNumber, Severity.ERROR));
		validation.registerValidator(PreOrderedField, Validator.createRegexValidator("Rate is Required", CpsRegEx.FloatNumber, Severity.ERROR));
	}
	
	@FXML
	private FloatNumberTextField RutineMonthlyField;

	@FXML
	private FloatNumberTextField OccasionalRateField;

	@FXML
	private FloatNumberTextField RutineMonthlyMultipleField;

	@FXML
	private ComboBox<Integer> ParkingLotIdField;

	@FXML
	private FloatNumberTextField FullNonthlyField;

	@FXML
	private FloatNumberTextField PreOrderedField;

	@FXML
	private Button orderButton;

	@FXML
	void SendRatesUpdateRequest(ActionEvent event) {
		BaseRequest request = WorkerRequestsFactory.CreateUpdateRatesRequest(
				ParkingLotIdField.getValue(),
				OccasionalRateField.getFloat(),
				PreOrderedField.getFloat(),
				RutineMonthlyField.getFloat(),
				RutineMonthlyMultipleField.getFloat(),
				FullNonthlyField.getFloat());
		connectionManager.sendMessageToServer(request);
	}
}
