package webGui.util;

import java.util.ArrayList;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.event.EventHandler;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.ListView;
import javafx.scene.control.cell.TextFieldListCell;
import javafx.scene.layout.FlowPane;
import javafx.scene.control.Button;

// TODO: Auto-generated Javadoc
/**
 * The Class MultipleCarsDialog.
 */
public class MultipleCarsDialog extends Dialog<List<String>> {
	
	/** The cars. */
	private ListView<String> cars;
	
	/** The current cars. */
	private List<String> currentCars;
	
	/** The default plate. */
	private String defaultPlate = "0000000";
	
	/** The Licence plate max length. */
	private final int LicencePlateMaxLength = 7;

	/**
	 * Instantiates a new multiple cars dialog.
	 *
	 * @param currentCars the current cars
	 */
	public MultipleCarsDialog(List<String> currentCars) {
		this.setResultConverter((ButtonType button) -> resultsConverter(button));
		this.setTitle("Cars Liscence plates");
		this.setHeaderText("Configure Cars liscence plates");
		this.currentCars = new ArrayList<String>(currentCars);
		this.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
		Button addLine = new Button("Add Car");
		addLine.setOnAction((e) -> {
			cars.getItems().add(defaultPlate);
		});

		cars = new ListView<>(FXCollections.observableArrayList(currentCars));
		cars.setEditable(true);
		cars.setCellFactory(TextFieldListCell.forListView());
		cars.setOnEditCommit(new EventHandler<ListView.EditEvent<String>>() {
			@Override
			public void handle(ListView.EditEvent<String> t) {
				String newValue = t.getNewValue().trim();
				if(newValue.length() >= LicencePlateMaxLength) {
					cars.getItems().set(t.getIndex(), newValue.length() > LicencePlateMaxLength ? t.getNewValue().substring(0, LicencePlateMaxLength) : newValue);
				}
			}
		});

		cars.setOnEditCancel(new EventHandler<ListView.EditEvent<String>>() {
			@Override
			public void handle(ListView.EditEvent<String> t) {

			}
		});

		this.getDialogPane().setContent(new FlowPane(cars, addLine));
	}

	/**
	 * Results converter.
	 *
	 * @param button the button
	 * @return the list
	 */
	private List<String> resultsConverter(ButtonType button) {
		if (button == ButtonType.OK) {
			return cars.getItems().filtered((lp) -> !lp.equals(defaultPlate) && !lp.isEmpty());
		}

		return currentCars;
	}
}
