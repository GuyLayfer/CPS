package core;

import java.text.DateFormat;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

// TODO: Auto-generated Javadoc
/**
 * The Class CpsGson.
 */
public class CpsGson {
	
	/**
	 * Gets the gson.
	 *
	 * @return the gson
	 */
	public static Gson GetGson(){
		return new GsonBuilder().setDateFormat(DateFormat.FULL, DateFormat.FULL).create();
	}
}
