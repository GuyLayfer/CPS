package workerGui.controllers;

import core.guiUtilities.FloatNumberTextField;
import core.guiUtilities.NumberTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class AcquitOrChargeAccountController {

	@FXML
	private FloatNumberTextField chargeAmountField;

	@FXML
	private NumberTextField AccountIdField;

	@FXML
	private Button submitButton;

	@FXML
	private FloatNumberTextField AcquitAmountField;

	@FXML
	void SendTransaction(ActionEvent event) {

	}
}
