package server.db.dbAPI; 

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import server.db.DBConnection;
import server.db.queries.RegularQueries;
import server.db.queries.ReportsQueries;

// TODO: Auto-generated Javadoc
/**
 * The Class ReportsDBAPI.
 */
public class ReportsDBAPI extends DBAPI{
	
	/**
	 * Select last day complaints.
	 *
	 * @param resultList the result list - contains all the records of complaints of yesterday. each record is an entry in the ArrayList. 
	 * each pair inside Map: ((Sring) columnName, (Object) value) is a column and it's value.
	 * @throws SQLException the SQL exception
	 */
	public static void selectLastDayComplaints(ArrayList<Map<String, Object>> resultList) throws SQLException{

		Calendar calendar = new GregorianCalendar();
		java.sql.Date today = new java.sql.Date(calendar.getTimeInMillis());
		calendar.add(Calendar.DATE, -1); //get a day back
		java.sql.Date yesterday = new java.sql.Date(calendar.getTimeInMillis());
		
		selectBetween2DatesQuery(RegularQueries.select_complaints_last_day, yesterday, today, resultList);
	}
	
	
	/**
	 * Gets the number of reservations of last week grouped by order.
	 *
	 * @param resultList the result list
	 * @return the number of reservations of last week grouped by order
	 * @throws SQLException the SQL exception
	 */
	public static void getNumberOfReservationsOfLastWeekGroupedByOrder(ArrayList<Map<String, Object>> resultList) throws SQLException{
		
		Calendar calendar = new GregorianCalendar();
		java.sql.Date today = new java.sql.Date(calendar.getTimeInMillis());
		calendar.add(Calendar.DATE, -7); //get a week back
		java.sql.Date lastWeekDate = new java.sql.Date(calendar.getTimeInMillis());
		
		selectBetween2DatesQuery(ReportsQueries.select_counts_all_reservations_between_2_dates_grouped_by_orderType, lastWeekDate, today, resultList);
	}
	
	
	
	/**
	 * Gets the number of reservations of last day.
	 *
	 * @param resultList the result list
	 * @return the number of reservations of last day
	 * @throws SQLException the SQL exception
	 */
	//TODO: test this query
	public static void getNumberOfReservationsOfLastDay(ArrayList<Map<String, Object>> resultList) throws SQLException{
		
		Calendar todayCalendar = new GregorianCalendar();
		java.sql.Date today = new java.sql.Date(todayCalendar.getTimeInMillis());
		todayCalendar.add(Calendar.DATE, -1);
		java.sql.Date yesterday = new java.sql.Date(todayCalendar.getTimeInMillis());
		
		selectBetween2DatesQuery(ReportsQueries.select_count_reservations_between_2_dates_grouped_by_kind, yesterday, today, resultList);
	}
	
	/**
	 * Gets the number of lating reservations of last day.
	 *
	 * @param resultList the result list
	 * @return the number of lating reservations of last day
	 * @throws SQLException the SQL exception
	 */
	public static void getNumberOfLatingReservationsOfLastDay(ArrayList<Map<String, Object>> resultList) throws SQLException{
		Queue<Object> params = new LinkedList<Object>(); // push all params to paramsValues. in order of SQL
		Queue<DBConnection.sqlTypeKind> paramTypes = new LinkedList<DBConnection.sqlTypeKind>(); // push all params to paramsValues. in order of SQL
		Calendar calendar = new GregorianCalendar();
		java.sql.Date today = new java.sql.Date(calendar.getTimeInMillis());
		calendar.add(Calendar.DATE, -1); //get a day back
		java.sql.Date yesterday = new java.sql.Date(calendar.getTimeInMillis());
		selectBetween2DatesQuery(ReportsQueries.select_lating_reservations_between_2_dates, yesterday, today, resultList);//TODO: verify order yesterday today
		DBConnection.selectSql(ReportsQueries.select_lating_reservations_between_2_dates, params, paramTypes, resultList);
	}
	
	/**
	 * Gets the daily stats of today.
	 *
	 * @param resultList the result list
	 * @return the daily stats of today
	 * @throws SQLException the SQL exception
	 */
	public static void getDailyStatsOfToday(ArrayList<Map<String, Object>> resultList) throws SQLException{
		Queue<Object> params = new LinkedList<Object>(); // push all params to paramsValues. in order of SQL
		Queue<DBConnection.sqlTypeKind> paramTypes = new LinkedList<DBConnection.sqlTypeKind>(); // push all params to paramsValues. in order of SQL
		
		Calendar todayCalendar = new GregorianCalendar();
		java.sql.Date today = new java.sql.Date(todayCalendar.getTimeInMillis());
		
		params.add(today);
		paramTypes.add(DBConnection.sqlTypeKind.DATE);
		
		DBConnection.selectSql(ReportsQueries.get_daily_stats_by_day_id, params, paramTypes, resultList);
		
	}	

	
	
	
}
