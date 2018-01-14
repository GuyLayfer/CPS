package workerGui.controllers;

import org.controlsfx.validation.ValidationSupport;
import org.controlsfx.validation.Validator;

import core.worker.requests.BaseRequest;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import workerGui.util.WorkerConnectionManager;
import workerGui.util.WorkerRequestsFactory;

public class ParkingLotFullController {
	private ValidationSupport validation = new ValidationSupport();
	private WorkerConnectionManager connectionManager;

	public ParkingLotFullController() {
		connectionManager = WorkerConnectionManager.getInstance();
	}

	@FXML
	protected void initialize() {
		SetParkingLotIsFullButton.disableProperty().bind(validation.invalidProperty());
		UnsetParkingLotIsFullButton.disableProperty().bind(validation.invalidProperty());
		validation.registerValidator(ParkingLotId, Validator.createEmptyValidator("Parking lot ID is Required"));
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
		BaseRequest request = WorkerRequestsFactory.CreateParkingLotFullRequest(setFull);
		connectionManager.sendMessageToServer(request);
	}
}
