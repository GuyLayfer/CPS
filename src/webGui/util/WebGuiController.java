package webGui.util;

import core.gui.BaseController;
import core.gui.UriDictionary;

public class WebGuiController extends BaseController {

	@Override
	protected String GetMainViewRegion() {
		return UriDictionary.Regions.webGuiMainViewRegion;
	}
}
