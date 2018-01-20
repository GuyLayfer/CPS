package server.db.dbAPI; 

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

import core.worker.Complaint;

import java.util.List;

import server.db.DBConnection;
import server.db.DBConnection.sqlTypeKind;
import server.db.DBConstants;
import server.db.DBConstants.DbSqlColumns;
import server.db.DBConstants.TrueFalse;
import server.db.queries.ParkingMapQueries;
import server.db.queries.RegularQueries;


// TODO: Auto-generated Javadoc
/**
 * The Class RegularDBAPI.
 */
public class RegularDBAPI extends DBAPI{

	/** The instance. */
	private static volatile RegularDBAPI instance;
	
	/** The regular queries inst. */
	private RegularQueries regularQueriesInst;

	/**
	 * Instantiates a new regular DBAPI.
	 */
	private RegularDBAPI() {
		regularQueriesInst = RegularQueries.getInstance();
		parkingMapQueriesInst = ParkingMapQueries.getInstance();
	}

	/**
	 * Gets the single instance of RegularDBAPI.
	 *
	 * @return single instance of RegularDBAPI
	 */
	public static RegularDBAPI getInstance() {
		RegularDBAPI result = instance;
		if (result == null) {
			synchronized (mutex) {
				result = instance;
				if (result == null)
					instance = result = new RegularDBAPI();
			}
		}
		return result;
	}
	
	
/**
 * **************************************** TODO Section *****************************************.
 *
 * @return the new parking lot id
 * @throws SQLException the SQL exception
 */

	/**
	 * returns a unique id for a new parking lot.
	 * this function inserts a new record with: new lot id that is generated by MYSQL, and NULL.
	 * 
	 * @return the new parking lot id
	 * @throws SQLException the SQL exception
	 */
	public int getNewParkingLotId() throws SQLException {
		Queue<Object> paramsValues = new LinkedList<Object>(); // push all params to paramsValues. in order of the SQL query
		paramsValues.add("");
		int id = DBConnection.updateSql(regularQueriesInst.insert_new_server_info, paramsValues);
		return id; 
	}
	
	/**
	 * Selects all ParkingLot json-string representations in the DB and stores them in a result map.
	 *
	 * @param resultMap - the keys are lotIds and the values are json-string representations of ParkingLot objects
	 * @throws SQLException the SQL exception
	 */
	public void selectAllParkingLots(Map<Integer, String> resultMap) throws SQLException {
		ArrayList<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
		selectAllParkingLotsAsObjects(resultList);
		Iterator<Map<String, Object>> iterator = resultList.iterator();
		while (iterator.hasNext()) {
			Map<String, Object> row = (Map<String, Object>) iterator.next();
			resultMap.put((Integer) row.get(DbSqlColumns.LOT_ID.getName()), (String)row.get(DbSqlColumns.INFO.getName()));
		}
	}
	
	/**
	 * Select all parking lots as objects.
	 *
	 * @param resultList the result list
	 * @throws SQLException the SQL exception
	 */
	private void selectAllParkingLotsAsObjects(ArrayList<Map<String, Object>> resultList) throws SQLException {
		DBConnection.selectSql(regularQueriesInst.select_server_info, null, resultList);
	}

	/**
	 * Updates lotId's entry with the value parkingLotJson.
	 *
	 * @param lotId the lot id
	 * @param parkingLotJson - a json-string representation of ParkingLot object
	 * @throws SQLException the SQL exception
	 */
	public void updateParkingLot(int lotId, String parkingLotJson) throws SQLException {
		Queue<Object> paramsValues = new LinkedList<Object>(); // push all params to paramsValues. in order of the SQL query
		paramsValues.add(parkingLotJson);
		paramsValues.add(lotId);

		DBConnection.updateSql(regularQueriesInst.update_server_info_of_lot_id, paramsValues);
	}
	
