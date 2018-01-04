package webGui.util;

import core.guiUtilities.BaseController;
import core.guiUtilities.UriDictionary;

public class WebGuiController extends BaseController {

	@Override
	protected String GetMainViewRegion() {
		return UriDictionary.Regions.webGuiMainViewRegion;
	}
}
