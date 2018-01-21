package workerGui.controllers;

import org.controlsfx.validation.Severity;
import org.controlsfx.validation.ValidationSupport;
import org.controlsfx.validation.Validator;

import core.guiUtilities.CpsRegEx;
import core.guiUtilities.FloatNumberTextField;
import core.guiUtilities.IServerResponseHandler;
import core.worker.WorkerRequestType;
import core.worker.WorkerRole;
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
 * The Class UpdateRatesRequestController.
 */
public class UpdateRatesRequestController implements IServerResponseHandler<WorkerBaseResponse>{
	
	/** The validation. */
	private ValidationSupport validation = new ValidationSupport();
	
	/** The connection manager. */
	private WorkerConnectionManager connectionManager;
	
	/** The worker account manager. */
	private WorkerAccountManager workerAccountManager;

	/**
	 * Instantiates a new update rates request controller.
	 */
	public UpdateRatesRequestController() {
		connectionManager = WorkerConnectionManager.getInstance();
		connectionManager.addServerMessageListener(this);
		workerAccountManager = WorkerAccountManager.getInstance();
	}

	/**
	 * Initialize.
	 */
	@FXML
	protected void initialize() {
		orderButton.disableProperty().bind(validation.invalidProperty());
		validation.registerValidator(ParkingLotIdField, Validator.createEmptyValidator("Parking lot ID is Required"));
		validation.registerValidator(RutineMonthlyField, Validator.createRegexValidator("Rate is Required", CpsRegEx.FloatNumber, Severity.ERROR));
		validation.registerValidator(OccasionalRateField, Validator.createRegexValidator("Rate is Required", CpsRegEx.FloatNumber, Severity.ERROR));
		validation.registerValidator(RutineMonthlyMultipleField, Validator.createRegexValidator("Rate is Required", CpsRegEx.FloatNumber, Severity.ERROR));
		validation.registerValidator(FullNonthlyField, Validator.createRegexValidator("Rate is Required", CpsRegEx.FloatNumber, Severity.ERROR));
		validation.registerValidator(PreOrderedField, Validator.createRegexValidator("Rate is Required", CpsRegEx.FloatNumber, Severity.ERROR));
		setParkingLotComboBox();
	}
	
	/** The Rutine monthly field. */
	@FXML
	private FloatNumberTextField RutineMonthlyField;

	/** The Occasional rate field. */
	@FXML
	private FloatNumberTextField OccasionalRateField;

	/** The Rutine monthly multiple field. */
	@FXML
	private FloatNumberTextField RutineMonthlyMultipleField;

	/** The Parking lot id field. */
	@FXML
	private ComboBox<Integer> ParkingLotIdField;

	/** The Full nonthly field. */
	@FXML
	private FloatNumberTextField FullNonthlyField;

	/** The Pre ordered field. */
	@FXML
	private FloatNumberTextField PreOrderedField;

	/** The order button. */
	@FXML
	private Button orderButton;

	/**
	 * Send rates update request.
	 *
	 * @param event the event
	 */
	@FXML
	private void SendRatesUpdateRequest(ActionEvent event) {
		BaseRequest request = WorkerRequestsFactory.CreateUpdateRatesRequest(
				ParkingLotIdField.getValue(),
				OccasionalRateField.getFloat(),
				PreOrderedField.getFloat(),
				RutineMonthlyField.getFloat(),
				RutineMonthlyMultipleField.getFloat(),
				FullNonthlyField.getFloat());
		connectionManager.sendMessageToServer(request);
	}

	/**
	 * Sets the parking lot combo box.
	 */
	private void setParkingLotComboBox() {
		if (workerAccountManager.getWorkerRole() == WorkerRole.FIRM_MANAGER) {
			connectionManager.sendMessageToServer(WorkerRequestsFactory.CreateParkingLotNamesRequest());
			ParkingLotIdField.setDisable(false);
		} else {
			ParkingLotIdField.getItems().add(workerAccountManager.getWorkerlotId());
			ParkingLotIdField.setValue(workerAccountManager.getWorkerlotId());
			ParkingLotIdField.setDisable(true);
		}
	}

	/* (non-Javadoc)
	 * @see core.guiUtilities.IServerResponseHandler#handleServerResponse(java.lang.Object)
	 */
	@Override
	public void handleServerResponse(WorkerBaseResponse response) {
		if (response.requestType == WorkerRequestType.PARKING_LOT_NAMES) {
			Platform.runLater(() -> {
				ParkingLotsNamesResponse parkingLotNames = (ParkingLotsNamesResponse) response;
				ParkingLotIdField.getItems().clear();
				ParkingLotIdField.getItems().addAll(parkingLotNames.lotNames);
				if (!parkingLotNames.lotNames.isEmpty()) {
					ParkingLotIdField.setValue(parkingLotNames.lotNames.get(0));
				}
			});
		}
	}
}
