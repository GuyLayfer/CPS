/**
 * Sample Skeleton for 'MockTrackOrderStatusView.fxml' Controller Class
 */

package webGui.controllers;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import webGui.models.MockTrackOrderStatusModel;
import webGui.util.ServerMessageHandler;

public class MockTrackOrderStatusController implements ServerMessageHandler{
	private MockTrackOrderStatusModel model;
	
	public MockTrackOrderStatusController() {
		model = new MockTrackOrderStatusModel(this);
	}

    @FXML // fx:id="trackOrderStatusBTN"
    private Button trackOrderStatusBTN; // Value injected by FXMLLoader

    @FXML // fx:id="orderIDTF"
    private TextField orderIDTF; // Value injected by FXMLLoader

    @FXML
    void TrackOrderStatus(ActionEvent event) {
    	model.SendTrackOrderStatusRequestToServer(orderIDTF.getText());

    }
    
    @Override
	public void handleServerMessage(String msg) {
		// Print the message from the server to the UI example: 
    	orderIDTF.setText(msg);
	}

}

