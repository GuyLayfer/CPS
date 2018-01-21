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

// TODO: Auto-generated Javadoc
/**
 * The Class WorkerGuiShellController.
 */
public class WorkerGuiShellController extends WorkerGuiController implements IServerResponseHandler<WorkerBaseResponse>, ICareAboutLoginState {
	
	/** The Constant IDLE_BUTTON_STYLE. */
	private static final String IDLE_BUTTON_STYLE = "-fx-background-color: transparent;";
	
	/** The Constant HOVERED_BUTTON_STYLE. */
	private static final String HOVERED_BUTTON_STYLE = "-fx-background-color: #0096C9";
	
	/** The worker account manager. */
	private WorkerAccountManager workerAccountManager;
	
	/** The reports manager. */
	private ReportsManager reportsManager;
	
	/** The connection manager. */
	private WorkerConnectionManager connectionManager;
	
	/** The login dialog. */
	private LoginDialog loginDialog;

	/**
	 * Initialize.
	 */
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

	/* (non-Javadoc)
	 * @see core.guiUtilities.IServerResponseHandler#handleServerResponse(java.lang.Object)
	 */
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

	/* (non-Javadoc)
	 * @see workerGui.util.ICareAboutLoginState#handleLogout()
	 */
	@Override
	public void handleLogout() {
		Platform.runLater(() -> {
			NavigateTo(workerMainViewRegion.getScene(), UriDictionary.WorkerGui.ClientView);
			openLoginDialog("");
		});
	}

	/* (non-Javadoc)
	 * @see workerGui.util.ICareAboutLoginState#handleLogin()
	 */
	@Override
	public void handleLogin() {
		Platform.runLater(() -> {
			loginDialog.close();
			setPermissions();
		});
	}

	/** The worker main view region. */
	@FXML
	private AnchorPane workerMainViewRegion;

	/** The Logout button. */
	@FXML
	private Button LogoutButton;

	/**
	 * Go set out of order.
	 *
	 * @param event the event
	 */
	@FXML
	private void GoSetOutOfOrder(ActionEvent event) {
		NavigateTo(workerMainViewRegion.getScene(), UriDictionary.WorkerGui.SetOutOfOrderParkingSpaceView);
	}

	/**
	 * Go update rates.
	 *
	 * @param event the event
	 */
	@FXML
	private void GoUpdateRates(ActionEvent event) {
		NavigateTo(workerMainViewRegion.getScene(), UriDictionary.WorkerGui.UpdateRatesRequestView);
	}

	/**
	 * Ask to logout.
	 *
	 * @param event the event
	 */
	@FXML
	private void AskToLogout(ActionEvent event) {
		Alert logout = LogoutDialog.getLogoutConfirmation();
		ButtonType response = logout.showAndWait().get();
		if (response == ButtonType.YES) {
			LogoutFromSystem();
		}
	}

	/**
	 * Initialize parking lot.
	 *
	 * @param event the event
	 */
	@FXML
	private void InitializeParkingLot(ActionEvent event) {
		NavigateTo(workerMainViewRegion.getScene(), UriDictionary.WorkerGui.InitializeParkingLotView);
	}

	/**
	 * Go set parking lot is full.
	 *
	 * @param event the event
	 */
	@FXML
	private void GoSetParkingLotIsFull(ActionEvent event) {
		NavigateTo(workerMainViewRegion.getScene(), UriDictionary.WorkerGui.ParkingLotFullView);
	}

	/**
	 * Go reserve parking space.
	 *
	 * @param event the event
	 */
	@FXML
	private void GoReserveParkingSpace(ActionEvent event) {
		NavigateTo(workerMainViewRegion.getScene(), UriDictionary.WorkerGui.ReserveParkingSpaceView);
	}

	/**
	 * Go cancel order.
	 *
	 * @param event the event
	 */
	@FXML
	private void GoCancelOrder(ActionEvent event) {
		NavigateTo(workerMainViewRegion.getScene(), UriDictionary.WorkerGui.CancelCustomerOrderView);
	}

	/**
	 * Go acquit or charge account.
	 *
	 * @param event the event
	 */
	@FXML
	private void GoAcquitOrChargeAccount(ActionEvent event) {
		NavigateTo(workerMainViewRegion.getScene(), UriDictionary.WorkerGui.AcquitOrChargeAccountView);
	}

	/**
	 * Go complaints portal.
	 *
	 * @param event the event
	 */
	@FXML
	private void GoComplaintsPortal(ActionEvent event) {
		NavigateTo(workerMainViewRegion.getScene(), UriDictionary.WorkerGui.ComplaintsPortalView);
	}

	/**
	 * Go rates requests portal.
	 *
	 * @param event the event
	 */
	@FXML
	private void GoRatesRequestsPortal(ActionEvent event) {
		NavigateTo(workerMainViewRegion.getScene(), UriDictionary.WorkerGui.ApproveRatesRequestsPortalView);
	}

	/**
	 * Go orders report.
	 *
	 * @param event the event
	 */
	@FXML
	private void goOrdersReport(ActionEvent event) {
		reportsManager.setReportContext(WorkerRequestType.ORDERS_REPORT);
		reportsManager.setReportName(ordersReportMenuItem.getText());
		NavigateTo(workerMainViewRegion.getScene(), UriDictionary.WorkerGui.ReportsView);
	}

