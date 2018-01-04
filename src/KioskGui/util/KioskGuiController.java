package kioskGui.util;

import org.controlsfx.control.BreadCrumbBar;

import core.GuiUtilities.BaseController;
import core.GuiUtilities.UriDictionary;
import javafx.scene.Scene;
import javafx.scene.control.TreeItem;

public class KioskGuiController extends BaseController {

	public void NavigateTo(String crumbName, Scene scene, String page) {
		NavigateToAndLeaveBreadCrumb(crumbName, scene, page);
	}

	private void NavigateToAndLeaveBreadCrumb(String crumbName, Scene scene, String page) {
		@SuppressWarnings("unchecked")
		BreadCrumbBar<UriToString> breadCrumbBar = (BreadCrumbBar<UriToString>) scene.lookup(UriDictionary.Regions.kisokBreadCrumb);
		AddToBreadCrumb(crumbName, page, breadCrumbBar);
		super.NavigateTo(scene, page);
	}

	@Override
	protected String GetMainViewRegion() {
		return UriDictionary.Regions.kisokMainViewRegion;
	}

	private void AddToBreadCrumb(String crumbName, String uri, BreadCrumbBar<UriToString> breadCrumb) {
		if (!crumbName.isEmpty()) {
			TreeItem<UriToString> crumb = new TreeItem<UriToString>(new UriToString(uri, crumbName));
			breadCrumb.getSelectedCrumb().getChildren().add(crumb);
			breadCrumb.setSelectedCrumb(crumb);
		}
	}
}
