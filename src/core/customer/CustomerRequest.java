package core.customer;

import java.util.Date;
import java.util.List;

// Used in requests between the web GUI and the server
public class CustomerRequest {
// Mandatory:
	public CustomerRequestType requestType;
	
// Optional:
	// used in most of the orders
	public int customerID;
	public int carID;		// TODO: change it to String when Raz changes it in the DB
	public String email;
	public int parkingLotID;
	public List<String> liscencePlates;
	
	// used in orderOneTimeParking
	public Date arrivalTime;
	public Date estimatedDepartureTime;
	
	// used in subscription orders
	public Date startingDate;
	// used in orderRoutineMonthlySubscription
	public String routineDepartureTime; // Parse this string using 'LocalTime.parse(CharSequence text)' or 'LocalTime.parse(CharSequence text, DateTimeFormatter formatter)'
	// used in cancelOrder and in TrackOrderStatus
	public int orderID;
	// used in subscriptionRenewal
	public int subscriptionID;
	
	public String Complaint;
	
// Add more parameters if required
}
