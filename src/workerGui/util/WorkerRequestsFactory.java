package workerGui.util;

import java.util.Date;

import core.worker.Complaint;
import core.worker.Rates;
import core.worker.WorkerRequestType;
import core.worker.requests.*;

public class WorkerRequestsFactory {

	public static BaseRequest CreateCancelCustomerOrderRequest(int orderId) {
		CancelCustomerOrderRequest request = new CancelCustomerOrderRequest();
		request.requestType = WorkerRequestType.CANCEL_CUSTOMER_ORDER;
		request.orderId = orderId;
		return request;
	}

	public static BaseRequest CreateAcquitOrChargeAccountRequest(int accountId, double amount) {
		AcquitOrChargeAccountRequest request = new AcquitOrChargeAccountRequest();
		request.requestType = WorkerRequestType.ACQUIT_OR_CHARGE_ACCOUNT;
		request.accountId = accountId;
		request.amount = amount;
		return request;
	}

	public static BaseRequest CreatePermissionsRequest(int workerId, String password) {
		PermissionsRequest request = new PermissionsRequest();
		request.requestType = WorkerRequestType.REQUEST_PERMISSIONS;
		request.workerId = workerId;
		request.password = password;
		return request;
	}

	public static BaseRequest CreateParkingLotNamesRequest() {
		ParkingLotsNamesRequest request = new ParkingLotsNamesRequest();
		request.requestType = WorkerRequestType.PARKING_LOT_NAMES;
		return request;
	}

	public static BaseRequest CreateInitializeParkingLotRequest(int numberOfCoulmns) {
		InitializeParkingLotRequest request = new InitializeParkingLotRequest();
		request.requestType = WorkerRequestType.INITIALIZE_PARKING_LOT;
		request.numberOfCoulmns = numberOfCoulmns;
		return request;
	}

	public static BaseRequest CreateParkingLotFullRequest(Boolean setParkingLotIsFull) {
		ParkingLotFullRequest request = new ParkingLotFullRequest();
		request.requestType = WorkerRequestType.PARKING_LOT_FULL;
		request.setParkingLotIsFull = setParkingLotIsFull;
		return request;
	}

	public static BaseRequest CreateReserveParkingSpaceRequest(
			int customerID,
			String licensePlate,
			String email,
			int parkingLotID,
			Date arrivalTime,
			Date estimatedDepartureTime) {
		ReserveParkingSpaceRequest request = new ReserveParkingSpaceRequest();
		request.requestType = WorkerRequestType.RESERVE_PARKING_SPACE;
		request.customerID = customerID;
		request.licensePlate = licensePlate;
		request.email = email;
		request.parkingLotID = parkingLotID;
		request.arrivalTime = arrivalTime;
		request.estimatedDepartureTime = estimatedDepartureTime;
		return request;
	}

	public static BaseRequest CreateOutOfOrderRequest(int lotId, int row, int column, int floor, Boolean isOutOfOrder) {
		OutOfOrderRequest request = new OutOfOrderRequest();
		request.requestType = WorkerRequestType.OUT_OF_ORDER;
		request.lotId = lotId;
		request.row = row;
		request.column = column;
		request.floor = floor;
		request.isOutOfOrder = isOutOfOrder;
		return request;
	}

	public static BaseRequest CreateUpdateRatesRequest(int lotId, double occasionalRate, double preOrderedRate, double rutineMonthlyRate,
			double rutineMonthlyMultipleRate, double fullNonthlyField) {
		UpdateRatesRequest request = new UpdateRatesRequest();
		request.requestType = WorkerRequestType.UPDATE_RATES;
		request.lotId = lotId;
		request.occasionalRate = occasionalRate;
		request.preOrderedRate = preOrderedRate;
		request.rutineMonthlyRate = rutineMonthlyRate;
		request.rutineMonthlyMultipleRate = rutineMonthlyMultipleRate;
		request.fullNonthlyField = fullNonthlyField;
		return request;
	}

	public static BaseRequest CreateRatesForReviewRequest() {
		DecideOnRateRequest request = new DecideOnRateRequest();
		request.requestType = WorkerRequestType.REQUEST_RATES_FOR_REVIEW;
		return request;
	}

	public static BaseRequest CreateComplaintsForReviewRequest() {
		DecideOnComplaintRequest request = new DecideOnComplaintRequest();
		request.requestType = WorkerRequestType.REQUEST_COMPLAINTS_FOR_REVIEW;
		return request;
	}

	public static BaseRequest CreateDecideOnRatesRequest(Rates ratesToDecide, Boolean isNewRateApproved) {
		DecideOnRateRequest request = new DecideOnRateRequest();
		request.requestType = WorkerRequestType.DECIDE_ON_RATES;
		request.ratesToDecide = ratesToDecide;
		request.isNewRateApproved = isNewRateApproved;
		return request;
	}

	public static BaseRequest CreateDecideOnComplaintsRequest(Complaint complaintToDecide, Boolean isComplaintApproved, double amountToAcquit) {
		DecideOnComplaintRequest request = new DecideOnComplaintRequest();
		request.requestType = WorkerRequestType.DECIDE_ON_COMPLAINTS;
		request.complaintToDecide = complaintToDecide;
		request.isComplaintApproved = isComplaintApproved;
		request.amountToAcquit = amountToAcquit;
		return request;
	}
	
	public static BaseRequest CreateReportRequest(WorkerRequestType reportType, Date startDate, Date endDate, int parkingLotId) {
		ReportRequest request = new ReportRequest();
		request.requestType = reportType;
		request.startDate = startDate;
		request.parkingLotId = parkingLotId;
		request.endDate = endDate;
		return request;
	}
}
