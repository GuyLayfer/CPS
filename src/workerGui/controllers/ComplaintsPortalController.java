package workerGui.controllers;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.controlsfx.control.table.TableRowExpanderColumn;

import core.guiUtilities.NumberTextField;
import core.worker.Complaint;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import workerGui.models.ComplaintsPortalModel;
import workerGui.util.ComplaintUiElement;
import workerGui.util.WorkerGuiController;

public class ComplaintsPortalController extends WorkerGuiController implements IAddItemsToTable<ComplaintUiElement>{
	private ComplaintsPortalModel model;

	public ComplaintsPortalController() {
		model = new ComplaintsPortalModel(this);
	}

	@FXML
	private GridPane CustomersComplaints;

	@FXML
	private TableView<ComplaintUiElement> ComplaintsTable;

	@FXML
	@SuppressWarnings("unchecked")
	protected void initialize() {
		TableRowExpanderColumn<ComplaintUiElement> expanderColumn = new TableRowExpanderColumn<>(this::createEditor);
		TableColumn<ComplaintUiElement, String> timeLeftColumn = new TableColumn<>("Time Left To Reply");
		TableColumn<ComplaintUiElement, Integer> customerId = new TableColumn<>("Customer ID");
		timeLeftColumn.setCellValueFactory(new PropertyValueFactory<>("timeLeftToReply"));
		TableColumn<ComplaintUiElement, String> contentColumn = new TableColumn<>("Brief Content");
		contentColumn.setCellValueFactory(new PropertyValueFactory<>("briefContent"));


		ComplaintsTable.setPlaceholder(new Label("No pending Rates Requests. Please come back later."));
		ComplaintsTable.getColumns().addAll(expanderColumn, timeLeftColumn, customerId, contentColumn);
		ComplaintsTable.setItems(FXCollections.observableArrayList(getExample()));
		model.sendRequestForPendingComplaintsRequests();
	}
	
	@Override
	public void AddToTable(List<ComplaintUiElement> pendingItems) {
		ComplaintsTable.setItems(FXCollections.observableArrayList(pendingItems));
	}

	private GridPane createEditor(TableRowExpanderColumn.TableRowDataFeatures<ComplaintUiElement> param) {
		GridPane editor = new GridPane();
		editor.setPadding(new Insets(10));
		editor.setHgap(10);
		editor.setVgap(5);

		ComplaintUiElement complaint = param.getValue();

		TextArea contentTextArea = new TextArea(complaint.getContent());
		contentTextArea.setEditable(false);
		TextField CustomerIdTextField = new TextField(Integer.toString(complaint.getCustomerId()));
		CustomerIdTextField.setEditable(false);
		CustomerIdTextField.setMaxWidth(150);
		TextField timeLeftToReplyField = new TextField(complaint.getTimeLeftToReply());
		timeLeftToReplyField.setEditable(false);
		timeLeftToReplyField.setMaxWidth(150);

		editor.addRow(0, new Label("Complaint Content:"), contentTextArea);
		editor.addRow(1, new Label("Customer ID:"), CustomerIdTextField);
		editor.addRow(2, new Label("Time Left To reply:"), timeLeftToReplyField);

		Button approveButton = new Button("Approve");
		approveButton.setOnAction(event -> {
			model.ApproveComplaint(param.getValue());
			ComplaintsTable.getItems().remove(complaint);
		});

		Button declineButton = new Button("Decline");
		declineButton.setOnAction(event -> {
			model.DeclineComplaint(complaint);
			ComplaintsTable.getItems().remove(complaint);
		});

		NumberTextField acquitAmountField = new NumberTextField();
		acquitAmountField.setPromptText("Enter amount to acquit");
		acquitAmountField.setMaxWidth(150);

		Button acquitButton = new Button("Acquit Customer");
		acquitButton.setOnAction(event -> {
			if (!acquitAmountField.getText().isEmpty()) {
				model.AcquitComplaint(complaint, acquitAmountField.getNumber());
				ComplaintsTable.getItems().remove(complaint);
			}
		});

		editor.addRow(3, approveButton, declineButton);
		editor.addRow(4, acquitButton, acquitAmountField);

		return editor;
	}

