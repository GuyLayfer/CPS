package kioskGui.util;

public class UriToString {
	public UriToString(String uri, String displayName) {
		this.Uri = uri;
		this.DisplayName = displayName;
	}
	
	public String Uri;
	public String DisplayName;
	
	@Override
	public String toString() {
		return DisplayName;
	}
}
