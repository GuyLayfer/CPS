
package webGui.controllers;

import org.controlsfx.validation.Severity;
import org.controlsfx.validation.ValidationSupport;
import org.controlsfx.validation.Validator;

import core.guiUtilities.CpsRegEx;
import core.guiUtilities.NumberTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import webGui.models.OpenComplaintModel;

// TODO: Auto-generated Javadoc
/**
 * The Class OpenComplaintController.
 */
public class OpenComplaintController {
	
	/** The model. */
	private OpenComplaintModel model;
	
	/** The validation. */
	private ValidationSupport validation = new ValidationSupport();

	/**
	 * Instantiates a new open complaint controller.
	 */
	public OpenComplaintController() {
		model = new OpenComplaintModel();
	}

	/**
	 * Initialize.
	 */
	@FXML
	protected void initialize() {
		openComplaintBT.disableProperty().bind(validation.invalidProperty());
		validation.registerValidator(openComplaintTextArea, Validator.createEmptyValidator("Complaint content is Required"));
		validation.registerValidator(customerIfTextField, Validator.createRegexValidator("Customer ID is Required", CpsRegEx.IntegerBetweenMinAndMaxLength, Severity.ERROR));
	}

	/** The open complaint text area. */
	@FXML
	private TextArea openComplaintTextArea;

	/** The open complaint BT. */
	@FXML
	private Button openComplaintBT;

	/** The customer if text field. */
	@FXML
	private NumberTextField customerIfTextField;

	/**
	 * Open complaint T.
	 *
	 * @param event the event
	 */
	@FXML
	void openComplaintT(ActionEvent event) {
		model.SendOpenComplaintRequestToServer(openComplaintTextArea.getText(), customerIfTextField.getNumber());
	}
}
