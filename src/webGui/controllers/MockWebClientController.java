package webGui.controllers;

import core.guiUtilities.ServerMessageHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import webGui.util.MockWebClientConnectionManager;
import webGui.util.WebGuiController;

public class MockWebClientController extends WebGuiController implements ServerMessageHandler{
	private MockWebClientConnectionManager connectionManager;
	
	public MockWebClientController() {
		connectionManager = MockWebClientConnectionManager.getInstance();
		connectionManager.addServerMessageListener(this);
//		MockWebClientConnectionManager.registerStartupListeners(this);
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

	// @FXML
	// private FooTabController fooTabPageController;

	@FXML
	private Tab TrackOrderStatus;

	@FXML
	private TrackOrderStatusController trackOrderStatusController;

	@FXML
	private Tab orderOneTimeParking;

	@FXML
	private OrderOneTimeParkingController orderOneTimeParkingController;
	
	@Override
	public void handleServerMessage(String msg) {
		responseTextArea.setText(msg);
	}

}