package webGui.util;

import java.time.LocalTime;
import java.util.Date;
import java.util.List;

import core.customer.CustomerRequest;
import core.customer.CustomerRequestType;

public class CustomerRequestFactory {
	
	public static CustomerRequest CreateOrderOneTimeParkingRequest(int customerID,int licensePlate, String email, int parkingLotID, Date arrivalTime, 
			Date estimatedDepartureTime){
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
	
	public static CustomerRequest CreateOrderRoutineMonthlySubscriptionRequest(int customerID,List<String> licensePlates, String email, int parkingLotID, Date startingDate, 
			LocalTime routineDepartureTime){
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
	
	public static CustomerRequest CreateOrderFullMonthlySubscriptionRequest(int customerID,int licensePlate, String email, Date startingDate){
		CustomerRequest OrderFullMonthlySubscriptionRequest = new CustomerRequest();
		OrderFullMonthlySubscriptionRequest.requestType = CustomerRequestType.ORDER_FULL_MONTHLY_SUBSCRIPTION;
		OrderFullMonthlySubscriptionRequest.customerID = customerID;
		OrderFullMonthlySubscriptionRequest.carID = licensePlate;
		OrderFullMonthlySubscriptionRequest.email = email;
		OrderFullMonthlySubscriptionRequest.startingDate = startingDate;
		return OrderFullMonthlySubscriptionRequest;
	}
	
	public static CustomerRequest CreateSubscriptionRenewalRequest(int customerID, int subscriptionId){
		CustomerRequest subscriptionRenewal = new CustomerRequest();
		subscriptionRenewal.requestType = CustomerRequestType.SUBSCRIPTION_RENEWAL;
		subscriptionRenewal.customerID = customerID;
		subscriptionRenewal.subscriptionID = subscriptionId;
		return subscriptionRenewal;
	}
	
	public static CustomerRequest CreateOpenComplaintRequest(String complaint){
		CustomerRequest openComplaint = new CustomerRequest();
		openComplaint.requestType = CustomerRequestType.OPEN_COMPLAINT;
		openComplaint.Complaint = complaint;
		return openComplaint;
	}
	
}
