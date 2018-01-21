package server.requestHandlers.worker;

import java.util.List;

import core.parkingLot.ParkingLotInfo;
import core.worker.Complaint;
import core.worker.Permissions;
import core.worker.Rates;
import core.worker.ReportItem;
import core.worker.WorkerRequestType;
import core.worker.WorkerRole;
import core.worker.responses.*;

// TODO: Auto-generated Javadoc
/**
 * A factory for creating WorkerResponse objects.
 */
public class WorkerResponseFactory {

	/**
	 * Creates the notification response.
	 *
	 * @param requestType the request type
	 * @param message the message
	 * @return the worker base response
	 */
	public static WorkerBaseResponse CreateNotificationResponse(WorkerRequestType requestType, String message) {
		NotificationResponse response = new NotificationResponse();
		response.requestType = requestType;
		response.message = message;
		return response;
	}

	/**
	 * Creates the acquit or charge account response.
	 *
	 * @param amount the amount
	 * @return the worker base response
	 */
	public static WorkerBaseResponse CreateAcquitOrChargeAccountResponse(double amount) {
		String message;
		if (amount < 0) {
			message = "Client was charged with " + (-1 * amount) + " NIS.";
		} else {
			message = "Client was acquited with the amount " + amount + " NIS.";
		}

		return CreateNotificationResponse(WorkerRequestType.ACQUIT_OR_CHARGE_ACCOUNT, message);
	}

	/**
	 * Creates the cancel response.
	 *
	 * @param refund the refund
	 * @return the worker base response
	 */
	public static WorkerBaseResponse CreateCancelResponse(double refund) {
		String message = "Notify customer he/she was acquited with " + refund + "NIS.";
		return CreateNotificationResponse(WorkerRequestType.CANCEL_CUSTOMER_ORDER, message);
	}

	/**
	 * Creates the initialize parking lot response.
	 *
	 * @param lotId the lot id
	 * @return the worker base response
	 */
	public static WorkerBaseResponse CreateInitializeParkingLotResponse(int lotId) {
		String message = "Successfully initlize parking lot " + lotId + ".";
		return CreateNotificationResponse(WorkerRequestType.INITIALIZE_PARKING_LOT, message);
	}

	/**
	 * Creates the parking lot full response.
	 *
	 * @param parkingLotIsFullState the parking lot is full state
	 * @param lotId the lot id
	 * @return the worker base response
	 */
	public static WorkerBaseResponse CreateParkingLotFullResponse(Boolean parkingLotIsFullState, int lotId) {
		String message = "Parking lot " + lotId + " is now " + (parkingLotIsFullState ? "in full state." : "not in full state.");
		return CreateNotificationResponse(WorkerRequestType.PARKING_LOT_FULL, message);
	}

	/**
	 * Creates the reserve parking space response.
	 *
	 * @param orderId the order id
	 * @param price the price
	 * @return the worker base response
	 */
	public static WorkerBaseResponse CreateReserveParkingSpaceResponse(int orderId, double price) {
		String message = "Resereved a parking space. your reservation Id is: " + orderId + "\n The price of your reserved parking is: " + price;
		return CreateNotificationResponse(WorkerRequestType.RESERVE_PARKING_SPACE, message);
	}

	/**
	 * Creates the out of order response.
	 *
	 * @param lotId the lot id
	 * @param row the row
	 * @param column the column
	 * @param floor the floor
	 * @param isOutOfOrder the is out of order
	 * @return the worker base response
	 */
	public static WorkerBaseResponse CreateOutOfOrderResponse(int lotId, int row, int column, int floor, Boolean isOutOfOrder) {
		String message = (isOutOfOrder ? "Disabled " : "Enabled ") + "parking space in parking lot + " + lotId + ",\nrow: " + row + "\ncolumn: " + column + "\nfloor: " + floor;
		return CreateNotificationResponse(WorkerRequestType.OUT_OF_ORDER, message);
	}

	/**
	 * Creates the update rates response.
	 *
	 * @return the worker base response
	 */
	public static WorkerBaseResponse CreateUpdateRatesResponse() {
		String message = "Rates update request was sent. Approval pending firm manager.";
		return CreateNotificationResponse(WorkerRequestType.UPDATE_RATES, message);
	}

