package workerGui.util;

import core.guiUtilities.BaseController;
import core.guiUtilities.UriDictionary;

// TODO: Auto-generated Javadoc
/**
 * The Class WorkerGuiController.
 */
public abstract class WorkerGuiController extends BaseController {

	/* (non-Javadoc)
	 * @see core.guiUtilities.BaseController#GetMainViewRegion()
	 */
	@Override
	protected String GetMainViewRegion() {
		return UriDictionary.Regions.workerMainViewRegion;
	}
}
