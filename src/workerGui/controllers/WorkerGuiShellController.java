package workerGui.controllers;

import org.controlsfx.dialog.LoginDialog;

import core.guiUtilities.CpsRegEx;
import core.guiUtilities.IServerResponseHandler;
import core.guiUtilities.UriDictionary;
import core.worker.WorkerOperations;
import core.worker.WorkerRequestType;
import core.worker.responses.BadResponse;
import core.worker.responses.WorkerBaseResponse;
import core.worker.responses.NotificationResponse;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.AnchorPane;
import javafx.util.Pair;
import workerGui.util.CpsLoginDialog;
import workerGui.util.ICareAboutLoginState;
import workerGui.util.LogoutDialog;
import workerGui.util.ReportsManager;
import workerGui.util.WorkerAccountManager;
import workerGui.util.WorkerConnectionManager;
import workerGui.util.WorkerGuiController;

public class WorkerGuiShellController extends WorkerGuiController implements IServerResponseHandler<WorkerBaseResponse>, ICareAboutLoginState {
	private static final String IDLE_BUTTON_STYLE = "-fx-background-color: transparent;";
	private static final String HOVERED_BUTTON_STYLE = "-fx-background-color: #0096C9";
	private WorkerAccountManager workerAccountManager;
	private ReportsManager reportsManager;
	private WorkerConnectionManager connectionManager;
	private LoginDialog loginDialog;

	@FXML
	protected void initialize() {
		workerAccountManager = WorkerAccountManager.getInstance();
		connectionManager = WorkerConnectionManager.getInstance();
		reportsManager = ReportsManager.getInstance();
		workerAccountManager.registerLoginListener(this);
		connectionManager.addServerMessageListener(this);
		LogoutButton.setOnMouseEntered(e -> LogoutButton.setStyle(HOVERED_BUTTON_STYLE));
		LogoutButton.setOnMouseExited(e -> LogoutButton.setStyle(IDLE_BUTTON_STYLE));
		openLoginDialog("");
	}

	@Override
	public void handleServerResponse(WorkerBaseResponse response) {
		if (response instanceof NotificationResponse) {
			NotificationResponse notificationResponse = (NotificationResponse) response;
			showNotification(notificationResponse.message);
		}

		if (response.requestType == WorkerRequestType.BAD_REQUEST) {
			BadResponse badResponse = (BadResponse) response;
			showError(badResponse.toString());
		}
	}

	@Override
	public void handleLogout() {
		Platform.runLater(() -> {
			NavigateTo(workerMainViewRegion.getScene(), UriDictionary.WorkerGui.ClientView);
			openLoginDialog("");
		});
	}

	@Override
	public void handleLogin() {
		Platform.runLater(() -> {
			loginDialog.close();
			setPermissions();
		});
	}

	@FXML
	private AnchorPane workerMainViewRegion;

	@FXML
	private Button LogoutButton;

	@FXML
	private void GoSetOutOfOrder(ActionEvent event) {
		NavigateTo(workerMainViewRegion.getScene(), UriDictionary.WorkerGui.SetOutOfOrderParkingSpaceView);
	}

	@FXML
	private void GoUpdateRates(ActionEvent event) {
		NavigateTo(workerMainViewRegion.getScene(), UriDictionary.WorkerGui.UpdateRatesRequestView);
	}

	@FXML
	private void AskToLogout(ActionEvent event) {
		Alert logout = LogoutDialog.getLogoutConfirmation();
		ButtonType response = logout.showAndWait().get();
		if (response == ButtonType.YES) {
			LogoutFromSystem();
		}
	}

	@FXML
	private void InitializeParkingLot(ActionEvent event) {
		NavigateTo(workerMainViewRegion.getScene(), UriDictionary.WorkerGui.InitializeParkingLotView);
	}

	@FXML
	private void GoSetParkingLotIsFull(ActionEvent event) {
		NavigateTo(workerMainViewRegion.getScene(), UriDictionary.WorkerGui.ParkingLotFullView);
	}

	@FXML
	private void GoReserveParkingSpace(ActionEvent event) {
		NavigateTo(workerMainViewRegion.getScene(), UriDictionary.WorkerGui.ReserveParkingSpaceView);
	}

