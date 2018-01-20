package server.parkingLot;

import core.parkingLot.ParkingLotInfo;
import core.parkingLot.ParkingState;
import core.parkingLot.ParkingStatus;
import core.CpsGson;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.TreeMap;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
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
	
	private HashMap<String, ReservationInfo> reservations;
	
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
		reservations = new HashMap<String, ReservationInfo>();
		
		int size = info.parkingMap.size();
		for (int i = 0; i < size; i++) {
			freePlacesMap.put(i, i);
		}	
	}
	
	
	synchronized public boolean isFull() {
		return freePlacesMap.isEmpty();
	}
	
	
	synchronized public String insertCar(String carId, long leaveTime, boolean withSubscription) throws RobotFailureException {
		ReservationInfo reservationInfo = reservations.get(carId);
		if (reservationInfo == null) {
			return "Access denied.\nPlease make an order before entering the parking lot.";
		} 
		// reservationInfo != null
		if (withSubscription) {
			Calendar myDate = Calendar.getInstance();
			int dow = myDate.get(Calendar.DAY_OF_WEEK);
			if ((dow == Calendar.FRIDAY) || (dow == Calendar.SATURDAY)) {
				return "Access denied.\nYour subscription is valid only on Sundays through Thursdays.";
			}
			if (!reservationInfo.hasSubscription) {
				return "Access denied.\nYou don't have a subscription for this parking lot.";
			} else if (reservationInfo.enteredTodayWithSubscription) {
				return "Access denied.\nYou have already entered today with this subscription.";
			} else {
				reservationInfo.enteredTodayWithSubscription = true;
			}
		} else { // withSubscription == false
			if (reservationInfo.numberOfOrdersForNext24Hours < 1) {
				return "Access denied.\nPlease make an order before entering the parking lot" + 
						"or try to enter with your subscription";
			} else {
				reservationInfo.numberOfOrdersForNext24Hours--;
				if (!reservationInfo.hasSubscription && reservationInfo.numberOfOrdersForNext24Hours < 1) {
					reservations.remove(carId);
				}
			}
		}
		
		int locationIndex = findPlaceForCar(leaveTime);
		if (locationIndex < 0) {
			return "Access denied.\nUnfortunatly this parking lot is full.";
		}
		calculateStateAfterInsertion(locationIndex, carId, leaveTime);
		robot.insertCar(locationIndex, info.parkingMap);
		return null;
	}
	
	
	synchronized public String removeCar(String carId) throws RobotFailureException {
		int carLocation = findCar(carId);
		if (carLocation < 0) {
			return "Your car is not inside this parking lot.";
		}
		calculateStateAfterRemoval(carLocation, carId);
		if (!freePlacesMap.isEmpty()) {
			if (!pendingBrokenPlaces.isEmpty()) {
				setBrokenPlace(pendingBrokenPlaces.pop());
			} else if (pendingReservedPlaces > 0) {
				// TODO: handle code duplicates
				int firstFreeIndex = freePlacesMap.firstKey();
				info.parkingMap.get(firstFreeIndex).parkingStatus = ParkingStatus.RESERVED;
				freePlacesMap.remove(firstFreeIndex);
				reservedPlacesMap.put(firstFreeIndex, firstFreeIndex);
				pendingReservedPlaces--;
			}
		}
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
				shiftCarsLeft(placeIndex);
				parkingState = info.parkingMap.get(placeIndex);
				if (parkingState.parkingStatus == ParkingStatus.RESERVED) {
					// TODO: handle code duplicates
					if (freePlacesMap.isEmpty()) {
						pendingReservedPlaces++;
					} else {
						int firstFreeIndex = freePlacesMap.firstKey();
						info.parkingMap.get(firstFreeIndex).parkingStatus = ParkingStatus.RESERVED;
						freePlacesMap.remove(firstFreeIndex);
						reservedPlacesMap.put(firstFreeIndex, firstFreeIndex);
					}
				}
				parkingState.parkingStatus = ParkingStatus.BROKEN;
				break;
			case RESERVED:
				parkingState.parkingStatus = ParkingStatus.BROKEN;
				reservedPlacesMap.remove(placeIndex);
				// TODO: handle code duplicates
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
		//TODO: implement after implementing all the other functions
	}
	
	
	synchronized public boolean reservePlaceForTheNext24Hours(String carId) {
		if (freePlacesMap.isEmpty()) {
			return false;
		}
		reservePlace(carId, false);
		//TODO: check if this function is complete
		return true;
	}
	
	
	synchronized public void reservePlace(String carId, boolean forSubscription) {
		ReservationInfo reservationInfo = reservations.get(carId);
		if (reservationInfo == null) {
			reservationInfo = new ReservationInfo();
			reservations.put(carId, reservationInfo);
		}
		if (forSubscription) {
			reservationInfo.hasSubscription = true;
		} else {
			reservationInfo.numberOfOrdersForNext24Hours++;
		}
		// TODO: handle code duplicates
		if (freePlacesMap.isEmpty()) {
			pendingReservedPlaces++;
		} else {
			int firstFreeIndex = freePlacesMap.firstKey();
			info.parkingMap.get(firstFreeIndex).parkingStatus = ParkingStatus.RESERVED;
			freePlacesMap.remove(firstFreeIndex);
			reservedPlacesMap.put(firstFreeIndex, firstFreeIndex);
		}
	}
	
	
	synchronized public void cancelReservation(String carId, boolean forSubscription) {
		ReservationInfo reservationInfo = reservations.get(carId);
		if (reservationInfo == null) {
			return;
		}
		if (forSubscription) {
			reservationInfo.hasSubscription = false;
		} else {
			reservationInfo.numberOfOrdersForNext24Hours--;
		}
		if (reservationInfo.hasSubscription == false && reservationInfo.numberOfOrdersForNext24Hours < 1) {
			reservations.remove(carId);
		}
		if (pendingReservedPlaces > 0) {
			pendingReservedPlaces--;
		} else if (!reservedPlacesMap.isEmpty()) {
			// TODO: handle code duplicates
			int lastReservedIndex = reservedPlacesMap.lastKey();
			info.parkingMap.get(lastReservedIndex).parkingStatus = ParkingStatus.FREE;
			reservedPlacesMap.remove(lastReservedIndex);
			freePlacesMap.put(lastReservedIndex, lastReservedIndex);
		}
		//TODO: check if this function is complete
	}
	
	
	synchronized public String toJson() {
		return gson.toJson(this);
	}
	
	
	synchronized public String infoToJson() {
		return gson.toJson(info);
	}
	
	/************************************** Private Methods **************************************/
	
	private int findCar(String carId) {
		int i = 0;
		for (ParkingState parkingState : info.parkingMap) {
			if (carId.equals(parkingState.carId)) {
				return i;
			}
			i++;
		}
		return -1;
	}
	
	
	private int findPlaceForCar(long leaveTime) {
		if (parkedPlacesMap.isEmpty()) {
			if (!reservedPlacesMap.isEmpty()) {
				return reservedPlacesMap.firstKey();
			} else {
				return freePlacesMap.firstKey();
			}
		}
		for (Integer i : parkedPlacesMap.descendingKeySet()) {
			if (info.parkingMap.get(i).leaveTime <= leaveTime) {
				Integer nextIndex = parkedPlacesMap.higherKey(i);
				if (nextIndex != null) {
					return nextIndex;
				} 
				if (!reservedPlacesMap.isEmpty()) {
					return reservedPlacesMap.firstKey();
				}
				return -1;
			}
		}
		return parkedPlacesMap.firstKey();
	}
	
	
	private void calculateStateAfterInsertion(int carLocation, String carId, long leaveTime) {
		ParkingState parkingState = info.parkingMap.get(carLocation);
		if (parkingState.parkingStatus == ParkingStatus.PARKED) {
			shiftCarsLeft(carLocation);
		}
		parkingState.parkingStatus = ParkingStatus.PARKED;
		parkingState.carId = carId;
		parkingState.leaveTime = leaveTime;
		parkedPlacesMap.put(carLocation, carLocation);
	}
	
	
	private void calculateStateAfterRemoval(int carLocation, String carId) {
		ParkingState parkingState = info.parkingMap.get(carLocation);
		if (reservations.containsKey(carId)) {
			parkingState.parkingStatus = ParkingStatus.RESERVED;
		} else {
			parkingState.parkingStatus = ParkingStatus.FREE;
		}
		parkingState.carId = null;
		parkingState.leaveTime = 0;
		shiftCarsRight(carLocation);
		//TODO: check if this function is complete
	}
	
	
	private void shiftCarsLeft(int index) {
		int firstKey = 0;
		ParkingState prev = null;
		if (!reservedPlacesMap.isEmpty()) {
			firstKey = reservedPlacesMap.firstKey();
			reservedPlacesMap.remove(firstKey);
		} else {
			firstKey = freePlacesMap.firstKey();
			freePlacesMap.remove(firstKey);
		}
		prev = info.parkingMap.get(firstKey);
		ParkingStatus tempParkingStatus = prev.parkingStatus;
		prev.parkingStatus = ParkingStatus.PARKED;
		ParkingState curr = null;
		for (Integer i : parkedPlacesMap.descendingKeySet()) {
			curr = info.parkingMap.get(i);
			prev.carId = curr.carId;
			prev.leaveTime = curr.leaveTime;
			prev = curr;
			if (i == index) {
				break;
			}
		}
		prev.parkingStatus = tempParkingStatus;
		prev.carId = null;
		prev.leaveTime = 0;
		parkedPlacesMap.remove(index);
		parkedPlacesMap.put(firstKey, firstKey);
		//TODO: check if this function is complete
	}
	
	
	private void shiftCarsRight(int index) {
		int lastParkedIndex = parkedPlacesMap.lastKey();
		ParkingState lastParkedState = info.parkingMap.get(lastParkedIndex);
		ParkingState prev = new ParkingState(lastParkedState);
		ParkingState curr = null;
		ParkingState temp = new ParkingState(ParkingStatus.PARKED);
		for (Integer i : parkedPlacesMap.descendingKeySet()) {
			curr = info.parkingMap.get(i);
			if (i == index) {
				break;
			}
			temp.carId = curr.carId;
			temp.leaveTime = curr.leaveTime;
			curr.carId = prev.carId;
			curr.leaveTime = prev.leaveTime;
			prev.carId = temp.carId;
			prev.leaveTime = temp.leaveTime;
		}
		temp.parkingStatus = curr.parkingStatus;
		curr.parkingStatus = prev.parkingStatus;
		curr.carId = prev.carId;
		curr.leaveTime = prev.leaveTime;
		lastParkedState.carId = null;
		lastParkedState.leaveTime = 0;
		parkedPlacesMap.remove(lastParkedIndex);
		if (temp.parkingStatus == ParkingStatus.RESERVED) {
			lastParkedState.parkingStatus = ParkingStatus.RESERVED;
			reservedPlacesMap.put(lastParkedIndex, lastParkedIndex);
		} else { // temp.parkingStatus == ParkingStatus.FREE
			if (reservedPlacesMap.isEmpty()) {
				lastParkedState.parkingStatus = ParkingStatus.FREE;
				freePlacesMap.put(lastParkedIndex, lastParkedIndex);
			} else { // reservedPlacesMap.isEmpty() == false
				lastParkedState.parkingStatus = ParkingStatus.RESERVED;
				reservedPlacesMap.put(lastParkedIndex, lastParkedIndex);
				// TODO: handle code duplicates
				int lastReservedIndex = reservedPlacesMap.lastKey();
				info.parkingMap.get(lastReservedIndex).parkingStatus = ParkingStatus.FREE;
				reservedPlacesMap.remove(lastReservedIndex);
				freePlacesMap.put(lastReservedIndex, lastReservedIndex);
			}
		}
		//TODO: check if this function is complete
	}
	
}


class ReservationInfo {
	public boolean hasSubscription;
	public boolean enteredTodayWithSubscription;
	public int numberOfOrdersForNext24Hours;
}