	/**
	 * Go complaints report.
	 *
	 * @param event the event
	 */
	@FXML
	private void goComplaintsReport(ActionEvent event) {
		reportsManager.setReportContext(WorkerRequestType.COMPLAINTS_REPORT);
		reportsManager.setReportName(complaintsReportMenuItem.getText());
		NavigateTo(workerMainViewRegion.getScene(), UriDictionary.WorkerGui.ReportsView);
	}

	/**
	 * Go out of order report.
	 *
	 * @param event the event
	 */
	@FXML
	private void goOutOfOrderReport(ActionEvent event) {
		reportsManager.setReportContext(WorkerRequestType.OUT_OF_ORDER_REPORT);
		reportsManager.setReportName(outOfOrderReportMenuItem.getText());
		NavigateTo(workerMainViewRegion.getScene(), UriDictionary.WorkerGui.ReportsView);
	}

	/**
	 * Go performence report.
	 *
	 * @param event the event
	 */
	@FXML
	private void goPerformenceReport(ActionEvent event) {
		reportsManager.setReportContext(WorkerRequestType.PERFORMENCE_REPORT);
		reportsManager.setReportName(performenceReportMenuItem.getText());
		NavigateTo(workerMainViewRegion.getScene(), UriDictionary.WorkerGui.ReportsView);
	}

	/**
	 * Go operations report.
	 *
	 * @param event the event
	 */
	@FXML
	private void goOperationsReport(ActionEvent event) {
		reportsManager.setReportContext(WorkerRequestType.OPERATIONS_REPORT);
		reportsManager.setReportName(operationsReportMenuItem.getText());
		NavigateTo(workerMainViewRegion.getScene(), UriDictionary.WorkerGui.ReportsView);
	}

	/**
	 * Go current subscribers report.
	 *
	 * @param event the event
	 */
	@FXML
	private void goCurrentSubscribersReport(ActionEvent event) {
		reportsManager.setReportContext(WorkerRequestType.CURRENT_SUBSCRIBERS_REPORT);
		reportsManager.setReportName(currentSubscribersReportMenuItem.getText());
		NavigateTo(workerMainViewRegion.getScene(), UriDictionary.WorkerGui.ReportsView);
	}

	/**
	 * Go lot spaces report.
	 *
	 * @param event the event
	 */
	@FXML
	private void goLotSpacesReport(ActionEvent event) {
		reportsManager.setReportContext(WorkerRequestType.LOT_SPACES_REPORT);
		reportsManager.setReportName(lotSpacesReportMenuItem.getText());
		NavigateTo(workerMainViewRegion.getScene(), UriDictionary.WorkerGui.ReportsView);
	}

	/**
	 * Logout from system.
	 */
	private void LogoutFromSystem() {
		workerAccountManager.Logout();
	}

	/** The Parking lot menu bar. */
	@FXML
	private MenuBar ParkingLotMenuBar;

	/** The Managment menu bar. */
	@FXML
	private MenuBar ManagmentMenuBar;

	/** The Customer service menu bar. */
	@FXML
	private MenuBar CustomerServiceMenuBar;

	/** The Initialize parking lot menu item. */
	@FXML
	private MenuItem InitializeParkingLotMenuItem;

	/** The Open complaints portal menu item. */
	@FXML
	private MenuItem OpenComplaintsPortalMenuItem;

	/** The Cancel customer order menu item. */
	@FXML
	private MenuItem CancelCustomerOrderMenuItem;

	/** The Disable parking space menu item. */
	@FXML
	private MenuItem DisableParkingSpaceMenuItem;

	/** The Check new rates requests menu item. */
	@FXML
	private MenuItem CheckNewRatesRequestsMenuItem;

	/** The Request parking lot rates update menu item. */
	@FXML
	private MenuItem RequestParkingLotRatesUpdateMenuItem;

	/** The Reserve parking space menu item. */
	@FXML
	private MenuItem ReserveParkingSpaceMenuItem;

	/** The Set parking lot full menu item. */
	@FXML
	private MenuItem SetParkingLotFullMenuItem;

	/** The Acquit or charge account menu item. */
	@FXML
	private MenuItem AcquitOrChargeAccountMenuItem;

	/** The out of order report menu item. */
	@FXML
	private MenuItem outOfOrderReportMenuItem;

	/** The performence report menu item. */
	@FXML
	private MenuItem performenceReportMenuItem;

	/** The orders report menu item. */
	@FXML
	private MenuItem ordersReportMenuItem;

	/** The complaints report menu item. */
	@FXML
	private MenuItem complaintsReportMenuItem;

	/** The lot spaces report menu item. */
	@FXML
	private MenuItem lotSpacesReportMenuItem;

	/** The operations report menu item. */
	@FXML
	private MenuItem operationsReportMenuItem;

	/** The current subscribers report menu item. */
	@FXML
	private MenuItem currentSubscribersReportMenuItem;

	/**
	 * Open login dialog.
	 *
	 * @param workerId the worker id
	 */
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

	/**
	 * Sets the permissions.
	 */
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
