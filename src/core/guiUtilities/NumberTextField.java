package core.guiUtilities;

import javafx.scene.control.TextField;

public class NumberTextField extends TextField {

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
		return text.matches(CpsRegEx.OnlyIntegers);
	}

	public Integer getNumber() {
		if (this.getText().length() < 9) {
			return Integer.parseInt(this.getText());
		} else {
			return Integer.MAX_VALUE;
		}
	}
}
