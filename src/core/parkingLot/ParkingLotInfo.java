package core.parkingLot;

import java.util.ArrayList;
import java.util.TreeMap;

public class ParkingLotInfo {
	final public int lotId;
	final public int floors;
	final public int rows;
	final public int cols;
	public ArrayList<ParkingState> parkingMap;

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
