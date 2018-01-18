package server;

import core.ParkingState;
import core.ParkingStatus;
import core.CpsGson;
import core.ParkingLotInfo;
import java.util.ArrayList;
import java.util.TreeMap;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import com.google.gson.Gson;
import server.db.dbAPI.RegularDBAPI;

public class ParkingLot {
	
	/**************************************** Properties ****************************************/
	
	private ParkingLotInfo info;
	// the keys and the values of these maps are indexes of parkingMap
	private TreeMap<Integer, Integer> parkedPlacesMap;
	private TreeMap<Integer, Integer> freePlacesMap;
	private TreeMap<Integer, Integer> reservedPlacesMap;
	private Robot robot;
	// a queue of broken place indexes which the system hasn't updated yet
	private LinkedList<Integer> pendingBrokenPlaces; 
	// number of reserved places which the system hasn't updated yet
	private int pendingReservedPlaces;
	
	private HashMap<String, reservationInfo> reservations;
	
	final private static Gson gson = CpsGson.GetGson();
	
	/************************************** Public Methods **************************************/
	
	// used only when adding new parking lot to the system
	public ParkingLot(int lotId, int floors, int rows, int cols) {
		info = new ParkingLotInfo(lotId, floors, rows, cols);
		int size = info.parkingMap.size();
		parkedPlacesMap = new TreeMap<Integer, Integer>();
		freePlacesMap = new TreeMap<Integer, Integer>();
		reservedPlacesMap = new TreeMap<Integer, Integer>();
		for (int i = 0; i < size; i++) {
			freePlacesMap.put(i, i);
		}	
	}
	
	synchronized public boolean isFull() {
		return (freePlacesMap.size() == 0) ? true : false;
	}
	
	synchronized public String insertCar(String carId, long leaveTime) {
		//TODO: implement
		return null;
	}
	
	synchronized public String removeCar(String carId) {
		//TODO: implement
		return null;
	}
	
	synchronized public void setBrokenPlace(int placeIndex) throws IndexOutOfBoundsException {
		ParkingState parkingState = info.parkingMap.get(placeIndex);
		if (freePlacesMap.isEmpty()) {
			pendingBrokenPlaces.add(placeIndex);
		} else {
			switch (parkingState.parkingStatus) {
			case FREE:
				parkingState.parkingStatus = ParkingStatus.BROKEN;
				freePlacesMap.remove(placeIndex);
				break;
			case PARKED:
				//TODO: implement
				break;
			case RESERVED:
				parkingState.parkingStatus = ParkingStatus.BROKEN;
				reservedPlacesMap.remove(placeIndex);
				int lastFreeIndex = freePlacesMap.lastKey();
				info.parkingMap.get(lastFreeIndex).parkingStatus = ParkingStatus.RESERVED;
				freePlacesMap.remove(lastFreeIndex);
				reservedPlacesMap.put(lastFreeIndex, lastFreeIndex);
				break;
			default: // if it's already BROKEN, do nothing. 
				break;
			}
		}
	}
	
	synchronized public void unSetBrokenPlace(int placeIndex) throws IndexOutOfBoundsException {
		//TODO: implement
	}
	
	synchronized public boolean reservePlace(Date date) {
		//TODO: implement
		return true;
	}
	
	synchronized public void unReservePlace(Date date) {
		//TODO: implement
	}
	
	synchronized public String toJson() {
		return gson.toJson(this);
	}
	
	synchronized public String infoToJson() {
		return gson.toJson(info);
	}
	
	/************************************** Private Methods **************************************/
	
	private void shiftCarsLeft(int index) {
		//TODO: implement
	}
	
	private int findCar(String carID) {
		//TODO: implement
		return -1;
	}
	
	private int findPlaceForCar(long leaveTime) {
		//TODO: implement
		return -1;
	}
	
	private void calculateStateAfterInsertion(int carLocation) {
		//TODO: implement
	}
	
	private void calculateStateAfterRemoval(int carLocation) {
		//TODO: implement
	}
}

class reservationInfo {
	public boolean hasSubscription;
	public boolean enteredTodayWithSubscription;
	public int numberOfOrdersForNext24Hours;
}
