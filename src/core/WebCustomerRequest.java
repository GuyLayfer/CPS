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
	public int parkingLotID;
	
	// used in orderOneTimeParking
	public long arrivalTime;				// milliseconds
	public long estimatedDepartureTime;		// milliseconds
	
	// used in subscription orders
	public long startingDate;				// milliseconds
	// used in orderRoutineMonthlySubscription
	public long routineDepartureTime;		// milliseconds
	// used in cancelOrder and in TrackOrderStatus
	public int orderID;
	// used in subscriptionRenewal
	public int subscriptionID;
	
// Add more parameters if required
}
