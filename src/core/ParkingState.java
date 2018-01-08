package core;

public class ParkingState {
	public ParkingStatus parkingState;
	public String carId; // null if not relevant
	
	public ParkingState(ParkingStatus parkingState, String carId) {
		this.parkingState = parkingState;
		this.carId = carId;
	}
}
