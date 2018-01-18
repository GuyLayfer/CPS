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

public class InitializeParkingLotController implements IServerResponseHandler<WorkerBaseResponse>{
	private ValidationSupport validation = new ValidationSupport();
	private WorkerConnectionManager connectionManager;
	private WorkerAccountManager workerAccountManager;

	public InitializeParkingLotController() {
		connectionManager = WorkerConnectionManager.getInstance();
		connectionManager.addServerMessageListener(this);
		workerAccountManager = WorkerAccountManager.getInstance();
	}

	@FXML
	protected void initialize() {
		InitializeParkingLotButton.disableProperty().bind(validation.invalidProperty());
		validation.registerValidator(ParkingLotId, Validator.createEmptyValidator("Parking Lot ID is Required"));
		validation.registerValidator(LotRowComboBox, Validator.createEmptyValidator("Lot row size is Required"));
		setParkingLotIdComboBox();
		SetRowComboBoxItems();
	}

	@FXML
	private Button InitializeParkingLotButton;

	@FXML
	private ComboBox<Integer> LotRowComboBox;

	@FXML
	private ComboBox<Integer> ParkingLotId;

	@FXML
	void InitializeParkingLot(ActionEvent event) {
		BaseRequest request = WorkerRequestsFactory.CreateInitializeParkingLotRequest(ParkingLotId.getValue(), LotRowComboBox.getValue());
		connectionManager.sendMessageToServer(request);
	}

	private void setParkingLotIdComboBox() {
		if (workerAccountManager.isOperationAllowed(WorkerOperations.CHANGE_PARKING_LOT)) {
			connectionManager.sendMessageToServer(WorkerRequestsFactory.CreateParkingLotNamesRequest());
			ParkingLotId.setDisable(false);
		} else {
			ParkingLotId.getItems().add(workerAccountManager.getWorkerlotId());
			ParkingLotId.setValue(workerAccountManager.getWorkerlotId());
			ParkingLotId.setDisable(true);
		}
	}
	
	private void SetRowComboBoxItems() {
		LotRowComboBox.getItems().addAll(4, 5, 6, 7, 8);
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
