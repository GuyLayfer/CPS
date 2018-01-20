package server.parkingLot;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.TreeMap;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;

import com.google.gson.Gson;

import core.CpsGson;
import server.db.dbAPI.RegularDBAPI;


/**
 *  A Singleton which provides all the required functions for managing and operating the parking lots.
 */
public class ParkingLotsManager {
	
	/**************************************** Properties ****************************************/
	
	private static volatile ParkingLotsManager instance = null;
	
	private ConcurrentHashMap<Integer, ParkingLot> parkingLots; // synchronized map
	private Vector<Integer> lotIds; // Vector is synchronized
	
	final private RegularDBAPI regularDBAPI = RegularDBAPI.getInstance();
	final private long numOfMillisIn24Hours = 24*60*60*1000;
	final private Gson gson = CpsGson.GetGson();
	
	
	private ParkingLotsManager() throws SQLException {
		parkingLots = new ConcurrentHashMap<Integer, ParkingLot>();
		lotIds = new Vector<Integer>();
		TreeMap<Integer, String> parkingLotJsons = new TreeMap<Integer, String>();
		regularDBAPI.selectAllParkingLots(parkingLotJsons);
		for (Entry<Integer, String> mapEntry : parkingLotJsons.entrySet()) {
			Integer key = mapEntry.getKey();
			String value = mapEntry.getValue();
			if (value != null && value != "") {
				parkingLots.put(key, gson.fromJson(value, ParkingLot.class));
				lotIds.add(key);
			}
		}
	}
	
	/************************************** Public Methods **************************************/
	