	@FXML
	private void GoCancelOrder(ActionEvent event) {
		NavigateTo(workerMainViewRegion.getScene(), UriDictionary.WorkerGui.CancelCustomerOrderView);
	}

	@FXML
	private void GoAcquitOrChargeAccount(ActionEvent event) {
		NavigateTo(workerMainViewRegion.getScene(), UriDictionary.WorkerGui.AcquitOrChargeAccountView);
	}

	@FXML
	private void GoComplaintsPortal(ActionEvent event) {
		NavigateTo(workerMainViewRegion.getScene(), UriDictionary.WorkerGui.ComplaintsPortalView);
	}

	@FXML
	private void GoRatesRequestsPortal(ActionEvent event) {
		NavigateTo(workerMainViewRegion.getScene(), UriDictionary.WorkerGui.ApproveRatesRequestsPortalView);
	}

	@FXML
	private void goOrdersReport(ActionEvent event) {
		reportsManager.setReportContext(WorkerRequestType.ORDERS_REPORT);
		reportsManager.setReportName(ordersReportMenuItem.getText());
		NavigateTo(workerMainViewRegion.getScene(), UriDictionary.WorkerGui.ReportsView);
	}

	@FXML
	private void goComplaintsReport(ActionEvent event) {
		reportsManager.setReportContext(WorkerRequestType.COMPLAINTS_REPORT);
		reportsManager.setReportName(complaintsReportMenuItem.getText());
		NavigateTo(workerMainViewRegion.getScene(), UriDictionary.WorkerGui.ReportsView);
	}

	@FXML
	private void goOutOfOrderReport(ActionEvent event) {
		reportsManager.setReportContext(WorkerRequestType.OUT_OF_ORDER_REPORT);
		reportsManager.setReportName(outOfOrderReportMenuItem.getText());
		NavigateTo(workerMainViewRegion.getScene(), UriDictionary.WorkerGui.ReportsView);
	}

	@FXML
	private void goPerformenceReport(ActionEvent event) {
		reportsManager.setReportContext(WorkerRequestType.PERFORMENCE_REPORT);
		reportsManager.setReportName(performenceReportMenuItem.getText());
		NavigateTo(workerMainViewRegion.getScene(), UriDictionary.WorkerGui.ReportsView);
	}

	@FXML
	private void goOperationsReport(ActionEvent event) {
		reportsManager.setReportContext(WorkerRequestType.OPERATIONS_REPORT);
		reportsManager.setReportName(operationsReportMenuItem.getText());
		NavigateTo(workerMainViewRegion.getScene(), UriDictionary.WorkerGui.ReportsView);
	}

	@FXML
	private void goCurrentSubscribersReport(ActionEvent event) {
		reportsManager.setReportContext(WorkerRequestType.CURRENT_SUBSCRIBERS_REPORT);
		reportsManager.setReportName(currentSubscribersReportMenuItem.getText());
		NavigateTo(workerMainViewRegion.getScene(), UriDictionary.WorkerGui.ReportsView);
	}

	@FXML
	private void goLotSpacesReport(ActionEvent event) {
		reportsManager.setReportContext(WorkerRequestType.LOT_SPACES_REPORT);
		reportsManager.setReportName(lotSpacesReportMenuItem.getText());
		NavigateTo(workerMainViewRegion.getScene(), UriDictionary.WorkerGui.ReportsView);
	}

	private void LogoutFromSystem() {
		workerAccountManager.Logout();
	}

	@FXML
	private MenuBar ParkingLotMenuBar;

	@FXML
	private MenuBar ManagmentMenuBar;

	@FXML
	private MenuBar CustomerServiceMenuBar;

	@FXML
	private MenuItem InitializeParkingLotMenuItem;

	@FXML
	private MenuItem OpenComplaintsPortalMenuItem;

	@FXML
	private MenuItem CancelCustomerOrderMenuItem;

	@FXML
	private MenuItem DisableParkingSpaceMenuItem;

	@FXML
	private MenuItem CheckNewRatesRequestsMenuItem;

	@FXML
	private MenuItem RequestParkingLotRatesUpdateMenuItem;

	@FXML
	private MenuItem ReserveParkingSpaceMenuItem;

