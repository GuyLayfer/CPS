package workerGui.controllers;

import org.controlsfx.validation.ValidationSupport;
import org.controlsfx.validation.Validator;

import core.worker.requests.BaseRequest;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import workerGui.util.WorkerConnectionManager;
import workerGui.util.WorkerRequestsFactory;

// TODO: Auto-generated Javadoc
/**
 * The Class InitializeParkingLotController.
 */
public class InitializeParkingLotController {
	
	/** The validation. */
	private ValidationSupport validation = new ValidationSupport();
	
	/** The connection manager. */
	private WorkerConnectionManager connectionManager;

	/**
	 * Instantiates a new initialize parking lot controller.
	 */
	public InitializeParkingLotController() {
		connectionManager = WorkerConnectionManager.getInstance();
	}

	/**
	 * Initialize.
	 */
	@FXML
	protected void initialize() {
		InitializeParkingLotButton.disableProperty().bind(validation.invalidProperty());
		validation.registerValidator(LotColumnComboBox, Validator.createEmptyValidator("Lot column size is required"));
		SetRowComboBoxItems();
	}

	/** The Initialize parking lot button. */
	@FXML
	private Button InitializeParkingLotButton;

	/** The Lot column combo box. */
	@FXML
	private ComboBox<Integer> LotColumnComboBox;

	/**
	 * Initialize parking lot.
	 *
	 * @param event the event
	 */
	@FXML
	void InitializeParkingLot(ActionEvent event) {
		BaseRequest request = WorkerRequestsFactory.CreateInitializeParkingLotRequest(LotColumnComboBox.getValue());
		connectionManager.sendMessageToServer(request);
	}

	/**
	 * Sets the row combo box items.
	 */
	private void SetRowComboBoxItems() {
		LotColumnComboBox.getItems().addAll(4, 5, 6, 7, 8);
	}
}
