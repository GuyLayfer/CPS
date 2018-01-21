package workerGui.controllers;

import core.guiUtilities.UriDictionary;
import core.worker.WorkerOperations;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Hyperlink;
import workerGui.util.ICareAboutLoginState;
import workerGui.util.WorkerAccountManager;
import workerGui.util.WorkerGuiController;

// TODO: Auto-generated Javadoc
/**
 * The Class WorkerGuiClientController.
 */
public class WorkerGuiClientController extends WorkerGuiController implements ICareAboutLoginState {
	
	/** The worker account manager. */
	private WorkerAccountManager workerAccountManager;
	
	/**
	 * Initialize.
	 */
	@FXML
	protected void initialize() {
		workerAccountManager = WorkerAccountManager.getInstance();
		workerAccountManager.registerLoginListener(this);
	}
	
	/** The Go parking lot full link. */
	@FXML
	private Hyperlink GoParkingLotFullLink;

	/** The Go disable parking space link. */
	@FXML
	private Hyperlink GoDisableParkingSpaceLink;

	/** The Go reserve parking space link. */
	@FXML
	private Hyperlink GoReserveParkingSpaceLink;

	/** The Go initialize link. */
	@FXML
	private Hyperlink GoInitializeLink;

	/**
	 * Go initialize.
	 *
	 * @param event the event
	 */
	@FXML
	void GoInitialize(ActionEvent event) {
		NavigateTo(GoDisableParkingSpaceLink.getScene(), UriDictionary.WorkerGui.InitializeParkingLotView);
	}

	/**
	 * Go disable parking space lot.
	 *
	 * @param event the event
	 */
	@FXML
	void GoDisableParkingSpaceLot(ActionEvent event) {
		NavigateTo(GoDisableParkingSpaceLink.getScene(), UriDictionary.WorkerGui.SetOutOfOrderParkingSpaceView);
	}

	/**
	 * Go parking lot full.
	 *
	 * @param event the event
	 */
	@FXML
	void GoParkingLotFull(ActionEvent event) {
		NavigateTo(GoDisableParkingSpaceLink.getScene(), UriDictionary.WorkerGui.ParkingLotFullView);
	}

	/**
	 * Go reserve parking space.
	 *
	 * @param event the event
	 */
	@FXML
	void GoReserveParkingSpace(ActionEvent event) {
		NavigateTo(GoDisableParkingSpaceLink.getScene(), UriDictionary.WorkerGui.ReserveParkingSpaceView);
	}

	/* (non-Javadoc)
	 * @see workerGui.util.ICareAboutLoginState#handleLogout()
	 */
	@Override
	public void handleLogout() {
	}

	/* (non-Javadoc)
	 * @see workerGui.util.ICareAboutLoginState#handleLogin()
	 */
	@Override
	public void handleLogin() {
		setPermissions();
	}
	
	/**
	 * Sets the permissions.
	 */
	private void setPermissions() {
		GoParkingLotFullLink.setDisable(!workerAccountManager.isOperationAllowed(WorkerOperations.PARKING_LOT_FULL));
		GoDisableParkingSpaceLink.setDisable(!workerAccountManager.isOperationAllowed(WorkerOperations.OUT_OF_ORDER));
		GoReserveParkingSpaceLink.setDisable(!workerAccountManager.isOperationAllowed(WorkerOperations.RESERVE_PARKING_SPACE));
		GoInitializeLink.setDisable(!workerAccountManager.isOperationAllowed(WorkerOperations.INITIALIZE_PARKING_LOT));
	}
}
