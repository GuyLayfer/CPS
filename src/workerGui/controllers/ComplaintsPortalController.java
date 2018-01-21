package workerGui.controllers;

import java.util.List;

import org.controlsfx.control.table.TableRowExpanderColumn;

import core.guiUtilities.NumberTextField;
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

// TODO: Auto-generated Javadoc
/**
 * The Class ComplaintsPortalController.
 */
public class ComplaintsPortalController extends WorkerGuiController implements IAddItemsToTable<ComplaintUiElement>{
	
	/** The model. */
	private ComplaintsPortalModel model;

	/**
	 * Instantiates a new complaints portal controller.
	 */
	public ComplaintsPortalController() {
		model = new ComplaintsPortalModel(this);
	}

	/** The Customers complaints. */
	@FXML
	private GridPane CustomersComplaints;

	/** The Complaints table. */
	@FXML
	private TableView<ComplaintUiElement> ComplaintsTable;

	/**
	 * Initialize.
	 */
	@FXML
	@SuppressWarnings("unchecked")
	protected void initialize() {
		TableRowExpanderColumn<ComplaintUiElement> expanderColumn = new TableRowExpanderColumn<>(this::createEditor);
		TableColumn<ComplaintUiElement, String> timeLeftColumn = new TableColumn<>("Time Left To Reply");
		timeLeftColumn.setCellValueFactory(new PropertyValueFactory<>("timeLeftToReply"));
		TableColumn<ComplaintUiElement, Integer> customerId = new TableColumn<>("Customer ID");
		customerId.setCellValueFactory(new PropertyValueFactory<>("customerId"));
		TableColumn<ComplaintUiElement, String> contentColumn = new TableColumn<>("Brief Content");
		contentColumn.setCellValueFactory(new PropertyValueFactory<>("briefContent"));


		ComplaintsTable.setPlaceholder(new Label("No pending Rates Requests. Please come back later."));
		ComplaintsTable.getColumns().addAll(expanderColumn, timeLeftColumn, customerId, contentColumn);
		model.sendRequestForPendingComplaintsRequests();
	}
	
	/* (non-Javadoc)
	 * @see workerGui.controllers.IAddItemsToTable#AddToTable(java.util.List)
	 */
	@Override
	public void AddToTable(List<ComplaintUiElement> pendingItems) {
		ComplaintsTable.setItems(FXCollections.observableArrayList(pendingItems));
	}

	/**
	 * Creates the editor.
	 *
	 * @param param the param
	 * @return the grid pane
	 */
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
}
