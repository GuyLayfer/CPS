package db;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

import db.DBConnection.sqlTypeKind;


public class DBAPI {

	public int [][] lots_dimensions;

	enum orderType {ONE_TIME(0), ORDER(1), SUBSCRIPTION(2), SUBSCRIPTION_FULL(3);
		
		int id;
		orderType(int p) {
		      id = p;
		   }
		   int showId() {
		      return id;
		   } 
	
	};

	public static boolean carEnteredParkingUpdate(int carId, int entranceId, Date predictionArrive,
												Date prediction_leave, /*DBConnection.orderType*/int kind/*hazmana, mizamen etc*/) throws SQLException {
		
		Queue<Object> q = new LinkedList<Object>(); // push all params to q. in order of SQL
		Queue<DBConnection.sqlTypeKind> paramTypes = new LinkedList<DBConnection.sqlTypeKind>(); // push all params to q. in order of SQL
		q.add(carId);
		paramTypes.add(DBConnection.sqlTypeKind.INT);
		q.add(entranceId);
		paramTypes.add(DBConnection.sqlTypeKind.INT);
		q.add(predictionArrive);
		paramTypes.add(DBConnection.sqlTypeKind.TIMESTAMP);
		q.add(prediction_leave);
		paramTypes.add(DBConnection.sqlTypeKind.TIMESTAMP);
		q.add(kind);
		paramTypes.add(DBConnection.sqlTypeKind.INT);
		try {
			DBConnection.updateSql(DBQueries.insert_car_entered, q, paramTypes);
			return true;
		}
		catch (Exception e) {
			System.out.println(e.getStackTrace());
			return false;
		}
	}
	
	
	public static boolean newOrder(int entranceId, int carId, Date predictionArrive, Date predictionLeave, ArrayList<Map<String, Object>> resultList)
			throws SQLException {
		return carEnteredParkingUpdate(carId, entranceId, predictionArrive, predictionLeave, orderType.ORDER.showId());
	}

	public static boolean trackOrderStatus(int entranceId, ArrayList<Map<String, Object>> resultList) {
		Queue<Object> q = new LinkedList<Object>(); // push all params to q. in order of SQL
		Queue<DBConnection.sqlTypeKind> paramTypes = new LinkedList<DBConnection.sqlTypeKind>(); // push all params to q. in order of SQL
		q.add(entranceId);
		paramTypes.add(DBConnection.sqlTypeKind.INT);
		try {
			DBConnection.selectSql(DBQueries.track_order, q, paramTypes, resultList);
			return true;
		}
		catch (Exception e) {
			System.out.println(e.getStackTrace());
			return false;
		}
	}



	public static void main(String[] args) {
		// TODO Auto-generated method stub

		
		Date dt_arrive =  new Date(System.currentTimeMillis());
		Calendar calendarLeft = new GregorianCalendar();
//		set(int year, int month, int date, int hourOfDay, int minute)
		calendarLeft.set(2017, 11, 31, 23, 59, 01);
		
		
		Date dt_leave = calendarLeft.getTime();
		System.out.println(dt_leave);
		
		
//		public static void updateSql(String stmtString, Queue<Object> params, Queue<sqlTypeKind> types) {
//		updateSql("current_cars_in_parking", create_table_current_cars_in_parking, null, null);
		
		
		//EXAMPLE of usage
		try {
			System.out.println("!!!!!" + orderType.ORDER.showId());
			carEnteredParkingUpdate(51, 2, dt_arrive, dt_leave, orderType.ORDER.showId()/*hazmana*/);	
		}
		catch (SQLException e) {
			System.out.println("ERRORR");
			e.printStackTrace();
		}
		
		
		ArrayList<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
//		selectSql("current_cars_in_parking", select_all, null, null, resultList);

		trackOrderStatus(2, resultList);
		
		int countRows = 0;
		
		for (Iterator iterator = resultList.iterator(); iterator.hasNext();) {
			System.out.println("next row " + countRows);
			Map<String, Object> row = (Map<String, Object>) iterator.next();
			countRows++;
			for (Map.Entry<String, Object> column : row.entrySet()) {
				System.out.println(column.getKey() + "/" + column.getValue());
			}
			
		}
		
		
//		for (Iterator iterator = resultList.iterator(); iterator.hasNext();) {
//			System.out.println("next row " + countRows);
//			Map<String, Object> row = (Map<String, Object>) iterator.next();
//			countRows++;
//			for (Map.Entry<String, Object> column : row.entrySet()) {
//				System.out.println(column.getKey() + "/" + column.getValue());
//			}
//			
//		}
		
	}

}
