package server;

import java.sql.SQLException;
import java.util.Date;
import java.util.Hashtable;

// Singleton
public class ParkingLotsManager {
	private static ParkingLotsManager instance = null;
	private Hashtable<Integer, ParkingLotOperations> parkingLots;
	
	private ParkingLotsManager() throws SQLException {
		//TODO: implement
	}
	
	public static void initialize() throws SQLException {
		if (instance == null) {
			instance = new ParkingLotsManager();
		}
	}
	
	public static ParkingLotsManager getInstance() {
		return instance;
	}
	
	public int addParkingLot(int floors, int rows, int cols) throws SQLException {
		//TODO: implement
		return 0;
	}
	
	public void removeParkingLot(int lotId) throws SQLException {
		//TODO: implement
	}
	
	public String getJsonOfParkingLotInfo(int lotId) {
		//TODO: implement
		return null;
	}
	
	public String insertCar(int lotId, String carId, long leaveTime) throws SQLException {
		//TODO: implement
		return null;
	}
	
	public String removeCar(int lotId, String carId) throws SQLException {
		//TODO: implement
		return null;
	}
	
	public void setBrokenPlace(int lotId, int placeIndex) throws IndexOutOfBoundsException, SQLException {
		//TODO: implement
	}
	
	public void unSetBrokenPlace(int lotId, int placeIndex) throws IndexOutOfBoundsException, SQLException {
		//TODO: implement
	}
	
	public boolean reservePlace(int lotId, Date date) throws SQLException {
		//TODO: implement
		return true;
	}
	
	public void unReservePlace(int lotId, Date date) throws SQLException {
		//TODO: implement
	}
}

// TODO: add exceptions: sql, lotId
