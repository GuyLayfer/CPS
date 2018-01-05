package workerGui.controllers;

import workerGui.util.WorkerGuiController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;

public class SetOutOfOrderParkingSpaceController extends WorkerGuiController {

	@FXML
	protected void initialize() {
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
