package workerGui.controllers;

import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.controlsfx.validation.ValidationSupport;
import org.controlsfx.validation.Validator;

import core.guiUtilities.IServerResponseHandler;
import core.worker.ReportItem;
import core.worker.WorkerOperations;
import core.worker.WorkerRequestType;
import core.worker.responses.ParkingLotsNamesResponse;
import core.worker.responses.WorkerBaseResponse;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import workerGui.models.ReportsModel;
import workerGui.util.ReportItemUiElement;
import workerGui.util.WorkerAccountManager;
import workerGui.util.WorkerConnectionManager;
import workerGui.util.WorkerRequestsFactory;

public class ReportsController implements IAddItemsToTable<ReportItemUiElement>, IServerResponseHandler<WorkerBaseResponse> {
	private ValidationSupport validation = new ValidationSupport();
	private WorkerConnectionManager connectionManager;
	private WorkerAccountManager workerAccountManager;
	private ReportsModel model;

	public ReportsController() {
		model = new ReportsModel(this);
		workerAccountManager = WorkerAccountManager.getInstance();
		connectionManager = WorkerConnectionManager.getInstance();
		connectionManager.addServerMessageListener(this);
	}

	@FXML
	@SuppressWarnings("unchecked")
	private void initialize() {
		TableColumn<ReportItemUiElement, String> reportDescription = new TableColumn<>("Description");
		reportDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
		TableColumn<ReportItemUiElement, String> reportDetail = new TableColumn<>("Detail");
		reportDetail.setCellValueFactory(new PropertyValueFactory<>("detail"));

		reportsTable.setPlaceholder(new Label("Choose parameters for the report."));
		reportsTable.getColumns().addAll(reportDescription, reportDetail);
		reportsTable.setItems(FXCollections.observableArrayList(getExample()));

		showReportButton.disableProperty().bind(validation.invalidProperty());
		startToEndSection.managedProperty().bind(startToEndSection.visibleProperty());
		selectParkingLotSection.managedProperty().bind(selectParkingLotSection.visibleProperty());

		if (workerAccountManager.isOperationAllowed(WorkerOperations.CHANGE_PARKING_LOT)) {
			connectionManager.sendMessageToServer(WorkerRequestsFactory.CreateParkingLotNamesRequest());
			ParkingLotId.setDisable(false);
		} else {
			ParkingLotId.getItems().add(workerAccountManager.getWorkerlotId());
			ParkingLotId.setValue(workerAccountManager.getWorkerlotId());
			ParkingLotId.setDisable(true);
		}

		reportHeader.setText(model.getReportName());
		if (!model.isDateRangeRequired()) {
			startToEndSection.setVisible(false);
		} else {
			startToEndSection.setVisible(true);
			validation.registerValidator(startDate, Validator.createEmptyValidator("Start date is required"));
			validation.registerValidator(endDate, Validator.createEmptyValidator("End date is required"));
		}

		if (!model.isSelectParkingLotIdRequired()) {
			selectParkingLotSection.setVisible(false);
		} else {
			selectParkingLotSection.setVisible(true);
			validation.registerValidator(ParkingLotId, Validator.createEmptyValidator("Parking Lot id is required"));
		}
	}

	@FXML
	private DatePicker endDate;

	@FXML
	private HBox startToEndSection;

	@FXML
	private HBox selectParkingLotSection;

	@FXML
	private TableView<ReportItemUiElement> reportsTable;

	@FXML
	private DatePicker startDate;

	@FXML
	private Button showReportButton;

	@FXML
	private Text reportHeader;

	@FXML
	private ComboBox<Integer> ParkingLotId;

	@FXML
	void showReport(ActionEvent event) {
		if (startDate.getValue() == null || endDate.getValue() == null) {
			model.sendReportRequest(new Date(), new Date(), ParkingLotId.getValue());
		} else {
			model.sendReportRequest(
					Date.from(startDate.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant()),
					Date.from(endDate.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant()),
					ParkingLotId.getValue());
		}
	}

	private ArrayList<ReportItemUiElement> getExample() {
		ArrayList<ReportItemUiElement> list = new ArrayList<ReportItemUiElement>();
		list.add(new ReportItemUiElement(new ReportItem("gla:", GetLoremIpsum2())));
		list.add(new ReportItemUiElement(new ReportItem("blaaa:", GetLoremIpsum3())));
		return list;
	}

	private String GetLoremIpsum2() {
		return "Ut condimentum placerat quam vel eleifend. Suspendisse pretium pulvinar nulla et sodales. Vivamus id magna varius erat sollicitudin dapibus non vel neque. Etiam sapien nisi, imperdiet sed luctus nec, porttitor non tellus. Duis ipsum mi, commodo id magna non,"
				+ "\r\n"
				+ "efficitur vulputate velit. Nunc nec consectetur nulla. Integer vestibulum vehicula velit, ac finibus sapien ultrices ac. Maecenas in ultrices ipsum.\r\n"
				+ "\r\n"
				+ "Duis pellentesque nisi tincidunt neque rutrum, ac maximus erat egestas. Nulla ut placerat dui, vitae molestie nibh. Donec nibh leo, feugiat at sapien nec, malesuada interdum magna. Nulla malesuada, enim at feugiat sollicitudin, nibh quam efficitur turpis, eu fermentum nisi felis sed felis."
				+ "\r\n"
				+ "Praesent lacinia velit et laoreet ullamcorper. Vivamus lobortis ante magna, a facilisis leo ullamcorper eu. Fusce finibus, tellus vel tristique porta, lacus lacus iaculis sapien, ac porta ligula urna quis orci. Phasellus ullamcorper gravida sem, vel suscipit tellus. Nulla ipsum dui, tristique eu elementum faucibus, varius vel ante."
				+ "\r\n" + "Aliquam porta, lectus non egestas commodo, magna lectus vulputate purus," + "\r\n"
				+ "quis facilisis sapien libero sit amet ipsum. Donec convallis," + "\r\n"
				+ "velit non varius finibus, lorem velit cursus est, vitae ultricies turpis ligula nec lorem." + "\r\n"
				+ "Orci varius natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus." + "\r\n"
				+ "Aliquam ut vehicula erat. Morbi scelerisque metus id imperdiet eleifend. " + "\r\n" + "Donec ut est consectetur, porta justo quis, fringilla erat.";
	}

	private String GetLoremIpsum3() {
		return "So there I was, in the middle of the parking lot, looking for my car, when this DAMN ROBOT TRIED TO INCINERATE ME!!"
				+ "\nThis is unacceptable. I want my car back and I want that robot fired.";
	}

	@Override
	public void AddToTable(List<ReportItemUiElement> pendingItems) {
		reportsTable.setItems(FXCollections.observableArrayList(pendingItems));
	}

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
