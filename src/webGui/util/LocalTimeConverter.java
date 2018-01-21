package webGui.util;

import java.time.LocalTime;

import javafx.util.StringConverter;

// TODO: Auto-generated Javadoc
/**
 * The Class LocalTimeConverter.
 */
public class LocalTimeConverter extends StringConverter<LocalTime> {

	/* (non-Javadoc)
	 * @see javafx.util.StringConverter#toString(java.lang.Object)
	 */
	@Override
	public String toString(LocalTime object) {
		return object.toString();
	}

	/* (non-Javadoc)
	 * @see javafx.util.StringConverter#fromString(java.lang.String)
	 */
	@Override
	public LocalTime fromString(String string) {
		return LocalTime.of(Integer.parseInt(string.substring(1, 0)), Integer.parseInt(string.substring(4, 3)));
	}

}
