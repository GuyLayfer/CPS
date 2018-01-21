package server.parkingLot;

import core.parkingLot.ParkingLotInfo;
import core.parkingLot.ParkingState;
import core.parkingLot.ParkingStatus;
import server.parkingLot.exceptions.RobotFailureException;
import core.CpsGson;

import java.util.Calendar;
import java.util.TreeMap;
import java.util.HashMap;
import java.util.LinkedList;
import com.google.gson.Gson;

// TODO: Auto-generated Javadoc
/**
 * The Class ParkingLot.
 */
public class ParkingLot {
	
	/** ************************************** Properties ***************************************. */
	
	private ParkingLotInfo info;
	
	/** The parked places map. */
	// the keys and the values of these maps are indexes of parkingMap
	private TreeMap<Integer, Integer> parkedPlacesMap;
	
	/** The free places map. */
	private TreeMap<Integer, Integer> freePlacesMap;
	
	/** The reserved places map. */
	private TreeMap<Integer, Integer> reservedPlacesMap;
	
	/** The robot. */
	private Robot robot;
	
	/** The pending broken places. */
	// a queue of broken place indexes which the system hasn't updated yet
	private LinkedList<Integer> pendingBrokenPlaces; 
	
	/** The pending reserved places. */
	// number of reserved places which the system hasn't updated yet
	private int pendingReservedPlaces;
	
	/** The reservations. */
	private HashMap<String, ReservationInfo> reservations;
	
	/** The Constant gson. */
	final private static Gson gson = CpsGson.GetGson();
	
