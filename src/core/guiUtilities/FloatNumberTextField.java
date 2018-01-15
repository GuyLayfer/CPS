package core.guiUtilities;

import javafx.scene.control.TextField;

public class FloatNumberTextField extends TextField {
	private final String FloatNumberRegex = "^(\\d*[.])?\\d+$";
	
	@Override
	public void replaceText(int start, int end, String text) {
		if (validate(text)) {
			super.replaceText(start, end, text);
		}
	}

	@Override
	public void replaceSelection(String text) {
		if (validate(text)) {
			super.replaceSelection(text);
		}
	}

	private boolean validate(String text) {
		return text.matches(CpsRegEx.FloatAllowedCharacter);
	}

	public Float getFloat() {
		if(!this.getText().matches(FloatNumberRegex)) {
			return Float.NaN;
		}
		
		if (this.getText().length() < 9) {
			return Float.parseFloat(this.getText());
		} else {
			return Float.MAX_VALUE;
		}
	}
}
