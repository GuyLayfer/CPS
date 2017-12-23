package core;

public class ServerBasicResponse {
	protected String classType;
	public ServerResponseStatus status;
	// if status == OK then statusDescription == null
	// Otherwise, statusDescription contains a string which explains why the status isn't OK
	public String statusDescription; 
	
	public ServerBasicResponse(ServerResponseStatus status, String statusDescription) {
		this.status = status;
		this.statusDescription = statusDescription;
		this.classType = "serverBasicResponse";
	}
	
	protected ServerBasicResponse(ServerResponseStatus status, String statusDescription, String classType) {
		this.status = status;
		this.statusDescription = statusDescription;
		this.classType = classType;
	}
	
	@Override
	public String toString(){
		return status + ": " + statusDescription;
	}
}