	private ArrayList<ComplaintUiElement> getExample() {
		ArrayList<ComplaintUiElement> list = new ArrayList<ComplaintUiElement>();
		list.add(new ComplaintUiElement(new Complaint(GetLoremIpsum1(), 1, new Date(), 1)));
		list.add(new ComplaintUiElement(new Complaint(GetLoremIpsum2(), 18, new Date(), 2)));
		list.add(new ComplaintUiElement(new Complaint(GetLoremIpsum3(), 42, new Date(), 3)));
		return list;
	}

	private String GetLoremIpsum1() {
		return "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec finibus vestibulum lacus, et elementum ipsum mattis vitae. Mauris convallis sem mauris, sit amet cursus eros dignissim ut. Sed id ornare elit. Nunc iaculis eu odio in ornare. In hac habitasse platea dictumst. Suspendisse vitae odio rhoncus, scelerisque diam et, pellentesque libero. Ut pharetra malesuada elit quis vulputate. Etiam ultricies semper mattis. Aenean sed lacus pellentesque, commodo tortor quis, volutpat mauris. Morbi interdum justo et urna fermentum egestas. Curabitur scelerisque a purus nec tincidunt. Curabitur et sapien purus. In sagittis neque ligula, eget pellentesque risus molestie a. Morbi aliquam lorem in sagittis volutpat. Cras hendrerit commodo efficitur.\r\n"
				+ "\r\n"
				+ "Orci varius natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Integer semper quam et neque egestas congue. Nulla viverra mollis leo vitae sollicitudin. Donec vitae turpis in diam commodo imperdiet nec eu libero. Duis semper justo non tellus laoreet, vitae consequat purus luctus. Nulla at felis tortor. Sed vitae urna ligula. Maecenas sodales quis metus quis efficitur. Sed ut est vulputate, porttitor risus ac, ullamcorper lectus.";
	}

	private String GetLoremIpsum2() {
		return "Ut condimentum placerat quam vel eleifend. Suspendisse pretium pulvinar nulla et sodales. Vivamus id magna varius erat sollicitudin dapibus non vel neque. Etiam sapien nisi, imperdiet sed luctus nec, porttitor non tellus. Duis ipsum mi, commodo id magna non,"
				+ "\r\n"
				+ "efficitur vulputate velit. Nunc nec consectetur nulla. Integer vestibulum vehicula velit, ac finibus sapien ultrices ac. Maecenas in ultrices ipsum.\r\n"
				+ "\r\n"
				+ "Duis pellentesque nisi tincidunt neque rutrum, ac maximus erat egestas. Nulla ut placerat dui, vitae molestie nibh. Donec nibh leo, feugiat at sapien nec, malesuada interdum magna. Nulla malesuada, enim at feugiat sollicitudin, nibh quam efficitur turpis, eu fermentum nisi felis sed felis."
				+ "\r\n"
				+ "Praesent lacinia velit et laoreet ullamcorper. Vivamus lobortis ante magna, a facilisis leo ullamcorper eu. Fusce finibus, tellus vel tristique porta, lacus lacus iaculis sapien, ac porta ligula urna quis orci. Phasellus ullamcorper gravida sem, vel suscipit tellus. Nulla ipsum dui, tristique eu elementum faucibus, varius vel ante."
				+ "\r\n"
				+ "Aliquam porta, lectus non egestas commodo, magna lectus vulputate purus,"
				+ "\r\n" 
				+ "quis facilisis sapien libero sit amet ipsum. Donec convallis,"
				+ "\r\n" 
				+ "velit non varius finibus, lorem velit cursus est, vitae ultricies turpis ligula nec lorem."
				+ "\r\n"
				+ "Orci varius natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus."
				+ "\r\n"
				+ "Aliquam ut vehicula erat. Morbi scelerisque metus id imperdiet eleifend. "
				+ "\r\n"
				+ "Donec ut est consectetur, porta justo quis, fringilla erat.";
	}
	
	private String GetLoremIpsum3() {
		return "So there I was, in the middle of the parking lot, looking for my car, when this DAMN ROBOT TRIED TO INCINERATE ME!!"
				+ "\nThis is unacceptable. I want my car back and I want that robot fired.";
	}
}
