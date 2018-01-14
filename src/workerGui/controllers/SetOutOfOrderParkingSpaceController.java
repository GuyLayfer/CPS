package workerGui.controllers;

import workerGui.util.WorkerConnectionManager;
import workerGui.util.WorkerGuiController;
import workerGui.util.WorkerRequestsFactory;

import org.controlsfx.validation.ValidationSupport;
import org.controlsfx.validation.Validator;

import core.worker.requests.BaseRequest;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;

public class SetOutOfOrderParkingSpaceController extends WorkerGuiController {
	private ValidationSupport validation = new ValidationSupport();
	private WorkerConnectionManager connectionManager;
	
	public SetOutOfOrderParkingSpaceController() {
		connectionManager = WorkerConnectionManager.getInstance();
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
	void setOutOfOrderParkingSpace(ActionEvent event) {
		BaseRequest request = WorkerRequestsFactory.CreateOutOfOrderRequest(
				ParkingLotId.getValue(),
				LotRowComboBox.getValue(),
				LotColumnComboBox.getValue(),
				LotFloorComboBox.getValue());
		connectionManager.sendMessageToServer(request);
	}

	private void SetParkingLotIdItems() {

	}

	private void SetLotColumnComboBoxItems() {

	}

	private void SetLotFloorComboBoxItems() {
		LotFloorComboBox.getItems().addAll(1, 2, 3);
	}

	private void SetLotRowComboBoxItems() {
		LotRowComboBox.getItems().addAll(1, 2, 3);
	}
}
