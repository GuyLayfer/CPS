package core.guiUtilities;

import core.worker.responses.BaseResponse;

public interface IServerResponseHandler {
	
	public void handleServerResponse(BaseResponse response);
}
