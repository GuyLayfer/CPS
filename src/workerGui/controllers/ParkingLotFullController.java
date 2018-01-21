package workerGui.controllers;

import org.controlsfx.validation.ValidationSupport;
import org.controlsfx.validation.Validator;

import core.guiUtilities.IServerResponseHandler;
import core.worker.WorkerOperations;
import core.worker.WorkerRequestType;
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

public class ParkingLotFullController implements IServerResponseHandler<WorkerBaseResponse> {
	private ValidationSupport validation = new ValidationSupport();
	private WorkerConnectionManager connectionManager;
	private WorkerAccountManager workerAccountManager;

	public ParkingLotFullController() {
		connectionManager = WorkerConnectionManager.getInstance();
		connectionManager.addServerMessageListener(this);
		workerAccountManager = WorkerAccountManager.getInstance();
	}

	@FXML
	protected void initialize() {
		SetParkingLotIsFullButton.disableProperty().bind(validation.invalidProperty());
		UnsetParkingLotIsFullButton.disableProperty().bind(validation.invalidProperty());
		validation.registerValidator(ParkingLotId, Validator.createEmptyValidator("Parking lot ID is Required"));
		if (workerAccountManager.isOperationAllowed(WorkerOperations.CHANGE_PARKING_LOT)) {
			connectionManager.sendMessageToServer(WorkerRequestsFactory.CreateParkingLotNamesRequest());
			ParkingLotId.setDisable(false);
		} else {
			ParkingLotId.getItems().add(workerAccountManager.getWorkerlotId());
			ParkingLotId.setValue(workerAccountManager.getWorkerlotId());
			ParkingLotId.setDisable(true);
		}
	}

	@FXML
	private Button SetParkingLotIsFullButton;

	@FXML
	private Button UnsetParkingLotIsFullButton;

	@FXML
	private ComboBox<Integer> ParkingLotId;

	@FXML
	public void SetParkingLotIsFull(ActionEvent event) {
		SendRequest(true);
	}

	@FXML
	public void UnsetParkingLotIsFull(ActionEvent event) {
		SendRequest(false);
	}

	private void SendRequest(Boolean setFull) {
		BaseRequest request = WorkerRequestsFactory.CreateParkingLotFullRequest(setFull, ParkingLotId.getValue());
		connectionManager.sendMessageToServer(request);
	}

	@Override
	public void handleServerResponse(WorkerBaseResponse response) {
		if (response.requestType == WorkerRequestType.PARKING_LOT_NAMES) {
			Platform.runLater(() -> {
				ParkingLotsNamesResponse parkingLotNames = (ParkingLotsNamesResponse) response;
				ParkingLotId.getItems().clear();
				ParkingLotId.getItems().addAll(parkingLotNames.lotNames);
				if (!parkingLotNames.lotNames.isEmpty()) {
					ParkingLotId.setValue(parkingLotNames.lotNames.get(0));
				}
			});
		}
	}
}
