package workerGui.controllers;

import org.controlsfx.validation.Severity;
import org.controlsfx.validation.ValidationSupport;
import org.controlsfx.validation.Validator;

import core.guiUtilities.CpsRegEx;
import core.guiUtilities.FloatNumberTextField;
import core.guiUtilities.IServerResponseHandler;
import core.worker.WorkerRequestType;
import core.worker.WorkerRole;
import core.worker.requests.BaseRequest;
import core.worker.responses.ParkingLotsNamesResponse;
import core.worker.responses.WorkerBaseResponse;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import workerGui.util.WorkerAccountManager;
import workerGui.util.WorkerConnectionManager;
import workerGui.util.WorkerRequestsFactory;

public class UpdateRatesRequestController implements IServerResponseHandler<WorkerBaseResponse>{
	private ValidationSupport validation = new ValidationSupport();
	private WorkerConnectionManager connectionManager;
	private WorkerAccountManager workerAccountManager;

	public UpdateRatesRequestController() {
		connectionManager = WorkerConnectionManager.getInstance();
		connectionManager.addServerMessageListener(this);
		workerAccountManager = WorkerAccountManager.getInstance();
	}

	@FXML
	protected void initialize() {
		orderButton.disableProperty().bind(validation.invalidProperty());
		validation.registerValidator(ParkingLotIdField, Validator.createEmptyValidator("Parking lot ID is Required"));
		validation.registerValidator(RutineMonthlyField, Validator.createRegexValidator("Rate is Required", CpsRegEx.FloatNumber, Severity.ERROR));
		validation.registerValidator(OccasionalRateField, Validator.createRegexValidator("Rate is Required", CpsRegEx.FloatNumber, Severity.ERROR));
		validation.registerValidator(RutineMonthlyMultipleField, Validator.createRegexValidator("Rate is Required", CpsRegEx.FloatNumber, Severity.ERROR));
		validation.registerValidator(FullNonthlyField, Validator.createRegexValidator("Rate is Required", CpsRegEx.FloatNumber, Severity.ERROR));
		validation.registerValidator(PreOrderedField, Validator.createRegexValidator("Rate is Required", CpsRegEx.FloatNumber, Severity.ERROR));
		setParkingLotComboBox();
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
	private void SendRatesUpdateRequest(ActionEvent event) {
		BaseRequest request = WorkerRequestsFactory.CreateUpdateRatesRequest(
				ParkingLotIdField.getValue(),
				OccasionalRateField.getFloat(),
				PreOrderedField.getFloat(),
				RutineMonthlyField.getFloat(),
				RutineMonthlyMultipleField.getFloat(),
				FullNonthlyField.getFloat());
		connectionManager.sendMessageToServer(request);
	}

	private void setParkingLotComboBox() {
		if (workerAccountManager.getWorkerRole() == WorkerRole.FIRM_MANAGER) {
			connectionManager.sendMessageToServer(WorkerRequestsFactory.CreateParkingLotNamesRequest());
			ParkingLotIdField.setDisable(false);
		} else {
			ParkingLotIdField.getItems().add(workerAccountManager.getWorkerlotId());
			ParkingLotIdField.setValue(workerAccountManager.getWorkerlotId());
			ParkingLotIdField.setDisable(true);
		}
	}

	@Override
	public void handleServerResponse(WorkerBaseResponse response) {
		if (response.requestType == WorkerRequestType.PARKING_LOT_NAMES) {
			Platform.runLater(() -> {
				ParkingLotsNamesResponse parkingLotNames = (ParkingLotsNamesResponse) response;
				ParkingLotIdField.getItems().clear();
				ParkingLotIdField.getItems().addAll(parkingLotNames.lotNames);
				if (!parkingLotNames.lotNames.isEmpty()) {
					ParkingLotIdField.setValue(parkingLotNames.lotNames.get(0));
				}
			});
		}
	}
}
