/**
 * Sample Skeleton for 'MockOrderOneTimeParkingView.fxml' Controller Class
 */

package webGui.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import webGui.models.MockOrderOneTimeParkingModel;
import webGui.util.ServerMessageHandler;

public class MockOrderOneTimeParkingController implements ServerMessageHandler{
	private MockOrderOneTimeParkingModel model;
	
	public MockOrderOneTimeParkingController() {
		model = new MockOrderOneTimeParkingModel(this);
	}

    @FXML // fx:id="EmailLBL"
    private Label EmailLBL; // Value injected by FXMLLoader

    @FXML // fx:id="msgTF"
    private TextField msgTF; // Value injected by FXMLLoader

    @FXML // fx:id="estimatedDepartureTimeTF"
    private TextField estimatedDepartureTimeTF; // Value injected by FXMLLoader

    @FXML // fx:id="OrderOneTimeParkingBTN"
    private Button OrderOneTimeParkingBTN; // Value injected by FXMLLoader

    @FXML // fx:id="LiscencePlateLBL"
    private Label LiscencePlateLBL; // Value injected by FXMLLoader

    @FXML // fx:id="liscencePlateTF"
    private TextField liscencePlateTF; // Value injected by FXMLLoader

    @FXML // fx:id="EstimatedDepartureTimeLBL"
    private Label EstimatedDepartureTimeLBL; // Value injected by FXMLLoader

    @FXML // fx:id="parkingLotIDTF"
    private TextField parkingLotIDTF; // Value injected by FXMLLoader

    @FXML // fx:id="customerIDTF"
    private TextField customerIDTF; // Value injected by FXMLLoader

    @FXML // fx:id="ArrivalTimeLBL"
    private Label ArrivalTimeLBL; // Value injected by FXMLLoader

    @FXML // fx:id="customerIDLBL"
    private Label customerIDLBL; // Value injected by FXMLLoader

    @FXML // fx:id="emailTF"
    private TextField emailTF; // Value injected by FXMLLoader

    @FXML // fx:id="arrivalTimeTF"
    private TextField arrivalTimeTF; // Value injected by FXMLLoader

    @FXML // fx:id="orderOneTimeParkingLBL"
    private Label orderOneTimeParkingLBL; // Value injected by FXMLLoader

    @FXML // fx:id="ParkingLotIDLBL"
    private Label ParkingLotIDLBL; // Value injected by FXMLLoader

    @FXML
    void OrderOneTimeParking(ActionEvent event) {
    	model.SendOrderOrderOneTimeParkingRequestToServer(customerIDTF.getText(), liscencePlateTF.getText(), emailTF.getText(), parkingLotIDTF.getText(), arrivalTimeTF.getText(), 
				 estimatedDepartureTimeTF.getText());
    }
    
    @Override
	public void handleServerMessage(String msg) {
		// Print the message from the server to the UI example: 
    	msgTF.setText(msg);
	}


}
