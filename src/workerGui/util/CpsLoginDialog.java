package workerGui.util;

import org.controlsfx.dialog.LoginDialog;

import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;
import javafx.util.Callback;
import javafx.util.Pair;

// TODO: Auto-generated Javadoc
/**
 * The Class CpsLoginDialog.
 */
public class CpsLoginDialog extends LoginDialog {

	/**
	 * Instantiates a new cps login dialog.
	 *
	 * @param initialUserInfo the initial user info
	 * @param authenticator the authenticator
	 */
	public CpsLoginDialog(Pair<String, String> initialUserInfo, Callback<Pair<String, String>, Void> authenticator) {
		super(initialUserInfo, authenticator);
		this.getDialogPane().setHeaderText("Login to CPS\nPlease submit your ID and password");
		this.getDialogPane().getButtonTypes().set(0, ButtonType.CLOSE);
		Button closeButton = (Button) this.getDialogPane().lookupButton(ButtonType.CLOSE);
		closeButton.setOnAction(event -> {
			Alert exitDialog = new Alert(AlertType.CONFIRMATION, "");
			exitDialog.setTitle("Confirm Exit");
			exitDialog.getDialogPane().setHeaderText("Are you sure you want to exit?");
			ButtonType result = exitDialog.showAndWait().get();
			if (result == ButtonType.OK) {
				System.exit(0);
			} else if (result == ButtonType.CANCEL) {
				event.consume();
			}
		});
	}
}
