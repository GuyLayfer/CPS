package core;

public enum ServerResponseStatus {
	OK,
	REQUEST_DENIED,
	SERVER_FAILLURE,
	UNSUPPORTED_FEATURE,	// will be used only during the development process
	BAD_REQUEST,			// for debugging
}
