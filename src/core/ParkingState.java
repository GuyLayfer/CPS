package core;

public class ParkingState {
	public ParkingStatus parkingStatus;
	public String carId; 	// null if not relevant
	public long leaveTime; 	// estimated leave time in milliseconds  
	
	// constructor for PARKED status
	public ParkingState(String carId, long leaveTime) {
		this.parkingStatus = ParkingStatus.PARKED;
		this.carId = carId;
		this.leaveTime = leaveTime;
	}
	
	// constructor for non PARKED status
	public ParkingState(ParkingStatus parkingStatus) {
		this.parkingStatus = parkingStatus;
	}
}
