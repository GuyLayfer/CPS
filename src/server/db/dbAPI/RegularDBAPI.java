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

import core.ParkingState;
import core.ParkingStatus;
import server.db.DBConnection;
import server.db.DBConnection.sqlTypeKind;
import server.db.DBConstants;
import server.db.queries.ParkingMapQueries;
import server.db.queries.RegularQueries;
import server.db.queries.ReportsQueries;

// TODO: Auto-generated Javadoc
/**
 * The Class RegularDBAPI.
 */
public class RegularDBAPI extends DBAPI{


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
	public static int insertParkingReservation(String carId, int accountId,/* int entranceId,*/ int lotId, Date predictionArrive,
			Date predictionLeave, Date timeArrive, Date timeLeave,
			/*DBConnection.orderType*/DBConstants.orderType orderType/*order, occasional entrance, etc*/) throws SQLException {
		Queue<Object> paramsValues = new LinkedList<Object>(); // push all params to paramsValues. in order of the SQL query
		Queue<DBConnection.sqlTypeKind> paramTypes = new LinkedList<DBConnection.sqlTypeKind>(); // push all param types to paramTypes. in order of the SQL query
		paramsValues.add(carId);
		paramTypes.add(DBConnection.sqlTypeKind.VARCHAR);
		paramsValues.add(accountId);
		paramTypes.add(DBConnection.sqlTypeKind.INT);
		//		paramsValues.add(entranceId);
		//		paramTypes.add(DBConnection.sqlTypeKind.INT);
		paramsValues.add(lotId);
		paramTypes.add(DBConnection.sqlTypeKind.INT);
		paramsValues.add(orderType.getValue());
		paramTypes.add(DBConnection.sqlTypeKind.VARCHAR);
		paramsValues.add(predictionArrive);
		paramTypes.add(DBConnection.sqlTypeKind.TIMESTAMP);
		paramsValues.add(predictionLeave);
		paramTypes.add(DBConnection.sqlTypeKind.TIMESTAMP);
		paramsValues.add(timeArrive);
		paramTypes.add(DBConnection.sqlTypeKind.TIMESTAMP);
		paramsValues.add(timeLeave);
		paramTypes.add(DBConnection.sqlTypeKind.TIMESTAMP);
		int entranceId = DBConnection.updateSql(RegularQueries.insert_car_planed_being_in_parking_to_log, paramsValues, paramTypes);
		paramsValues.add(entranceId);
		paramTypes.add(DBConnection.sqlTypeKind.INT);
		paramsValues.add(carId);
		paramTypes.add(DBConnection.sqlTypeKind.VARCHAR);
		paramsValues.add(accountId);
		paramTypes.add(DBConnection.sqlTypeKind.INT);
		paramsValues.add(lotId);
		paramTypes.add(DBConnection.sqlTypeKind.INT);
		paramsValues.add(orderType.getValue());
		paramTypes.add(DBConnection.sqlTypeKind.VARCHAR);
		paramsValues.add(predictionArrive);
		paramTypes.add(DBConnection.sqlTypeKind.TIMESTAMP);
		paramsValues.add(predictionLeave);
		paramTypes.add(DBConnection.sqlTypeKind.TIMESTAMP);
		paramsValues.add(timeArrive);
		paramTypes.add(DBConnection.sqlTypeKind.TIMESTAMP);
		paramsValues.add(timeLeave);
		paramTypes.add(DBConnection.sqlTypeKind.TIMESTAMP);
		DBConnection.updateSql(RegularQueries.insert_car_planed_being_in_parking, paramsValues, paramTypes);
		return entranceId;
	}


