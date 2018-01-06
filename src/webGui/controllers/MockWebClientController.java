package webGui.controllers;

import org.controlsfx.control.Notifications;

import core.guiUtilities.ServerMessageHandler;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.util.Duration;
import webGui.util.MockWebClientConnectionManager;
import webGui.util.WebGuiController;

public class MockWebClientController extends WebGuiController implements ServerMessageHandler{
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
	public void handleServerMessage(String msg) {
		responseTextArea.setText(msg);
		Platform.runLater(() -> {
			Notifications notificationBuilder = Notifications.create()
				.title("Message from server:")
				.text(msg)
				.hideAfter(Duration.seconds(10))
				.position(Pos.BOTTOM_RIGHT)
				.onAction(new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent arg0) {
						
					}
				});
		
		notificationBuilder.showInformation();
		});
	}

}