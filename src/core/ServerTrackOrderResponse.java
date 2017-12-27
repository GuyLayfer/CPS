package core;

// The properties of this class are valid only if status == OK.
public class ServerTrackOrderResponse extends ServerBasicResponse {
	public int orderID;
	public int customerID;
	public String carID;
	public int parkingLotID;
	public long arrivalTime;				// milliseconds
	public long estimatedDepartureTime;		// milliseconds
	
	public ServerTrackOrderResponse() {
		super(ServerResponseStatus.OK, null);
	}
}
