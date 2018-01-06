
package webGui.controllers;


import org.controlsfx.validation.Severity;
import org.controlsfx.validation.ValidationSupport;
import org.controlsfx.validation.Validator;

import core.guiUtilities.CpsRegEx;
import core.guiUtilities.NumberTextField;
import core.guiUtilities.ServerMessageHandler;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import webGui.models.TrackOrderStatusModel;

public class TrackOrderStatusController implements ServerMessageHandler{
	private TrackOrderStatusModel model;
	private ValidationSupport validation = new ValidationSupport();
	
	public TrackOrderStatusController() {
		model = new TrackOrderStatusModel(this);
	}
	
	@FXML
	protected void initialize() {
		trackOrderStatusBTN.disableProperty().bind(validation.invalidProperty());
		validation.registerValidator(orderIDTF, Validator.createRegexValidator("Order ID is Required", CpsRegEx.OneOrMoreIntegers, Severity.ERROR));
	}

    @FXML // fx:id="trackOrderStatusBTN"
    private Button trackOrderStatusBTN; // Value injected by FXMLLoader

    @FXML // fx:id="orderIDTF"
    private NumberTextField orderIDTF; // Value injected by FXMLLoader

    @FXML
    void TrackOrderStatus(ActionEvent event) {
    	model.SendTrackOrderStatusRequestToServer(orderIDTF.getNumber());

    }
    
    @Override
	public void handleServerMessage(String msg) {

    }

}

