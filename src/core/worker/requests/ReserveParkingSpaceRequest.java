package core.worker.requests;

import java.util.Date;

// TODO: Auto-generated Javadoc
/**
 * The Class ReserveParkingSpaceRequest.
 */
public class ReserveParkingSpaceRequest extends BaseRequest {

	/** The customer ID. */
	public int customerID;

	/** The license plate. */
	public String licensePlate;

	/** The email. */
	public String email;

	/** The parking lot ID. */
	public int parkingLotID;

	/** The arrival time. */
	public Date arrivalTime;

	/** The estimated departure time. */
	public Date estimatedDepartureTime;
}
