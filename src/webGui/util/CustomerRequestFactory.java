package webGui.util;

import java.time.LocalTime;
import java.util.Date;
import java.util.List;

import core.customer.CustomerRequest;
import core.customer.CustomerRequestType;

// TODO: Auto-generated Javadoc
/**
 * A factory for creating CustomerRequest objects.
 */
public class CustomerRequestFactory {

	/**
	 * Creates a new CustomerRequest object.
	 *
	 * @param customerID the customer ID
	 * @param licensePlate the license plate
	 * @param email the email
	 * @param parkingLotID the parking lot ID
	 * @param arrivalTime the arrival time
	 * @param estimatedDepartureTime the estimated departure time
	 * @return the customer request
	 */
	public static CustomerRequest createOrderOneTimeParkingRequest(int customerID, String licensePlate, String email, int parkingLotID, Date arrivalTime,
			Date estimatedDepartureTime) {
		CustomerRequest OrderOneTimeParkingRequest = new CustomerRequest();
		OrderOneTimeParkingRequest.requestType = CustomerRequestType.PRE_ORDERED_PARKING;
		OrderOneTimeParkingRequest.customerID = customerID;
		OrderOneTimeParkingRequest.carID = licensePlate;
		OrderOneTimeParkingRequest.email = email;
		OrderOneTimeParkingRequest.parkingLotID = parkingLotID;
		OrderOneTimeParkingRequest.arrivalTime = arrivalTime;
		OrderOneTimeParkingRequest.estimatedDepartureTime = estimatedDepartureTime;
		return OrderOneTimeParkingRequest;
	}

	/**
	 * Creates a new CustomerRequest object.
	 *
	 * @param orderId the order id
	 * @return the customer request
	 */
	public static CustomerRequest createCancelRequest(int orderId) {
		CustomerRequest cancelOrder = new CustomerRequest();
		cancelOrder.requestType = CustomerRequestType.CANCEL_ORDER;
		cancelOrder.orderID = orderId;
		return cancelOrder;
	}

	/**
	 * Creates a new CustomerRequest object.
	 *
	 * @param orderId the order id
	 * @return the customer request
	 */
	public static CustomerRequest createTrackOrderStatusRequest(int orderId) {
		CustomerRequest trackOrderStatus = new CustomerRequest();
		trackOrderStatus.requestType = CustomerRequestType.TRACK_ORDER_STATUS;
		trackOrderStatus.orderID = orderId;
		return trackOrderStatus;
	}

	/**
	 * Creates a new CustomerRequest object.
	 *
	 * @param customerID the customer ID
	 * @param licensePlates the license plates
	 * @param email the email
	 * @param parkingLotID the parking lot ID
	 * @param startingDate the starting date
	 * @param routineDepartureTime the routine departure time
	 * @return the customer request
	 */
	public static CustomerRequest createOrderRoutineMonthlySubscriptionRequest(int customerID, List<String> licensePlates, String email, int parkingLotID,
			Date startingDate, LocalTime routineDepartureTime) {
		CustomerRequest OrderRoutineMonthlySubscriptionRequest = new CustomerRequest();
		OrderRoutineMonthlySubscriptionRequest.requestType = CustomerRequestType.ORDER_ROUTINE_MONTHLY_SUBSCRIPTION;
		OrderRoutineMonthlySubscriptionRequest.customerID = customerID;
		OrderRoutineMonthlySubscriptionRequest.liscencePlates = licensePlates;
		OrderRoutineMonthlySubscriptionRequest.email = email;
		OrderRoutineMonthlySubscriptionRequest.parkingLotID = parkingLotID;
		OrderRoutineMonthlySubscriptionRequest.startingDate = startingDate;
		OrderRoutineMonthlySubscriptionRequest.routineDepartureTime = routineDepartureTime.toString();
		return OrderRoutineMonthlySubscriptionRequest;
	}

	/**
	 * Creates a new CustomerRequest object.
	 *
	 * @param customerID the customer ID
	 * @param licensePlate the license plate
	 * @param email the email
	 * @param startingDate the starting date
	 * @return the customer request
	 */
	public static CustomerRequest createOrderFullMonthlySubscriptionRequest(int customerID, String licensePlate, String email, Date startingDate) {
		CustomerRequest OrderFullMonthlySubscriptionRequest = new CustomerRequest();
		OrderFullMonthlySubscriptionRequest.requestType = CustomerRequestType.ORDER_FULL_MONTHLY_SUBSCRIPTION;
		OrderFullMonthlySubscriptionRequest.customerID = customerID;
		OrderFullMonthlySubscriptionRequest.carID = licensePlate;
		OrderFullMonthlySubscriptionRequest.email = email;
		OrderFullMonthlySubscriptionRequest.startingDate = startingDate;
		return OrderFullMonthlySubscriptionRequest;
	}

	/**
	 * Creates a new CustomerRequest object.
	 *
	 * @param customerID the customer ID
	 * @param subscriptionId the subscription id
	 * @return the customer request
	 */
	public static CustomerRequest createSubscriptionRenewalRequest(int customerID, int subscriptionId) {
		CustomerRequest subscriptionRenewal = new CustomerRequest();
		subscriptionRenewal.requestType = CustomerRequestType.SUBSCRIPTION_RENEWAL;
		subscriptionRenewal.customerID = customerID;
		subscriptionRenewal.subscriptionID = subscriptionId;
		return subscriptionRenewal;
	}

	/**
	 * Creates a new CustomerRequest object.
	 *
	 * @param complaint the complaint
	 * @param customerId the customer id
	 * @return the customer request
	 */
	public static CustomerRequest createOpenComplaintRequest(String complaint, int customerId) {
		CustomerRequest openComplaint = new CustomerRequest();
		openComplaint.requestType = CustomerRequestType.OPEN_COMPLAINT;
		openComplaint.complaint = complaint;
		openComplaint.customerID = customerId;
		return openComplaint;
	}

	/**
	 * Creates a new CustomerRequest object.
	 *
	 * @return the customer request
	 */
	public static CustomerRequest createParkingLotNamesRequest() {
		CustomerRequest parkingLotNamesRequest = new CustomerRequest();
		parkingLotNamesRequest.requestType = CustomerRequestType.PARKING_LOT_NAMES;
		return parkingLotNamesRequest;
	}
}
