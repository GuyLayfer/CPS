package core.guiUtilities;

// TODO: Auto-generated Javadoc
/**
 * The Interface IServerResponseHandler.
 *
 * @param <RT> the generic type
 */
public interface IServerResponseHandler<RT> {
	
	/**
	 * Handle server response.
	 *
	 * @param response the response
	 */
	public void handleServerResponse(RT response);
}
