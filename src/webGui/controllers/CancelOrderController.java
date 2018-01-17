
package webGui.controllers;

import org.controlsfx.validation.Severity;
import org.controlsfx.validation.ValidationSupport;
import org.controlsfx.validation.Validator;

import core.guiUtilities.CpsRegEx;
import core.guiUtilities.NumberTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import webGui.models.CancelOrderModel;

public class CancelOrderController {
	private CancelOrderModel model;
	private ValidationSupport validation = new ValidationSupport();

	public CancelOrderController() {
		model = new CancelOrderModel();
	}

	@FXML
	protected void initialize() {
		CancelOrderBTN.disableProperty().bind(validation.invalidProperty());
		validation.registerValidator(orderIDTF, Validator.createRegexValidator("Order ID is Required", CpsRegEx.IntegerBetweenMinAndMaxLength, Severity.ERROR));
	}

	@FXML // fx:id="CancelOrderBTN"
	private Button CancelOrderBTN; // Value injected by FXMLLoader

	@FXML // fx:id="orderIDTF"
	private NumberTextField orderIDTF; // Value injected by FXMLLoader

	@FXML
	void orderID(ActionEvent event) {

	}

	@FXML
	void cancelOrder(ActionEvent event) {
		model.SendCancelRequestToServer(orderIDTF.getNumber());
	}
}
