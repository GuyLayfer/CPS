package db.queries;
import db.DBConstants.sqlColumns;
import db.DBConstants.sqlTables;

public class ReportsQueries {


//	TODO: test this query
	public static final String select_all_reservations_between_2_dates =
			" SELECT * FROM " + sqlTables.CURRENT_CARS_PLANED_BEING_IN_PARKING_LOG.getName() +
			 "	 WHERE " + sqlColumns.ARRIVE_TIME.getName() + " BETWEEN ? and ?" ;
	
	
//	TODO: test this query
	public static final String select_counts_all_reservations_between_2_dates_grouped_by_orderType =
			" SELECT  " + sqlColumns.ORDER_TYPE.getName() + ", count(" + sqlColumns.ENTRANCE_ID.getName() + ") " + 
			 " FROM " + sqlTables.CURRENT_CARS_PLANED_BEING_IN_PARKING_LOG.getName() +
			 "	 WHERE " + sqlColumns.ARRIVE_TIME.getName() + " BETWEEN ? and ? " +
				" GROUP BY " + sqlColumns.ORDER_TYPE.getName() +
				" ORDER BY " + sqlColumns.ORDER_TYPE.getName();
	
	public static String get_daily_stats_by_day_id =
			"SELECT " + 
					sqlColumns.FILLED_RESERVATIONS.getName() + ", "
					+  sqlColumns.CANCELED_ORDERS.getName() + ", " 
					+ sqlColumns.LATING_PER_PARK.getName() +
			" FROM " + sqlTables.DAILY_STATS.getName() +
			" WHERE " + sqlColumns.DAY_ID.getName() + " = ?";
				
	
	public static String add_canceled_reservation_daily =
			"  UPDATE   " + sqlTables.DAILY_STATS.getName()  +
			" SET " + sqlColumns.CANCELED_ORDERS.getName() + " = " + sqlColumns.CANCELED_ORDERS.getName() + " + 1 " +
			" WHERE " + sqlColumns.DAY_ID.getName() + "= ?";
	
	
	public static String add_filled_reservation_daily = 
			" UPDATE " +  sqlTables.DAILY_STATS.getName() +
			" SET " + sqlColumns.FILLED_RESERVATIONS.getName() + " = " + sqlColumns.FILLED_RESERVATIONS.getName() + " + 1 " +
			" WHERE " + sqlColumns.DAY_ID.getName() + "= ?";
	
//	TODO: test this query
	public static final String select_count_reservations_between_2_dates_grouped_by_kind =
			" SELECT count(" + sqlColumns.ENTRANCE_ID.getName() + ") " + 
			" FROM " + sqlTables.CURRENT_CARS_PLANED_BEING_IN_PARKING_LOG.getName() +
			 "	 WHERE " + sqlColumns.ARRIVE_TIME.getName() + " BETWEEN ? and ?" +
			 "	GROUP BY " + sqlColumns.ORDER_TYPE.getName();
	
	public static final String select_lating_reservations_between_2_dates = 
			"SELECT count( " + sqlColumns.ENTRANCE_ID.getName() + ")" +
			"  FROM " + sqlTables.CURRENT_CARS_PLANED_BEING_IN_PARKING_LOG.getName() +
			"  WHERE (( " + sqlColumns.DAY_ID.getName() + " BETWEEN ? and ?) &&" +
					 " ( " + sqlColumns.ARRIVE_PREDICTION.getName() + " < " + sqlColumns.ARRIVE_TIME.getName() +
					 " || " + sqlColumns.ARRIVE_PREDICTION.getName() + " < " + sqlColumns.ARRIVE_TIME.getName() + "))";
	
	/*  day, filled_reservations, canceled_reservations, lating_per_park. might be done on initialization, with values of (0, 0, 0, 0)*/
	public static final String insert_into_daily_stats_new_day =
			"INSERT into " + sqlTables.DAILY_STATS.getName() +
			" Values(?,?,?,?) "; 
	
	
	public static final String select_the_mean_of_entrances = 
			"SELECT (mean(count(" + sqlColumns.ENTRANCE_ID.getName() + "))) " +
			"  FROM " + sqlTables.CURRENT_CARS_PLANED_BEING_IN_PARKING.getName()+
			"  WHERE " + sqlColumns.ARRIVE_TIME.getName() + " < " + sqlColumns.LEAVE_TIME.getName()  + /*this guarantees the order filled and ended (car left)*/
			" and " + sqlColumns.ARRIVE_TIME.getName() + " between ? and ?";

	/*gives the count of lating reservations of the last day */
//	SELECT count(entranceID)
//	 FROM parking status log
//	 WHERE (prediction_arrive < time_entered || prediction_leave > time_leave)
	
//	SELECT (mean(count(entranceID))
//	 FROM parking_status
//	 WHERE time_entered < time_leave /*this guarantees the order filled and ended (car left)*/
//	and time_entered between this day and last week
	

	
	
	
	
	
	
	
	
	
	
}
