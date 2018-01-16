package core;

import java.util.ArrayList;

public class ParkingLotInfo {
	final public int lotId;
	final public int floors;
	final public int rows;
	final public int cols;
	public ArrayList<ParkingState> parkingMap;	// initialized by ParkingLot class

	public ParkingLotInfo(int lotId, int floors, int rows, int cols) {
		this.lotId = lotId;
		this.floors = floors;
		this.rows = rows;
		this.cols = cols;
	}
}
