package server.parkingLot;

import core.parkingLot.ParkingLotInfo;
import core.parkingLot.ParkingState;
import core.parkingLot.ParkingStatus;
import core.CpsGson;

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
		parkedPlacesMap = new TreeMap<Integer, Integer>();
		freePlacesMap = new TreeMap<Integer, Integer>();
		reservedPlacesMap = new TreeMap<Integer, Integer>();
		robot = new Robot();
		pendingBrokenPlaces = new LinkedList<Integer>();
		reservations = new HashMap<String, reservationInfo>();
		
		int size = info.parkingMap.size();
		for (int i = 0; i < size; i++) {
			freePlacesMap.put(i, i);
		}	
	}
	
	synchronized public boolean isFull() {
		return (freePlacesMap.size() == 0) ? true : false;
	}
	
	synchronized public String insertCar(String carId, long leaveTime, boolean withSubscription) throws RobotFailureException {
		reservationInfo reservationInfo = reservations.get(carId);
		if (reservationInfo == null) {
			return "Access denied.\nPlease make an order before entering the parking lot.";
		} 
		// reservationInfo != null
		if (withSubscription) {
			if (!reservationInfo.hasSubscription) {
				return "Access denied.\nYou don't have a subscription for this parking lot.";
			} else if (reservationInfo.enteredTodayWithSubscription) {
				return "Access denied.\nYou have already entered today with this subscription.";
			} else {
				reservationInfo.enteredTodayWithSubscription = true;
			}
		} else { // withSubscription == false
			if (reservationInfo.numberOfOrdersForNext24Hours == 0) {
				return "Access denied.\nPlease make an order before entering the parking lot" + 
						"or try to enter with your subscription";
			} else {
				reservationInfo.numberOfOrdersForNext24Hours--;
				if (!reservationInfo.hasSubscription && reservationInfo.numberOfOrdersForNext24Hours == 0) {
					reservations.remove(carId);
				}
			}
		}
		
		int locationIndex = findPlaceForCar(leaveTime);
		calculateStateAfterInsertion(locationIndex);
		robot.insertCar(locationIndex, info.parkingMap);
		return null;
	}
	
	synchronized public String removeCar(String carId) throws RobotFailureException {
		int carLocation = findCar(carId);
		if (carLocation == -1) {
			return "Your car is not inside this parking lot.";
		}
		calculateStateAfterRemoval(carLocation);
		robot.removeCar(carLocation, info.parkingMap);
		return null;
	}
	
	synchronized public void setBrokenPlace(int placeIndex) throws IndexOutOfBoundsException {
		ParkingState parkingState = info.parkingMap.get(placeIndex);
		if (freePlacesMap.isEmpty() && reservedPlacesMap.isEmpty()) {
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
				if (freePlacesMap.isEmpty()) {
					pendingReservedPlaces++;
				} else {
					int firstFreeIndex = freePlacesMap.firstKey();
					info.parkingMap.get(firstFreeIndex).parkingStatus = ParkingStatus.RESERVED;
					freePlacesMap.remove(firstFreeIndex);
					reservedPlacesMap.put(firstFreeIndex, firstFreeIndex);
				}
				break;
			default: // if it's already BROKEN, do nothing. 
				break;
			}
		}
	}
	
	synchronized public void cancelBrokenPlaceSetting(int placeIndex) throws IndexOutOfBoundsException {
		//TODO: implement
	}
	
	synchronized public boolean reservePlace(String carId, Date estimatedArrivalTime) {
		//TODO: implement
		return true;
	}
	
	synchronized public void cancelReservation(String carId, Date estimatedArrivalTime) {
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
	
	private void shiftCarsRight(int index) {
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
