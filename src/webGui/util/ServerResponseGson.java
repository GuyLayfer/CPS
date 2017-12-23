package webGui.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import core.RuntimeTypeAdapterFactory;
import core.ServerBasicResponse;
import core.ServerGenericResponse;
import core.ServerTrackOrderResponse;

public class ServerResponseGson {
	private Gson gson;
	
	public ServerResponseGson() {
		RuntimeTypeAdapterFactory<ServerBasicResponse> runtimeTypeAdapterFactory = RuntimeTypeAdapterFactory
			    .of(ServerBasicResponse.class, "classType")
			    .registerSubtype(ServerBasicResponse.class, "serverBasicResponse")
			    .registerSubtype(ServerGenericResponse.class, "serverGenericResponse")
				.registerSubtype(ServerTrackOrderResponse.class, "serverTrackOrderResponse");

		gson = new GsonBuilder()
				.registerTypeAdapterFactory(runtimeTypeAdapterFactory)
			    .create();
	}
	
	public Gson getGson(){
		return gson;
	}
}
