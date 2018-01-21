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

// TODO: Auto-generated Javadoc
/**
 * The Class MockWebClientController.
 */
public class MockWebClientController extends WebGuiController implements IServerResponseHandler<CustomerBaseResponse>{
	
	/** The connection manager. */
	private MockWebClientConnectionManager connectionManager;
	
	/**
	 * Instantiates a new mock web client controller.
	 */
	public MockWebClientController() {
		connectionManager = MockWebClientConnectionManager.getInstance();
		connectionManager.addServerMessageListener(this);
	}
	
	/** The response text area. */
	@FXML
	private TextArea responseTextArea;
	
	/** The tab pane. */
	@FXML
	private TabPane tabPane;

	/** The Open complaint. */
	@FXML
	private Tab OpenComplaint;

	/** The open complaint controller. */
	@FXML
	private OpenComplaintController openComplaintController;

	/** The cancel order. */
	@FXML
	private Tab cancelOrder;

	/** The cancel order controller. */
	@FXML
	private CancelOrderController cancelOrderController;

	/** The renew subscription. */
	@FXML
	private Tab renewSubscription;

	/** The order routine monthly subscription. */
	@FXML
	private Tab orderRoutineMonthlySubscription;

	/** The order routine monthly subscription controller. */
	@FXML
	private OrderRoutineMonthlySubscriptionController orderRoutineMonthlySubscriptionController;

	/** The order full monthly subscription. */
	@FXML
	private Tab orderFullMonthlySubscription;

	/** The Track order status. */
	@FXML
	private Tab TrackOrderStatus;

	/** The track order status controller. */
	@FXML
	private TrackOrderStatusController trackOrderStatusController;

	/** The Pre order parking. */
	@FXML
	private Tab PreOrderParking;

	/** The pre order parking controller. */
	@FXML
	private PreOrderParkingController preOrderParkingController;
	
	/* (non-Javadoc)
	 * @see core.guiUtilities.IServerResponseHandler#handleServerResponse(java.lang.Object)
	 */
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