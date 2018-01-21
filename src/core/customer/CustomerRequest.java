package core.customer;

import java.util.Date;
import java.util.List;

// TODO: Auto-generated Javadoc
/**
 * The Class CustomerRequest.
 */
// Used in requests between the web GUI and the server
public class CustomerRequest {

/** The request type. */
// Mandatory:
	public CustomerRequestType requestType;
	
// Optional:
	/** The customer ID. */
// used in most of the orders
	public int customerID;
	
	/** The car ID. */
	public String carID;		

	/** The email. */
	public String email;
	
	/** The parking lot ID. */
	public int parkingLotID;
	
	/** The liscence plates. */
	public List<String> liscencePlates;
	
	/** The arrival time. */
	// used in orderOneTimeParking
	public Date arrivalTime;
	
	/** The estimated departure time. */
	public Date estimatedDepartureTime;
	
	/** The starting date. */
	// used in subscription orders
	public Date startingDate;
	
	/** The routine departure time. */
	// used in orderRoutineMonthlySubscription
	public String routineDepartureTime; // Parse this string using 'LocalTime.parse(CharSequence text)' or 'LocalTime.parse(CharSequence text, DateTimeFormatter formatter)'
	
	/** The order ID. */
	// used in cancelOrder and in TrackOrderStatus
	public int orderID;
	
	/** The subscription ID. */
	// used in subscriptionRenewal
	public int subscriptionID;
	
	/** The complaint. */
	public String complaint;
	
// Add more parameters if required
}
