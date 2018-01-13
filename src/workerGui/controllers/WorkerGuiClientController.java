package workerGui.controllers;

import core.guiUtilities.UriDictionary;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Hyperlink;
import workerGui.util.ICareAboutLoginState;
import workerGui.util.WorkerAccountManager;
import workerGui.util.WorkerGuiController;

public class WorkerGuiClientController extends WorkerGuiController implements ICareAboutLoginState {
	private WorkerAccountManager workerAccountManager;
	
	@FXML
	protected void initialize() {
		workerAccountManager = WorkerAccountManager.getInstance();
		workerAccountManager.registerLoginListener(this);
	}
	
	@FXML
	private Hyperlink GoParkingLotFullLink;

	@FXML
	private Hyperlink GoDisableParkingSpaceLink;

	@FXML
	private Hyperlink GoReserveParkingSpaceLink;

	@FXML
	private Hyperlink GoInitializeLink;

	@FXML
	void GoInitialize(ActionEvent event) {
		NavigateTo(GoDisableParkingSpaceLink.getScene(), UriDictionary.WorkerGui.InitializeParkingLotView);
	}

	@FXML
	void GoDisableParkingSpaceLot(ActionEvent event) {
		NavigateTo(GoDisableParkingSpaceLink.getScene(), UriDictionary.WorkerGui.SetOutOfOrderParkingSpaceView);
	}

	@FXML
	void GoParkingLotFull(ActionEvent event) {
		NavigateTo(GoDisableParkingSpaceLink.getScene(), UriDictionary.WorkerGui.ParkingLotFullView);
	}

	@FXML
	void GoReserveParkingSpace(ActionEvent event) {
		NavigateTo(GoDisableParkingSpaceLink.getScene(), UriDictionary.WorkerGui.ReserveParkingSpaceView);
	}

	@Override
	public void handleLogout() {
		// TODO Auto-generated method stub

	}

	@Override
	public void handleLogin() {
		// TODO Auto-generated method stub

	}
}