	/**
	 * Update the new parking lot entry into the DB (the id is determined by the DB).
	 *
	 * @param lotId the lot id
	 * @param parkingLotJson - a json-string representation of ParkingLot object
	 * @throws SQLException the SQL exception
	 */
	public void insertParkingLot(int lotId, String parkingLotJson) throws SQLException {
		updateParkingLot(lotId, parkingLotJson);
	}
	
	/**
	 * Deletes a parking lot entry from the DB.
	 *
	 * @param lotId the lot id
	 * @throws SQLException the SQL exception
	 */
	public void deleteParkingLot(int lotId) throws SQLException {
		Queue<Object> paramsValues = new LinkedList<Object>(); // push all params to paramsValues. in order of the SQL query
		paramsValues.add(lotId);
		DBConnection.updateSql(regularQueriesInst.delete_server_info_of_lot_id, paramsValues);
	}
	
	/**
	 * Car left parking.
	 * 
	 * updates in logs table the leaving time.
	 * deletes from the table of current cars in parking.
	 *
	 * @param lotId the lot id
	 * @param carId the car id
	 * @param timeLeft the time left
	 * @throws SQLException the SQL exception
	 */
	public void carLeftParking(int lotId, String carId, Date timeLeft) throws SQLException , Exception{
		Queue<Object> paramsValues = new LinkedList<Object>(); // push all params to paramsValues. in order of SQL
		paramsValues.add(ServerUtils.getTimeStampOfDate(timeLeft));
		paramsValues.add(lotId);
		paramsValues.add(carId);
		DBConnection.updateSql(regularQueriesInst.update_time_car_left_parking_in_logs, paramsValues);
		paramsValues.add(carId);
		DBConnection.updateSql(regularQueriesInst.delete_entrance_from_car_planed_being_in_parking_by_entrance_id, paramsValues);
	}
	
	/**
	 * adds a routine monthly subscription to an account.
	 *
	 * @param accountId the account id
	 * @param lotId the lot id
	 * @param carIds a list of cars (may contain 1 car)
	 * @throws SQLException the SQL exception
	 */
	public void addRoutineMonthlySubscriptionToAccount(int accountId, int lotId, List<String> carIds) throws SQLException {
		//TODO: implement
	}
	
	/**
	 * adds a Full monthly subscription to an account.
	 *
	 * @param accountId the account id
	 * @param lotId the lot id
	 * @param carId the car id
	 * @throws SQLException the SQL exception
	 */
	public void addFullMonthlySubscriptionToAccount(int accountId, int lotId, String carId) throws SQLException {
		//TODO: implement
	}

/**
 * ************************************* End Of TODO Section *************************************.
 *
 * @param carId the car id
 * @param accountId the account id
 * @param lotId the lot id
 * @param predictionArrive the prediction arrive
 * @param predictionLeave the prediction leave
 * @param timeArrive the time arrive
 * @param timeLeave the time leave
 * @param orderType the order type
 * @return the int
 * @throws SQLException the SQL exception
 */


