package kioskGui.util;

import java.util.Date;

import core.customer.CustomerRequest;
import core.customer.CustomerRequestType;

public class KioskRequestsFactory {
	
	public static CustomerRequest CreateOccasionalParkingRequest(int customerID, String licensePlate, Date estimatedDepartureTime, String email){
		CustomerRequest occasionalParkingRequest = new CustomerRequest();
		occasionalParkingRequest.requestType = CustomerRequestType.OCCASIONAL_PARKING;
		occasionalParkingRequest.customerID = customerID;
//		occasionalParkingRequest.carID = licensePlate;
//		Uncomment when carID will become string
		occasionalParkingRequest.estimatedDepartureTime = estimatedDepartureTime;
		occasionalParkingRequest.email = email;
//		occasionalParkingRequest.parkingLotID = parkingLotID;
//		Need to get current parking lot ID
		return occasionalParkingRequest;
	}
	
	public static CustomerRequest CreatePreOrderedEntranceRequest(String licensePlate){
		CustomerRequest preOrderedEntranceRequest = new CustomerRequest();
		preOrderedEntranceRequest.requestType = CustomerRequestType.ENTER_PARKING_PRE_ORDERED;
//		preOrderedEntranceRequest.carID = licensePlate;
//		Uncomment when carID will become string
//		occasionalParkingRequest.parkingLotID = parkingLotID;
//		Need to get current parking lot ID
		return preOrderedEntranceRequest;
	}
	
	public static CustomerRequest CreateSubscriberEntranceRequest(String licensePlate, int subscriptionId){
		CustomerRequest subscriberEntranceRequest = new CustomerRequest();
		subscriberEntranceRequest.requestType = CustomerRequestType.ENTER_PARKING_SUBSCRIBER;
		
//		subscriberEntranceRequest.carID = licensePlate;
//		Uncomment when carID will become string
		
		subscriberEntranceRequest.subscriptionID = subscriptionId;
		
//		occasionalParkingRequest.parkingLotID = parkingLotID;
//		Need to get current parking lot ID
		
		return subscriberEntranceRequest;
	}
	
	public static CustomerRequest CreateParkingLotExitRequest(String licensePlate){
		CustomerRequest parkingLotExitRequest = new CustomerRequest();
		parkingLotExitRequest.requestType = CustomerRequestType.EXIT_PARKING;
		
//		parkingLotExitRequest.carID = licensePlate;
//		Uncomment when carID will become string
		
//		parkingLotExitRequest.parkingLotID = parkingLotID;
//		Need to get current parking lot ID
		
		return parkingLotExitRequest;
	}
}
