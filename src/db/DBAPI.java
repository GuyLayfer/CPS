package db;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;


public class DBAPI {

	public int [][] lots_dimensions;


	public static boolean carEnteredParkingUpdate(int carId, int entranceId, Date predictionArrive,
												Date prediction_leave, DBConnection.orderType kind/*hazmana, mizamen etc*/) {
		
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
	
	
	public static boolean newOrder(int entranceId, int carId, Date predictionArrive, Date predictionLeave, ArrayList<Map<String, Object>> resultList) {
		return carEnteredParkingUpdate(carId, entranceId, predictionArrive, predictionLeave, DBConnection.orderType.ORDER);
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
		
		
		
		
		carEnteredParkingUpdate(5, 2, dt_arrive, dt_leave, DBConnection.orderType.ORDER/*hazmana*/);
		
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
