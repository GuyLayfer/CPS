package workerGui.controllers;

import workerGui.util.WorkerAccountManager;
import workerGui.util.WorkerConnectionManager;
import workerGui.util.WorkerGuiController;
import workerGui.util.WorkerRequestsFactory;

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

public class SetOutOfOrderParkingSpaceController extends WorkerGuiController implements IServerResponseHandler<WorkerBaseResponse>{
	private ValidationSupport validation = new ValidationSupport();
	private WorkerConnectionManager connectionManager;
	private WorkerAccountManager workerAccountManager;

	public SetOutOfOrderParkingSpaceController() {
		connectionManager = WorkerConnectionManager.getInstance();
		connectionManager.addServerMessageListener(this);
		workerAccountManager = WorkerAccountManager.getInstance();
	}

	@FXML
	protected void initialize() {
		SetOutOfOrderButton.disableProperty().bind(validation.invalidProperty());
		validation.registerValidator(ParkingLotId, Validator.createEmptyValidator("Parking lot ID is Required"));
		validation.registerValidator(LotColumnComboBox, Validator.createEmptyValidator("Lot space column is Required"));
		validation.registerValidator(LotFloorComboBox, Validator.createEmptyValidator("Lot space floor is Required"));
		validation.registerValidator(LotRowComboBox, Validator.createEmptyValidator("Lot space row is Required"));
		SetParkingLotIdItems();
		SetLotColumnComboBoxItems();
		SetLotFloorComboBoxItems();
		SetLotRowComboBoxItems();
	}

	@FXML
	private ComboBox<Integer> ParkingLotId;

	@FXML
	private ComboBox<Integer> LotColumnComboBox;

	@FXML
	private ComboBox<Integer> LotFloorComboBox;

	@FXML
	private ComboBox<Integer> LotRowComboBox;

	@FXML
	private Button SetOutOfOrderButton;

	@FXML
	private Button UnsetOutOfOrderButton;

	@FXML
	private void setOutOfOrderParkingSpace(ActionEvent event) {
		sendOutOfOrderRequest(true);
	}
	
	@FXML
	private void unsetOutOfOrderParkingSpace(ActionEvent event) {
		sendOutOfOrderRequest(false);
	}
	
	private void sendOutOfOrderRequest(Boolean isOutOfOrder) {
		BaseRequest request = WorkerRequestsFactory.CreateOutOfOrderRequest(
				ParkingLotId.getValue(),
				LotRowComboBox.getValue(),
				LotColumnComboBox.getValue(),
				LotFloorComboBox.getValue(),
				isOutOfOrder);
		connectionManager.sendMessageToServer(request);
	}

	private void SetParkingLotIdItems() {
		if (workerAccountManager.isOperationAllowed(WorkerOperations.CHANGE_PARKING_LOT)) {
			connectionManager.sendMessageToServer(WorkerRequestsFactory.CreateParkingLotNamesRequest());
			ParkingLotId.setDisable(false);
		} else {
			ParkingLotId.getItems().add(workerAccountManager.getWorkerlotId());
			ParkingLotId.setValue(workerAccountManager.getWorkerlotId());
			ParkingLotId.setDisable(true);
		}
	}

	private void SetLotColumnComboBoxItems() {

	}

	private void SetLotFloorComboBoxItems() {
		LotFloorComboBox.getItems().addAll(1, 2, 3);
	}

	private void SetLotRowComboBoxItems() {
		LotRowComboBox.getItems().addAll(1, 2, 3);
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
