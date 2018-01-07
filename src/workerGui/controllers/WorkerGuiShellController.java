package workerGui.controllers;

import core.guiUtilities.UriDictionary;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.AnchorPane;
import workerGui.util.LogoutDialog;
import workerGui.util.WorkerGuiController;

public class WorkerGuiShellController extends WorkerGuiController {
	private static final String IDLE_BUTTON_STYLE = "-fx-background-color: transparent;";
	private static final String HOVERED_BUTTON_STYLE = "-fx-background-color: #0096C9";

	@FXML
	private AnchorPane workerMainViewRegion;

	@FXML
	private Button LogoutButton;

	@FXML
	protected void initialize() {
		LogoutButton.setOnMouseEntered(e -> LogoutButton.setStyle(HOVERED_BUTTON_STYLE));
		LogoutButton.setOnMouseExited(e -> LogoutButton.setStyle(IDLE_BUTTON_STYLE));
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
		Alert logout = LogoutDialog.GetLogOotConfirmatio();

		logout.showAndWait().filter(response -> response == ButtonType.YES).ifPresent(response -> LogoutFromSystem());
	}

	@FXML
	void InitializeParkingLot(ActionEvent event) {

	}

	@FXML
	void GoSetParkingLotIsFull(ActionEvent event) {

	}

	@FXML
	void GoReserveParkingSpace(ActionEvent event) {

	}

	@FXML
	void GoCancelOrder(ActionEvent event) {

	}

	@FXML
	void GoChargeAccount(ActionEvent event) {

	}

	@FXML
	void GoComplaintsPortal(ActionEvent event) {

	}

	@FXML
	void GoNewRatesRequests(ActionEvent event) {

	}

	private void LogoutFromSystem() {
		System.out.println("log out");
	}
}
