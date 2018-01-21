package core.parkingLot;

import java.util.ArrayList;

// TODO: Auto-generated Javadoc
/**
 * The Class ParkingLotInfo.
 */
public class ParkingLotInfo {
	
	/** The lot id. */
	final public int lotId;
	
	/** The floors. */
	final public int floors;
	
	/** The rows. */
	final public int rows;
	
	/** The cols. */
	final public int cols;
	
	/** The parking map. */
	public ArrayList<ParkingState> parkingMap;

	/**
	 * Instantiates a new parking lot info.
	 *
	 * @param lotId the lot id
	 * @param floors the floors
	 * @param rows the rows
	 * @param cols the cols
	 */
	public ParkingLotInfo(int lotId, int floors, int rows, int cols) {
		this.lotId = lotId;
		this.floors = floors;
		this.rows = rows;
		this.cols = cols;
		int size = floors * rows * cols;
		parkingMap = new ArrayList<ParkingState>(size);
		for (int i = 0; i < size; i++) {
			parkingMap.add(new ParkingState(ParkingStatus.FREE));
		}	
	}
}
