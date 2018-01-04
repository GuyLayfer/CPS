package webGui.util;

import core.GuiUtilities.BaseController;
import core.GuiUtilities.UriDictionary;

public class WebGuiController extends BaseController {

	@Override
	protected String GetMainViewRegion() {
		return UriDictionary.Regions.webGuiMainViewRegion;
	}
}
