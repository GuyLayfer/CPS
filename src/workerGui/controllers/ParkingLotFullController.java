package workerGui.controllers;

import org.controlsfx.validation.ValidationSupport;
import org.controlsfx.validation.Validator;

import core.guiUtilities.IServerResponseHandler;
import core.worker.WorkerOperations;
import core.worker.WorkerRequestType;
import core.worker.requests.BaseRequest;
import core.worker.responses.ParkingLotsNamesResponse;
import core.worker.responses.WorkerBaseResponse;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import workerGui.util.WorkerAccountManager;
import workerGui.util.WorkerConnectionManager;
import workerGui.util.WorkerRequestsFactory;

// TODO: Auto-generated Javadoc
/**
 * The Class ParkingLotFullController.
 */
public class ParkingLotFullController implements IServerResponseHandler<WorkerBaseResponse> {
	
	/** The validation. */
	private ValidationSupport validation = new ValidationSupport();
	
	/** The connection manager. */
	private WorkerConnectionManager connectionManager;
	
	/** The worker account manager. */
	private WorkerAccountManager workerAccountManager;

	/**
	 * Instantiates a new parking lot full controller.
	 */
	public ParkingLotFullController() {
		connectionManager = WorkerConnectionManager.getInstance();
		connectionManager.addServerMessageListener(this);
		workerAccountManager = WorkerAccountManager.getInstance();
	}

	/**
	 * Initialize.
	 */
	@FXML
	protected void initialize() {
		SetParkingLotIsFullButton.disableProperty().bind(validation.invalidProperty());
		UnsetParkingLotIsFullButton.disableProperty().bind(validation.invalidProperty());
		validation.registerValidator(ParkingLotId, Validator.createEmptyValidator("Parking lot ID is Required"));
		if (workerAccountManager.isOperationAllowed(WorkerOperations.CHANGE_PARKING_LOT)) {
			connectionManager.sendMessageToServer(WorkerRequestsFactory.CreateParkingLotNamesRequest());
			ParkingLotId.setDisable(false);
		} else {
			ParkingLotId.getItems().add(workerAccountManager.getWorkerlotId());
			ParkingLotId.setValue(workerAccountManager.getWorkerlotId());
			ParkingLotId.setDisable(true);
		}
	}

	/** The Set parking lot is full button. */
	@FXML
	private Button SetParkingLotIsFullButton;

	/** The Unset parking lot is full button. */
	@FXML
	private Button UnsetParkingLotIsFullButton;

	/** The Parking lot id. */
	@FXML
	private ComboBox<Integer> ParkingLotId;

	/**
	 * Sets the parking lot is full.
	 *
	 * @param event the event
	 */
	@FXML
	public void SetParkingLotIsFull(ActionEvent event) {
		SendRequest(true);
	}

	/**
	 * Unset parking lot is full.
	 *
	 * @param event the event
	 */
	@FXML
	public void UnsetParkingLotIsFull(ActionEvent event) {
		SendRequest(false);
	}

	/**
	 * Send request.
	 *
	 * @param setFull the set full
	 */
	private void SendRequest(Boolean setFull) {
		BaseRequest request = WorkerRequestsFactory.CreateParkingLotFullRequest(setFull, ParkingLotId.getValue());
		connectionManager.sendMessageToServer(request);
	}

	/* (non-Javadoc)
	 * @see core.guiUtilities.IServerResponseHandler#handleServerResponse(java.lang.Object)
	 */
	@Override
	public void handleServerResponse(WorkerBaseResponse response) {
		if (response.requestType == WorkerRequestType.PARKING_LOT_NAMES) {
			Platform.runLater(() -> {
				ParkingLotsNamesResponse parkingLotNames = (ParkingLotsNamesResponse) response;
				ParkingLotId.getItems().clear();
				ParkingLotId.getItems().addAll(parkingLotNames.lotNames);
				if (!parkingLotNames.lotNames.isEmpty()) {
					ParkingLotId.setValue(parkingLotNames.lotNames.get(0));
				}
			});
		}
	}
}
