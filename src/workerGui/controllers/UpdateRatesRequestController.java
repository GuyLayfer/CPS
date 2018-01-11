package workerGui.controllers;

import core.guiUtilities.FloatNumberTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;

public class UpdateRatesRequestController {

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
	void SendRatesUpdateRequest(ActionEvent event) {

	}
}
