package workerGui.controllers;

import core.guiUtilities.NumberTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;

public class InitializeParkingLotController {

	@FXML
	protected void initialize() {
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

	}

	private void SetRowComboBoxItems() {
		LotRowComboBox.getItems().addAll(4, 5, 6, 7, 8);
	}
}
