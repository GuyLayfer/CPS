package webGui.util;

import core.Customer.CustomerRequest;
import core.Customer.CustomerRequestType;

public class CustomerRequestFactory {
	
	public static CustomerRequest CreateOrderOneTimeParkingRequest(int customerID,int licensePlate, String email, int parkingLotID, long arrivalTime, 
			long estimatedDepartureTime){
		CustomerRequest OrderOneTimeParkingRequest = new CustomerRequest();
		OrderOneTimeParkingRequest.requestType = CustomerRequestType.ORDER_ONE_TIME_PARKING;
		OrderOneTimeParkingRequest.customerID = customerID;
		OrderOneTimeParkingRequest.carID = licensePlate;
		OrderOneTimeParkingRequest.email = email;
		OrderOneTimeParkingRequest.parkingLotID = parkingLotID;
		OrderOneTimeParkingRequest.arrivalTime = arrivalTime;
		OrderOneTimeParkingRequest.estimatedDepartureTime = estimatedDepartureTime;
		return OrderOneTimeParkingRequest;
	}
	
	public static CustomerRequest CreateCancelRequest(int orderId){
		CustomerRequest cancelOrder = new CustomerRequest();
		cancelOrder.requestType = CustomerRequestType.CANCEL_ORDER;
		cancelOrder.orderID = orderId;
		return cancelOrder;
	}
	
	public static CustomerRequest CreateTrackOrderStatusRequest(int orderId){
		CustomerRequest trackOrderStatus = new CustomerRequest();
		trackOrderStatus.requestType = CustomerRequestType.TRACK_ORDER_STATUS;
		trackOrderStatus.orderID = orderId;
		return trackOrderStatus;
	}
	
	public static CustomerRequest CreateOrderRoutineMonthlySubscriptionRequest(int customerID,int licensePlate, String email, int parkingLotID, long startingDat, 
			long routineDepartureTime){
		CustomerRequest OrderRoutineMonthlySubscriptionRequest = new CustomerRequest();
		OrderRoutineMonthlySubscriptionRequest.requestType = CustomerRequestType.ORDER_ROUTINE_MONTHLY_SUBSCRIPTION;
		OrderRoutineMonthlySubscriptionRequest.customerID = customerID;
		OrderRoutineMonthlySubscriptionRequest.carID = licensePlate;
		OrderRoutineMonthlySubscriptionRequest.email = email;
		OrderRoutineMonthlySubscriptionRequest.parkingLotID = parkingLotID;
		OrderRoutineMonthlySubscriptionRequest.arrivalTime = startingDat;
		OrderRoutineMonthlySubscriptionRequest.estimatedDepartureTime = routineDepartureTime;
		return OrderRoutineMonthlySubscriptionRequest;
	}
	
	public static CustomerRequest CreateOrderFullMonthlySubscriptionRequest(int customerID,int licensePlate, String email, long startingDat){
		CustomerRequest OrderFullMonthlySubscriptionRequest = new CustomerRequest();
		OrderFullMonthlySubscriptionRequest.requestType = CustomerRequestType.ORDER_FULL_MONTHLY_SUBSCRIPTION;
		OrderFullMonthlySubscriptionRequest.customerID = customerID;
		OrderFullMonthlySubscriptionRequest.carID = licensePlate;
		OrderFullMonthlySubscriptionRequest.email = email;
		OrderFullMonthlySubscriptionRequest.arrivalTime = startingDat;
		return OrderFullMonthlySubscriptionRequest;
	}
	
	public static CustomerRequest CreateSubscriptionRenewalRequest(int orderId){
		CustomerRequest subscriptionRenewal = new CustomerRequest();
		subscriptionRenewal.requestType = CustomerRequestType.SUBSCRIPTION_RENEWAL;
		subscriptionRenewal.orderID = orderId;
		return subscriptionRenewal;
	}
	
	public static CustomerRequest CreateOpenComplaintRequest(){
		CustomerRequest openComplaint = new CustomerRequest();
		openComplaint.requestType = CustomerRequestType.OPEN_COMPLAINT;
		return openComplaint;
	}
	
}
