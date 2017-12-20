package core;

public class ServerBasicResponse {
	public ServerResponseStatus status;
	// if status == OK then statusDescription == null
	// Otherwise, statusDescription contains a string which explains why the status isn't OK
	public String statusDescription; 
	
	public ServerBasicResponse(ServerResponseStatus status, String statusDescription) {
		this.status = status;
		this.statusDescription = statusDescription;
	}
}
