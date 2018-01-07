package core;

import java.sql.SQLException;

import server.db.dbAPI.RegularDBAPI;

public class ParkingLotState {
	public int lotId;
	public int floors;
	public int rows;
	public int cols;
	public ParkingMap[] parkingMap;
	
	public ParkingLotState(int lotId, int floors, int rows, int cols) throws SQLException {
		this.lotId = lotId;
		this.floors = floors;
		this.rows = rows;
		this.cols = cols;
		parkingMap = new ParkingMap[floors * rows * cols];
		RegularDBAPI.selectParkingMapByLotId(lotId, parkingMap);
	}
}
