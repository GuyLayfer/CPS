package workerGui.controllers;

import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.controlsfx.validation.ValidationSupport;
import org.controlsfx.validation.Validator;

import core.worker.ReportItem;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import workerGui.models.ReportsModel;
import workerGui.util.ReportItemUiElement;

public class ReportsController implements IAddItemsToTable<ReportItemUiElement> {
	private ValidationSupport validation = new ValidationSupport();
	private ReportsModel model;

	public ReportsController() {
		model = new ReportsModel(this);
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
		validation.registerValidator(startDate, Validator.createEmptyValidator("Start date is required"));
		validation.registerValidator(endDate, Validator.createEmptyValidator("End date is required"));

		reportHeader.setText(model.getReportName());
		if(!model.isDateRangeRequired()) {
			model.sendReportRequest();
			startToEndSection.setVisible(false);
		} else {
			startToEndSection.setVisible(true);
		}
	}

	@FXML
	private DatePicker endDate;

	@FXML
	private HBox startToEndSection;

	@FXML
	private TableView<ReportItemUiElement> reportsTable;

	@FXML
	private DatePicker startDate;

	@FXML
	private Button showReportButton;
	
	@FXML
	private Text reportHeader;

	@FXML
	void showReport(ActionEvent event) {
		model.sendReportRequest(
				Date.from(startDate.getValue().atStartOfDay(ZoneOffset.UTC).toInstant()),
				Date.from(endDate.getValue().atStartOfDay(ZoneOffset.UTC).toInstant()));
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
}
