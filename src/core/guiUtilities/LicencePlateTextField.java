package core.guiUtilities;

import javafx.scene.control.TextField;

// TODO: Auto-generated Javadoc
/**
 * The Class LicencePlateTextField.
 */
public class LicencePlateTextField extends TextField {
	
	/** The Licence plate max length. */
	private int LicencePlateMaxLength = 7;

	/* (non-Javadoc)
	 * @see javafx.scene.control.TextInputControl#replaceText(int, int, java.lang.String)
	 */
	@Override
	public void replaceText(int start, int end, String insertedText) {
		if(insertedText.trim().isEmpty() && start >= end) {
			return;
		}
		
		String text = this.getText();
		String currentText = (text == null) ? "" : text;

		String finalText = currentText.substring(0, start) + insertedText + currentText.substring(end);

		int numberOfexceedingCharacters = finalText.length() - LicencePlateMaxLength;
		if (numberOfexceedingCharacters <= 0) {
			super.replaceText(start, end, insertedText);
		} else {
			String cutInsertedText = insertedText.substring(0, insertedText.length() - numberOfexceedingCharacters);
			super.replaceText(start, end, cutInsertedText);
		}
	}
}
