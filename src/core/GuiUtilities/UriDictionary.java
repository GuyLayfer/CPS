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
		public static final String KioskGuiShell = "../../kioskGui/views/KioskGuiShell.fxml";
		public static final String KioskClientView = "../../kioskGui/views/KioskClientView.fxml";
		public static final String KioskEntranceView = "../../kioskGui/views/EntranceView.fxml";
		public static final String KioskOrderOccasionalParkingView = "../../kioskGui/views/OrderOccasionalParkingView.fxml";
		public static final String KioskPreOrderedParkingLoginView = "../../kioskGui/views/PreOrderedParkingLoginView.fxml";
		public static final String KioskSubscriptionsLoginView = "../../kioskGui/views/SubscriptionsLoginView.fxml";
	}

	// Regions used as placeholder for views
	public class Regions {
		public static final String webGuiMainViewRegion = "#webGuiMainViewRegion";
		public static final String kisokMainViewRegion = "#kisokMainViewRegion";
		public static final String kisokBreadCrumb = "#breadCrumbBar";
	}
}
