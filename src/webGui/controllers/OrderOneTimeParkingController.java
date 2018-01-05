
package webGui.controllers;

import java.time.ZoneOffset;
import java.util.Date;

import org.apache.commons.validator.routines.EmailValidator;
import org.controlsfx.validation.Severity;
import org.controlsfx.validation.ValidationSupport;
import org.controlsfx.validation.Validator;

import core.guiUtilities.CpsRegEx;
import core.guiUtilities.LicencePlateTextField;
import core.guiUtilities.NumberTextField;
import core.guiUtilities.ServerMessageHandler;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import tornadofx.control.DateTimePicker;
import webGui.models.OrderOneTimeParkingModel;

public class OrderOneTimeParkingController implements ServerMessageHandler{
	private OrderOneTimeParkingModel model;
	private ValidationSupport validation = new ValidationSupport();
	private EmailValidator emailValidator = EmailValidator.getInstance();
	
	public OrderOneTimeParkingController() {
		model = new OrderOneTimeParkingModel(this);
	}
	
    @FXML
    protected void initialize() {
		OrderOneTimeParkingBTN.disableProperty().bind(validation.invalidProperty());
		validation.registerValidator(emailTF, Validator.createPredicateValidator((email) -> emailValidator.isValid((String)email), "Email is not valid"));
		validation.registerValidator(parkingLotIDTF, Validator.createRegexValidator("Parking lot ID is Required", CpsRegEx.OneOrMoreIntegers, Severity.ERROR));
		validation.registerValidator(customerIDTF, Validator.createRegexValidator("Customer ID is Required", CpsRegEx.OneOrMoreIntegers, Severity.ERROR));
		validation.registerValidator(liscencePlateTF, Validator.createRegexValidator("Liscence plate is Required", CpsRegEx.LicencePlateLength, Severity.ERROR));
		validation.registerValidator(estimatedDepartureTimeTF, Validator.createEmptyValidator("Departure time is Required"));
		validation.registerValidator(arrivalTimeTF, Validator.createEmptyValidator("Arrival time is Required"));
//		validation.registerValidator(arrivalTimeTF, Validator.createPredicateValidator((nothing) -> CheckDate(), "Departure time should be later than Arrival"));
	}

    @FXML // fx:id="EmailLBL"
    private Label EmailLBL; // Value injected by FXMLLoader

    @FXML // fx:id="estimatedDepartureTimeTF"
    private DateTimePicker estimatedDepartureTimeTF; // Value injected by FXMLLoader

    @FXML // fx:id="OrderOneTimeParkingBTN"
    private Button OrderOneTimeParkingBTN; // Value injected by FXMLLoader

    @FXML // fx:id="LiscencePlateLBL"
    private Label LiscencePlateLBL; // Value injected by FXMLLoader

    @FXML // fx:id="liscencePlateTF"
    private LicencePlateTextField liscencePlateTF; // Value injected by FXMLLoader

    @FXML // fx:id="EstimatedDepartureTimeLBL"
    private Label EstimatedDepartureTimeLBL; // Value injected by FXMLLoader

    @FXML // fx:id="parkingLotIDTF"
    private NumberTextField parkingLotIDTF; // Value injected by FXMLLoader

    @FXML // fx:id="customerIDTF"
    private NumberTextField customerIDTF; // Value injected by FXMLLoader

    @FXML // fx:id="ArrivalTimeLBL"
    private Label ArrivalTimeLBL; // Value injected by FXMLLoader

    @FXML // fx:id="customerIDLBL"
    private Label customerIDLBL; // Value injected by FXMLLoader

    @FXML // fx:id="emailTF"
    private TextField emailTF; // Value injected by FXMLLoader

    @FXML // fx:id="arrivalTimeTF"
    private DateTimePicker arrivalTimeTF; // Value injected by FXMLLoader

    @FXML // fx:id="orderOneTimeParkingLBL"
    private Label orderOneTimeParkingLBL; // Value injected by FXMLLoader

    @FXML // fx:id="ParkingLotIDLBL"
    private Label ParkingLotIDLBL; // Value injected by FXMLLoader

    @FXML
    public void OrderOneTimeParking(ActionEvent event) {
    	model.SendOrderOneTimeParkingRequestToServer(
    			Integer.parseInt(customerIDTF.getText()),
    			Integer.parseInt(liscencePlateTF.getText()),
    			emailTF.getText(), Integer.parseInt(parkingLotIDTF.getText()),
    			Date.from(arrivalTimeTF.getDateTimeValue().atOffset(ZoneOffset.UTC).toInstant()), 
    			Date.from(estimatedDepartureTimeTF.getDateTimeValue().atOffset(ZoneOffset.UTC).toInstant()));
    }
    
    @Override
	public void handleServerMessage(String msg) {

    }
    
//	Doesn't work well. Need to validate both fields at same time
//    private Boolean CheckDate(){
//    	if(arrivalTimeTF.getDateTimeValue() == null || estimatedDepartureTimeTF.getDateTimeValue() == null){
//    		return false;
//    	}
//    	
//    	return arrivalTimeTF.getDateTimeValue().isBefore(estimatedDepartureTimeTF.getDateTimeValue());
//    }
}
