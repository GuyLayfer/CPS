package webGui.util;

import java.time.LocalTime;

import javafx.util.StringConverter;

public class LocalTimeConverter extends StringConverter<LocalTime> {

	@Override
	public String toString(LocalTime object) {
		return object.toString();
	}

	@Override
	public LocalTime fromString(String string) {
		return LocalTime.of(Integer.parseInt(string.substring(1, 0)), Integer.parseInt(string.substring(4, 3)));
	}

}
