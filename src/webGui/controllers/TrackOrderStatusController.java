
package webGui.controllers;

import org.controlsfx.validation.Severity;
import org.controlsfx.validation.ValidationSupport;
import org.controlsfx.validation.Validator;

import core.customer.CustomerRequestType;
import core.customer.responses.CustomerBaseResponse;
import core.customer.responses.TrackOrderResponse;
import core.guiUtilities.CpsRegEx;
import core.guiUtilities.IServerResponseHandler;
import core.guiUtilities.NumberTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import webGui.models.TrackOrderStatusModel;
import webGui.util.WebGuiController;

// TODO: Auto-generated Javadoc
/**
 * The Class TrackOrderStatusController.
 */
public class TrackOrderStatusController extends WebGuiController implements IServerResponseHandler<CustomerBaseResponse> {
	
	/** The model. */
	private TrackOrderStatusModel model;
	
	/** The validation. */
	private ValidationSupport validation = new ValidationSupport();

	/**
	 * Instantiates a new track order status controller.
	 */
	public TrackOrderStatusController() {
		model = new TrackOrderStatusModel(this);
	}

	/**
	 * Initialize.
	 */
	@FXML
	protected void initialize() {
		trackOrderStatusBTN.disableProperty().bind(validation.invalidProperty());
		validation.registerValidator(orderIDTF, Validator.createRegexValidator("Order ID is Required", CpsRegEx.IntegerBetweenMinAndMaxLength, Severity.ERROR));
	}

	/** The track order status BTN. */
	@FXML // fx:id="trackOrderStatusBTN"
	private Button trackOrderStatusBTN; // Value injected by FXMLLoader

	/** The order IDTF. */
	@FXML // fx:id="orderIDTF"
	private NumberTextField orderIDTF; // Value injected by FXMLLoader

	/**
	 * Track order status.
	 *
	 * @param event the event
	 */
	@FXML
	void TrackOrderStatus(ActionEvent event) {
		model.SendTrackOrderStatusRequestToServer(orderIDTF.getNumber());
	}

	/* (non-Javadoc)
	 * @see core.guiUtilities.IServerResponseHandler#handleServerResponse(java.lang.Object)
	 */
	@Override
	public void handleServerResponse(CustomerBaseResponse response) {
		if (response.requestType == CustomerRequestType.TRACK_ORDER_STATUS) {
			TrackOrderResponse specificResponse = (TrackOrderResponse) response;
			showNotification(specificResponse.toString());
		}
	}
}