	/**
	 * ************************************ Public Methods *************************************.
	 *
	 * @param lotId the lot id
	 * @param floors the floors
	 * @param rows the rows
	 * @param cols the cols
	 */
	
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
	
	
	/**
	 * Checks if is full.
	 *
	 * @return true, if is full
	 */
	synchronized public boolean isFull() {
		return freePlacesMap.isEmpty();
	}
	
	
	/**
	 * Insert car.
	 *
	 * @param carId the car id
	 * @param leaveTime the leave time
	 * @param withSubscription the with subscription
	 * @return the string
	 * @throws RobotFailureException the robot failure exception
	 */
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
	
	
	/**
	 * Removes the car.
	 *
	 * @param carId the car id
	 * @return the string
	 * @throws RobotFailureException the robot failure exception
	 */
	synchronized public String removeCar(String carId) throws RobotFailureException {
		int carLocation = findCar(carId);
		if (carLocation < 0) {
			return "Your car is not inside this parking lot.";
		}
		calculateStateAfterRemoval(carLocation, carId);
		if (!freePlacesMap.isEmpty()) {
			if (!pendingBrokenPlaces.isEmpty()) {
				setBrokenPlaceAux(pendingBrokenPlaces.pop());
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
	
	
	/**
	 * Sets the broken place.
	 *
	 * @param floors the floors
	 * @param rows the rows
	 * @param cols the cols
	 * @throws IndexOutOfBoundsException the index out of bounds exception
	 */
	synchronized public void setBrokenPlace(int floors, int rows, int cols) throws IndexOutOfBoundsException {
		 setBrokenPlaceAux(convert3DIndexesTo1DIndex(floors, rows, cols));
	}
	
	
	/**
	 * Cancel broken place setting.
	 *
	 * @param floors the floors
	 * @param rows the rows
	 * @param cols the cols
	 * @throws IndexOutOfBoundsException the index out of bounds exception
	 */
	synchronized public void cancelBrokenPlaceSetting(int floors, int rows, int cols) throws IndexOutOfBoundsException {
		int placeIndex = convert3DIndexesTo1DIndex(floors, rows, cols);
		ParkingState parkingState = info.parkingMap.get(placeIndex);
		if (parkingState.parkingStatus != ParkingStatus.BROKEN) {
			return;
		}
		
		parkingState.parkingStatus = ParkingStatus.FREE;
		if (!parkedPlacesMap.isEmpty() && placeIndex < parkedPlacesMap.lastKey()) {
			shiftCarsRight(placeIndex);
		} else if (!reservedPlacesMap.isEmpty() && placeIndex < reservedPlacesMap.lastKey()) {
			parkingState.parkingStatus = ParkingStatus.RESERVED;
			reservedPlacesMap.put(placeIndex, placeIndex);
			int lastReservedIndex = reservedPlacesMap.lastKey();
			info.parkingMap.get(lastReservedIndex).parkingStatus = ParkingStatus.FREE;
			reservedPlacesMap.remove(lastReservedIndex);
			freePlacesMap.put(lastReservedIndex, lastReservedIndex);
		} else { // placeIndex > reservedPlacesMap.lastKey()
			freePlacesMap.put(placeIndex, placeIndex);
		}
		
		if (!pendingBrokenPlaces.isEmpty()) {
			setBrokenPlaceAux(pendingBrokenPlaces.pop());
		} else if (pendingReservedPlaces > 0 && !freePlacesMap.isEmpty()) {
			// TODO: handle code duplicates
			int firstFreeIndex = freePlacesMap.firstKey();
			info.parkingMap.get(firstFreeIndex).parkingStatus = ParkingStatus.RESERVED;
			freePlacesMap.remove(firstFreeIndex);
			reservedPlacesMap.put(firstFreeIndex, firstFreeIndex);
			pendingReservedPlaces--;
		}
	}
	
	
	/**
	 * Reserve place for the next 24 hours.
	 *
	 * @param carId the car id
	 * @return true, if successful
	 */
	synchronized public boolean reservePlaceForTheNext24Hours(String carId) {
		if (freePlacesMap.isEmpty()) {
			return false;
		}
		reservePlace(carId, false);
		return true;
	}
	
	
	/**
	 * Reserve place.
	 *
	 * @param carId the car id
	 * @param forSubscription the for subscription
	 */
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
	
	
	/**
	 * Cancel reservation.
	 *
	 * @param carId the car id
	 * @param forSubscription the for subscription
	 */
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
	
	
	/**
	 * To json.
	 *
	 * @return the string
	 */
	synchronized public String toJson() {
		return gson.toJson(this);
	}
	
	
	/**
	 * Info to json.
	 *
	 * @return the string
	 */
	synchronized public String infoToJson() {
		return gson.toJson(info);
	}
	
	/**
	 * ************************************ Private Methods *************************************.
	 *
	 * @param carId the car id
	 * @return the int
	 */
	
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
	
	
	/**
	 * Find place for car.
	 *
	 * @param leaveTime the leave time
	 * @return the int
	 */
	private int findPlaceForCar(long leaveTime) {
		if (parkedPlacesMap.isEmpty()) {
			if (!reservedPlacesMap.isEmpty()) {
				return reservedPlacesMap.firstKey();
			} else if (!freePlacesMap.isEmpty()) {
				return freePlacesMap.firstKey();
			} else { // means that the whole parking lot is broken
				return -1;
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
	
	
	/**
	 * Calculate state after insertion.
	 *
	 * @param carLocation the car location
	 * @param carId the car id
	 * @param leaveTime the leave time
	 */
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
	
	
	/**
	 * Calculate state after removal.
	 *
	 * @param carLocation the car location
	 * @param carId the car id
	 */
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
	
	
	/**
	 * Shift cars left.
	 *
	 * @param index the index
	 */
	private void shiftCarsLeft(int index) {
		if (parkedPlacesMap.isEmpty() || (reservedPlacesMap.isEmpty() && freePlacesMap.isEmpty())) {
			return;
		}
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
	
	
	/**
	 * Shift cars right.
	 *
	 * @param index the index
	 */
	private void shiftCarsRight(int index) {
		if (parkedPlacesMap.isEmpty()) {
			return;
		}
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
	
	
	/**
	 * Convert 3 D indexes to 1 D index.
	 *
	 * @param floors the floors
	 * @param rows the rows
	 * @param cols the cols
	 * @return the int
	 */
	private int convert3DIndexesTo1DIndex(int floors, int rows, int cols) {
		return ((floors - 1) * info.rows * info.cols) + ((rows - 1) * info.cols) + cols - 1;
	}
	
	
	/**
	 * Sets the broken place aux.
	 *
	 * @param placeIndex the new broken place aux
	 * @throws IndexOutOfBoundsException the index out of bounds exception
	 */
	private void setBrokenPlaceAux(int placeIndex) throws IndexOutOfBoundsException {
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
	
}


class ReservationInfo {
	public boolean hasSubscription;
	public boolean enteredTodayWithSubscription;
	public int numberOfOrdersForNext24Hours;
}
