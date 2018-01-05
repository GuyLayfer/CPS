package workerGui;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class LogoutDialog {
	
	public static Alert GetLogOotConfirmatio() {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Logout Confirmation");
		alert.setHeaderText("Logout confirmation");
		alert.setContentText("Are you sure you want to logout? ");
		return alert;
	}

}
