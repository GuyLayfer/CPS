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
		public static final String OuterShell = "../../kioskGui/views/KioskOuterShell.fxml";
		public static final String KioskStartPageView = "../../kioskGui/views/KioskStartPageView.fxml";
		public static final String ClientView = "../../kioskGui/views/KioskClientView.fxml";
		public static final String EntranceView = "../../kioskGui/views/EntranceView.fxml";
		public static final String OrderOccasionalParkingView = "../../kioskGui/views/OrderOccasionalParkingView.fxml";
		public static final String PreOrderedParkingLoginView = "../../kioskGui/views/PreOrderedParkingLoginView.fxml";
		public static final String SubscriptionsLoginView = "../../kioskGui/views/SubscriptionsLoginView.fxml";
		public static final String LeaveParkingLotView = "../../kioskGui/views/LeaveParkingLotView.fxml";
	}

	// Regions used as placeholder for views
	public class Regions {
		public static final String webGuiMainViewRegion = "#webGuiMainViewRegion";
		public static final String kisokMainViewRegion = "#kisokMainViewRegion";
		public static final String kisokOuterShellRegion = "#kisokOuterShellRegion";
		public static final String kisokBreadCrumb = "#breadCrumbBar";
	}
}
