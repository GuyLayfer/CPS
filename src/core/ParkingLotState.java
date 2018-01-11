package core;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import server.db.dbAPI.RegularDBAPI;

public class ParkingLotState {
	public int lotId;
	public int floors;
	public int rows;
	public int cols;
	public List<ParkingState> parkingMap;
	
	public ParkingLotState(int lotId, int floors, int rows, int cols) throws SQLException {
		this.lotId = lotId;
		this.floors = floors;
		this.rows = rows;
		this.cols = cols;
		String [] parkingMapArrForSelectQuery = new String[floors * rows * cols];
		RegularDBAPI.getInstance().selectParkingMapByLotId(lotId, parkingMapArrForSelectQuery);
		parkingMap = Arrays.stream(parkingMapArrForSelectQuery)
				.map(lotStatus -> new ParkingState(ParkingStatus.convertStringToParkingMapEnum(lotStatus), ""))
				.collect(Collectors.toList());
	}
}
