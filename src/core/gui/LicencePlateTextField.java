package core.gui;

import javafx.scene.control.TextField;

public class LicencePlateTextField extends TextField {
	private int LicencePlateMaxLength = 7;

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
