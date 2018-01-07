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

public class ReportsDBAPI extends DBAPI{
	
	public static void selectLastDayComplains(ArrayList<Map<String, Object>> resultList) throws SQLException{

		Calendar calendar = new GregorianCalendar();
		java.sql.Date today = new java.sql.Date(calendar.getTimeInMillis());
		calendar.add(Calendar.DATE, -1); //get a day back
		java.sql.Date yesterday = new java.sql.Date(calendar.getTimeInMillis());
		
		selectBetween2DatesQuery(RegularQueries.select_complaints_last_day, yesterday, today, resultList);
	}
	
	
	public static void getNumberOfReservationsOfLastWeekGroupedByOrder(ArrayList<Map<String, Object>> resultList) throws SQLException{
		
		Calendar calendar = new GregorianCalendar();
		java.sql.Date today = new java.sql.Date(calendar.getTimeInMillis());
		calendar.add(Calendar.DATE, -7); //get a week back
		java.sql.Date lastWeekDate = new java.sql.Date(calendar.getTimeInMillis());
		
		selectBetween2DatesQuery(ReportsQueries.select_counts_all_reservations_between_2_dates_grouped_by_orderType, lastWeekDate, today, resultList);
	}
	
	
	
	//TODO: test this query
	public static void getNumberOfReservationsOfLastDay(ArrayList<Map<String, Object>> resultList) throws SQLException{
		
		Calendar todayCalendar = new GregorianCalendar();
		java.sql.Date today = new java.sql.Date(todayCalendar.getTimeInMillis());
		todayCalendar.add(Calendar.DATE, -1);
		java.sql.Date yesterday = new java.sql.Date(todayCalendar.getTimeInMillis());
		
		selectBetween2DatesQuery(ReportsQueries.select_count_reservations_between_2_dates_grouped_by_kind, yesterday, today, resultList);
	}
	
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
