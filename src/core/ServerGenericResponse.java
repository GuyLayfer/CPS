package core;

// Used in all cases in which the response data is only one string.
// data is valid only if status == OK.
public class ServerGenericResponse extends ServerBasicResponse {
	String data;
}
