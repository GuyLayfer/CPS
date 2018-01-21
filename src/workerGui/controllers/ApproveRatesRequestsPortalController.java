package workerGui.controllers;

import java.util.List;

import org.controlsfx.control.table.TableRowExpanderColumn;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.layout.GridPane;
import workerGui.models.ApproveRatesRequestsPortalModel;
import workerGui.util.RatesUiElement;
import workerGui.util.WorkerGuiController;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.geometry.Insets;

// TODO: Auto-generated Javadoc
/**
 * The Class ApproveRatesRequestsPortalController.
 */
public class ApproveRatesRequestsPortalController extends WorkerGuiController implements IAddItemsToTable<RatesUiElement> {
	
	/** The model. */
	private ApproveRatesRequestsPortalModel model;

	/**
	 * Instantiates a new approve rates requests portal controller.
	 */
	public ApproveRatesRequestsPortalController() {
		model = new ApproveRatesRequestsPortalModel(this);
	}
	
	/** The Rates requests. */
	@FXML
	private GridPane RatesRequests;

	/** The Rates table. */
	@FXML
	private TableView<RatesUiElement> RatesTable;

	/**
	 * Initialize.
	 */
	@FXML
	@SuppressWarnings("unchecked")
	protected void initialize() {
		TableRowExpanderColumn<RatesUiElement> expanderColumn = new TableRowExpanderColumn<>(this::createEditor);
		TableColumn<RatesUiElement, Integer> idColumn = new TableColumn<>("Parking Lot ID");
		idColumn.setCellValueFactory(new PropertyValueFactory<>("parkingLotId"));
		TableColumn<RatesUiElement, Double> occasionalColumn = new TableColumn<>("Occasional");
		occasionalColumn.setCellValueFactory(new PropertyValueFactory<>("occasionalParkingRate"));
		TableColumn<RatesUiElement, Double> preOrderedColumn = new TableColumn<>("Pre Ordered");
		preOrderedColumn.setCellValueFactory(new PropertyValueFactory<>("preOrderedParkingRate"));
		TableColumn<RatesUiElement, Double> routinelColumn = new TableColumn<>("Routine Subscription");
		routinelColumn.setCellValueFactory(new PropertyValueFactory<>("routineMonthlySubscriptionRate"));
		TableColumn<RatesUiElement, Double> multipleroutinelColumn = new TableColumn<>("Multiple Cars Subscription");
		multipleroutinelColumn.setCellValueFactory(new PropertyValueFactory<>("routineMonthlySubscriptionMultipleCarsRate"));
		TableColumn<RatesUiElement, Double> fullMonthlyColumn = new TableColumn<>("Full Subscription");
		fullMonthlyColumn.setCellValueFactory(new PropertyValueFactory<>("fullMonthlySubscriptionRate"));

		RatesTable.setPlaceholder(new Label("No pending Rates Requests. Please come back later."));
		RatesTable.getColumns().addAll(expanderColumn, idColumn, occasionalColumn, preOrderedColumn, routinelColumn, multipleroutinelColumn, fullMonthlyColumn);
		model.sendRequestForPendingRatesRequests();
	}

	/* (non-Javadoc)
	 * @see workerGui.controllers.IAddItemsToTable#AddToTable(java.util.List)
	 */
	@Override
	public void AddToTable(List<RatesUiElement> pendingItems) {
		RatesTable.setItems(FXCollections.observableArrayList(pendingItems));
		showNotification("Youv'e got " + pendingItems.size() + " pending rates requests.");
		
	}

	/**
	 * Creates the editor.
	 *
	 * @param param the param
	 * @return the grid pane
	 */
	private GridPane createEditor(TableRowExpanderColumn.TableRowDataFeatures<RatesUiElement> param) {
		GridPane editor = new GridPane();
		editor.setPadding(new Insets(10));
		editor.setHgap(10);
		editor.setVgap(5);

		RatesUiElement rates = param.getValue();

		TextField parkingLotIdField = new TextField(Integer.toString(rates.getParkingLotId()));
		parkingLotIdField.setEditable(false);
		TextField occasionalParkingRateField = new TextField(Double.toString(rates.getOccasionalParkingRate()));
		occasionalParkingRateField.setEditable(false);
		TextField preOrderedParkingRateField = new TextField(Double.toString(rates.getPreOrderedParkingRate()));
		preOrderedParkingRateField.setEditable(false);
		TextField routineMonthlySubscriptionRateField = new TextField(Double.toString(rates.getRoutineMonthlySubscriptionRate()));
		routineMonthlySubscriptionRateField.setEditable(false);
		TextField monthlyMultipleCarsSubscriptionRateField = new TextField(Double.toString(rates.getRoutineMonthlySubscriptionMultipleCarsRate()));
		monthlyMultipleCarsSubscriptionRateField.setEditable(false);
		TextField fullMonthlySubscriptionRateField = new TextField(Double.toString(rates.getFullMonthlySubscriptionRate()));
		fullMonthlySubscriptionRateField.setEditable(false);

		editor.addRow(0, new Label("Parking Lot ID:"), parkingLotIdField);
		editor.addRow(1, new Label("Occasional Parking Rate:"), occasionalParkingRateField);
		editor.addRow(2, new Label("Pre Ordered Parking Rate:"), preOrderedParkingRateField);
		editor.addRow(3, new Label("Routine Monthly Subscription Rate:"), routineMonthlySubscriptionRateField);
		editor.addRow(4, new Label("Monthly Multiple Cars Subscription Rate:"), monthlyMultipleCarsSubscriptionRateField);
		editor.addRow(5, new Label("Full Monthly Subscription Rate:"), fullMonthlySubscriptionRateField);

		Button approveButton = new Button("Approve");
		approveButton.setOnAction(event -> {
			model.SendApproveRates(param.getValue());
			RatesTable.getItems().remove(rates);
		});

		Button declineButton = new Button("Decline");
		declineButton.setOnAction(event -> {
			model.SendDeclineRates(rates);
			RatesTable.getItems().remove(rates);
		});

		editor.addRow(6, approveButton, declineButton);
		return editor;
	}
}
