package webGui.controllers;

import core.customer.CustomerRequestType;
import core.customer.responses.BadCustomerResponse;
import core.customer.responses.CustomerBaseResponse;
import core.customer.responses.CustomerNotificationResponse;
import core.customer.responses.IdPricePairResponse;
import core.guiUtilities.IServerResponseHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import webGui.util.MockWebClientConnectionManager;
import webGui.util.WebGuiController;

public class MockWebClientController extends WebGuiController implements IServerResponseHandler<CustomerBaseResponse>{
	private MockWebClientConnectionManager connectionManager;
	
	public MockWebClientController() {
		connectionManager = MockWebClientConnectionManager.getInstance();
		connectionManager.addServerMessageListener(this);
	}
	
	@FXML
	private TextArea responseTextArea;
	
	@FXML
	private TabPane tabPane;

	@FXML
	private Tab OpenComplaint;

	@FXML
	private OpenComplaintController openComplaintController;

	@FXML
	private Tab cancelOrder;

	@FXML
	private CancelOrderController cancelOrderController;

	@FXML
	private Tab renewSubscription;

	@FXML
	private Tab orderRoutineMonthlySubscription;

	@FXML
	private OrderRoutineMonthlySubscriptionController orderRoutineMonthlySubscriptionController;

	@FXML
	private Tab orderFullMonthlySubscription;

	@FXML
	private Tab TrackOrderStatus;

	@FXML
	private TrackOrderStatusController trackOrderStatusController;

	@FXML
	private Tab orderOneTimeParking;

	@FXML
	private OrderOneTimeParkingController orderOneTimeParkingController;
	
	@Override
	public void handleServerResponse(CustomerBaseResponse response) {
		if (response instanceof CustomerNotificationResponse) {
			CustomerNotificationResponse notificationResponse = (CustomerNotificationResponse)response;
			showNotification(notificationResponse.message);
			return;
		}

		if (response.requestType == CustomerRequestType.BAD_REQUEST) {
			BadCustomerResponse badResponse = (BadCustomerResponse) response;
			showError(badResponse.toString());
			return;
		}
		
		if (response instanceof IdPricePairResponse) {
			IdPricePairResponse idPricePair = (IdPricePairResponse) response;
			showNotification(idPricePair.toString());
		}
	}
}