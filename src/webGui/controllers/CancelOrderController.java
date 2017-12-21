
package webGui.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import webGui.models.CancelOrderModel;
import webGui.util.ServerMessageHandler;

public class CancelOrderController implements ServerMessageHandler{
	private CancelOrderModel model;
	
	public CancelOrderController() {
		model = new CancelOrderModel(this);
	}

    @FXML // fx:id="CancelOrderBTN"
    private Button CancelOrderBTN; // Value injected by FXMLLoader

    @FXML // fx:id="orderIDTF"
    private TextField orderIDTF; // Value injected by FXMLLoader

    @FXML
    void orderID(ActionEvent event) {
    	
    }

    @FXML
    void cancelOrder(ActionEvent event) {
		model.SendCancelRequestToServer(Integer.parseInt(orderIDTF.getText()));
    }
    
    @Override
	public void handleServerMessage(String msg) {
    	orderIDTF.setText(msg);
	}
}