	/**
	 * Insert parking reservation.
	 *
	 * @param carId the car id
	 * @param accountId the account id
	 * @param lotId the lot id
	 * @param predictionArrive the prediction arrive
	 * @param predictionLeave the prediction leave
	 * @param timeArrive the time arrive
	 * @param timeLeave the time leave
	 * @param orderType the order type
	 * @return the int
	 * @throws SQLException the SQL exception
	 */
	public int insertParkingReservation(String carId, int accountId,/* int entranceId,*/ int lotId, Date predictionArrive,
			Date predictionLeave, Date timeArrive, Date timeLeave,
			/*DBConnection.orderType*/DBConstants.OrderType orderType/*order, occasional entrance, etc*/) throws SQLException {
		Queue<Object> paramsValues = new LinkedList<Object>(); // push all params to paramsValues. in order of the SQL query
		paramsValues.add(carId);
		paramsValues.add(accountId);
		//		paramsValues.add(entranceId);
		paramsValues.add(lotId);
		paramsValues.add(orderType.getValue());
		paramsValues.add(ServerUtils.getTimeStampOfDate(predictionArrive));
		paramsValues.add(ServerUtils.getTimeStampOfDate(predictionLeave));
		paramsValues.add(ServerUtils.getTimeStampOfDate(timeArrive));
		paramsValues.add(ServerUtils.getTimeStampOfDate(timeLeave));
		int entranceId = DBConnection.updateSql(regularQueriesInst.insert_car_planed_being_in_parking_to_log, paramsValues);
		paramsValues.add(entranceId);
		paramsValues.add(carId);
		paramsValues.add(accountId);
		paramsValues.add(lotId);
		paramsValues.add(orderType.getValue());
		paramsValues.add(ServerUtils.getTimeStampOfDate(predictionArrive));
		paramsValues.add(ServerUtils.getTimeStampOfDate(predictionLeave));
		paramsValues.add(ServerUtils.getTimeStampOfDate(timeArrive));
		paramsValues.add(ServerUtils.getTimeStampOfDate(timeLeave));
		DBConnection.updateSql(regularQueriesInst.insert_car_planed_being_in_parking, paramsValues);
		return entranceId;
	}


	/**
	 * Update arrive time.
	 *
	 * @param carId the car id
	 * @param arriveTime the arrive time
	 * @throws SQLException the SQL exception
	 */
	public void updateArriveTime(String carId, Date arriveTime) throws SQLException {
		Queue<Object> paramsValues = new LinkedList<Object>(); // push all params to paramsValues. in order of the SQL query

		paramsValues.add(ServerUtils.getTimeStampOfDate(arriveTime));
		paramsValues.add(carId);
		DBConnection.updateSql(regularQueriesInst.update_arrive_time_current_cars_in_parking_by_car_id, paramsValues);
		
		int entranceId = getEntranceIdFromCurrentCarsInParkingByCarId(carId);
		paramsValues.add(ServerUtils.getTimeStampOfDate(arriveTime));
		paramsValues.add(entranceId);
		DBConnection.updateSql(regularQueriesInst.update_arrive_time_logs_by_entrance_id, paramsValues);
		
}

	/**
	 * Gets the entrance id from current cars in parking by car id.
	 *
	 * @param carId the car id
	 * @return the entrance id from current cars in parking by car id
	 * @throws SQLException the SQL exception
	 */
	private int getEntranceIdFromCurrentCarsInParkingByCarId (String carId) throws SQLException {
		
		Queue<Object> paramsValues = new LinkedList<Object>(); // push all params to paramsValues. in order of the SQL query
		ArrayList<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
		paramsValues.add(carId);
		DBConnection.selectSql(regularQueriesInst.select_all_details_by_car_id_of_car_in_parking, paramsValues, resultList);
		int entranceId = -1;
		Iterator<Map<String, Object>> iteratorTwo = resultList.iterator();
		while (iteratorTwo.hasNext()) {
			Map<String, Object> row = (Map<String, Object>) iteratorTwo.next();
			entranceId = (int) row.get(DbSqlColumns.ENTRANCE_ID.getName());
		}
		return entranceId;
	}

	/**
	 * Select order status.
	 *
	 *select all details of the reservation (includes: one time order, regular order and any other kind that is inside the parking right now).
	 *
	 * @param entranceId the entrance id
	 * @param resultList the result list
	 * @throws SQLException the SQL exception
	 */
	public void selectOrderStatus(int entranceId, ArrayList<Map<String, Object>> resultList)
			throws SQLException {
		Queue<Object> paramsValues = new LinkedList<Object>(); // push all params to q. in order of SQL
		paramsValues.add(entranceId);
		DBConnection.selectSql(regularQueriesInst.track_order, paramsValues, resultList);
	}

