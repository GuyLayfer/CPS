package workerGui.controllers;

import org.controlsfx.dialog.LoginDialog;

import core.guiUtilities.CpsRegEx;
import core.guiUtilities.IServerResponseHandler;
import core.guiUtilities.UriDictionary;
import core.worker.responses.BaseResponse;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.AnchorPane;
import javafx.util.Pair;
import workerGui.util.CpsLoginDialog;
import workerGui.util.ICareAboutLoginState;
import workerGui.util.LogoutDialog;
import workerGui.util.WorkerAccountManager;
import workerGui.util.WorkerGuiController;

public class WorkerGuiShellController extends WorkerGuiController implements IServerResponseHandler, ICareAboutLoginState {
	private static final String IDLE_BUTTON_STYLE = "-fx-background-color: transparent;";
	private static final String HOVERED_BUTTON_STYLE = "-fx-background-color: #0096C9";
	private WorkerAccountManager workerAccountManager;
	private LoginDialog loginDialog;

	@FXML
	private AnchorPane workerMainViewRegion;

	@FXML
	private Button LogoutButton;

	@FXML
	protected void initialize() {
		workerAccountManager = WorkerAccountManager.getInstance();
		workerAccountManager.registerLoginListener(this);
		LogoutButton.setOnMouseEntered(e -> LogoutButton.setStyle(HOVERED_BUTTON_STYLE));
		LogoutButton.setOnMouseExited(e -> LogoutButton.setStyle(IDLE_BUTTON_STYLE));
		openLogoutDialog("");
	}

	@Override
	public void handleServerResponse(BaseResponse response) {

	}

	@FXML
	void GoSetOutOfOrder(ActionEvent event) {
		NavigateTo(workerMainViewRegion.getScene(), UriDictionary.WorkerGui.SetOutOfOrderParkingSpaceView);
	}

	@FXML
	void GoUpdateRates(ActionEvent event) {
		NavigateTo(workerMainViewRegion.getScene(), UriDictionary.WorkerGui.UpdateRatesRequestView);
	}

	@FXML
	void AskToLogout(ActionEvent event) {
		Alert logout = LogoutDialog.getLogoutConfirmation();
		ButtonType response = logout.showAndWait().get();
		if (response == ButtonType.YES) {
			LogoutFromSystem();
		}
	}

	@FXML
	void InitializeParkingLot(ActionEvent event) {
		NavigateTo(workerMainViewRegion.getScene(), UriDictionary.WorkerGui.InitializeParkingLotView);
	}

	@FXML
	void GoSetParkingLotIsFull(ActionEvent event) {
		NavigateTo(workerMainViewRegion.getScene(), UriDictionary.WorkerGui.ParkingLotFullView);
	}

	@FXML
	void GoReserveParkingSpace(ActionEvent event) {
		NavigateTo(workerMainViewRegion.getScene(), UriDictionary.WorkerGui.ReserveParkingSpaceView);
	}

	@FXML
	void GoCancelOrder(ActionEvent event) {
		NavigateTo(workerMainViewRegion.getScene(), UriDictionary.WorkerGui.CancelCustomerOrderView);
	}

	@FXML
	void GoAcquitOrChargeAccount(ActionEvent event) {
		NavigateTo(workerMainViewRegion.getScene(), UriDictionary.WorkerGui.AcquitOrChargeAccountView);
	}

	@FXML
	void GoComplaintsPortal(ActionEvent event) {
		NavigateTo(workerMainViewRegion.getScene(), UriDictionary.WorkerGui.ComplaintsPortalView);
	}

	@FXML
	void GoRatesRequestsPortal(ActionEvent event) {
		NavigateTo(workerMainViewRegion.getScene(), UriDictionary.WorkerGui.ApproveRatesRequestsPortalView);
	}

	private void LogoutFromSystem() {
		workerAccountManager.Logout();
	}

	@Override
	public void handleLogout() {
		Platform.runLater(() -> {
			NavigateTo(workerMainViewRegion.getScene(), UriDictionary.WorkerGui.ClientView);
			openLogoutDialog("");
		});
	}

	@Override
	public void handleLogin() {
		Platform.runLater(() -> {
			loginDialog.close();
		});
	}

	private void openLogoutDialog(String workerId) {
		Platform.runLater(() -> {
			if (!workerAccountManager.isWorkerLoggedIn()) {
				loginDialog = new CpsLoginDialog(new Pair<String, String>(workerId, ""), null);
				Pair<String, String> login = loginDialog.showAndWait().orElse(new Pair<String, String>("", ""));
				if (login.getKey() != "" && login.getValue() != "" && login.getKey().matches(CpsRegEx.IntegerBetweenMinAndMaxLength)) {
					workerAccountManager.Login(Integer.parseInt(login.getKey()), login.getValue());
				}

				openLogoutDialog(login.getKey());
			}
		});
	}
}
