/**
 * Sample Skeleton for 'MockOpenComplaintView.fxml' Controller Class
 */

package webGui.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import webGui.models.MockOpenComplaintModel;
import webGui.util.ServerMessageHandler;

public class MockOpenComplaintController implements ServerMessageHandler{
	private MockOpenComplaintModel model;
	
	public MockOpenComplaintController() {
		model = new MockOpenComplaintModel(this);
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
   		// Print the message from the server to the UI example: 
    	openComplaintTF.setText(msg);
   	}

}