	/**
	 * Select account details.
	 *
	 * @param accountId the account id
	 * @param resultList the result list
	 * @throws SQLException the SQL exception
	 */
	public void selectAccountDetails(int accountId, ArrayList<Map<String, Object>> resultList)
			throws SQLException {
		Queue<Object> q = new LinkedList<Object>(); // push all params to q. in order of SQL
		q.add(accountId);
		DBConnection.selectSql(regularQueriesInst.select_account_details, q, resultList);
	}


	/**
	 * Insert new account.
	 *
	 * create a new account in the db
	 *
	 * @param accountId the account id
	 * @param email the email
	 * @param carId the car id
	 * @param hasSubscription the has subscription
	 * @throws SQLException the SQL exception
	 */
	public void insertNewAccount(int accountId, String email, String carId, DBConstants.TrueFalse hasSubscription)
			throws SQLException {
		double balance = 0;
		Queue<Object> q = new LinkedList<Object>(); // push all params to q. in order of SQL
		q.add(accountId);
		q.add(email);
		q.add(carId);
		q.add(balance);
		q.add((hasSubscription.getValue()));
		DBConnection.updateSql(regularQueriesInst.insert_new_account, q);
	}


	/**
	 * Select customer account details.
	 *
	 * selects all customer account details.
	 *
	 * @param customerId the customer id
	 * @param resultList the result list
	 * @throws SQLException the SQL exception
	 */
	public void selectCustomerAccountDetails(int customerId, ArrayList<Map<String, Object>> resultList) throws SQLException {
		Queue<Object> params = new LinkedList<Object>(); // push all params to paramsValues. in order of SQL
		params.add(customerId);
		DBConnection.selectSql(regularQueriesInst.select_account_details, params, resultList);
	}


	/**
	 * Car left parking.
	 * 
	 * updates in logs table the leaving time.
	 * deletes from the table of current cars in parking.
	 *
	 * @param entranceId the entrance id
	 * @param timeLeft the time left
	 * @throws SQLException the SQL exception
	 */
	public void carLeftParkingByEntranceId(int entranceId, Date timeLeft) throws SQLException {
		
		Queue<Object> paramsValues = new LinkedList<Object>(); // push all params to paramsValues. in order of SQL
		paramsValues.add(ServerUtils.getTimeStampOfDate(timeLeft));
		paramsValues.add(entranceId);
		DBConnection.updateSql(regularQueriesInst.car_left_parking_update_time_left, paramsValues);
		paramsValues.add(entranceId);
		DBConnection.updateSql(regularQueriesInst.delete_entrance_from_car_planed_being_in_parking_by_entrance_id, paramsValues);
	}
	
	/**
	 * Car left parking by car id.
	 * deletes from table of current cars. updates leave time in logs.
	 * @param carId the car id
	 * @param lotId the lot id
	 * @param timeLeft the time left
	 * @throws SQLException the SQL exception
	 */
	public void carLeftParkingByCarId(String carId, int lotId, Date timeLeft) throws SQLException {
		int entranceId = getEntranceIdFromCurrentCarsInParkingByCarId(carId);
		carLeftParkingByEntranceId(entranceId, timeLeft);
	}


	/**
	 * Update customer balance.
	 *
	 * @param customerId the customer id
	 * @param valueInCashToAdd the value in cash to add
	 * @throws SQLException the SQL exception
	 */
	public void updateCustomerBalance(int customerId, double valueInCashToAdd) throws SQLException {
		Queue<Object> paramsValues = new LinkedList<Object>(); // push all params to paramsValues. in order of SQL
		paramsValues.add(valueInCashToAdd);
		paramsValues.add(customerId);
		DBConnection.updateSql(regularQueriesInst.update_customer_balance, paramsValues);
	}

