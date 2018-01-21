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
import server.db.DBConnection;
import server.db.DBConstants;
import server.db.DBConstants.DbSqlColumns;
import server.db.queries.ReportsQueries;

// TODO: Auto-generated Javadoc
/**
 * The Class ReportsDBAPI.
 */
public class ReportsDBAPI extends DBAPI {
	
/** The instance. */
//	private static Object mutex = new Object();
	private static volatile ReportsDBAPI instance;
	
	/** The reports queries inst. */
	private ReportsQueries reportsQueriesInst;

	/**
	 * Instantiates a new reports DBAPI.
	 */
	private ReportsDBAPI() {
		reportsQueriesInst = ReportsQueries.getInstance();
	}

	/**
	 * Gets the single instance of ReportsDBAPI.
	 *
	 * @return single instance of ReportsDBAPI
	 */
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
	public void getNumberOfReservationsBetween2Dates(java.sql.Date earlyDate,
			java.sql.Date latterDate, ArrayList<Map<String, Object>> resultList) throws SQLException{
		selectBetween2DatesQuery(reportsQueriesInst.select_count_reservations_between_2_dates_grouped_by_kind,
									earlyDate, latterDate, resultList);
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
	public void getNumberOfLatingReservationsBetween2Dates(java.sql.Date earlyDate, java.sql.Date latterDate, 
			ArrayList<Map<String, Object>> resultList)
			throws SQLException{
		selectBetween2DatesQuery(reportsQueriesInst.select_lating_reservations_between_2_dates, earlyDate, latterDate, resultList);
	}
	
	/**
	 * Gets the number of canceled reservations between 2 dates.
	 *
	 * @param earlyDate the early date
	 * @param latterDate the latter date
	 * @param resultList the result list
	 * @return the number of canceled reservations between 2 dates
	 * @throws SQLException the SQL exception
	 */
	public void getNumberOfCanceledReservationsBetween2Dates(java.sql.Date earlyDate,
			java.sql.Date latterDate, ArrayList<Map<String, Object>> resultList)
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
		java.sql.Date today = ServerUtils.getToday();
		java.sql.Date lastWeekDate = ServerUtils.getLastWeek(); 
		selectBetween2DatesQuery(reportsQueriesInst.select_counts_all_reservations_between_2_dates_grouped_by_orderType,
								lastWeekDate, today, resultList);
	}

	
	/**
	 * Gets the number of reservations of last week grouped by order.
	 *
	 * @param resultList the result list
	 * @param lotId the lot id
	 * @return the number of reservations of last week grouped by order
	 * @throws SQLException the SQL exception
	 */
	public void getNumberOfReservationsOfLastWeekGroupedByOrderOfLotId(ArrayList<Map<String, Object>> resultList,
																		int lotId) throws SQLException{
		
		java.sql.Date today = ServerUtils.getToday();
		java.sql.Date lastWeekDate = ServerUtils.getLastWeek(); 
		
		selectBetween2DatesQueryOfLotId(reportsQueriesInst.select_counts_all_reservations_between_2_dates_grouped_by_orderType_of_lot_id,
				lastWeekDate, today, resultList, lotId);
	}
	
	/**
	 * Gets the number of filled of last week grouped by order of lot id.
	 *
	 * @param resultList the result list
	 * @param lotId the lot id
	 * @return the number of filled of last week grouped by order of lot id
	 * @throws SQLException the SQL exception
	 */
	public void getNumberOfFilledOfLastWeekGroupedByOrderOfLotId(ArrayList<Map<String, Object>> resultList,
			int lotId) throws SQLException{
		
		java.sql.Date today = ServerUtils.getToday();
		java.sql.Date lastWeekDate = ServerUtils.getLastWeek(); 
		
		selectBetween2DatesQueryOfLotId(reportsQueriesInst.select_filled_reservations_between_2_dates_grouped_by_kind_of_lot_id,
				lastWeekDate, today, resultList, lotId);
	}
	
	/**
	 * Gets the number of canceled of last week grouped by order of lot id.
	 *
	 * @param resultList the result list
	 * @param lotId the lot id
	 * @return the number of canceled of last week grouped by order of lot id
	 * @throws SQLException the SQL exception
	 */
	public void getNumberOfCanceledOfLastWeekGroupedByOrderOfLotId(ArrayList<Map<String, Object>> resultList,
			int lotId) throws SQLException{
		
		java.sql.Date today = ServerUtils.getToday();
		java.sql.Date lastWeekDate = ServerUtils.getLastWeek(); 
		
		selectBetween2DatesQueryOfLotId(reportsQueriesInst.select_canceled_reservations_between_2_dates_grouped_by_kind_of_lot_id,
				lastWeekDate, today, resultList, lotId);
	}	
	
