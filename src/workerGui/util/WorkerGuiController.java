package workerGui.util;

import core.guiUtilities.BaseController;
import core.guiUtilities.UriDictionary;

public abstract class WorkerGuiController extends BaseController {

	@Override
	protected String GetMainViewRegion() {
		return UriDictionary.Regions.workerMainViewRegion;
	}

}