	/**
	 * Cancel order.
	 * 
	 * updates in logs table the leaving time.
	 * deletes from the table of current cars in parking.
	 * TODO: for now it also updates in the daily stats the canceled orders for today. maybe delete it, and do it once in a week.
	 * 
	 * @param entrance_id the entrance id
	 * @param valueInCashToAddReduce the value in cash to add reduce
	 * @throws SQLException the SQL exception
	 */
	public void cancelOrder(int entrance_id, double valueInCashToAddReduce) throws SQLException {
		Queue<Object> paramsValues = new LinkedList<Object>(); // push all params to paramsValues. in order of SQL
		paramsValues.add(entrance_id);
		ArrayList<Map<String, Object>> rs = new ArrayList<Map<String, Object>>();
		DBConnection.selectSql(regularQueriesInst.select_customer_id_by_entrance_id, paramsValues, rs);
		int customerId = (int) rs.get(0).get("account_id");
		paramsValues.add(entrance_id);
		DBConnection.updateSql(regularQueriesInst.delete_entrance_from_car_planed_being_in_parking_by_entrance_id, paramsValues);

		Calendar todayCalendar = new GregorianCalendar();
		java.sql.Date today = new java.sql.Date(todayCalendar.getTimeInMillis());
		paramsValues.add(today);

		updateCustomerBalance(customerId, valueInCashToAddReduce);
	}
	
	/**
	 * Insert new complaint.
	 *
	 * @param customerId
	 *            the customer id
	 * @param complaintDescription
	 *            the complaint description
	 * @param complaintTime
	 *            the complaint time
	 * @throws SQLException
	 *             the SQL exception
	 */
	public int insertComplaint(int customerId, String complaintDescription, Date complaintTime) throws SQLException {
		Queue<Object> paramsValues = new LinkedList<Object>();

		paramsValues.add(customerId);
		paramsValues.add(complaintDescription);
		paramsValues.add(TrueFalse.FALSE.getValue());
		paramsValues.add(complaintTime);

		int complaintId = DBConnection.updateSql(regularQueriesInst.insert_complaint, paramsValues);
		return complaintId;
	}

	public void selectAllOpenedComplaints(ArrayList<Map<String, Object>> resultList) throws SQLException {
		Queue<Object> paramsValues = new LinkedList<Object>();
		DBConnection.selectSql(regularQueriesInst.get_all_opened_complains, paramsValues, resultList);
	}

	public void updateComplaint(Complaint complaint, Boolean isComplaintApproved, String customerServiceResponse)
			throws SQLException {
		Queue<Object> paramsValues = new LinkedList<Object>();
		paramsValues.add(isComplaintApproved ? TrueFalse.TRUE.getValue() : TrueFalse.FALSE.getValue());
		paramsValues.add(customerServiceResponse);
		paramsValues.add(complaint.getComplaintId());
		DBConnection.updateSql(regularQueriesInst.update_complaint, paramsValues);
	}

	public void selectComplaintDetails(int complainId, ArrayList<Map<String, Object>> resultList) throws SQLException {
		Queue<Object> params = new LinkedList<Object>();
		params.add(complainId);
		DBConnection.selectSql(regularQueriesInst.select_complain_details, params, resultList);
	}
	
	public void selectAllParkingLotsId(ArrayList<Integer> list) throws SQLException {
		ArrayList<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>(); 
		DBConnection.selectSql(regularQueriesInst.select_all_lot_id_from_rates, null, resultList);
		
		Iterator<Map<String, Object>> iterator = resultList.iterator();
		while (iterator.hasNext()) {
			Map<String, Object> row = (Map<String, Object>) iterator.next();
			list.add((int)row.get(DbSqlColumns.LOT_ID.getName()));
		}
		
		
	}
	

