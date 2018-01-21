package workerGui.util;

import java.util.Date;

import core.worker.Complaint;
import core.worker.Rates;
import core.worker.WorkerRequestType;
import core.worker.requests.*;

// TODO: Auto-generated Javadoc
/**
 * A factory for creating WorkerRequests objects.
 */
public class WorkerRequestsFactory {

	/**
	 * Creates the cancel customer order request.
	 *
	 * @param orderId the order id
	 * @return the base request
	 */
	public static BaseRequest CreateCancelCustomerOrderRequest(int orderId) {
		CancelCustomerOrderRequest request = new CancelCustomerOrderRequest();
		request.requestType = WorkerRequestType.CANCEL_CUSTOMER_ORDER;
		request.orderId = orderId;
		return request;
	}

	/**
	 * Creates the acquit or charge account request.
	 *
	 * @param accountId the account id
	 * @param amount the amount
	 * @return the base request
	 */
	public static BaseRequest CreateAcquitOrChargeAccountRequest(int accountId, double amount) {
		AcquitOrChargeAccountRequest request = new AcquitOrChargeAccountRequest();
		request.requestType = WorkerRequestType.ACQUIT_OR_CHARGE_ACCOUNT;
		request.accountId = accountId;
		request.amount = amount;
		return request;
	}

	/**
	 * Creates the permissions request.
	 *
	 * @param workerId the worker id
	 * @param password the password
	 * @return the base request
	 */
	public static BaseRequest CreatePermissionsRequest(int workerId, String password) {
		PermissionsRequest request = new PermissionsRequest();
		request.requestType = WorkerRequestType.REQUEST_PERMISSIONS;
		request.workerId = workerId;
		request.password = password;
		return request;
	}

	/**
	 * Creates the parking lot names request.
	 *
	 * @return the base request
	 */
	public static BaseRequest CreateParkingLotNamesRequest() {
		ParkingLotsNamesRequest request = new ParkingLotsNamesRequest();
		request.requestType = WorkerRequestType.PARKING_LOT_NAMES;
		return request;
	}

	/**
	 * Creates the initialize parking lot request.
	 *
	 * @param numberOfCoulmns the number of coulmns
	 * @return the base request
	 */
	public static BaseRequest CreateInitializeParkingLotRequest(int numberOfCoulmns) {
		InitializeParkingLotRequest request = new InitializeParkingLotRequest();
		request.requestType = WorkerRequestType.INITIALIZE_PARKING_LOT;
		request.numberOfCoulmns = numberOfCoulmns;
		return request;
	}

	/**
	 * Creates the parking lot full request.
	 *
	 * @param setParkingLotIsFull the set parking lot is full
	 * @param parkingLotId the parking lot id
	 * @return the base request
	 */
	public static BaseRequest CreateParkingLotFullRequest(Boolean setParkingLotIsFull, int parkingLotId) {
		ParkingLotFullRequest request = new ParkingLotFullRequest();
		request.requestType = WorkerRequestType.PARKING_LOT_FULL;
		request.setParkingLotIsFull = setParkingLotIsFull;
		request.parkingLotId = parkingLotId;
		return request;
	}

	/**
	 * Creates the reserve parking space request.
	 *
	 * @param customerID the customer ID
	 * @param licensePlate the license plate
	 * @param email the email
	 * @param parkingLotID the parking lot ID
	 * @param arrivalTime the arrival time
	 * @param estimatedDepartureTime the estimated departure time
	 * @return the base request
	 */
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

	/**
	 * Creates the out of order request.
	 *
	 * @param lotId the lot id
	 * @param row the row
	 * @param column the column
	 * @param floor the floor
	 * @param isOutOfOrder the is out of order
	 * @return the base request
	 */
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

	/**
	 * Creates the update rates request.
	 *
	 * @param lotId the lot id
	 * @param occasionalRate the occasional rate
	 * @param preOrderedRate the pre ordered rate
	 * @param rutineMonthlyRate the rutine monthly rate
	 * @param rutineMonthlyMultipleRate the rutine monthly multiple rate
	 * @param fullNonthlyField the full nonthly field
	 * @return the base request
	 */
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

	/**
	 * Creates the rates for review request.
	 *
	 * @return the base request
	 */
	public static BaseRequest CreateRatesForReviewRequest() {
		DecideOnRateRequest request = new DecideOnRateRequest();
		request.requestType = WorkerRequestType.REQUEST_RATES_FOR_REVIEW;
		return request;
	}

	/**
	 * Creates the complaints for review request.
	 *
	 * @return the base request
	 */
	public static BaseRequest CreateComplaintsForReviewRequest() {
		DecideOnComplaintRequest request = new DecideOnComplaintRequest();
		request.requestType = WorkerRequestType.REQUEST_COMPLAINTS_FOR_REVIEW;
		return request;
	}

	/**
	 * Creates the decide on rates request.
	 *
	 * @param ratesToDecide the rates to decide
	 * @param isNewRateApproved the is new rate approved
	 * @return the base request
	 */
	public static BaseRequest CreateDecideOnRatesRequest(Rates ratesToDecide, Boolean isNewRateApproved) {
		DecideOnRateRequest request = new DecideOnRateRequest();
		request.requestType = WorkerRequestType.DECIDE_ON_RATES;
		request.ratesToDecide = ratesToDecide;
		request.isNewRateApproved = isNewRateApproved;
		return request;
	}

	/**
	 * Creates the decide on complaints request.
	 *
	 * @param complaintToDecide the complaint to decide
	 * @param isComplaintApproved the is complaint approved
	 * @param amountToAcquit the amount to acquit
	 * @return the base request
	 */
	public static BaseRequest CreateDecideOnComplaintsRequest(Complaint complaintToDecide, Boolean isComplaintApproved, double amountToAcquit) {
		DecideOnComplaintRequest request = new DecideOnComplaintRequest();
		request.requestType = WorkerRequestType.DECIDE_ON_COMPLAINTS;
		request.complaintToDecide = complaintToDecide;
		request.isComplaintApproved = isComplaintApproved;
		request.amountToAcquit = amountToAcquit;
		return request;
	}
	
	/**
	 * Creates the report request.
	 *
	 * @param reportType the report type
	 * @param startDate the start date
	 * @param endDate the end date
	 * @param parkingLotId the parking lot id
	 * @return the base request
	 */
	public static BaseRequest CreateReportRequest(WorkerRequestType reportType, Date startDate, Date endDate, int parkingLotId) {
		ReportRequest request = new ReportRequest();
		request.requestType = reportType;
		request.startDate = startDate;
		request.parkingLotId = parkingLotId;
		request.endDate = endDate;
		return request;
	}
	
	/**
	 * Creates a new WorkerRequests object.
	 *
	 * @param parkingLotId the parking lot id
	 * @return the base request
	 */
	public static BaseRequest createParkingLotInfoRequest(int parkingLotId) {
		ParkingLotInfoRequest request = new ParkingLotInfoRequest();
		request.requestType = WorkerRequestType.PARKING_LOT_INFO;
		request.parkingLotId = parkingLotId;
		return request;
	}
}
