
/**
 * Sample Skeleton for 'MockCancelOrderView.fxml' Controller Class
 */

package webGui.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import webGui.models.MockCancelOrderModel;
import webGui.util.ServerMessageHandler;

public class MockCancelOrderController implements ServerMessageHandler{
	private MockCancelOrderModel model;
	
	public MockCancelOrderController() {
		model = new MockCancelOrderModel(this);
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
		model.SendCancelRequestToServer(orderIDTF.getText());
    }
    
    @Override
	public void handleServerMessage(String msg) {
		// Print the message from the server to the UI example: 
    	orderIDTF.setText(msg);
	}
}
