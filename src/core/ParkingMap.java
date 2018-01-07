package core;

public class ParkingMap {
	public ParkingState parkingState;
	public String carId; // null if not relevant
	
	public ParkingMap(ParkingState parkingState, String carId) {
		this.parkingState = parkingState;
		this.carId = carId;
	}
}
