
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

public class TrackOrderStatusController extends WebGuiController implements IServerResponseHandler<CustomerBaseResponse> {
	private TrackOrderStatusModel model;
	private ValidationSupport validation = new ValidationSupport();

	public TrackOrderStatusController() {
		model = new TrackOrderStatusModel(this);
	}

	@FXML
	protected void initialize() {
		trackOrderStatusBTN.disableProperty().bind(validation.invalidProperty());
		validation.registerValidator(orderIDTF, Validator.createRegexValidator("Order ID is Required", CpsRegEx.IntegerBetweenMinAndMaxLength, Severity.ERROR));
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
	public void handleServerResponse(CustomerBaseResponse response) {
		if (response.requestType == CustomerRequestType.TRACK_ORDER_STATUS) {
			TrackOrderResponse specificResponse = (TrackOrderResponse) response;
			showNotification(specificResponse.toString());
		}
	}
}
