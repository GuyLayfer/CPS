package core.guiUtilities;

public class UriDictionary {

	// Web GUI resources
	public class WebGui {
		public static final String HostAddressStartPageView = "../../webGui/views/HostAddressStartPageView.fxml";
		public static final String ClientView = "../../webGui/views/MockWebClientView.fxml";
		public static final String Shell = "../../webGui/views/WebGuiShell.fxml";
	}

	// Kiosk GUI resources
	public class Kiosk {
		public static final String Shell = "../../kioskGui/views/KioskGuiShell.fxml";
		public static final String ClientView = "../../kioskGui/views/KioskClientView.fxml";
		public static final String EntranceView = "../../kioskGui/views/EntranceView.fxml";
		public static final String OrderOccasionalParkingView = "../../kioskGui/views/OrderOccasionalParkingView.fxml";
		public static final String PreOrderedParkingLoginView = "../../kioskGui/views/PreOrderedParkingLoginView.fxml";
		public static final String SubscriptionsLoginView = "../../kioskGui/views/SubscriptionsLoginView.fxml";
		public static final String LeaveParkingLotView = "../../kioskGui/views/LeaveParkingLotView.fxml";
	}

	// Worker GUI resources
	public class WorkerGui {
		public static final String Shell = "../../WorkerGui/views/WorkerGuiShell.fxml";
		public static final String ClientView = "../../WorkerGui/views/WorkerGuiClientView.fxml";
		public static final String SetOutOfOrderParkingSpaceView = "../../WorkerGui/views/SetOutOfOrderParkingSpaceView.fxml";
		public static final String UpdateRatesRequestView = "../../WorkerGui/views/UpdateRatesRequestView.fxml";
	}

	// Regions used as placeholder for views
	public class Regions {
		public static final String webGuiMainViewRegion = "#webGuiMainViewRegion";
		public static final String kisokMainViewRegion = "#kisokMainViewRegion";
		public static final String workerMainViewRegion = "#workerMainViewRegion";
		public static final String kisokBreadCrumb = "#breadCrumbBar";
	}
}
