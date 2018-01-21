package core.guiUtilities;

import javafx.scene.control.TextField;

// TODO: Auto-generated Javadoc
/**
 * The Class FloatNumberTextField.
 */
public class FloatNumberTextField extends TextField {
	
	/** The Float number regex. */
	private final String FloatNumberRegex = "^(\\d*[.])?\\d+$";
	
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
		return text.matches(CpsRegEx.FloatAllowedCharacter);
	}

	/**
	 * Gets the float.
	 *
	 * @return the float
	 */
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