	/**
	 * Gets the subscription id by car id.
	 *
	 * @param subscription_id the subscription id
	 * @param resultList the result list
	 * @return the subscription id by car id
	 * @throws SQLException the SQL exception
	 */
	public static void getSubscriptionIdByCarId(int subscription_id, ArrayList<Map<String, Object>> resultList) throws SQLException {
		/*return if customer has subscription*/	
		Queue<Object> paramsValues = new LinkedList<Object>(); // push all params to q. in order of SQL
		Queue<DBConnection.sqlTypeKind> paramTypes = new LinkedList<DBConnection.sqlTypeKind>(); // push all params to q. in order of SQL
		paramsValues.add(subscription_id);
		paramTypes.add(DBConnection.sqlTypeKind.INT);
		DBConnection.selectSql(RegularQueries.select_subscriptioin_id_by_car_id, paramsValues, paramTypes, resultList);
	}


	/**
	 * Insert new order.
	 *
	 * @param accountId the account id
	 * @param carId the car id
	 * @param lotId the lot id
	 * @param predictionArrive the prediction arrive
	 * @param predictionLeave the prediction leave
	 * @param timeArrive the time arrive
	 * @param timeLeave the time leave
	 * @return the int
	 * @throws SQLException the SQL exception
	 */
	public static int insertNewOrder(/*int entranceId,*/ int accountId, String carId, int lotId, Date predictionArrive, Date predictionLeave, Date timeArrive, Date timeLeave)
			throws SQLException {
		return insertParkingReservation(carId, accountId,/* entranceId,*/ lotId, predictionArrive, predictionLeave, timeArrive, timeLeave, DBConstants.orderType.ORDER);
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
	public static void selectOrderStatus(int entranceId, ArrayList<Map<String, Object>> resultList)
			throws SQLException {
		Queue<Object> paramsValues = new LinkedList<Object>(); // push all params to q. in order of SQL
		Queue<DBConnection.sqlTypeKind> paramTypes = new LinkedList<DBConnection.sqlTypeKind>(); // push all params to q. in order of SQL
		paramsValues.add(entranceId);
		paramTypes.add(DBConnection.sqlTypeKind.INT);
		DBConnection.selectSql(RegularQueries.track_order, paramsValues, paramTypes, resultList);
	}

	/**
	 * Select account details.
	 *
	 * @param accountId the account id
	 * @param resultList the result list
	 * @throws SQLException the SQL exception
	 */
	public static void selectAccountDetails(int accountId, ArrayList<Map<String, Object>> resultList)
			throws SQLException {
		Queue<Object> q = new LinkedList<Object>(); // push all params to q. in order of SQL
		Queue<DBConnection.sqlTypeKind> paramTypes = new LinkedList<DBConnection.sqlTypeKind>(); // push all params to q. in order of SQL
		q.add(accountId);
		paramTypes.add(DBConnection.sqlTypeKind.INT);
		DBConnection.selectSql(RegularQueries.select_account_details, q, paramTypes, resultList);
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
	public static void insertNewAccount(int accountId, String email, String carId, DBConstants.trueFalse hasSubscription)
			throws SQLException {
		double balance = 0;
		Queue<Object> q = new LinkedList<Object>(); // push all params to q. in order of SQL
		Queue<DBConnection.sqlTypeKind> paramTypes = new LinkedList<DBConnection.sqlTypeKind>(); // push all params to q. in order of SQL
		q.add(accountId);
		paramTypes.add(DBConnection.sqlTypeKind.INT);
		q.add(email);
		paramTypes.add(DBConnection.sqlTypeKind.VARCHAR);
		q.add(carId);
		paramTypes.add(DBConnection.sqlTypeKind.VARCHAR);
		q.add(balance);
		paramTypes.add(DBConnection.sqlTypeKind.DOUBLE);
		q.add((hasSubscription.getValue()));
		paramTypes.add(DBConnection.sqlTypeKind.VARCHAR);
		DBConnection.updateSql(RegularQueries.insert_new_account, q, paramTypes);
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
	public static void selectCustomerAccountDetails(int customerId, ArrayList<Map<String, Object>> resultList) throws SQLException {
		Queue<Object> params = new LinkedList<Object>(); // push all params to paramsValues. in order of SQL
		Queue<DBConnection.sqlTypeKind> paramTypes = new LinkedList<DBConnection.sqlTypeKind>(); // push all params to paramsValues. in order of SQL
		params.add(customerId);
		paramTypes.add(DBConnection.sqlTypeKind.INT);
		DBConnection.selectSql(RegularQueries.select_account_details, params, paramTypes, resultList);
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
	public static void carLeftParking(int entranceId, Date timeLeft) throws SQLException {
		Queue<Object> paramsValues = new LinkedList<Object>(); // push all params to paramsValues. in order of SQL
		Queue<DBConnection.sqlTypeKind> paramTypes = new LinkedList<DBConnection.sqlTypeKind>(); // push all params to paramsValues. in order of SQL
		paramsValues.add(timeLeft);
		paramTypes.add(DBConnection.sqlTypeKind.TIMESTAMP);
		paramsValues.add(entranceId);
		paramTypes.add(DBConnection.sqlTypeKind.INT);
		DBConnection.updateSql(RegularQueries.car_left_parking_update_time_left, paramsValues, paramTypes);
		paramsValues.add(entranceId);
		paramTypes.add(DBConnection.sqlTypeKind.INT);		
		DBConnection.updateSql(RegularQueries.delete_entrance_from_car_planed_being_in_parking, paramsValues, paramTypes);
	}

	/**
	 * Selects the complaint details.
	 * selects all the complaingId details.
	 * @param complainId the complain id
	 * @param resultList the result list
	 * @return the complain details
	 * @throws SQLException the SQL exception
	 */
	public static void selectComplaintDetails(int complainId, ArrayList<Map<String, Object>> resultList) throws SQLException {
		Queue<Object> params = new LinkedList<Object>(); // push all params to paramsValues. in order of SQL
		Queue<DBConnection.sqlTypeKind> paramTypes = new LinkedList<DBConnection.sqlTypeKind>(); // push all params to paramsValues. in order of SQL
		params.add(complainId);
		paramTypes.add(DBConnection.sqlTypeKind.INT);
		DBConnection.selectSql(RegularQueries.select_complain_details, params, paramTypes, resultList);
	}


	/**
	 * Update customer balance.
	 *
	 * @param customerId the customer id
	 * @param valueInCashToAdd the value in cash to add
	 * @throws SQLException the SQL exception
	 */
	public static void updateCustomerBalance(int customerId, double valueInCashToAdd) throws SQLException {
		Queue<Object> paramsValues = new LinkedList<Object>(); // push all params to paramsValues. in order of SQL
		Queue<DBConnection.sqlTypeKind> paramTypes = new LinkedList<DBConnection.sqlTypeKind>(); // push all params to paramsValues. in order of SQL
		paramsValues.add(valueInCashToAdd);
		paramTypes.add(DBConnection.sqlTypeKind.DOUBLE);
		paramsValues.add(customerId);
		paramTypes.add(DBConnection.sqlTypeKind.INT);
		DBConnection.updateSql(RegularQueries.update_customer_balance, paramsValues, paramTypes);
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
	public static void cancelOrder(int entrance_id, double valueInCashToAddReduce) throws SQLException {
		Queue<Object> paramsValues = new LinkedList<Object>(); // push all params to paramsValues. in order of SQL
		Queue<DBConnection.sqlTypeKind> paramTypes = new LinkedList<DBConnection.sqlTypeKind>(); // push all params to paramsValues. in order of SQL
		paramsValues.add(entrance_id);
		paramTypes.add(sqlTypeKind.INT);
		ArrayList<Map<String, Object>> rs = new ArrayList<Map<String, Object>>();
		DBConnection.selectSql(RegularQueries.select_customer_id_by_entrance_id, paramsValues, paramTypes, rs);
		int customerId = (int) rs.get(0).get("account_id");
		paramsValues.add(entrance_id);
		paramTypes.add(sqlTypeKind.INT);	
		DBConnection.updateSql(RegularQueries.delete_entrance_from_car_planed_being_in_parking, paramsValues, paramTypes);

		Calendar todayCalendar = new GregorianCalendar();
		java.sql.Date today = new java.sql.Date(todayCalendar.getTimeInMillis());
		paramsValues.add(today);
		paramTypes.add(DBConnection.sqlTypeKind.DATE);

		DBConnection.updateSql(ReportsQueries.add_canceled_reservation_daily, paramsValues, paramTypes);
		updateCustomerBalance(customerId, valueInCashToAddReduce);
	}

	
//	TODO: still need to check if it is possible to create the array inside the function. for now, the user should create an array with the appropriate size.
	/**
	 * Select parking map by lot id.
	 *
	 * @param lotId the lot id
	 * @param parkingMapArr the parking map array - called with empty array. returned with a full one.
	 * @throws SQLException the SQL exception
	 */
	public static void selectParkingMapByLotId(int lotId, ParkingState [] parkingMapArr) throws SQLException {
		Queue<Object> paramsValues = new LinkedList<Object>(); // push all params to paramsValues. in order of SQL
		Queue<DBConnection.sqlTypeKind> paramTypes = new LinkedList<DBConnection.sqlTypeKind>(); // push all params to paramsValues. in order of SQL
		paramsValues.add(lotId);
		paramTypes.add(sqlTypeKind.INT);
		ArrayList<Map<String, Object>> rs = new ArrayList<Map<String,Object>>();
		DBConnection.selectSql(ParkingMapQueries.select_parking_map_by_lot_id, paramsValues, paramTypes, rs);

		for (Iterator iterator = rs.iterator(); iterator.hasNext();) {
			Map<String, Object> row = (Map<String, Object>) iterator.next();
			for (int i = 0; i < parkingMapArr.length; i++) {
				String curIdx = "c"+(i+1);
				// TODO: check if this parking is parked or reserved and replace the null with the actual carId in these cases.
				parkingMapArr[i] = new ParkingState(ParkingStatus.convertStringToParkingMapEnum((String)row.get(curIdx)), null);
			}
		}
	}

	/**
	 * Insert parking map of lot id.
	 *
	 * @param lotId the lot id
	 * @param parkingMapArr the parking map arr
	 * @throws SQLException the SQL exception
	 */
	public static void insertParkingMapOfLotId(int lotId, ParkingState [] parkingMapArr) throws SQLException {
		Queue<Object> paramsValues = new LinkedList<Object>(); // push all params to paramsValues. in order of SQL
		Queue<DBConnection.sqlTypeKind> paramTypes = new LinkedList<DBConnection.sqlTypeKind>(); // push all params to paramsValues. in order of SQL
		paramsValues.add(lotId);
		paramTypes.add(sqlTypeKind.INT);	
		for (int i = 0; i < parkingMapArr.length; i++) {
			// TODO: insert the carId into the DB
			paramsValues.add(parkingMapArr[i].parkingState.getValue());
			paramTypes.add(sqlTypeKind.VARCHAR);			
		}
		DBConnection.updateSql(ParkingMapQueries.parkingMapInsertQueriesIdxByLotId[lotId-1], paramsValues, paramTypes);
	}

	/**
	 * Delete parking map.
	 *
	 * @param lotId the lot id
	 * @throws SQLException the SQL exception
	 */
	public static void deleteParkingMap(int lotId) throws SQLException {
		Queue<Object> paramsValues = new LinkedList<Object>(); // push all params to paramsValues. in order of SQL
		Queue<DBConnection.sqlTypeKind> paramTypes = new LinkedList<DBConnection.sqlTypeKind>(); // push all params to paramsValues. in order of SQL
		paramsValues.add(lotId);
		paramTypes.add(sqlTypeKind.INT);
		DBConnection.updateSql(ParkingMapQueries.delete_parking_map_of_lot_id, paramsValues, paramTypes);

	}


}
