package webGui;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class MockWebClientController implements ServerMessageHandler{
	private MockWebClientModel model;
	
	public MockWebClientController() {
		try {
			//	Observer pattern
			model = new MockWebClientModel();
			model.addServerMessageListener(this);
		} catch (IOException e) {
			ScreenTF.setText("Error: Can't setup connection!");
		}
	}
	
    @FXML // fx:id="ScreenTF"
    private TextField ScreenTF; // Value injected by FXMLLoader
    
    @FXML // fx:id="buttonOne"
    private Button SendMessageToServerButton; // Value injected by FXMLLoader
    
    @FXML
    void buttonClickAction(ActionEvent event) {
    	sendRequestToServer(event);
    }
    
	@FXML
    void sendRequestToServer(ActionEvent event) {
		//	TODO Use the binded properties as parameters to send to the server.
		model.sendMessageToServer(model.CreateOrder());
    }

	@Override
	public void handleServerMessage(String msg) {
		// Print the message from the server to the UI example: 
		ScreenTF.setText(msg);
	}
}
