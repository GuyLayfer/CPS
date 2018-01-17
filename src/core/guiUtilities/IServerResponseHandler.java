package core.guiUtilities;

public interface IServerResponseHandler<RT> {
	
	public void handleServerResponse(RT response);
}
