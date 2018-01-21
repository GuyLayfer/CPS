package core.customer.responses;

import java.text.DateFormat;
import java.util.Date;

// TODO: Auto-generated Javadoc
/**
 * The Class TrackOrderResponse.
 */
public class TrackOrderResponse extends CustomerBaseResponse {
	
	/** The order ID. */
	public int orderID;
	
	/** The customer ID. */
	public int customerID;
	
	/** The car ID. */
	public String carID;
	
	/** The parking lot ID. */
	public int parkingLotID;
	
	/** The arrival time. */
	public Date arrivalTime;
	
	/** The estimated departure time. */
	public Date estimatedDepartureTime;

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Track order: " + orderID + "\n" + customerID + "\n" + carID + "\n" + parkingLotID + "\n"
				+ DateFormat.getTimeInstance(DateFormat.SHORT).format(arrivalTime)
				+ "\n" +  DateFormat.getTimeInstance(DateFormat.SHORT).format(estimatedDepartureTime);
	}
}
