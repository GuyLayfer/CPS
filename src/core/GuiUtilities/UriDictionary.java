package core.GuiUtilities;

public class UriDictionary {

	// Web GUI resources
	public class WebGui {
		public static final String HostAddressStartPageView = "../../webGui/views/HostAddressStartPageView.fxml";
		public static final String MockWebClientView = "../../webGui/views/MockWebClientView.fxml";
		public static final String WebGuiShell = "../../webGui/views/WebGuiShell.fxml";
	}

	// Kiosk GUI resources
	public class Kiosk {
		public static final String KioskGuiShell = "../../kiosk/views/KioskGuiShell.fxml";
		public static final String KioskClientView = "../../kiosk/views/KioskClientView.fxml";
		public static final String KioskEntranceView = "../../kiosk/views/EntranceView.fxml";
		public static final String KioskOrderOccasionalParkingView = "../../kiosk/views/OrderOccasionalParkingView.fxml";
		public static final String KioskPreOrderedParkingLoginView = "../../kiosk/views/PreOrderedParkingLoginView.fxml";
		public static final String KioskSubscriptionsLoginView = "../../kiosk/views/SubscriptionsLoginView.fxml";
	}

	// Regions used as placeholder for views
	public class Regions {
		public static final String webGuiMainViewRegion = "#webGuiMainViewRegion";
		public static final String kisokMainViewRegion = "#kisokMainViewRegion";
		public static final String kisokBreadCrumb = "#breadCrumbBar";
	}
}
