package server.workerServer;

import java.util.List;

import core.worker.Complaint;
import core.worker.Permissions;
import core.worker.Rates;
import core.worker.WorkerRequestType;
import core.worker.WorkerRole;
import core.worker.responses.*;
import javafx.util.Pair;

public class WorkerResponseFactory {

	public static BaseResponse CreateNotificationResponse(WorkerRequestType requestType, String message) {
		NotificationResponse response = new NotificationResponse();
		response.requestType = requestType;
		response.message = message;
		return response;
	}

	public static BaseResponse CreateAcquitOrChargeAccountResponse(double amount) {
		String message;
		if (amount < 0) {
			message = "Client was charged with " + (-1 * amount) + " NIS.";
		} else {
			message = "Client was acquited with the amount " + amount + " NIS.";
		}

		return CreateNotificationResponse(WorkerRequestType.ACQUIT_OR_CHARGE_ACCOUNT, message);
	}

	public static BaseResponse CreateCancelResponse(double refund) {
		String message = "Notify customer he/she was acquited with " + refund + "NIS.";
		return CreateNotificationResponse(WorkerRequestType.CANCEL_CUSTOMER_ORDER, message);
	}

	public static BaseResponse CreateInitializeParkingLotResponse(int lotId) {
		String message = "Successfully initlize parking lot " + lotId + ".";
		return CreateNotificationResponse(WorkerRequestType.INITIALIZE_PARKING_LOT, message);
	}

	public static BaseResponse CreateParkingLotFullResponse(Boolean parkingLotIsFullState, int lotId) {
		String message = "Parking lot " + lotId + " is now " + (parkingLotIsFullState ? "in full state." : "not in full state.");
		return CreateNotificationResponse(WorkerRequestType.PARKING_LOT_FULL, message);
	}

	public static BaseResponse CreateReserveParkingSpaceResponse(int lotId, int row, int column, int floor) {
		String message = "Resereved a parking space in parking lot + " + lotId + ",\nrow: " + row + "\ncolumn: " + column + "\nfloor: " + floor;
		return CreateNotificationResponse(WorkerRequestType.RESERVE_PARKING_SPACE, message);
	}

	public static BaseResponse CreateOutOfOrderResponse(int lotId, int row, int column, int floor) {
		String message = "Disabled parking space in parking lot + " + lotId + ",\nrow: " + row + "\ncolumn: " + column + "\nfloor: " + floor;
		return CreateNotificationResponse(WorkerRequestType.OUT_OF_ORDER, message);
	}

	public static BaseResponse CreateUpdateRatesResponse() {
		String message = "Rates update request was sent. Approval pending firm manager.";
		return CreateNotificationResponse(WorkerRequestType.UPDATE_RATES, message);
	}

	public static BaseResponse CreateRequestComplaintsForReviewResponse(List<Complaint> complaintsToReview) {
		ComplaintsForReviewResponse response = new ComplaintsForReviewResponse();
		response.requestType = WorkerRequestType.REQUEST_COMPLAINTS_FOR_REVIEW;
		response.complaintsForReview = complaintsToReview;
		return response;
	}

	public static BaseResponse CreateRequestRatesForReviewResponse(List<Rates> ratesToReview) {
		RatesForReviewResponse response = new RatesForReviewResponse();
		response.requestType = WorkerRequestType.REQUEST_RATES_FOR_REVIEW;
		response.ratesForReview = ratesToReview;
		return response;
	}

	public static BaseResponse CreateDecideOnRatesResponse() {
		String message = "Your decision was submitted. Thank you.";
		return CreateNotificationResponse(WorkerRequestType.DECIDE_ON_RATES, message);
	}

	public static BaseResponse CreateDecideOnComplaintsResponse() {
		String message = "Your decision was submitted. Thank you.";
		return CreateNotificationResponse(WorkerRequestType.DECIDE_ON_COMPLAINTS, message);
	}

	public static BaseResponse CreateParkingLotNamesResponse(List<String> lotNames) {
		ParkingLotsNamesResponse response = new ParkingLotsNamesResponse();
		response.requestType = WorkerRequestType.PARKING_LOT_NAMES;
		response.lotNames = lotNames;
		return response;
	}

	public static BaseResponse CreatePermissionsResponse(int workerId, int workerLotId, WorkerRole workerRole, Permissions permissions) {
		PermissionsResponse response = new PermissionsResponse();
		response.requestType = WorkerRequestType.REQUEST_PERMISSIONS;
		response.workerId = workerId;
		response.workerLotId = workerLotId;
		response.workerRole = workerRole;
		response.permissions = permissions;
		return response;
	}

	public static BaseResponse CreateOperationReportResponse(List<Pair<String, String>> listOfDescriptionToData) {
		return null;
	}

	public static BaseResponse CreateStatisticsReportResponse(List<Pair<String, String>> listOfDescriptionToData) {
		return null;
	}
}
