package core.gui;

public class UriDictionary {

	// Web GUI resources
	public class WebGui {
		public static final String HostAddressStartPageView = "../../webGui/views/HostAddressStartPageView.fxml";
		public static final String ClientView = "../../webGui/views/MockWebClientView.fxml";
		public static final String Shell = "../../webGui/views/WebGuiShell.fxml";
	}

	// Kiosk GUI resources
	public class Kiosk {
		public static final String Shell = "../../kiosk/views/KioskGuiShell.fxml";
		public static final String ClientView = "../../kiosk/views/KioskClientView.fxml";
		public static final String EntranceView = "../../kiosk/views/EntranceView.fxml";
		public static final String OrderOccasionalParkingView = "../../kiosk/views/OrderOccasionalParkingView.fxml";
		public static final String PreOrderedParkingLoginView = "../../kiosk/views/PreOrderedParkingLoginView.fxml";
		public static final String SubscriptionsLoginView = "../../kiosk/views/SubscriptionsLoginView.fxml";
		public static final String LeaveParkingLotView = "../../kiosk/views/LeaveParkingLotView.fxml";
	}

	// Regions used as placeholder for views
	public class Regions {
		public static final String webGuiMainViewRegion = "#webGuiMainViewRegion";
		public static final String kisokMainViewRegion = "#kisokMainViewRegion";
		public static final String kisokBreadCrumb = "#breadCrumbBar";
	}
}
