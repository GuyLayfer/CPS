
package webGui.controllers;

import org.controlsfx.validation.ValidationSupport;
import org.controlsfx.validation.Validator;

import core.guiUtilities.ServerMessageHandler;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import webGui.models.OpenComplaintModel;

public class OpenComplaintController implements ServerMessageHandler{
	private OpenComplaintModel model;
	private ValidationSupport validation = new ValidationSupport();
	
	public OpenComplaintController() {
		model = new OpenComplaintModel(this);
	}
	
	@FXML
	protected void initialize() {
		openComplaintBT.disableProperty().bind(validation.invalidProperty());
		validation.registerValidator(openComplaintTF, Validator.createEmptyValidator(" is Required"));
	}

    @FXML // fx:id="openComplaintTF"
    private TextField openComplaintTF; // Value injected by FXMLLoader

    @FXML // fx:id="openComplaintBT"
    private Button openComplaintBT; // Value injected by FXMLLoader

    @FXML
    void openComplaintT(ActionEvent event) {
		model.SendOpenComplaintRequestToServer(openComplaintTF.getText());
    }
    
    @Override
   	public void handleServerMessage(String msg) {

    }
}
