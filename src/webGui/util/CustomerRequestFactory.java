package webGui.util;

import core.WebCustomerRequestType;
import core.WebCustomerRequest;

public class CustomerRequestFactory {
	
	public static WebCustomerRequest CreateOrderOneTimeParkingRequest(int customerID,int licensePlate, String email, int parkingLotID, long arrivalTime, 
			long estimatedDepartureTime){
		WebCustomerRequest OrderOneTimeParkingRequest = new WebCustomerRequest();
		OrderOneTimeParkingRequest.webCustomerRequestType = WebCustomerRequestType.ORDER_ONE_TIME_PARKING;
		OrderOneTimeParkingRequest.customerID = customerID;
		OrderOneTimeParkingRequest.carID = licensePlate;
		OrderOneTimeParkingRequest.email = email;
		OrderOneTimeParkingRequest.parkingLotID = parkingLotID;
		OrderOneTimeParkingRequest.arrivalTime = arrivalTime;
		OrderOneTimeParkingRequest.estimatedDepartureTime = estimatedDepartureTime;
		return OrderOneTimeParkingRequest;
	}
	
	public static WebCustomerRequest CreateCancelRequest(int orderId){
		WebCustomerRequest cancelOrder = new WebCustomerRequest();
		cancelOrder.webCustomerRequestType = WebCustomerRequestType.CANCEL_ORDER;
		cancelOrder.orderID = orderId;
		return cancelOrder;
	}
	
	public static WebCustomerRequest CreateTrackOrderStatusRequest(int orderId){
		WebCustomerRequest trackOrderStatus = new WebCustomerRequest();
		trackOrderStatus.webCustomerRequestType = WebCustomerRequestType.TRACK_ORDER_STATUS;
		trackOrderStatus.orderID = orderId;
		return trackOrderStatus;
	}
	
	public static WebCustomerRequest CreateOrderRoutineMonthlySubscriptionRequest(int customerID,int licensePlate, String email, int parkingLotID, long startingDat, 
			long routineDepartureTime){
		WebCustomerRequest OrderRoutineMonthlySubscriptionRequest = new WebCustomerRequest();
		OrderRoutineMonthlySubscriptionRequest.webCustomerRequestType = WebCustomerRequestType.ORDER_ROUTINE_MONTHLY_SUBSCRIPTION;
		OrderRoutineMonthlySubscriptionRequest.customerID = customerID;
		OrderRoutineMonthlySubscriptionRequest.carID = licensePlate;
		OrderRoutineMonthlySubscriptionRequest.email = email;
		OrderRoutineMonthlySubscriptionRequest.parkingLotID = parkingLotID;
		OrderRoutineMonthlySubscriptionRequest.arrivalTime = startingDat;
		OrderRoutineMonthlySubscriptionRequest.estimatedDepartureTime = routineDepartureTime;
		return OrderRoutineMonthlySubscriptionRequest;
	}
	
	public static WebCustomerRequest CreateOrderFullMonthlySubscriptionRequest(int customerID,int licensePlate, String email, long startingDat){
		WebCustomerRequest OrderFullMonthlySubscriptionRequest = new WebCustomerRequest();
		OrderFullMonthlySubscriptionRequest.webCustomerRequestType = WebCustomerRequestType.ORDER_FULL_MONTHLY_SUBSCRIPTION;
		OrderFullMonthlySubscriptionRequest.customerID = customerID;
		OrderFullMonthlySubscriptionRequest.carID = licensePlate;
		OrderFullMonthlySubscriptionRequest.email = email;
		OrderFullMonthlySubscriptionRequest.arrivalTime = startingDat;
		return OrderFullMonthlySubscriptionRequest;
	}
	
	public static WebCustomerRequest CreateSubscriptionRenewalRequest(int orderId){
		WebCustomerRequest subscriptionRenewal = new WebCustomerRequest();
		subscriptionRenewal.webCustomerRequestType = WebCustomerRequestType.SUBSCRIPTION_RENEWAL;
		subscriptionRenewal.orderID = orderId;
		return subscriptionRenewal;
	}
	
	public static WebCustomerRequest CreateOpenComplaintRequest(){
		WebCustomerRequest openComplaint = new WebCustomerRequest();
		openComplaint.webCustomerRequestType = WebCustomerRequestType.OPEN_COMPLAINT;
		return openComplaint;
	}
	
}
