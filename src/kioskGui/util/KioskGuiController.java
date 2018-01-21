package kioskGui.util;

import org.controlsfx.control.BreadCrumbBar;

import core.guiUtilities.BaseController;
import core.guiUtilities.UriDictionary;
import javafx.scene.Scene;
import javafx.scene.control.TreeItem;

// TODO: Auto-generated Javadoc
/**
 * The Class KioskGuiController.
 */
public class KioskGuiController extends BaseController {

	/**
	 * Navigate to.
	 *
	 * @param crumbName the crumb name
	 * @param scene the scene
	 * @param page the page
	 */
	public void NavigateTo(String crumbName, Scene scene, String page) {
		NavigateToAndLeaveBreadCrumb(crumbName, scene, page);
	}

	/**
	 * Navigate to and leave bread crumb.
	 *
	 * @param crumbName the crumb name
	 * @param scene the scene
	 * @param page the page
	 */
	private void NavigateToAndLeaveBreadCrumb(String crumbName, Scene scene, String page) {
		@SuppressWarnings("unchecked")
		BreadCrumbBar<UriToString> breadCrumbBar = (BreadCrumbBar<UriToString>) scene.lookup(UriDictionary.Regions.kisokBreadCrumb);
		AddToBreadCrumb(crumbName, page, breadCrumbBar);
		super.NavigateTo(scene, page);
	}

	/* (non-Javadoc)
	 * @see core.guiUtilities.BaseController#GetMainViewRegion()
	 */
	@Override
	protected String GetMainViewRegion() {
		return UriDictionary.Regions.kisokMainViewRegion;
	}

	/**
	 * Adds the to bread crumb.
	 *
	 * @param crumbName the crumb name
	 * @param uri the uri
	 * @param breadCrumb the bread crumb
	 */
	private void AddToBreadCrumb(String crumbName, String uri, BreadCrumbBar<UriToString> breadCrumb) {
		if (!crumbName.isEmpty()) {
			TreeItem<UriToString> crumb = new TreeItem<UriToString>(new UriToString(uri, crumbName));
			breadCrumb.getSelectedCrumb().getChildren().add(crumb);
			breadCrumb.setSelectedCrumb(crumb);
		}
	}
}
