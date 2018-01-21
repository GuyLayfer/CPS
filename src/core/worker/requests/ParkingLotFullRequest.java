package core.worker.requests;

public class ParkingLotFullRequest extends BaseRequest {

	public Boolean setParkingLotIsFull; // True to set parking lot full. False to set not full.
	
	public int parkingLotId;
}
