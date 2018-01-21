package webGui.util;

import core.guiUtilities.BaseController;
import core.guiUtilities.UriDictionary;

// TODO: Auto-generated Javadoc
/**
 * The Class WebGuiController.
 */
public class WebGuiController extends BaseController {

	/* (non-Javadoc)
	 * @see core.guiUtilities.BaseController#GetMainViewRegion()
	 */
	@Override
	protected String GetMainViewRegion() {
		return UriDictionary.Regions.webGuiMainViewRegion;
	}
}
