package core.guiUtilities;

public class UriDictionary {
	// We had to put all fxml resources in a resource folder. Since it was a last minute change, it looks
	// Ugly as ****.

	// Web GUI resources
	public class WebGui {
		// public static final String HostAddressStartPageView = "../../webGui/views/HostAddressStartPageView.fxml";
		// public static final String ClientView = "../../webGui/views/MockWebClientView.fxml";
		// public static final String Shell = "../../webGui/views/WebGuiShell.fxml";
		public static final String HostAddressStartPageView = "/HostAddressStartPageView.fxml";
		public static final String ClientView = "/MockWebClientView.fxml";
		public static final String Shell = "/WebGuiShell.fxml";
	}

	// Kiosk GUI resources
	public class Kiosk {
		// public static final String Shell = "../../kioskGui/views/KioskGuiShell.fxml";
		// public static final String OuterShell = "../../kioskGui/views/KioskOuterShell.fxml";
		// public static final String KioskStartPageView = "../../kioskGui/views/KioskStartPageView.fxml";
		// public static final String ClientView = "../../kioskGui/views/KioskClientView.fxml";
		// public static final String EntranceView = "../../kioskGui/views/EntranceView.fxml";
		// public static final String OrderOccasionalParkingView = "../../kioskGui/views/OrderOccasionalParkingView.fxml";
		// public static final String PreOrderedParkingLoginView = "../../kioskGui/views/PreOrderedParkingLoginView.fxml";
		// public static final String SubscriptionsLoginView = "../../kioskGui/views/SubscriptionsLoginView.fxml";
		// public static final String LeaveParkingLotView = "../../kioskGui/views/LeaveParkingLotView.fxml";
		public static final String Shell = "/KioskGuiShell.fxml";
		public static final String OuterShell = "/KioskOuterShell.fxml";
		public static final String KioskStartPageView = "/KioskStartPageView.fxml";
		public static final String ClientView = "/KioskClientView.fxml";
		public static final String EntranceView = "/EntranceView.fxml";
		public static final String OrderOccasionalParkingView = "/OrderOccasionalParkingView.fxml";
		public static final String PreOrderedParkingLoginView = "/PreOrderedParkingLoginView.fxml";
		public static final String SubscriptionsLoginView = "/SubscriptionsLoginView.fxml";
		public static final String LeaveParkingLotView = "/LeaveParkingLotView.fxml";
	}

	// Worker GUI resources
	public class WorkerGui {
		// public static final String Shell = "../../workerGui/views/WorkerGuiShell.fxml";
		// public static final String OuterShell = "../../workerGui/views/WorkerOuterShell.fxml";
		// public static final String WorkerStartPageView = "../../workerGui/views/WorkerStartPageView.fxml";
		// public static final String ClientView = "../../workerGui/views/WorkerGuiClientView.fxml";
		// public static final String SetOutOfOrderParkingSpaceView = "../../workerGui/views/SetOutOfOrderParkingSpaceView.fxml";
		// public static final String UpdateRatesRequestView = "../../workerGui/views/UpdateRatesRequestView.fxml";
		// public static final String ApproveRatesRequestsPortalView = "../../workerGui/views/ApproveRatesRequestsPortalView.fxml";
		// public static final String InitializeParkingLotView = "../../workerGui/views/InitializeParkingLotView.fxml";
		// public static final String AcquitOrChargeAccountView = "../../workerGui/views/AcquitOrChargeAccountView.fxml";
		// public static final String CancelCustomerOrderView = "../../workerGui/views/CancelCustomerOrderView.fxml";
		// public static final String ComplaintsPortalView = "../../workerGui/views/ComplaintsPortalView.fxml";
		// public static final String ParkingLotFullView = "../../workerGui/views/ParkingLotFullView.fxml";
		// public static final String ReserveParkingSpaceView = "../../workerGui/views/ReserveParkingSpaceView.fxml"; // Not implemented yet
		// public static final String ReportsView = "../../workerGui/views/ReportView.fxml";
		public static final String Shell = "/WorkerGuiShell.fxml";
		public static final String OuterShell = "/WorkerOuterShell.fxml";
		public static final String WorkerStartPageView = "/WorkerStartPageView.fxml";
		public static final String ClientView = "/WorkerGuiClientView.fxml";
		public static final String SetOutOfOrderParkingSpaceView = "/SetOutOfOrderParkingSpaceView.fxml";
		public static final String UpdateRatesRequestView = "/UpdateRatesRequestView.fxml";
		public static final String ApproveRatesRequestsPortalView = "/ApproveRatesRequestsPortalView.fxml";
		public static final String InitializeParkingLotView = "/InitializeParkingLotView.fxml";
		public static final String AcquitOrChargeAccountView = "/AcquitOrChargeAccountView.fxml";
		public static final String CancelCustomerOrderView = "/CancelCustomerOrderView.fxml";
		public static final String ComplaintsPortalView = "/ComplaintsPortalView.fxml";
		public static final String ParkingLotFullView = "/ParkingLotFullView.fxml";
		public static final String ReserveParkingSpaceView = "/ReserveParkingSpaceView.fxml"; // Not implemented yet
		public static final String ReportsView = "/ReportView.fxml";
	}

	// Regions used as placeholder for views
	public class Regions {
		public static final String webGuiMainViewRegion = "#webGuiMainViewRegion";
		public static final String kisokMainViewRegion = "#kisokMainViewRegion";
		public static final String workerMainViewRegion = "#workerMainViewRegion";
		public static final String kisokOuterShellRegion = "#kisokOuterShellRegion";
		public static final String workerOuterShellRegion = "#workerOuterShellRegion";
		public static final String kisokBreadCrumb = "#breadCrumbBar";
	}
	
	public class Images {
		public static final String icon = "/CpsLogo.png";
	}
}
