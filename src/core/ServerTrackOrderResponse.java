package core;

// The properties of this class are valid only if status == OK.
public class ServerTrackOrderResponse extends ServerBasicResponse {
	public String orderID;
	public String customerID;
	public String licensePlate;
	public String email;
	public String parkingLotID;
	public String arrivalTime;
	public String estimatedDepartureTime;
	
	public ServerTrackOrderResponse() {
		super(ServerResponseStatus.OK, null);
	}
}
