package server.db.dbAPI; 

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import server.db.DBConnection;
import server.db.queries.ReportsQueries;

// TODO: Auto-generated Javadoc
/**
 * The Class ReportsDBAPI.
 */
public class ReportsDBAPI extends DBAPI {
	
//	private static Object mutex = new Object();
	private static volatile ReportsDBAPI instance;
	private ReportsQueries reportsQueriesInst;

	private ReportsDBAPI() {
		reportsQueriesInst = ReportsQueries.getInstance();
	}

	public static ReportsDBAPI getInstance() {
		ReportsDBAPI result = instance;
		if (result == null) {
			synchronized (mutex) {
				result = instance;
				if (result == null)
					instance = result = new ReportsDBAPI();
			}
		}
		return result;
	}
	
	
	/**
	 * Gets the number of reservations between two dates.
	 *
	 * @param earlyDate the early date
	 * @param latterDate the latter date
	 * @param resultList the result list
	 * @return the number of reservations between 2 dates. each row is grouped by order_type
	 * @throws SQLException the SQL exception
	 */
	//TODO: test this query
	public void getNumberOfReservationsBetween2Dates(java.sql.Date earlyDate, java.sql.Date latterDate, ArrayList<Map<String, Object>> resultList) throws SQLException{
		selectBetween2DatesQuery(reportsQueriesInst.select_count_reservations_between_2_dates_grouped_by_kind, earlyDate, latterDate, resultList);
	}
	
	/**
	 * get the number of lating reservations between 2 dates.
	 *
	 * @param earlyDate the early date
	 * @param latterDate the latter date
	 * @param resultList the result list
	 * @return the number of lating reservations between 2 dates.
	 * @throws SQLException the SQL exception
	 */
	public void getNumberOfLatingReservationsBetween2Dates(java.sql.Date earlyDate, java.sql.Date latterDate, ArrayList<Map<String, Object>> resultList)
			throws SQLException{
		selectBetween2DatesQuery(reportsQueriesInst.select_lating_reservations_between_2_dates, earlyDate, latterDate, resultList);
	}
	
	public void getNumberOfCanceledReservationsBetween2Dates(java.sql.Date earlyDate, java.sql.Date latterDate, ArrayList<Map<String, Object>> resultList)
			throws SQLException{
		selectBetween2DatesQuery(reportsQueriesInst.select_canceled_reservations_between_2_dates, earlyDate, latterDate, resultList);
	}
	

	
	
	/**
	 * Gets the number of reservations of last week grouped by order.
	 *
	 * @param resultList the result list
	 * @return the number of reservations of last week grouped by order
	 * @throws SQLException the SQL exception
	 */
	public void getNumberOfReservationsOfLastWeekGroupedByOrder(ArrayList<Map<String, Object>> resultList) throws SQLException{
		
		Calendar calendar = new GregorianCalendar();
		java.sql.Date today = new java.sql.Date(calendar.getTimeInMillis());
		calendar.add(Calendar.DATE, -7); //get a week back
		java.sql.Date lastWeekDate = new java.sql.Date(calendar.getTimeInMillis());
		
		selectBetween2DatesQuery(reportsQueriesInst.select_counts_all_reservations_between_2_dates_grouped_by_orderType, lastWeekDate, today, resultList);
	}
	
	
	
	/**
	 * Gets the number of reservations of last day.
	 *
	 * @param resultList the result list
	 * @return the number of reservations of last day
	 * @throws SQLException the SQL exception
	 */
	//TODO: test this query
	public void getNumberOfReservationsOfLastDay(ArrayList<Map<String, Object>> resultList) throws SQLException{
		
		Calendar todayCalendar = new GregorianCalendar();
		java.sql.Date today = new java.sql.Date(todayCalendar.getTimeInMillis());
		todayCalendar.add(Calendar.DATE, -1);
		java.sql.Date yesterday = new java.sql.Date(todayCalendar.getTimeInMillis());
		
		selectBetween2DatesQuery(reportsQueriesInst.select_count_reservations_between_2_dates_grouped_by_kind, yesterday, today, resultList);
	}
	
	/**
	 * Gets the number of lating reservations of last day.
	 *
	 * @param resultList the result list
	 * @return the number of lating reservations of last day
	 * @throws SQLException the SQL exception
	 */
	public void getNumberOfLatingReservationsOfLastDay(ArrayList<Map<String, Object>> resultList) throws SQLException{
		Queue<Object> params = new LinkedList<Object>(); // push all params to paramsValues. in order of SQL
		Calendar calendar = new GregorianCalendar();
		java.sql.Date today = new java.sql.Date(calendar.getTimeInMillis());
		calendar.add(Calendar.DATE, -1); //get a day back
		java.sql.Date yesterday = new java.sql.Date(calendar.getTimeInMillis());
		selectBetween2DatesQuery(reportsQueriesInst.select_lating_reservations_between_2_dates, yesterday, today, resultList);//TODO: verify order yesterday today
		DBConnection.selectSql(reportsQueriesInst.select_lating_reservations_between_2_dates, params, resultList);
	}
	
	/**
	 * Gets the daily stats of today.
	 *
	 * @param resultList the result list
	 * @return the daily stats of today
	 * @throws SQLException the SQL exception
	 */
	public void getDailyStatsOfToday(ArrayList<Map<String, Object>> resultList) throws SQLException{
		Queue<Object> params = new LinkedList<Object>(); // push all params to paramsValues. in order of SQL
		
		Calendar todayCalendar = new GregorianCalendar();
		java.sql.Date today = new java.sql.Date(todayCalendar.getTimeInMillis());
		
		params.add(today);
		
		DBConnection.selectSql(ReportsQueries.get_daily_stats_by_day_id, params, resultList);
		
	}	

	
	
	
}
