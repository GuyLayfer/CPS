package core.parkingLot;

// TODO: Auto-generated Javadoc
/**
 * The Class ParkingState.
 */
public class ParkingState {
	
	/** The parking status. */
	public ParkingStatus parkingStatus;
	
	/** The car id. */
	public String carId; 	// null if not relevant
	
	/** The leave time. */
	public long leaveTime; 	// estimated leave time in milliseconds (0 if not relevant)
	
	/**
	 * Instantiates a new parking state.
	 *
	 * @param carId the car id
	 * @param leaveTime the leave time
	 */
	// constructor for PARKED status
	public ParkingState(String carId, long leaveTime) {
		this.parkingStatus = ParkingStatus.PARKED;
		this.carId = carId;
		this.leaveTime = leaveTime;
	}
	
	/**
	 * Instantiates a new parking state.
	 *
	 * @param parkingStatus the parking status
	 */
	// constructor for non PARKED status
	public ParkingState(ParkingStatus parkingStatus) {
		this.parkingStatus = parkingStatus;
	}
	
	/**
	 * Instantiates a new parking state.
	 *
	 * @param parkingState the parking state
	 */
	// copy constructor
	public ParkingState(ParkingState parkingState) {
		this.parkingStatus = parkingState.parkingStatus;
		this.carId = parkingState.carId;
		this.leaveTime = parkingState.leaveTime;
	}
}
