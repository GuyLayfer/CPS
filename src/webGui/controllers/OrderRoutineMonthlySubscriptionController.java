
package webGui.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import webGui.models.OrderRoutineMonthlySubscriptionModel;
import webGui.util.ServerMessageHandler;

public class OrderRoutineMonthlySubscriptionController implements ServerMessageHandler{
	private OrderRoutineMonthlySubscriptionModel model;
	
	public OrderRoutineMonthlySubscriptionController() {
		model = new OrderRoutineMonthlySubscriptionModel(this);
	}

    @FXML // fx:id="EmailLBL"
    private Label EmailLBL; // Value injected by FXMLLoader
    
    @FXML // fx:id="msgTF"
    private TextField msgTF; // Value injected by FXMLLoader
    
    @FXML // fx:id="routineDepartureTimeTF"
    private TextField routineDepartureTimeTF; // Value injected by FXMLLoader

    @FXML // fx:id="LiscencePlateLBL"
    private Label LiscencePlateLBL; // Value injected by FXMLLoader

    @FXML // fx:id="liscencePlateTF"
    private TextField liscencePlateTF; // Value injected by FXMLLoader

    @FXML // fx:id="RoutineDepartureTimeLBL"
    private Label RoutineDepartureTimeLBL; // Value injected by FXMLLoader

    @FXML // fx:id="parkingLotIDTF"
    private TextField parkingLotIDTF; // Value injected by FXMLLoader

    @FXML // fx:id="customerIDTF"
    private TextField customerIDTF; // Value injected by FXMLLoader

    @FXML // fx:id="customerIDLBL"
    private Label customerIDLBL; // Value injected by FXMLLoader

    @FXML // fx:id="emailTF"
    private TextField emailTF; // Value injected by FXMLLoader

    @FXML // fx:id="RotMonSubLBL"
    private Label RotMonSubLBL; // Value injected by FXMLLoader

    @FXML // fx:id="startingDateTF"
    private TextField startingDateTF; // Value injected by FXMLLoader

    @FXML // fx:id="ParkingLotIDLBL"
    private Label ParkingLotIDLBL; // Value injected by FXMLLoader
    
    @FXML // fx:id="StartingDateLBL"
    private Label StartingDateLBL; // Value injected by FXMLLoader

    @FXML // fx:id="createSubscriptionBTN"
    private Button createSubscriptionBTN; // Value injected by FXMLLoader

    @FXML
    void CreateSubscription(ActionEvent event) {
    	model.SendOrderRoutineMonthlySubscriptionRequestToServer(Integer.parseInt(customerIDTF.getText()), Integer.parseInt(liscencePlateTF.getText()), 
    			emailTF.getText(), Integer.parseInt(parkingLotIDTF.getText()), Long.parseLong(startingDateTF.getText()), Long.parseLong(routineDepartureTimeTF.getText()));

    }
    
    @Override
	public void handleServerMessage(String msg) {
    	msgTF.setText(msg);
	}

}
