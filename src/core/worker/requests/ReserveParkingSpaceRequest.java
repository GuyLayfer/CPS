package core.worker.requests;

import java.util.Date;

public class ReserveParkingSpaceRequest extends BaseRequest {

	public int customerID;

	public String licensePlate;

	public String email;

	public int parkingLotID;

	public Date arrivalTime;

	public Date estimatedDepartureTime;
}