	/**
	 * Gets the number of lating of last week grouped by order of lot id.
	 *
	 * @param resultList the result list
	 * @param lotId the lot id
	 * @return the number of lating of last week grouped by order of lot id
	 * @throws SQLException the SQL exception
	 */
	public void getNumberOfLatingOfLastWeekGroupedByOrderOfLotId(ArrayList<Map<String, Object>> resultList,
			int lotId) throws SQLException{
		
		java.sql.Date today = ServerUtils.getToday();
		java.sql.Date lastWeekDate = ServerUtils.getLastWeek(); 
		
		selectBetween2DatesQueryOfLotId(reportsQueriesInst.select_lating_reservations_between_2_dates_grouped_by_kind_of_lot_id,
				lastWeekDate, today, resultList, lotId);
	}		

	
	
	//TODO: floating point double = long / long - gives no floating point. need to make if for at least 2 digits after point.
//	/**
//	 * Generate reports data of lot id.
//	 * this function should be broken that could return the values calculated in the end of it.
//	 * 
//	 * @param reservationsFilledCanceledLatings the reservations filled canceled latings
//	 * @param lotId the lot id
//	 * @throws SQLException the SQL exception
//	 */
//	public void generateReportsDataOfLotId(String reservationsFilledCanceledLatings, int lotId) throws SQLException {
//		ArrayList<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
//		
//		// here select what data you would like to fetch
//		if (reservationsFilledCanceledLatings.equals("reservations")){
//		getNumberOfReservationsOfLastWeekGroupedByOrderOfLotId(resultList, lotId);
//		} else if (reservationsFilledCanceledLatings.equals("filled")){
//			getNumberOfFilledOfLastWeekGroupedByOrderOfLotId(resultList, lotId);
//		} else if (reservationsFilledCanceledLatings.equals("canceled")){
//			getNumberOfCanceledOfLastWeekGroupedByOrderOfLotId(resultList, lotId);
//		} else if (reservationsFilledCanceledLatings.equals("lating")){
//			getNumberOfLatingOfLastWeekGroupedByOrderOfLotId(resultList, lotId);
//		}
//		// number of reservations of the 'reservationsFilledCanceledLatings' category
//		long countOneTimeOrder = 0;
//		long countOrder = 0;
//		long countSubsFull = 0;
//		long countSubsOcc = 0;
//		long totalReservations = 0;
//		Iterator<Map<String, Object>> iterator = resultList.iterator();
//		// iterate over the 'count' entries (have only one for each order type) and get data.
//		while (iterator.hasNext()) {
//			Map<String, Object> row = (Map<String, Object>) iterator.next();
//			if(row.get(DbSqlColumns.ORDER_TYPE.getName()).equals(DBConstants.OrderType.ONE_TIME.getValue())){
//				countOneTimeOrder = (long) row.get("count(entrance_id)");
//			}
//			else if (row.get(DbSqlColumns.ORDER_TYPE.getName()).equals(DBConstants.OrderType.ORDER.getValue())){ 
//				System.out.println(row.get("count(entrance_id)"));
//				countOrder =  (long) (row.get("count(entrance_id)"));
//			}
//			else if (row.get(DbSqlColumns.ORDER_TYPE.getName()).equals(DBConstants.OrderType.SUBSCRIPTION.getValue())){
//				countSubsOcc  = (long) row.get("count(entrance_id)");
//			}
//			else if (row.get(DbSqlColumns.ORDER_TYPE.getName()).equals(DBConstants.OrderType.SUBSCRIPTION_FULL.getValue())){
//				countSubsFull  = (long) row.get("count(entrance_id)");
//			}
//		}
//		//total number of reservations is the sum of all possebilities
//		totalReservations = countOneTimeOrder + countOrder + countSubsFull + countSubsOcc;
//		
//		// how many order types were in last week in percenteges.
//		double oneTimeOrderPercents =  countOneTimeOrder / totalReservations;
//		double orderPercents =  countOrder / totalReservations;
//		double subsFullPercents =  countSubsFull / totalReservations;
//		double subsOccPercents =  countSubsOcc / totalReservations;
//		
//		// weekly average of each order type
//		double dailyAvgOneTimeOrder = countOneTimeOrder / 7; 
//		double dailyAvgOrder = countOrder / 7; 
//		double dailyAvgSubsOccOrder = countSubsOcc / 7; 
//		double dailyAvgSubsFullOrder = countSubsFull / 7; 
//		
//	}
	
	
	