	@FXML
	private MenuItem SetParkingLotFullMenuItem;

	@FXML
	private MenuItem AcquitOrChargeAccountMenuItem;

	@FXML
	private MenuItem outOfOrderReportMenuItem;

	@FXML
	private MenuItem performenceReportMenuItem;

	@FXML
	private MenuItem ordersReportMenuItem;

	@FXML
	private MenuItem complaintsReportMenuItem;

	@FXML
	private MenuItem lotSpacesReportMenuItem;

	@FXML
	private MenuItem operationsReportMenuItem;

	@FXML
	private MenuItem currentSubscribersReportMenuItem;

	private void openLoginDialog(String workerId) {
		Platform.runLater(() -> {
			if (!workerAccountManager.isWorkerLoggedIn()) {
				loginDialog = new CpsLoginDialog(new Pair<String, String>(workerId, ""), null);
				Pair<String, String> login = loginDialog.showAndWait().orElse(new Pair<String, String>("", ""));
				if (login.getKey() != "" && login.getValue() != "" && login.getKey().matches(CpsRegEx.IntegerBetweenMinAndMaxLength)) {
					workerAccountManager.Login(Integer.parseInt(login.getKey()), login.getValue());
				}

				openLoginDialog(login.getKey());
			}
		});
	}

	private void setPermissions() {
		ManagmentMenuBar.setDisable(!workerAccountManager.isOperationAllowed(WorkerOperations.UPDATE_RATES));
		CustomerServiceMenuBar.setDisable(!workerAccountManager.isOperationAllowed(WorkerOperations.ACQUIT_OR_CHARGE_ACCOUNT));
		InitializeParkingLotMenuItem.setDisable(!workerAccountManager.isOperationAllowed(WorkerOperations.INITIALIZE_PARKING_LOT));
		OpenComplaintsPortalMenuItem.setDisable(!workerAccountManager.isOperationAllowed(WorkerOperations.DECIDE_ON_COMPLAINTS));
		CancelCustomerOrderMenuItem.setDisable(!workerAccountManager.isOperationAllowed(WorkerOperations.CANCEL_CUSTOMER_ORDER));
		DisableParkingSpaceMenuItem.setDisable(!workerAccountManager.isOperationAllowed(WorkerOperations.OUT_OF_ORDER));
		CheckNewRatesRequestsMenuItem.setDisable(!workerAccountManager.isOperationAllowed(WorkerOperations.DECIDE_ON_RATES));
		RequestParkingLotRatesUpdateMenuItem.setDisable(!workerAccountManager.isOperationAllowed(WorkerOperations.UPDATE_RATES));
		ReserveParkingSpaceMenuItem.setDisable(!workerAccountManager.isOperationAllowed(WorkerOperations.RESERVE_PARKING_SPACE));
		SetParkingLotFullMenuItem.setDisable(!workerAccountManager.isOperationAllowed(WorkerOperations.PARKING_LOT_FULL));
		AcquitOrChargeAccountMenuItem.setDisable(!workerAccountManager.isOperationAllowed(WorkerOperations.ACQUIT_OR_CHARGE_ACCOUNT));
		outOfOrderReportMenuItem.setDisable(!workerAccountManager.isOperationAllowed(WorkerOperations.OUT_OF_ORDER_REPORT));
		performenceReportMenuItem.setDisable(!workerAccountManager.isOperationAllowed(WorkerOperations.PERFORMENCE_REPORT));
		ordersReportMenuItem.setDisable(!workerAccountManager.isOperationAllowed(WorkerOperations.ORDERS_REPORT));
		complaintsReportMenuItem.setDisable(!workerAccountManager.isOperationAllowed(WorkerOperations.COMPLAINTS_REPORT));
		lotSpacesReportMenuItem.setDisable(!workerAccountManager.isOperationAllowed(WorkerOperations.LOT_SPACES_REPORT));
		operationsReportMenuItem.setDisable(!workerAccountManager.isOperationAllowed(WorkerOperations.OPERATIONS_REPORT));
		currentSubscribersReportMenuItem.setDisable(!workerAccountManager.isOperationAllowed(WorkerOperations.CURRENT_SUBSCRIBERS_REPORT));
	}
}