	/**
	 * Initializer - used only once in CPSMain.
	 *
	 * @throws SQLException the SQL exception
	 */
	public static void initialize() throws SQLException {
		if (instance == null) {
			instance = new ParkingLotsManager();
		}
	}
	
	
	/**
	 * Gets the single instance of ParkingLotsManager.
	 *
	 * @return single instance of ParkingLotsManager
	 */
	public static ParkingLotsManager getInstance() {
		return instance;
	}
	
	
	/**
	 * Gets a Vector which contains the ids of all the parking lots in CPS.
	 *
	 * @return the ids of all the parking lots in CPS
	 */
	public Vector<Integer> getAllIds() {
		return lotIds;
	}
	
	
	/**
	 * Adds a new parking lot to CPS.
	 *
	 * @param floors the number of floors
	 * @param rows the number of rows
	 * @param cols the number of columns
	 * @return the id of the new parking lot
	 * @throws SQLException the SQL exception
	 */
	public int addParkingLot(int floors, int rows, int cols) throws SQLException {
		int lotId = regularDBAPI.insertParkingLot();
		ParkingLot newParkingLot = new ParkingLot(lotId, floors, rows, cols);
		regularDBAPI.updateParkingLot(lotId, newParkingLot.toJson());
		parkingLots.put(lotId, newParkingLot);
		lotIds.addElement(lotId);
		return lotId;
	}
	
	
	/**
	 * Removes a parking lot from CPS.
	 *
	 * @param lotId the lot id
	 * @throws LotIdDoesntExistException the lot id doesnt exist exception
	 * @throws SQLException the SQL exception
	 */
	public void removeParkingLot(int lotId) throws LotIdDoesntExistException, SQLException {
		//TODO: implement if I'll will have time for that
	}
	
	
	/**
	 * Gets the json representation of the parking lot info.
	 *
	 * @param lotId the lot id
	 * @return the json representation of a ParkingLotInfo object which related to lotId
	 * @throws LotIdDoesntExistException the lot id doesnt exist exception
	 */
	public String parkingLotInfoToJson(int lotId) throws LotIdDoesntExistException {
		return getLot(lotId).infoToJson();
	}
	
	
	/**
	 * Checks if a parking lot is full.
	 *
	 * @param lotId the lot id
	 * @return true, if the parking lot is full
	 * @throws LotIdDoesntExistException the lot id doesnt exist exception
	 */
	public boolean parkingLotIsFull(int lotId) throws LotIdDoesntExistException {
		return getLot(lotId).isFull();
	}
	
	
	/**
	 * Inserts a car into a parking lot (only after reserving a place for that car).
	 *
	 * @param lotId the lot id
	 * @param carId the car id
	 * @param leaveTime the leave time
	 * @param withSubscription should be true, if this car is entering with a subscription
	 * @return if the insertion succeeded, returns null.
	 * 		   otherwise, returns a message which explains what went wrong 
	 * @throws LotIdDoesntExistException the lot id doesnt exist exception
	 * @throws SQLException the SQL exception
	 */
	public String insertCar(int lotId, String carId, Date leaveTime, boolean withSubscription) throws LotIdDoesntExistException, SQLException {
		try {
			return getLot(lotId).insertCar(carId, leaveTime.getTime(), withSubscription);
		} catch (RobotFailureException e) {
			// TODO: handle exception - restore the previous ParkingLot state from the DB.
			return "The insertion of the car didn't succeed due to a Robot failure.\n" +
					"Please open a complaint.";
		}
	}
	
	
	/**
	 * Removes a car from a parking lot.
	 *
	 * @param lotId the lot id
	 * @param carId the car id
	 * @return if the removal succeeded, returns null.
	 * 		   otherwise, returns a message which explains what went wrong 
	 * @throws LotIdDoesntExistException the lot id doesnt exist exception
	 * @throws SQLException the SQL exception
	 */
	public String removeCar(int lotId, String carId) throws LotIdDoesntExistException, SQLException {
		//TODO: implement
		return null;
	}
	
	
	/**
	 * Sets the status of a parking place to BROKEN.
	 *
	 * @param lotId the lot id
	 * @param placeIndex the broken place index in the parkingMap ArrayList
	 * @throws LotIdDoesntExistException the lot id doesnt exist exception
	 * @throws IndexOutOfBoundsException the index out of bounds exception
	 * @throws SQLException the SQL exception
	 */
	public void setBrokenPlace(int lotId, int placeIndex) throws LotIdDoesntExistException, IndexOutOfBoundsException, SQLException {
		//TODO: implement
	}
	
	
	/**
	 * Cancel broken place setting.
	 *
	 * @param lotId the lot id
	 * @param placeIndex the broken place index in the parkingMap ArrayList
	 * @throws LotIdDoesntExistException the lot id doesnt exist exception
	 * @throws IndexOutOfBoundsException the index out of bounds exception
	 * @throws SQLException the SQL exception
	 */
	public void cancelBrokenPlaceSetting(int lotId, int placeIndex) throws LotIdDoesntExistException, IndexOutOfBoundsException, SQLException {
		//TODO: implement
	}
	
	
	/**
	 * Reserves a parking place inside a specific parking lot for one time order 
	 * with estimatedArrivalTime within the next 24 hours.
	 * Don't use this function for reservations with estimatedArrivalTime after that, just update the DB. 
	 *
	 * @param lotId the lot id
	 * @param carId the car id
	 * @param estimatedArrivalTime the estimated arrival time (should be within the next 24 hours)
	 * @return true,  if successful.
	 * 		   false, if the parking lot is full 
	 * @throws LotIdDoesntExistException the lot id doesnt exist exception
	 * @throws SQLException the SQL exception
	 * @throws DateIsNotWithinTheNext24Hours 
	 */
	public boolean reservePlace(int lotId, String carId, Date estimatedArrivalTime) throws LotIdDoesntExistException, DateIsNotWithinTheNext24Hours {
		long arrivalTime = estimatedArrivalTime.getTime();
		assertDateIsWithinTheNext24Hours(arrivalTime);
		//TODO: check if this function is complete
		return getLot(lotId).reservePlaceForTheNext24Hours(carId);
	}
	
	
	/**
	 * Cancel reservation of a parking place inside a specific parking lot for one time order 
	 * with estimatedArrivalTime within the next 24 hours.
	 * Don't use this function to cancel reservations with estimatedArrivalTime after that, just update the DB.
	 *
	 * @param lotId the lot id
	 * @param carId the car id
	 * @param estimatedArrivalTime the estimated arrival time (should be within the next 24 hours)
	 * @throws LotIdDoesntExistException the lot id doesn't exist exception
	 * @throws DateIsNotWithinTheNext24Hours 
	 * @throws SQLException the SQL exception
	 */
	public void cancelReservation(int lotId, String carId, Date estimatedArrivalTime) throws LotIdDoesntExistException, DateIsNotWithinTheNext24Hours {
		long arrivalTime = estimatedArrivalTime.getTime();
		assertDateIsWithinTheNext24Hours(arrivalTime);
		//TODO: check if this function is complete
		getLot(lotId).cancelReservation(carId, false);
	}
	
	/************************************** Private Methods **************************************/
	
	private ParkingLot getLot(int lotId) throws LotIdDoesntExistException {
		ParkingLot parkingLot = parkingLots.get(lotId);
		if (parkingLot == null) {
			throw new LotIdDoesntExistException();
		}
		return parkingLot;
	}
	
	
	private void assertDateIsWithinTheNext24Hours(long date) throws DateIsNotWithinTheNext24Hours {
		long now = new Date().getTime();
		if (date > now + numOfMillisIn24Hours) {
			throw new DateIsNotWithinTheNext24Hours();
		}
	}
	
	
	//TODO: implement time events
}



class LotIdDoesntExistException extends Exception {
	private static final long serialVersionUID = 1L;
}

class DateIsNotWithinTheNext24Hours extends Exception {
	private static final long serialVersionUID = 1L;
}
