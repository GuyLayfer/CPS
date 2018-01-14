package server;

import core.ParkingState;
import core.ParkingStatus;
import core.ParkingLotInfo;
import java.util.ArrayList;

// TODO: remove these imports if they are not required
import java.sql.SQLException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.TreeMap;
import java.util.stream.Collectors;
import server.db.dbAPI.RegularDBAPI;

public class ParkingLotOperations extends ParkingLotInfo {
	// the keys and the values of these maps are indexes of parkingMap
	private TreeMap<Integer, Integer> parkedPlacesMap;
	private TreeMap<Integer, Integer> freePlacesMap;
	private TreeMap<Integer, Integer> reservedPlacesMap;
	private Robot robot;
	// a queue of broken place indexes which the system hasn't updated yet
	private LinkedList<Integer> brokenPlacesQueue; 
	
	
	// used only when adding new parking lot to the system
	public ParkingLotOperations(int lotId, int floors, int rows, int cols) {
		super(lotId, floors, rows, cols);
		int size = floors * rows * cols;
		parkingMap = new ArrayList<ParkingState>(size);
		parkedPlacesMap = new TreeMap<Integer, Integer>();
		freePlacesMap = new TreeMap<Integer, Integer>();
		reservedPlacesMap = new TreeMap<Integer, Integer>();
		for (int i = 0; i < size; i++) {
			parkingMap.add(new ParkingState(ParkingStatus.FREE));
			freePlacesMap.put(i, i);
		}	
	}
	
	synchronized public String insertCar(String carId, long leaveTime) {
		//TODO
		return null;
	}
	
	synchronized public String removeCar(String carId) {
		//TODO
		return null;
	}
	
	synchronized public void setBrokenPlace(int locationIndex) throws IndexOutOfBoundsException {
		ParkingState parkingState = parkingMap.get(locationIndex);
		if (freePlacesMap.isEmpty()) {
			brokenPlacesQueue.add(locationIndex);
		} else {
			switch (parkingState.parkingStatus) {
			case FREE:
				parkingState.parkingStatus = ParkingStatus.BROKEN;
				freePlacesMap.remove(locationIndex);
				break;
			case PARKED:
				//TODO
				break;
			case RESERVED:
				parkingState.parkingStatus = ParkingStatus.BROKEN;
				reservedPlacesMap.remove(locationIndex);
				int lastFreeIndex = freePlacesMap.lastKey();
				parkingMap.get(lastFreeIndex).parkingStatus = ParkingStatus.RESERVED;
				freePlacesMap.remove(lastFreeIndex);
				reservedPlacesMap.put(lastFreeIndex, lastFreeIndex);
				break;
			default: // if it's already BROKEN, do nothing. 
				break;
			}
		}
	}
	
	
	
	
	/*
Bool reserveParkingForCustomer();
int[3] findCar(int carID);
int[3] findPlaceForCar(int leaveTime);
ParkingPositioning calculateStatusAfterPositioning(int carLocation, bool forInsertion);
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
