
package webGui.controllers;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import webGui.models.TrackOrderStatusModel;
import webGui.util.ServerMessageHandler;

public class TrackOrderStatusController implements ServerMessageHandler{
	private TrackOrderStatusModel model;
	
	public TrackOrderStatusController() {
		model = new TrackOrderStatusModel(this);
	}

    @FXML // fx:id="trackOrderStatusBTN"
    private Button trackOrderStatusBTN; // Value injected by FXMLLoader

    @FXML // fx:id="orderIDTF"
    private TextField orderIDTF; // Value injected by FXMLLoader

    @FXML
    void TrackOrderStatus(ActionEvent event) {
    	model.SendTrackOrderStatusRequestToServer(Integer.parseInt(orderIDTF.getText()));

    }
    
    @Override
	public void handleServerMessage(String msg) {
    	orderIDTF.setText(msg);
	}

}

