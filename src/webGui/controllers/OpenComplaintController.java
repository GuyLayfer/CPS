
package webGui.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import webGui.models.OpenComplaintModel;
import webGui.util.ServerMessageHandler;

public class OpenComplaintController implements ServerMessageHandler{
	private OpenComplaintModel model;
	
	public OpenComplaintController() {
		model = new OpenComplaintModel(this);
	}

    @FXML // fx:id="openComplaintTF"
    private TextField openComplaintTF; // Value injected by FXMLLoader

    @FXML // fx:id="openComplaintBT"
    private Button openComplaintBT; // Value injected by FXMLLoader

    @FXML
    void openComplaintT(ActionEvent event) {
		model.SendOpenComplaintRequestToServer();


    }
    
    @Override
   	public void handleServerMessage(String msg) {
    	openComplaintTF.setText(msg);
   	}

}
