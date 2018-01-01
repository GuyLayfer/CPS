package core;

public abstract class AbstractResponse {
	public ResponseStatus status;
	// if status == OK then statusDescription == null
	// Otherwise, statusDescription contains a string which explains why the status isn't OK
	public String statusDescription; 
	// jsonData is valid only if status == OK.
	public String jsonData;
	
	@Override
	public String toString(){
		return status + ": " + statusDescription;
	}
}
