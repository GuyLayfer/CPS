package core;

import java.text.DateFormat;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class CpsGson {
	
	public static Gson GetGson(){
		return new GsonBuilder().setDateFormat(DateFormat.FULL, DateFormat.FULL).create();
	}
}