	/**
	 * Gets the number of reservations of last day.
	 *
	 * @param resultList the result list
	 * @return the number of reservations of last day
	 * @throws SQLException the SQL exception
	 */
	//TODO: test this query
	public void getNumberOfReservationsOfLastDay(ArrayList<Map<String, Object>> resultList) throws SQLException{
		java.sql.Date today = ServerUtils.getToday();
		java.sql.Date yesterday = ServerUtils.getLastDay();
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
		java.sql.Date today = ServerUtils.getToday();
		java.sql.Date yesterday = ServerUtils.getLastDay();
		selectBetween2DatesQuery(reportsQueriesInst.select_lating_reservations_between_2_dates, yesterday, today, resultList);
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
	
	

	

	/**
	 * Gets the number of reservations between 2 dates grouped by order.
	 *
	 * @param resultList the result list
	 * @param lotId the lot id
	 * @return the number of reservations between 2 dates grouped by order
	 * @throws SQLException the SQL exception
	 */
	public void getNumberOfReservationsBetween2DatesGroupedByOrderOfLotId(ArrayList<Map<String, Object>> resultList,
			int lotId, java.sql.Date first, java.sql.Date second) throws SQLException{
		selectBetween2DatesQueryOfLotId(reportsQueriesInst.select_counts_all_reservations_between_2_dates_grouped_by_orderType_of_lot_id,
				first, second, resultList, lotId);
	}
	
	/**
	 * Gets the number of filled between 2 dates grouped by order of lot id.
	 *
	 * @param resultList the result list
	 * @param lotId the lot id
	 * @return the number of filled between 2 dates grouped by order of lot id
	 * @throws SQLException the SQL exception
	 */
	public void getNumberOfFilledBetween2DatesGroupedByOrderOfLotId(ArrayList<Map<String, Object>> resultList,
			int lotId, java.sql.Date first, java.sql.Date second) throws SQLException{
		selectBetween2DatesQueryOfLotId(reportsQueriesInst.select_filled_reservations_between_2_dates_grouped_by_kind_of_lot_id,
				first, second, resultList, lotId);
	}
	
	/**
	 * Gets the number of canceled between 2 dates grouped by order of lot id.
	 *
	 * @param resultList the result list
	 * @param lotId the lot id
	 * @return the number of canceled between 2 dates grouped by order of lot id
	 * @throws SQLException the SQL exception
	 */
	public void getNumberOfCanceledBetween2DatesGroupedByOrderOfLotId(ArrayList<Map<String, Object>> resultList,
			int lotId, java.sql.Date first, java.sql.Date second) throws SQLException{
		selectBetween2DatesQueryOfLotId(reportsQueriesInst.select_canceled_reservations_between_2_dates_grouped_by_kind_of_lot_id,
				first, second, resultList, lotId);
	}	
	
	/**
	 * Gets the number of lating between 2 dates grouped by order of lot id.
	 *
	 * @param resultList the result list
	 * @param lotId the lot id
	 * @return the number of lating between 2 dates grouped by order of lot id
	 * @throws SQLException the SQL exception
	 */
	public void getNumberOfLatingBetween2DatesGroupedByOrderOfLotId(ArrayList<Map<String, Object>> resultList,
			int lotId, java.sql.Date first, java.sql.Date second) throws SQLException{
		selectBetween2DatesQueryOfLotId(reportsQueriesInst.select_lating_reservations_between_2_dates_grouped_by_kind_of_lot_id,
				first, second, resultList, lotId);
	}
	
	public void insertIntoDailyStatsToday(int lotId, long filled, long canceled, long lating) throws SQLException {
		Queue<Object> params = new LinkedList<Object>(); // push all params to paramsValues. in order of SQL
		params.add(ServerUtils.getToday());
		params.add(lotId);
		params.add(filled);
		params.add(canceled);
		params.add(lating);
		DBConnection.updateSql(reportsQueriesInst.insert_into_daily_stats_new_day, params);
	}
	
	public void insertIntoDailyStats(int lotId, long filled, long canceled, long lating, java.sql.Date date) throws SQLException {
		Queue<Object> params = new LinkedList<Object>(); // push all params to paramsValues. in order of SQL
		params.add(date);
		params.add(lotId);
		params.add(filled);
		params.add(canceled);
		params.add(lating);
		DBConnection.updateSql(reportsQueriesInst.insert_into_daily_stats_new_day, params);
	}
	
	
	public void insertIntoWeeklyStats(int lotId, int filledMean, int canceledMean, int latingMean,
			double filledStd, double canceledStd, double latingStd,
			double filledAvg, double canceledAvg, double latingAvg, java.sql.Date first) throws SQLException {
		Queue<Object> params = new LinkedList<Object>(); // push all params to paramsValues. in order of SQL
		params.add(first);
		params.add(lotId);
		params.add(filledMean);
		params.add(canceledMean);
		params.add(latingMean);
		params.add(filledStd);
		params.add(canceledStd);
		params.add(latingStd);
		params.add(filledAvg);
		params.add(canceledAvg);
		params.add(latingAvg);
		DBConnection.updateSql(reportsQueriesInst.insert_into_weekly_stats_new_day, params);
	}

	public void updateBrokenParkingStatus(Boolean isParkingBroken, int lotId, int row, int coloumn, int floor) throws SQLException {
		Queue<Object> params1 = new LinkedList<Object>();
		Queue<Object> params2 = new LinkedList<Object>();
		if(isParkingBroken) {
			params1.add(lotId);
			params1.add(new Date().getTime());
			params1.add(null);
			params1.add(row);
			params1.add(coloumn);
			params1.add(floor);
			DBConnection.updateSql(reportsQueriesInst.insertBrokenParking, params1);
		}
		
		else {
			params2.add(new Date().getTime());
			params2.add(lotId);
			params2.add(row);
			params2.add(coloumn);
			params2.add(floor);
			DBConnection.updateSql(reportsQueriesInst.cancelBrokenParking, params2);
		}
	}
}