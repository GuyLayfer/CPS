package core.customer.responses;

import java.text.DateFormat;
import java.util.Date;

public class TrackOrderResponse extends CustomerBaseResponse {
	public int orderID;
	public int customerID;
	public String carID;
	public int parkingLotID;
	public Date arrivalTime;
	public Date estimatedDepartureTime;

	@Override
	public String toString() {
		return "Track order: " + orderID + "\n" + customerID + "\n" + carID + "\n" + parkingLotID + "\n"
				+ DateFormat.getTimeInstance(DateFormat.SHORT).format(arrivalTime)
				+ "\n" +  DateFormat.getTimeInstance(DateFormat.SHORT).format(estimatedDepartureTime);
	}
}
