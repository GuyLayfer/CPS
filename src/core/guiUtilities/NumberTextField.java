package core.guiUtilities;

import javafx.scene.control.TextField;

// TODO: Auto-generated Javadoc
/**
 * The Class NumberTextField.
 */
public class NumberTextField extends TextField {

	/* (non-Javadoc)
	 * @see javafx.scene.control.TextInputControl#replaceText(int, int, java.lang.String)
	 */
	@Override
	public void replaceText(int start, int end, String text) {
		if (validate(text)) {
			super.replaceText(start, end, text);
		}
	}

	/* (non-Javadoc)
	 * @see javafx.scene.control.TextInputControl#replaceSelection(java.lang.String)
	 */
	@Override
	public void replaceSelection(String text) {
		if (validate(text)) {
			super.replaceSelection(text);
		}
	}

	/**
	 * Validate.
	 *
	 * @param text the text
	 * @return true, if successful
	 */
	private boolean validate(String text) {
		return text.matches(CpsRegEx.OnlyIntegers);
	}

	/**
	 * Gets the number.
	 *
	 * @return the number
	 */
	public Integer getNumber() {
		if (this.getText().isEmpty()) {
			return 0;
		}

		if (this.getText().length() < 9) {
			return Integer.parseInt(this.getText());
		} else {
			return Integer.MAX_VALUE;
		}
	}
}
