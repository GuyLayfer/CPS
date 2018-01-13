package workerGui.util;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;

public class LogoutDialog {
	
	public static Alert getLogoutConfirmation() {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Logout Confirmation");
		alert.setHeaderText("You are about to logout from CPS.");
		alert.setContentText("Are you sure you want to logout?");
		alert.getDialogPane().getButtonTypes().clear();
		alert.getDialogPane().getButtonTypes().addAll(ButtonType.YES, ButtonType.CANCEL);
		return alert;
	}
}
