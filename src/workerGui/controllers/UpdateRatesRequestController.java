package workerGui.controllers;

import workerGui.util.WorkerGuiController;

import core.guiUtilities.FloatNumberTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;

public class UpdateRatesRequestController extends WorkerGuiController {

	@FXML
	protected void initialize() {
		
	}

	@FXML
	private FloatNumberTextField RutineMonthlyField;

	@FXML
	private FloatNumberTextField OccasionalRateField;

	@FXML
	private FloatNumberTextField RutineMonthlyMultipleField;

	@FXML
	private FloatNumberTextField FullNonthlyField;

	@FXML
	private FloatNumberTextField CarIdField;

	@FXML
	private AnchorPane PreOrderedField;

	@FXML
	private Button orderBTN;

	@FXML
	void SendRatesUpdateRequest(ActionEvent event) {

	}
}