	/**
	 * Update parking reservaion.
	 *
	 * @param carID the car ID
	 * @param customerID the customer ID
	 * @param entranceId the entrance id
	 * @param parkingLotID the parking lot ID
	 * @param arrivalTime the arrival time
	 * @param estimatedDepartureTime the estimated departure time
	 * @param date the date
	 * @param date2 the date 2
	 * @param value the value
	 */
	public void updateParkingReservaion(String carID, int customerID, int entranceId, int parkingLotID,
			Date arrivalTime, Date estimatedDepartureTime, Date date, Date date2, String value) {
		// TODO Auto-generated method stub

	}


/**
 * ***************************************************************************************************************************************************************************.
 *
 * @param lotId the lot id
 * @param parkingMapArr the parking map arr
 * @throws SQLException the SQL exception
 */
/*BEGIN TODO:  verify that we don't need those 3 functions: {selectParkingMapByLotId, insertParkingMapOfLotId, deleteParkingMap}.
 * it seemes that the table PARKING_MAP is not needed any more*/
//	TODO: still need to check if it is possible to create the array inside the function. for now, the user should create an array with the appropriate size.
	/**
	 * Select parking map by lot id.
	 *
	 * @param lotId the lot id
	 * @param parkingMapArr the parking map array - called with empty array. returned with a full one.
	 * @throws SQLException the SQL exception
	 */
	public void selectParkingMapByLotId(int lotId, String [] parkingMapArr) throws SQLException {

		Queue<Object> paramsValues = new LinkedList<Object>(); // push all params to paramsValues. in order of SQL
		paramsValues.add(lotId);
		ArrayList<Map<String, Object>> rs = new ArrayList<Map<String,Object>>();
		DBConnection.selectSql(parkingMapQueriesInst.select_parking_map_by_lot_id, paramsValues, rs);
		Iterator<Map<String, Object>> iterator = rs.iterator();
		while (iterator.hasNext()) {
			Map<String, Object> row = (Map<String, Object>) iterator.next();
			for (int i = 0; i < parkingMapArr.length; i++) {
				String curIdx = "c"+(i+1);
				parkingMapArr[i] = (String)row.get(curIdx);
			}
		}
	}

	/**
	 * Insert parking map of lot id.
	 *
	 * @param lotId the lot id
	 * @param numOfColumns the num of columns. needed for the first time initialization. can be found in the second column of parking_map table
	 * @param parkingMapArr the parking map arr
	 * @throws SQLException the SQL exception
	 */
	public void insertParkingMapOfLotId(int lotId, int numOfColumns, String [] parkingMapArr) throws SQLException {
		Queue<Object> paramsValues = new LinkedList<Object>(); // push all params to paramsValues. in order of SQL
		paramsValues.add(lotId);
		paramsValues.add(numOfColumns);
		for (int i = 0; i < parkingMapArr.length; i++) {
			paramsValues.add(parkingMapArr[i]);
		}
		switch (numOfColumns) {
		case 4:
			DBConnection.updateSql(parkingMapQueriesInst.insert_parking_map_lot_has_4_columns, paramsValues);
			break;
		case 5:
			DBConnection.updateSql(parkingMapQueriesInst.insert_parking_map_lot_has_5_columns, paramsValues);
			break;
		case 6:
			DBConnection.updateSql(parkingMapQueriesInst.insert_parking_map_lot_has_6_columns, paramsValues);
			break;
		case 7:
			DBConnection.updateSql(parkingMapQueriesInst.insert_parking_map_lot_has_7_columns, paramsValues);
			break;
		case 8:
			DBConnection.updateSql(parkingMapQueriesInst.insert_parking_map_lot_has_8_columns, paramsValues);
			break;
		default:
//			throw Excep; //not supported size.
		}
	}

	/**
	 * Delete parking map.
	 *
	 * @param lotId the lot id
	 * @throws SQLException the SQL exception
	 */
	public void deleteParkingMap(int lotId) throws SQLException {
		Queue<Object> paramsValues = new LinkedList<Object>(); // push all params to paramsValues. in order of SQL
		paramsValues.add(lotId);
		DBConnection.updateSql(parkingMapQueriesInst.delete_parking_map_of_lot_id, paramsValues);
	}
/*END ****************************************************************************************************************************************************/

}
