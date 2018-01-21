package server.parkingLot;

import java.sql.SQLException;
import java.util.Date;
import java.util.Map.Entry;
import java.util.TreeMap;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import com.google.gson.Gson;

import core.CpsGson;
import server.db.DBConstants.DbSqlColumns;
import server.db.DBConstants.OrderType;
import server.db.DBConstants.SqlTables;
import server.db.dbAPI.RegularDBAPI;
import server.parkingLot.exceptions.*;


// TODO: Auto-generated Javadoc
/**
 *  A Singleton which provides all the required functions for managing and operating the parking lots.
 */
public class ParkingLotsManager {
	
	/** ************************************** Properties ***************************************. */
	
	private static volatile ParkingLotsManager instance = null;
	
	/** The parking lots. */
	private ConcurrentHashMap<Integer, ParkingLot> parkingLots; // synchronized map
	
	/** The lot ids. */
	private Vector<Integer> lotIds; // Vector is synchronized
	
	/** The regular DBAPI. */
	final private RegularDBAPI regularDBAPI = RegularDBAPI.getInstance();
	
	/** The num of millis in 24 hours. */
	final public long numOfMillisIn24Hours = 24*60*60*1000;
	
	/** The gson. */
	final private Gson gson = CpsGson.GetGson();
	
	
	/**
	 * Instantiates a new parking lots manager.
	 *
	 * @throws SQLException the SQL exception
	 */
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
		/*
		Runnable runnable1 = new Runnable() {
			public void run() {
		        // TODO: task to run goes here
		        
			}
	    };
	    ScheduledExecutorService service1 = Executors.newSingleThreadScheduledExecutor();
	    service1.scheduleAtFixedRate(runnable1, 1, 1, TimeUnit.HOURS);
	    */
	}
	
	/**
	 * ************************************ Public Methods *************************************.
	 *
	 * @throws SQLException the SQL exception
	 */
	
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
	 * @param withSubscription should be true, if this car is entering with a routine subscription (not full subscription)
	 * @return if the insertion succeeded, returns null.
	 * 		   otherwise, returns a message which explains what went wrong 
	 * @throws LotIdDoesntExistException the lot id doesnt exist exception
	 * @throws SQLException the SQL exception
	 */
	public String insertCar(int lotId, String carId, Date leaveTime, boolean withSubscription) throws LotIdDoesntExistException, SQLException {
		try {
			ParkingLot parkingLot = getLot(lotId);
			String result = parkingLot.insertCar(carId, leaveTime.getTime(), withSubscription);
			regularDBAPI.updateParkingLot(lotId, parkingLot.toJson());
			return result;
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
		try {
			ParkingLot parkingLot = getLot(lotId);
			String result = parkingLot.removeCar(carId);
			regularDBAPI.updateParkingLot(lotId, parkingLot.toJson());
			return result;
		} catch (RobotFailureException e) {
			// TODO: handle exception - restore the previous ParkingLot state from the DB.
			return "The removal of the car didn't succeed due to a Robot failure.\n" +
					"Please open a complaint.";
		}
	}
	
	
	/**
	 * Sets the status of a parking place to BROKEN.
	 *
	 * @param lotId the lot id
	 * @param floors the floors
	 * @param rows the rows
	 * @param cols the cols
	 * @throws LotIdDoesntExistException the lot id doesnt exist exception
	 * @throws IndexOutOfBoundsException the index out of bounds exception
	 * @throws SQLException the SQL exception
	 */
	public void setBrokenPlace(int lotId, int floors, int rows, int cols) throws LotIdDoesntExistException, IndexOutOfBoundsException, SQLException {
		ParkingLot parkingLot = getLot(lotId);
		parkingLot.setBrokenPlace(floors, rows, cols);
		regularDBAPI.updateParkingLot(lotId, parkingLot.toJson());
	}
	
	
	/**
	 * Cancel broken place setting.
	 *
	 * @param lotId the lot id
	 * @param floors the floors
	 * @param rows the rows
	 * @param cols the cols
	 * @throws LotIdDoesntExistException the lot id doesnt exist exception
	 * @throws IndexOutOfBoundsException the index out of bounds exception
	 * @throws SQLException the SQL exception
	 */
	public void cancelBrokenPlaceSetting(int lotId, int floors, int rows, int cols) throws LotIdDoesntExistException, IndexOutOfBoundsException, SQLException {
		ParkingLot parkingLot = getLot(lotId);
		parkingLot.cancelBrokenPlaceSetting(floors, rows, cols);
		regularDBAPI.updateParkingLot(lotId, parkingLot.toJson());
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
	 * @throws DateIsNotWithinTheNext24Hours the date is not within the next 24 hours
	 * @throws SQLException the SQL exception
	 */
	public boolean reservePlace(int lotId, String carId, Date estimatedArrivalTime) throws LotIdDoesntExistException, DateIsNotWithinTheNext24Hours, SQLException {
		long arrivalTime = estimatedArrivalTime.getTime();
		assertDateIsWithinTheNext24Hours(arrivalTime);
		ParkingLot parkingLot = getLot(lotId);
		boolean result = parkingLot.reservePlaceForTheNext24Hours(carId);
		regularDBAPI.updateParkingLot(lotId, parkingLot.toJson());
		return result;
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
	 * @throws DateIsNotWithinTheNext24Hours the date is not within the next 24 hours
	 * @throws SQLException the SQL exception
	 */
	public void cancelReservation(int lotId, String carId, Date estimatedArrivalTime) throws LotIdDoesntExistException, DateIsNotWithinTheNext24Hours, SQLException {
		long arrivalTime = estimatedArrivalTime.getTime();
		assertDateIsWithinTheNext24Hours(arrivalTime);
		ParkingLot parkingLot = getLot(lotId);
		parkingLot.cancelReservation(carId, false);
		regularDBAPI.updateParkingLot(lotId, parkingLot.toJson());
	}
	
	/**
	 * ************************************ Private Methods *************************************.
	 *
	 * @param lotId the lot id
	 * @return the lot
	 * @throws LotIdDoesntExistException the lot id doesnt exist exception
	 */
	
	private ParkingLot getLot(int lotId) throws LotIdDoesntExistException {
		ParkingLot parkingLot = parkingLots.get(lotId);
		if (parkingLot == null) {
			throw new LotIdDoesntExistException();
		}
		return parkingLot;
	}
	
	
	/**
	 * Assert date is within the next 24 hours.
	 *
	 * @param date the date
	 * @throws DateIsNotWithinTheNext24Hours the date is not within the next 24 hours
	 */
	private void assertDateIsWithinTheNext24Hours(long date) throws DateIsNotWithinTheNext24Hours {
		long now = new Date().getTime();
		if (date > now + numOfMillisIn24Hours) {
			throw new DateIsNotWithinTheNext24Hours();
		}
	}
	
	
	//TODO: implement time events
	/*
	private void fetchNewOrdersFromDB() {
		String query = 
				"SELECT * " +
				" FROM " + SqlTables.CURRENT_CARS_PLANED_BEING_IN_PARKING.getName() +
				" WHERE (" + DbSqlColumns.ARRIVE_PREDICTION.getName() + " BETWEEN ? AND ? ) AND lot_id = ? " +
				" AND " + DbSqlColumns.ORDER_TYPE.getName() + " = " + OrderType.ONE_TIME.getValue();
	}
	
	
	private void fetchNewSubscriptionsFromDB() {
		
	}
	*/
}

