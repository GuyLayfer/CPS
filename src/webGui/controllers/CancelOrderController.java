
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

// TODO: Auto-generated Javadoc
/**
 * The Class CancelOrderController.
 */
public class CancelOrderController {
	
	/** The model. */
	private CancelOrderModel model;
	
	/** The validation. */
	private ValidationSupport validation = new ValidationSupport();

	/**
	 * Instantiates a new cancel order controller.
	 */
	public CancelOrderController() {
		model = new CancelOrderModel();
	}

	/**
	 * Initialize.
	 */
	@FXML
	protected void initialize() {
		CancelOrderBTN.disableProperty().bind(validation.invalidProperty());
		validation.registerValidator(orderIDTF, Validator.createRegexValidator("Order ID is Required", CpsRegEx.IntegerBetweenMinAndMaxLength, Severity.ERROR));
	}

	/** The Cancel order BTN. */
	@FXML // fx:id="CancelOrderBTN"
	private Button CancelOrderBTN; // Value injected by FXMLLoader

	/** The order IDTF. */
	@FXML // fx:id="orderIDTF"
	private NumberTextField orderIDTF; // Value injected by FXMLLoader

	/**
	 * Order ID.
	 *
	 * @param event the event
	 */
	@FXML
	void orderID(ActionEvent event) {

	}

	/**
	 * Cancel order.
	 *
	 * @param event the event
	 */
	@FXML
	void cancelOrder(ActionEvent event) {
		model.SendCancelRequestToServer(orderIDTF.getNumber());
	}
}
