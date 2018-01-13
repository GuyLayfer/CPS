package core.worker.requests;

public class ReserveParkingSpaceRequest extends BaseRequest{
	
	public int lotId;
	
	public int row;
	
	public int column;
	
	public int floor;
}