	/**
	 * Creates the request complaints for review response.
	 *
	 * @param complaintsToReview the complaints to review
	 * @return the worker base response
	 */
	public static WorkerBaseResponse CreateRequestComplaintsForReviewResponse(List<Complaint> complaintsToReview) {
		ComplaintsForReviewResponse response = new ComplaintsForReviewResponse();
		response.requestType = WorkerRequestType.REQUEST_COMPLAINTS_FOR_REVIEW;
		response.complaintsForReview = complaintsToReview;
		return response;
	}

	/**
	 * Creates the request rates for review response.
	 *
	 * @param ratesToReview the rates to review
	 * @return the worker base response
	 */
	public static WorkerBaseResponse CreateRequestRatesForReviewResponse(List<Rates> ratesToReview) {
		RatesForReviewResponse response = new RatesForReviewResponse();
		response.requestType = WorkerRequestType.REQUEST_RATES_FOR_REVIEW;
		response.ratesForReview = ratesToReview;
		return response;
	}

	/**
	 * Creates the decide on rates response.
	 *
	 * @param parkingLotId the parking lot id
	 * @param isApproved the is approved
	 * @return the worker base response
	 */
	public static WorkerBaseResponse CreateDecideOnRatesResponse(int parkingLotId, Boolean isApproved) {
		String message = null;
		if (isApproved) {
			message = "Your decision to approve the rate for parking lot " + parkingLotId + " was listed.\nThank you.";
		} else {
			message = "Your decision to decline the rate for parking lot " + parkingLotId + " was discarded.\nThank you.";
		}
		
		return CreateNotificationResponse(WorkerRequestType.DECIDE_ON_RATES, message);
	}

	/**
	 * Creates the decide on complaints response.
	 *
	 * @param customerServiceResponse the customer service response
	 * @return the worker base response
	 */
	public static WorkerBaseResponse CreateDecideOnComplaintsResponse(String customerServiceResponse) {
		String message = customerServiceResponse + "\nYour decision was submitted. Thank you.";
		return CreateNotificationResponse(WorkerRequestType.DECIDE_ON_COMPLAINTS, message);
	}

	/**
	 * Creates the parking lot names response.
	 *
	 * @param lotNames the lot names
	 * @return the worker base response
	 */
	public static WorkerBaseResponse CreateParkingLotNamesResponse(List<Integer> lotNames) {
		ParkingLotsNamesResponse response = new ParkingLotsNamesResponse();
		response.requestType = WorkerRequestType.PARKING_LOT_NAMES;
		response.lotNames = lotNames;
		return response;
	}

	/**
	 * Creates the permissions response.
	 *
	 * @param workerId the worker id
	 * @param workerLotId the worker lot id
	 * @param workerRole the worker role
	 * @param permissions the permissions
	 * @return the worker base response
	 */
	public static WorkerBaseResponse CreatePermissionsResponse(int workerId, int workerLotId, WorkerRole workerRole, Permissions permissions) {
		PermissionsResponse response = new PermissionsResponse();
		response.requestType = WorkerRequestType.REQUEST_PERMISSIONS;
		response.workerId = workerId;
		response.workerLotId = workerLotId;
		response.workerRole = workerRole;
		response.permissions = permissions;
		return response;
	}

	/**
	 * Creates the report response.
	 *
	 * @param reportItems the report items
	 * @param reportType the report type
	 * @return the worker base response
	 */
	public static WorkerBaseResponse CreateReportResponse(List<ReportItem> reportItems, WorkerRequestType reportType) {
		ReportResponse response = new ReportResponse();
		response.requestType = reportType;
		response.reportItems = reportItems;
		return response;
	}
	
	/**
	 * Creates the parking lot info response.
	 *
	 * @param parkingLotInfo the parking lot info
	 * @return the worker base response
	 */
	public static WorkerBaseResponse CreateParkingLotInfoResponse(ParkingLotInfo parkingLotInfo) {
		ParkingLotInfoResponse response = new ParkingLotInfoResponse();
		response.requestType = WorkerRequestType.PARKING_LOT_INFO;
		response.parkingLotInfo = parkingLotInfo;
		return response;
	}
}
