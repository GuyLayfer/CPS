package kioskGui.util;

import java.util.Date;

import core.customer.CustomerRequest;
import core.customer.CustomerRequestType;

public class KioskRequestsFactory {
	
	public static Integer currentLotId;
	
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
	
	public static CustomerRequest CreatePreOrderedEntranceRequest(String licensePlate){
		CustomerRequest preOrderedEntranceRequest = new CustomerRequest();
		preOrderedEntranceRequest.requestType = CustomerRequestType.ENTER_PARKING_PRE_ORDERED;
		preOrderedEntranceRequest.carID = licensePlate;
		preOrderedEntranceRequest.parkingLotID = currentLotId;
		return preOrderedEntranceRequest;
	}
	
	public static CustomerRequest CreateSubscriberEntranceRequest(String licensePlate, int subscriptionId){
		CustomerRequest subscriberEntranceRequest = new CustomerRequest();
		subscriberEntranceRequest.requestType = CustomerRequestType.ENTER_PARKING_SUBSCRIBER;
		subscriberEntranceRequest.carID = licensePlate;
		subscriberEntranceRequest.subscriptionID = subscriptionId;
		subscriberEntranceRequest.parkingLotID = currentLotId;
		return subscriberEntranceRequest;
	}
	
	public static CustomerRequest CreateParkingLotExitRequest(String licensePlate){
		CustomerRequest parkingLotExitRequest = new CustomerRequest();
		parkingLotExitRequest.requestType = CustomerRequestType.EXIT_PARKING;
		parkingLotExitRequest.carID = licensePlate;
		parkingLotExitRequest.parkingLotID = currentLotId;
		return parkingLotExitRequest;
	}
	
	public static CustomerRequest CreateParkingLotNamesRequest(){
		CustomerRequest parkingLotNamesRequest = new CustomerRequest();
		parkingLotNamesRequest.requestType = CustomerRequestType.PARKING_LOT_NAMES;
		return parkingLotNamesRequest;
	}
}
