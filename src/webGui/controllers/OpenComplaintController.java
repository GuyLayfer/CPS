
package webGui.controllers;

import org.controlsfx.validation.Severity;
import org.controlsfx.validation.ValidationSupport;
import org.controlsfx.validation.Validator;

import core.guiUtilities.CpsRegEx;
import core.guiUtilities.NumberTextField;
import core.guiUtilities.ServerMessageHandler;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import webGui.models.OpenComplaintModel;

public class OpenComplaintController implements ServerMessageHandler {
	private OpenComplaintModel model;
	private ValidationSupport validation = new ValidationSupport();

	public OpenComplaintController() {
		model = new OpenComplaintModel(this);
	}

	@FXML
	protected void initialize() {
		openComplaintBT.disableProperty().bind(validation.invalidProperty());
		validation.registerValidator(openComplaintTextArea, Validator.createEmptyValidator("Complaint content is Required"));
		validation.registerValidator(customerIfTextField, Validator.createRegexValidator("Customer ID is Required", CpsRegEx.IntegerBetweenMinAndMaxLength, Severity.ERROR));
	}

	@FXML
	private TextArea openComplaintTextArea;

	@FXML
	private Button openComplaintBT;

	@FXML
	private NumberTextField customerIfTextField;

	@FXML
	void openComplaintT(ActionEvent event) {
		model.SendOpenComplaintRequestToServer(openComplaintTextArea.getText(), customerIfTextField.getNumber());
	}

	@Override
	public void handleServerMessage(String msg) {

	}
}
