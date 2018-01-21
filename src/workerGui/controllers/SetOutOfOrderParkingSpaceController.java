package workerGui.controllers;

import workerGui.util.WorkerAccountManager;
import workerGui.util.WorkerConnectionManager;
import workerGui.util.WorkerGuiController;
import workerGui.util.WorkerRequestsFactory;

import org.controlsfx.validation.ValidationSupport;
import org.controlsfx.validation.Validator;

import core.guiUtilities.IServerResponseHandler;
import core.worker.WorkerOperations;
import core.worker.WorkerRequestType;
import core.worker.requests.BaseRequest;
import core.worker.responses.ParkingLotInfoResponse;
import core.worker.responses.ParkingLotsNamesResponse;
import core.worker.responses.WorkerBaseResponse;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;

// TODO: Auto-generated Javadoc
/**
 * The Class SetOutOfOrderParkingSpaceController.
 */
public class SetOutOfOrderParkingSpaceController extends WorkerGuiController implements IServerResponseHandler<WorkerBaseResponse>{
	
	/** The validation. */
	private ValidationSupport validation = new ValidationSupport();
	
	/** The connection manager. */
	private WorkerConnectionManager connectionManager;
	
	/** The worker account manager. */
	private WorkerAccountManager workerAccountManager;

	/**
	 * Instantiates a new sets the out of order parking space controller.
	 */
	public SetOutOfOrderParkingSpaceController() {
		connectionManager = WorkerConnectionManager.getInstance();
		connectionManager.addServerMessageListener(this);
		workerAccountManager = WorkerAccountManager.getInstance();
	}

	/**
	 * Initialize.
	 */
	@FXML
	protected void initialize() {
		SetOutOfOrderButton.disableProperty().bind(validation.invalidProperty());
		validation.registerValidator(ParkingLotId, Validator.createEmptyValidator("Parking lot ID is Required"));
		validation.registerValidator(LotColumnComboBox, Validator.createEmptyValidator("Lot space column is Required"));
		validation.registerValidator(LotFloorComboBox, Validator.createEmptyValidator("Lot space floor is Required"));
		validation.registerValidator(LotRowComboBox, Validator.createEmptyValidator("Lot space row is Required"));
		ParkingLotId.valueProperty().addListener(new ChangeListener<Integer>() {
			@Override
			public void changed(ObservableValue<? extends Integer> observable, Integer oldValue, Integer newValue) {
				connectionManager.sendMessageToServer(WorkerRequestsFactory.createParkingLotInfoRequest(newValue));
			}
		});
		SetParkingLotIdItems();
		SetLotFloorComboBoxItems();
		SetLotRowComboBoxItems();
	}

	/** The Parking lot id. */
	@FXML
	private ComboBox<Integer> ParkingLotId;

	/** The Lot column combo box. */
	@FXML
	private ComboBox<Integer> LotColumnComboBox;

	/** The Lot floor combo box. */
	@FXML
	private ComboBox<Integer> LotFloorComboBox;

	/** The Lot row combo box. */
	@FXML
	private ComboBox<Integer> LotRowComboBox;

	/** The Set out of order button. */
	@FXML
	private Button SetOutOfOrderButton;

	/** The Unset out of order button. */
	@FXML
	private Button UnsetOutOfOrderButton;

	/**
	 * Sets the out of order parking space.
	 *
	 * @param event the new out of order parking space
	 */
	@FXML
	private void setOutOfOrderParkingSpace(ActionEvent event) {
		sendOutOfOrderRequest(true);
	}
	
	/**
	 * Unset out of order parking space.
	 *
	 * @param event the event
	 */
	@FXML
	private void unsetOutOfOrderParkingSpace(ActionEvent event) {
		sendOutOfOrderRequest(false);
	}
	
	/**
	 * Send out of order request.
	 *
	 * @param isOutOfOrder the is out of order
	 */
	private void sendOutOfOrderRequest(Boolean isOutOfOrder) {
		BaseRequest request = WorkerRequestsFactory.CreateOutOfOrderRequest(
				ParkingLotId.getValue(),
				LotRowComboBox.getValue(),
				LotColumnComboBox.getValue(),
				LotFloorComboBox.getValue(),
				isOutOfOrder);
		connectionManager.sendMessageToServer(request);
	}

	/**
	 * Sets the parking lot id items.
	 */
	private void SetParkingLotIdItems() {
		if (workerAccountManager.isOperationAllowed(WorkerOperations.CHANGE_PARKING_LOT)) {
			connectionManager.sendMessageToServer(WorkerRequestsFactory.CreateParkingLotNamesRequest());
			ParkingLotId.setDisable(false);
		} else {
			ParkingLotId.getItems().add(workerAccountManager.getWorkerlotId());
			ParkingLotId.setValue(workerAccountManager.getWorkerlotId());
			ParkingLotId.setDisable(true);
		}
	}

	/**
	 * Sets the lot floor combo box items.
	 */
	private void SetLotFloorComboBoxItems() {
		LotFloorComboBox.getItems().addAll(1, 2, 3);
	}

	/**
	 * Sets the lot row combo box items.
	 */
	private void SetLotRowComboBoxItems() {
		LotRowComboBox.getItems().addAll(1, 2, 3);
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

		if (response.requestType == WorkerRequestType.PARKING_LOT_INFO) {
			Platform.runLater(() -> {
				ParkingLotInfoResponse parkingLotNames = (ParkingLotInfoResponse) response;
				LotColumnComboBox.getItems().clear();
				for (int i = 1; i <= parkingLotNames.parkingLotInfo.cols; i++) {
					LotColumnComboBox.getItems().add(i);
				}
			});
		}
	}
}
