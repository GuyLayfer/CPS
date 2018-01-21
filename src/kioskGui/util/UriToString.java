package kioskGui.util;

// TODO: Auto-generated Javadoc
/**
 * The Class UriToString.
 */
public class UriToString {
	
	/**
	 * Instantiates a new uri to string.
	 *
	 * @param uri the uri
	 * @param displayName the display name
	 */
	public UriToString(String uri, String displayName) {
		this.Uri = uri;
		this.DisplayName = displayName;
	}
	
	/** The Uri. */
	public String Uri;
	
	/** The Display name. */
	public String DisplayName;
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return DisplayName;
	}
}
