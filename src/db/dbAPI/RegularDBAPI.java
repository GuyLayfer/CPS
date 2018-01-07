package db.dbAPI;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import db.DBConnection;
import db.DBConnection.sqlTypeKind;
import db.DBConstants;
import db.DBConstants.parkingMap;
import db.queries.ParkingMapQueries;
import db.queries.RegularQueries;
import db.queries.ReportsQueries;

public class RegularDBAPI extends DBAPI{


	public static int insertParkingReservaion(String carId, int accountId,/* int entranceId,*/ int lotId, Date predictionArrive,
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


	public static void getSubscriptionIdByCarId(int subscription_id, ArrayList<Map<String, Object>> resultList) throws SQLException {
		/*return if customer has subscription*/	
		Queue<Object> paramsValues = new LinkedList<Object>(); // push all params to q. in order of SQL
		Queue<DBConnection.sqlTypeKind> paramTypes = new LinkedList<DBConnection.sqlTypeKind>(); // push all params to q. in order of SQL
		paramsValues.add(subscription_id);
		paramTypes.add(DBConnection.sqlTypeKind.INT);
		DBConnection.selectSql(RegularQueries.select_subscriptioin_id_by_car_id, paramsValues, paramTypes, resultList);
	}


	public static int insertNewOrder(/*int entranceId,*/ int accountId, String carId, int lotId, Date predictionArrive, Date predictionLeave, Date timeArrive, Date timeLeave)
			throws SQLException {
		return insertParkingReservaion(carId, accountId,/* entranceId,*/ lotId, predictionArrive, predictionLeave, timeArrive, timeLeave, DBConstants.orderType.ORDER);
	}

	public static void selectOrderStatus(int entranceId, ArrayList<Map<String, Object>> resultList)
			throws SQLException {
		Queue<Object> paramsValues = new LinkedList<Object>(); // push all params to q. in order of SQL
		Queue<DBConnection.sqlTypeKind> paramTypes = new LinkedList<DBConnection.sqlTypeKind>(); // push all params to q. in order of SQL
		paramsValues.add(entranceId);
		paramTypes.add(DBConnection.sqlTypeKind.INT);
		DBConnection.selectSql(RegularQueries.track_order, paramsValues, paramTypes, resultList);
	}

	public static void selectAccountDetails(int accountId, ArrayList<Map<String, Object>> resultList)
			throws SQLException {
		Queue<Object> q = new LinkedList<Object>(); // push all params to q. in order of SQL
		Queue<DBConnection.sqlTypeKind> paramTypes = new LinkedList<DBConnection.sqlTypeKind>(); // push all params to q. in order of SQL
		q.add(accountId);
		paramTypes.add(DBConnection.sqlTypeKind.INT);
		DBConnection.selectSql(RegularQueries.select_account_details, q, paramTypes, resultList);
	}


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


	public static void selectCustomerAccountDetails(int customerId, ArrayList<Map<String, Object>> resultList) throws SQLException {
		Queue<Object> params = new LinkedList<Object>(); // push all params to paramsValues. in order of SQL
		Queue<DBConnection.sqlTypeKind> paramTypes = new LinkedList<DBConnection.sqlTypeKind>(); // push all params to paramsValues. in order of SQL
		params.add(customerId);
		paramTypes.add(DBConnection.sqlTypeKind.INT);
		DBConnection.selectSql(RegularQueries.select_account_details, params, paramTypes, resultList);
	}


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

	public static void getComplainDetails(int complainId, ArrayList<Map<String, Object>> resultList) throws SQLException {
		Queue<Object> params = new LinkedList<Object>(); // push all params to paramsValues. in order of SQL
		Queue<DBConnection.sqlTypeKind> paramTypes = new LinkedList<DBConnection.sqlTypeKind>(); // push all params to paramsValues. in order of SQL
		params.add(complainId);
		paramTypes.add(DBConnection.sqlTypeKind.INT);
		DBConnection.selectSql(RegularQueries.select_complain_details, params, paramTypes, resultList);
	}


	public static void updateCustomerBalance(int customerId, double valueInCashToAdd) throws SQLException {
		Queue<Object> paramsValues = new LinkedList<Object>(); // push all params to paramsValues. in order of SQL
		Queue<DBConnection.sqlTypeKind> paramTypes = new LinkedList<DBConnection.sqlTypeKind>(); // push all params to paramsValues. in order of SQL
		paramsValues.add(valueInCashToAdd);
		paramTypes.add(DBConnection.sqlTypeKind.DOUBLE);
		paramsValues.add(customerId);
		paramTypes.add(DBConnection.sqlTypeKind.INT);
		DBConnection.updateSql(RegularQueries.update_customer_balance, paramsValues, paramTypes);
	}

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

	public static void selectParkingMapByLotId(int lotId, parkingMap [] parkingMapArr) throws SQLException {
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
				parkingMapArr[i] = parkingMap.convertStringToParkingMapEnum((String)row.get(curIdx));
			}
		}
	}

	public static void insertParkingMapOfLotId(int lotId, DBConstants.parkingMap [] parkingMapArr) throws SQLException {
		Queue<Object> paramsValues = new LinkedList<Object>(); // push all params to paramsValues. in order of SQL
		Queue<DBConnection.sqlTypeKind> paramTypes = new LinkedList<DBConnection.sqlTypeKind>(); // push all params to paramsValues. in order of SQL
		paramsValues.add(lotId);
		paramTypes.add(sqlTypeKind.INT);	
		for (int i = 0; i < parkingMapArr.length; i++) {
			paramsValues.add(parkingMapArr[i].getValue());
			paramTypes.add(sqlTypeKind.VARCHAR);			
		}
		DBConnection.updateSql(ParkingMapQueries.parkingMapInsertQueriesIdxByLotId[lotId-1], paramsValues, paramTypes);
	}

	public static void deleteParkingMap(int lotId) throws SQLException {
		Queue<Object> paramsValues = new LinkedList<Object>(); // push all params to paramsValues. in order of SQL
		Queue<DBConnection.sqlTypeKind> paramTypes = new LinkedList<DBConnection.sqlTypeKind>(); // push all params to paramsValues. in order of SQL
		paramsValues.add(lotId);
		paramTypes.add(sqlTypeKind.INT);
		DBConnection.updateSql(ParkingMapQueries.delete_parking_map_of_lot_id, paramsValues, paramTypes);

	}


}
