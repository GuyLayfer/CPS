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
		validation.registerValidator(ParkingLotId, Validator.createRegexValidator("Parking Lot ID is Required", CpsRegEx.IntegerBetweenMinAndMaxLength, Severity.ERROR));
		validation.registerValidator(LotRowComboBox, Validator.createEmptyValidator("Lot row size is Required"));
		SetRowComboBoxItems();
	}

	@FXML
	private Button InitializeParkingLotButton;

	@FXML
	private ComboBox<Integer> LotRowComboBox;

	@FXML
	private NumberTextField ParkingLotId;

	@FXML
	void InitializeParkingLot(ActionEvent event) {
		BaseRequest request = WorkerRequestsFactory.CreateInitializeParkingLotRequest(ParkingLotId.getNumber(), LotRowComboBox.getValue());
		connectionManager.sendMessageToServer(request);
	}

	private void SetRowComboBoxItems() {
		LotRowComboBox.getItems().addAll(4, 5, 6, 7, 8);
	}
}
