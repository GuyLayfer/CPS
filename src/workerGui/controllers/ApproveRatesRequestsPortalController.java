package workerGui.controllers;

import java.util.ArrayList;

import org.controlsfx.control.table.TableRowExpanderColumn;

import core.worker.Rates;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.layout.GridPane;
import workerGui.models.ApproveRatesRequestsPortalModel;
import workerGui.util.RatesUiElement;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.geometry.Insets;

public class ApproveRatesRequestsPortalController {
	private ApproveRatesRequestsPortalModel model;

	public ApproveRatesRequestsPortalController() {
		model = new ApproveRatesRequestsPortalModel();
	}
	
	@FXML
	private GridPane RatesRequests;

	@FXML
	private TableView<RatesUiElement> RatesTable;

	@FXML
	@SuppressWarnings("unchecked")
	protected void initialize() {
		TableRowExpanderColumn<RatesUiElement> expanderColumn = new TableRowExpanderColumn<>(this::createEditor);
		TableColumn<RatesUiElement, Integer> idColumn = new TableColumn<>("Parking Lot ID");
		idColumn.setCellValueFactory(new PropertyValueFactory<>("parkingLotId"));
		TableColumn<RatesUiElement, Double> occasionalColumn = new TableColumn<>("Occasional Parking Rate");
		occasionalColumn.setCellValueFactory(new PropertyValueFactory<>("occasionalParkingRate"));
		TableColumn<RatesUiElement, Double> preOrderedColumn = new TableColumn<>("Pre Ordered Parking Rate");
		preOrderedColumn.setCellValueFactory(new PropertyValueFactory<>("preOrderedParkingRate"));
		TableColumn<RatesUiElement, Double> routinelColumn = new TableColumn<>("Routine Monthly Subscription");
		routinelColumn.setCellValueFactory(new PropertyValueFactory<>("routineMonthlySubscriptionRate"));
		TableColumn<RatesUiElement, Double> fullMonthlyColumn = new TableColumn<>("Full Monthly Subscription");
		fullMonthlyColumn.setCellValueFactory(new PropertyValueFactory<>("fullMonthlySubscriptionRate"));

		RatesTable.setPlaceholder(new Label("No pending Rates Requests. Please come back later."));
		RatesTable.getColumns().addAll(expanderColumn, idColumn, occasionalColumn, preOrderedColumn, routinelColumn, fullMonthlyColumn);
		RatesTable.setItems(FXCollections.observableArrayList(getExample()));
	}

	public void AddToTableWhenApplicable() {
		RatesTable.setItems(FXCollections.observableArrayList(model.GetPendingRatesRequests()));
	}

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
		occasionalParkingRateField.setEditable(false);
		TextField routineMonthlySubscriptionRateField = new TextField(Double.toString(rates.getRoutineMonthlySubscriptionRate()));
		occasionalParkingRateField.setEditable(false);
		TextField fullMonthlySubscriptionRateField = new TextField(Double.toString(rates.getFullMonthlySubscriptionRate()));
		occasionalParkingRateField.setEditable(false);

		editor.addRow(0, new Label("Parking Lot ID:"), parkingLotIdField);
		editor.addRow(1, new Label("Occasional Parking Rate:"), occasionalParkingRateField);
		editor.addRow(2, new Label("Pre Ordered Parking Rate:"), preOrderedParkingRateField);
		editor.addRow(3, new Label("Routine Monthly Subscription:"), routineMonthlySubscriptionRateField);
		editor.addRow(4, new Label("Full Monthly Subscription"), fullMonthlySubscriptionRateField);

		Button approveButton = new Button("Approve");
		approveButton.setOnAction(event -> {
			model.SendApproveRates(param.getValue());
			RatesTable.getItems().remove(param.getValue());
		});

		Button declineButton = new Button("Decline");
		declineButton.setOnAction(event -> {
			Object obj = rates;
			System.out.println(obj);
			model.SendDeclineRates(rates);
			RatesTable.getItems().remove(rates);
		});

		editor.addRow(5, approveButton, declineButton);

		return editor;
	}

	private ArrayList<RatesUiElement> getExample() {
		ArrayList<RatesUiElement> list = new ArrayList<RatesUiElement>();
		list.add(new RatesUiElement(new Rates(1, 5, 6, 20, 30)));
		return list;
	}
}
