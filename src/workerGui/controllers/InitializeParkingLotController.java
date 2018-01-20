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

public class InitializeParkingLotController {
	private ValidationSupport validation = new ValidationSupport();
	private WorkerConnectionManager connectionManager;

	public InitializeParkingLotController() {
		connectionManager = WorkerConnectionManager.getInstance();
	}

	@FXML
	protected void initialize() {
		InitializeParkingLotButton.disableProperty().bind(validation.invalidProperty());
		validation.registerValidator(LotColumnComboBox, Validator.createEmptyValidator("Lot column size is required"));
		SetRowComboBoxItems();
	}

	@FXML
	private Button InitializeParkingLotButton;

	@FXML
	private ComboBox<Integer> LotColumnComboBox;

	@FXML
	void InitializeParkingLot(ActionEvent event) {
		BaseRequest request = WorkerRequestsFactory.CreateInitializeParkingLotRequest(LotColumnComboBox.getValue());
		connectionManager.sendMessageToServer(request);
	}

	private void SetRowComboBoxItems() {
		LotColumnComboBox.getItems().addAll(4, 5, 6, 7, 8);
	}
}
