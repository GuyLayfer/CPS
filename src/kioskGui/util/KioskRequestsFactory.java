package kioskGui.util;

import java.util.Date;

import core.customer.CustomerRequest;
import core.customer.CustomerRequestType;

// TODO: Auto-generated Javadoc
/**
 * A factory for creating KioskRequests objects.
 */
public class KioskRequestsFactory {
	
	/** The current lot id. */
	public static Integer currentLotId;
	
	/**
	 * Creates the occasional parking request.
	 *
	 * @param customerID the customer ID
	 * @param licensePlate the license plate
	 * @param estimatedDepartureTime the estimated departure time
	 * @param email the email
	 * @return the customer request
	 */
	public static CustomerRequest CreateOccasionalParkingRequest(int customerID, String licensePlate, Date estimatedDepartureTime, String email){
		CustomerRequest occasionalParkingRequest = new CustomerRequest();
		occasionalParkingRequest.requestType = CustomerRequestType.OCCASIONAL_PARKING;
		occasionalParkingRequest.customerID = customerID;
		occasionalParkingRequest.carID = licensePlate;
		occasionalParkingRequest.estimatedDepartureTime = estimatedDepartureTime;
		occasionalParkingRequest.email = email;
		occasionalParkingRequest.parkingLotID = currentLotId;
		return occasionalParkingRequest;
	}
	
	/**
	 * Creates the pre ordered entrance request.
	 *
	 * @param licensePlate the license plate
	 * @return the customer request
	 */
	public static CustomerRequest CreatePreOrderedEntranceRequest(String licensePlate){
		CustomerRequest preOrderedEntranceRequest = new CustomerRequest();
		preOrderedEntranceRequest.requestType = CustomerRequestType.ENTER_PARKING_PRE_ORDERED;
		preOrderedEntranceRequest.carID = licensePlate;
		preOrderedEntranceRequest.parkingLotID = currentLotId;
		return preOrderedEntranceRequest;
	}
	
	/**
	 * Creates the subscriber entrance request.
	 *
	 * @param licensePlate the license plate
	 * @param subscriptionId the subscription id
	 * @return the customer request
	 */
	public static CustomerRequest CreateSubscriberEntranceRequest(String licensePlate, int subscriptionId){
		CustomerRequest subscriberEntranceRequest = new CustomerRequest();
		subscriberEntranceRequest.requestType = CustomerRequestType.ENTER_PARKING_SUBSCRIBER;
		subscriberEntranceRequest.carID = licensePlate;
		subscriberEntranceRequest.subscriptionID = subscriptionId;
		subscriberEntranceRequest.parkingLotID = currentLotId;
		return subscriberEntranceRequest;
	}
	
	/**
	 * Creates the parking lot exit request.
	 *
	 * @param licensePlate the license plate
	 * @return the customer request
	 */
	public static CustomerRequest CreateParkingLotExitRequest(String licensePlate){
		CustomerRequest parkingLotExitRequest = new CustomerRequest();
		parkingLotExitRequest.requestType = CustomerRequestType.EXIT_PARKING;
		parkingLotExitRequest.carID = licensePlate;
		parkingLotExitRequest.parkingLotID = currentLotId;
		return parkingLotExitRequest;
	}
	
	/**
	 * Creates the parking lot names request.
	 *
	 * @return the customer request
	 */
	public static CustomerRequest CreateParkingLotNamesRequest(){
		CustomerRequest parkingLotNamesRequest = new CustomerRequest();
		parkingLotNamesRequest.requestType = CustomerRequestType.PARKING_LOT_NAMES;
		return parkingLotNamesRequest;
	}
}
