package server;
// TODO: delete this class
import java.sql.SQLException;

import server.db.dbAPI.DBAPI;

// Singleton.
// All the non-static methods must be synchronized, 
// because kiosk client and webCustomer client may use them concurrently.
public class IDsGenerator {
	private Integer entranceID;
	private Integer subscriptionID;
	private Integer complaintID;
	// add more IDs if required 
	
	private static IDsGenerator instance;
	
//	TODO: maybe to delete this?
//	private IDsGenerator() throws SQLException {
//		this.entranceID = DBAPI.getLastEntranceID();
//		this.subscriptionID = DBAPI.getLastSubscriptionID();
//		this.complaintID = DBAPI.getLastComplaintID();
//	}

	
	public static IDsGenerator getInstance() throws SQLException {
		if (instance == null) {
			instance = new IDsGenerator();
		}
		return instance;
	}
	
	public int nextEntranceID() {
		synchronized (entranceID) {
			return ++entranceID;
		}
	}
	
	public int nextSubscriptionID() {
		synchronized (subscriptionID) {
			return ++subscriptionID;
		}
	}
	
	public int nextComplaintID() {
		synchronized (complaintID) {
			return ++complaintID;
		}
	}
	
}
