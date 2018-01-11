package server.db.dbAPI;
//import java.sql.SQLException;
//import java.util.ArrayList;
//import java.util.Calendar;
//import java.util.Date;
//import java.util.GregorianCalendar;
//import java.util.Iterator;
//import java.util.LinkedList;
//import java.util.Map;
//import java.util.Queue;
//
//import db.DBConnection.sqlTypeKind;
//
////TODO: change orderType representation in DB from int to ENUM.
//public class DBAPI {
//
////	public int [][] lots_dimensions; // not needed for now.
//
//	public enum orderType {ONE_TIME(0), ORDER(1), SUBSCRIPTION(2), SUBSCRIPTION_FULL(3);
//		
//		int id;
//		orderType(int p) {
//		      id = p;
//		   }
//		   public int getId() {
//		      return id;
//		   } 
//	
//	};
//
//	// TODO: change the type of carId to String (the carId of embassy cars is often consisted of digits and letters)
//	public static void updateParkingReservaion(int carId, int accountId, int entranceId, int lotId, Date predictionArrive,
//												Date predictionLeave, Date timeArrive, Date timeLeave,/*DBConnection.orderType*/int kind/*order, occasional entrance, etc*/) throws SQLException {
//		Queue<Object> paramsValues = new LinkedList<Object>(); // push all params to paramsValues. in order of the SQL query
//		Queue<DBConnection.sqlTypeKind> paramTypes = new LinkedList<DBConnection.sqlTypeKind>(); // push all param types to paramTypes. in order of the SQL query
//		paramsValues.add(carId);
//		paramTypes.add(DBConnection.sqlTypeKind.INT);
//		paramsValues.add(accountId);
//		paramTypes.add(DBConnection.sqlTypeKind.INT);
//		paramsValues.add(entranceId);
//		paramTypes.add(DBConnection.sqlTypeKind.INT);
//		paramsValues.add(lotId);
//		paramTypes.add(DBConnection.sqlTypeKind.INT);
//		paramsValues.add(predictionArrive);
//		paramTypes.add(DBConnection.sqlTypeKind.TIMESTAMP);
//		paramsValues.add(predictionLeave);
//		paramTypes.add(DBConnection.sqlTypeKind.TIMESTAMP);
//		paramsValues.add(timeArrive);
//		paramTypes.add(DBConnection.sqlTypeKind.TIMESTAMP);
//		paramsValues.add(timeLeave);
//		paramTypes.add(DBConnection.sqlTypeKind.TIMESTAMP);
//		paramsValues.add(kind);
//		paramTypes.add(DBConnection.sqlTypeKind.INT);
//		DBConnection.updateSql(DBQueries.insert_car_planed_being_in_parking, paramsValues, paramTypes);
//	}
//	
//	
//	public static void newOrder(int entranceId, int accountId, int carId, int lotId, Date predictionArrive, Date predictionLeave, Date timeArrive, Date timeLeave)
//			throws SQLException {
//		updateParkingReservaion(carId, accountId, entranceId, lotId, predictionArrive, predictionLeave, timeArrive, timeLeave, orderType.ORDER.getId());
//	}
//
//	public static void trackOrderStatus(int entranceId, ArrayList<Map<String, Object>> resultList)
//		throws SQLException {
//		Queue<Object> paramsValues = new LinkedList<Object>(); // push all params to q. in order of SQL
//		Queue<DBConnection.sqlTypeKind> paramTypes = new LinkedList<DBConnection.sqlTypeKind>(); // push all params to q. in order of SQL
//		paramsValues.add(entranceId);
//		paramTypes.add(DBConnection.sqlTypeKind.INT);
//		DBConnection.selectSql(DBQueries.track_order, paramsValues, paramTypes, resultList);
//	}
//	
//	public static void getAccountDetails(int accountId, ArrayList<Map<String, Object>> resultList)
//			throws SQLException {
//			Queue<Object> q = new LinkedList<Object>(); // push all params to q. in order of SQL
//			Queue<DBConnection.sqlTypeKind> paramTypes = new LinkedList<DBConnection.sqlTypeKind>(); // push all params to q. in order of SQL
//			q.add(accountId);
//			paramTypes.add(DBConnection.sqlTypeKind.INT);
//			DBConnection.selectSql(DBQueries.get_account_details, q, paramTypes, resultList);
//		}
//	
//	
//	public static void createNewAccount(int accountId, String email, int carId, boolean hasSubscription)
//			throws SQLException {
//		double balance = 0;
//		Queue<Object> q = new LinkedList<Object>(); // push all params to q. in order of SQL
//		Queue<DBConnection.sqlTypeKind> paramTypes = new LinkedList<DBConnection.sqlTypeKind>(); // push all params to q. in order of SQL
//		q.add(accountId);
//		paramTypes.add(DBConnection.sqlTypeKind.INT);
//		q.add(email);
//		paramTypes.add(DBConnection.sqlTypeKind.VARCHAR);
//		q.add(carId);
//		paramTypes.add(DBConnection.sqlTypeKind.INT);
//		q.add(balance);
//		paramTypes.add(DBConnection.sqlTypeKind.DOUBLE);
//		q.add((hasSubscription ? 1 : 0));
//		paramTypes.add(DBConnection.sqlTypeKind.INT);
//		DBConnection.updateSql(DBQueries.create_new_account, q, paramTypes);
//	}
//
//
//
//	public static void main(String[] args) {
//
//		
//		Date dateArrive =  new Date(System.currentTimeMillis());
//		Calendar calendarLeft = new GregorianCalendar();
////		set(int year, int month, int date, int hourOfDay, int minute)
//		calendarLeft.set(2017, 11, 31, 23, 59, 01);
//		
//		
//		Date dateLeave = calendarLeft.getTime();
//		System.out.println(dateLeave);
//		
//		
////		public static void updateSql(String stmtString, Queue<Object> params, Queue<sqlTypeKind> types) {
////		updateSql("current_cars_in_parking", create_table_current_cars_in_parking, null, null);
//		
//		
//		//EXAMPLE of usage
//		try {
//			System.out.println("!!!!!" + orderType.ORDER.getId());
//			Date timeArrive = new Date(0);
//			Date timeLeave = new Date(0);
//			updateParkingReservaion(51/*carId*/,4/*accountId*/, 2/*entranceId*/, 1/*lotId*/, dateArrive, dateLeave, timeArrive, timeLeave, orderType.ORDER.getId()/*order*/);	
//		}
//		catch (SQLException e) {
//			System.out.println("ERRORR");
//			e.printStackTrace();
//		}
//		
//		
//		ArrayList<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
////		selectSql("current_cars_in_parking", select_all, null, null, resultList);
//
//		try {
//			trackOrderStatus(2, resultList);
//			createNewAccount(123, "asdas@jsdflkj.com", 123456, false);
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
//		int countRows = 0;
//		
//		for (Iterator iterator = resultList.iterator(); iterator.hasNext();) {
//			System.out.println("next row " + countRows);
//			Map<String, Object> row = (Map<String, Object>) iterator.next();
//			countRows++;
//			for (Map.Entry<String, Object> column : row.entrySet()) {
//				System.out.println(column.getKey() + "/" + column.getValue());
//			}
//			
//		}
//		
//		
////		for (Iterator iterator = resultList.iterator(); iterator.hasNext();) {
////			System.out.println("next row " + countRows);
////			Map<String, Object> row = (Map<String, Object>) iterator.next();
////			countRows++;
////			for (Map.Entry<String, Object> column : row.entrySet()) {
////				System.out.println(column.getKey() + "/" + column.getValue());
////			}
////			
////		}
//		
//	}
//
//}
