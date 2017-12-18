package core;

// Used in requests between the web GUI and the server
public class WebCustomerRequest {
// Mandatory:
	public WebCustomerRequestType webCustomerRequestType;
	
// Optional:
	// used in most of the orders
	public String customerID;
	public String licensePlate;
	public String email;
	public String parkingLotID;
	
	// used in orderOneTimeParking
	public String arrivalTime;
	public String estimatedDepartureTime;
	
	// used in subscription orders
	public String startingDate;
	// used in orderRoutineMonthlySubscription
	public String routineDepartureTime;
	// used in cancelOrder and in TrackOrderStatus
	public String orderID;
	// used in subscriptionRenewal
	public String subscriptionID;
	
// Add more parameters if required
}
