package server;

import core.ParkingState;
import core.ParkingStatus;
import core.ParkingLotInfo;
import java.util.ArrayList;

// TODO: remove these imports if they are not required
import java.sql.SQLException;
import java.util.Arrays;
import java.util.stream.Collectors;
import server.db.dbAPI.RegularDBAPI;

public class ParkingLotOperations extends ParkingLotInfo {
	private int freePlacesNum;
	private int reservedPlacesNum;
	private int firstFreeIndex;
	private int firstReservedIndex;
	
	// used only when adding new parking lot to the system
	public ParkingLotOperations(int lotId, int floors, int rows, int cols) {
		super(lotId, floors, rows, cols);
		
		int size = floors * rows * cols;
		parkingMap = new ArrayList<ParkingState>(size);
		for (int i = 0; i < size; i++) {
			parkingMap.add(new ParkingState(ParkingStatus.FREE));
		}
		
		freePlacesNum = size;
		firstReservedIndex = size; // means there are no reserved places
	}
	
	public int insertCar(String carId, long leaveTime) {
		return 0;
	}
	
	
	
	/*
Int[3][3][] getAvailableParkingPlaces();
Void setOutOfOrder(int row, int coulmn, int floor);
Bool reserveParkingForCustomer();
int[3] findCar(int carID);
int[3] findPlaceForCar(int leaveTime);
ParkingPositioning calculateStatusAfterPositioning(int carLocation, bool forInsertion);
Class Robot
Public:
Bool insertCar(int[3] carLocation, ParkingPositioning newParkingStatus);
Bool removeCar(int[3] carLocation, ParkingPositioning newParkingStatus);
	 */
	
	
	// TODO: check if we need this constructor
	/*
	public ParkingLotInfo(int lotId, int floors, int rows, int cols) throws SQLException {
		this.lotId = lotId;
		this.floors = floors;
		this.rows = rows;
		this.cols = cols;
		String [] parkingMapArrForSelectQuery = new String[floors * rows * cols];
		RegularDBAPI.getInstance().selectParkingMapByLotId(lotId, parkingMapArrForSelectQuery);
		parkingMap = (ArrayList<ParkingState>) Arrays.stream(parkingMapArrForSelectQuery)
				.map(lotStatus -> new ParkingState(ParkingStatus.convertStringToParkingMapEnum(lotStatus), ""))
				.collect(Collectors.toList());
	}
	*/
}
