package server.db.queries;
import server.db.DBConstants.DbSqlColumns;
import server.db.DBConstants.SqlTables;

public class ReportsQueries {

	private static volatile ReportsQueries instance;
	private static Object mutex = new Object();

	private ReportsQueries() {
	}

	public static ReportsQueries getInstance() {
		ReportsQueries result = instance;
		if (result == null) {
			synchronized (mutex) {
				result = instance;
				if (result == null)
					instance = result = new ReportsQueries();
			}
		}
		return result;
	}
	
	

//	TODO: test this query
	public final String select_all_reservations_between_2_dates =
			" SELECT * FROM " + SqlTables.CURRENT_CARS_PLANED_BEING_IN_PARKING_LOG.getName() +
			 "	 WHERE " + DbSqlColumns.ARRIVE_TIME.getName() + " BETWEEN ? and ?" ;
	
	
//	TODO: test this query
	public final String select_counts_all_reservations_between_2_dates_grouped_by_orderType =
			" SELECT  " + DbSqlColumns.ORDER_TYPE.getName() + ", count(" + DbSqlColumns.ENTRANCE_ID.getName() + ") " + 
			 " FROM " + SqlTables.CURRENT_CARS_PLANED_BEING_IN_PARKING_LOG.getName() +
			 "	 WHERE " + DbSqlColumns.ARRIVE_TIME.getName() + " BETWEEN ? and ? " +
				" GROUP BY " + DbSqlColumns.ORDER_TYPE.getName() +
				" ORDER BY " + DbSqlColumns.ORDER_TYPE.getName();
	
	public static String get_daily_stats_by_day_id =
			"SELECT " + 
					DbSqlColumns.FILLED_RESERVATIONS.getName() + ", "
					+  DbSqlColumns.CANCELED_ORDERS.getName() + ", " 
					+ DbSqlColumns.LATING_PER_PARK.getName() +
			" FROM " + SqlTables.DAILY_STATS.getName() +
			" WHERE " + DbSqlColumns.DAY_ID.getName() + " = ?";
				
	
	public static String add_canceled_reservation_daily =
			"  UPDATE   " + SqlTables.DAILY_STATS.getName()  +
			" SET " + DbSqlColumns.CANCELED_ORDERS.getName() + " = " + DbSqlColumns.CANCELED_ORDERS.getName() + " + 1 " +
			" WHERE " + DbSqlColumns.DAY_ID.getName() + "= ?";
	
	
	public static String add_filled_reservation_daily = 
			" UPDATE " +  SqlTables.DAILY_STATS.getName() +
			" SET " + DbSqlColumns.FILLED_RESERVATIONS.getName() + " = " + DbSqlColumns.FILLED_RESERVATIONS.getName() + " + 1 " +
			" WHERE " + DbSqlColumns.DAY_ID.getName() + "= ?";
	
//	TODO: test this query
	public final String select_count_reservations_between_2_dates_grouped_by_kind =
			" SELECT count(" + DbSqlColumns.ENTRANCE_ID.getName() + ") " + 
			" FROM " + SqlTables.CURRENT_CARS_PLANED_BEING_IN_PARKING_LOG.getName() +
			 "	 WHERE " + DbSqlColumns.ARRIVE_TIME.getName() + " BETWEEN ? and ?" +
			 "	GROUP BY " + DbSqlColumns.ORDER_TYPE.getName();
	
	public final String select_lating_reservations_between_2_dates = 
			"SELECT count( " + DbSqlColumns.ENTRANCE_ID.getName() + ")" +
			"  FROM " + SqlTables.CURRENT_CARS_PLANED_BEING_IN_PARKING_LOG.getName() +
			"  WHERE (( " + DbSqlColumns.DAY_ID.getName() + " BETWEEN ? and ?) &&" +
					 " ( " + DbSqlColumns.ARRIVE_PREDICTION.getName() + " < " + DbSqlColumns.ARRIVE_TIME.getName() +
					 " || " + DbSqlColumns.LEAVE_PREDICTION.getName() + " > " + DbSqlColumns.LEAVE_TIME.getName() + "))";

	public final String select_canceled_reservations_between_2_dates = 
			"SELECT count( " + DbSqlColumns.ENTRANCE_ID.getName() + ")" +
			"  FROM " + SqlTables.CURRENT_CARS_PLANED_BEING_IN_PARKING_LOG.getName() +
			"  WHERE (( " + DbSqlColumns.ARRIVE_PREDICTION.getName() + " BETWEEN ? and ?) && (" +
					 DbSqlColumns.ARRIVE_TIME.getName() + " IS NULL ))" ;
	
	
//	TODO: test this query
	public final String select_count_reservations_between_2_dates_grouped_by_kind_of_lot_id =
			" SELECT count(" + DbSqlColumns.ENTRANCE_ID.getName() + ") " + 
			" FROM " + SqlTables.CURRENT_CARS_PLANED_BEING_IN_PARKING_LOG.getName() +
			 "	 WHERE (" + DbSqlColumns.ARRIVE_TIME.getName() + " BETWEEN ? and ?) and lot_id = ?" +
			 "	GROUP BY " + DbSqlColumns.ORDER_TYPE.getName();
	
	public final String select_lating_reservations_between_2_dates_grouped_by_kind_of_lot_id = 
			"SELECT count( " + DbSqlColumns.ENTRANCE_ID.getName() + ")" +
			"  FROM " + SqlTables.CURRENT_CARS_PLANED_BEING_IN_PARKING_LOG.getName() +
			"  WHERE (( " +  DbSqlColumns.ARRIVE_TIME.getName() + " BETWEEN ? and ?) && " +
					 " ( " + DbSqlColumns.ARRIVE_PREDICTION.getName() + " < " + DbSqlColumns.ARRIVE_TIME.getName() +
					 " || " + DbSqlColumns.LEAVE_PREDICTION.getName() + " > " + DbSqlColumns.LEAVE_TIME.getName() + ")"
					 		+ " and lot_id = ?) " +
			 		 "	GROUP BY " + DbSqlColumns.ORDER_TYPE.getName();

	public final String select_canceled_reservations_between_2_dates_grouped_by_kind_of_lot_id = 
			"SELECT count( " + DbSqlColumns.ENTRANCE_ID.getName() + ")" +
			"  FROM " + SqlTables.CURRENT_CARS_PLANED_BEING_IN_PARKING_LOG.getName() +
			"  WHERE (( " + DbSqlColumns.ARRIVE_PREDICTION.getName() + " BETWEEN ? and ?) && (" +
					 DbSqlColumns.ARRIVE_TIME.getName() + " IS NULL ) and lot_id = ?)" +
					 "	GROUP BY " + DbSqlColumns.ORDER_TYPE.getName();
	
	
//	TODO: test this query
	public final String select_counts_all_reservations_between_2_dates_grouped_by_orderType_of_lot_id =
			" SELECT  " + DbSqlColumns.ORDER_TYPE.getName() + ", count(" + DbSqlColumns.ENTRANCE_ID.getName() + ") " + 
			 " FROM " + SqlTables.CURRENT_CARS_PLANED_BEING_IN_PARKING_LOG.getName() +
			 "	 WHERE (" + DbSqlColumns.ARRIVE_TIME.getName() + " BETWEEN ? and ? ) and lot_id = ?" +
				" GROUP BY " + DbSqlColumns.ORDER_TYPE.getName() +
				" ORDER BY " + DbSqlColumns.ORDER_TYPE.getName();
	
	public final String select_filled_reservations_between_2_dates_grouped_by_kind_of_lot_id = 
			"SELECT count( " + DbSqlColumns.ENTRANCE_ID.getName() + ")" +
			"  FROM " + SqlTables.CURRENT_CARS_PLANED_BEING_IN_PARKING_LOG.getName() +
			"  WHERE (( " + DbSqlColumns.ARRIVE_PREDICTION.getName() + " BETWEEN ? and ?) && (" +
					 DbSqlColumns.ARRIVE_TIME.getName() + " IS NOT NULL ) and lot_id = ?)" +
					 "	GROUP BY " + DbSqlColumns.ORDER_TYPE.getName();
	
	public final String select_filled_reservations_between_2_dates_of_lot_id = 
			"SELECT " + DbSqlColumns.FILLED_RESERVATIONS.getName() + " " +
			"  FROM " + SqlTables.DAILY_STATS.getName() +
			"  WHERE (( " + DbSqlColumns.DAY_ID.getName() + " BETWEEN ? and ?) && (" +
					 DbSqlColumns.DAY_ID.getName() + " IS NOT NULL ) and lot_id = ?)";
	
	public final String select_canceled_reservations_between_2_dates_of_lot_id = 
			"SELECT " + DbSqlColumns.CANCELED_ORDERS.getName() + " " +
			"  FROM " + SqlTables.DAILY_STATS.getName() +
			"  WHERE (( " + DbSqlColumns.DAY_ID.getName() + " BETWEEN ? and ?) && (" +
					 DbSqlColumns.DAY_ID.getName() + " IS NOT NULL ) and lot_id = ?)";
	
	public final String select_lating_reservations_between_2_dates_of_lot_id = 
			"SELECT " + DbSqlColumns.LATING_PER_PARK.getName() + " " +
			"  FROM " + SqlTables.DAILY_STATS.getName() +
			"  WHERE (( " + DbSqlColumns.DAY_ID.getName() + " BETWEEN ? and ?) && (" +
					 DbSqlColumns.DAY_ID.getName() + " IS NOT NULL ) and lot_id = ?)";
	
	
	/*  day, filled_reservations, canceled_reservations, lating_per_park. might be done on initialization, with values of (0, 0, 0, 0)*/
	public final String insert_into_daily_stats_new_day =
			"INSERT into " + SqlTables.DAILY_STATS.getName() +
			"( " + DbSqlColumns.DAY_ID.getName() + "," +
					DbSqlColumns.LOT_ID.getName() + "," +
					DbSqlColumns.FILLED_RESERVATIONS.getName() + "," +
					DbSqlColumns.CANCELED_ORDERS.getName() + "," +
					DbSqlColumns.LATING_PER_PARK.getName() + ")" +
					" Values(?,?,?,?,?) "; 
	
	
	public final String insert_into_weekly_stats_new_day =
			"INSERT into " + SqlTables.WEEKLY_STATS.getName() +
			"  Values(?,?,?,?,?,?,?,?,?,?,?) "; 

	
	
	
	//TODO: test the query
	public final String select_the_mean_of_entrances = 
			"SELECT (mean(count(" + DbSqlColumns.ENTRANCE_ID.getName() + "))) " +
			"  FROM " + SqlTables.CURRENT_CARS_PLANED_BEING_IN_PARKING.getName()+
			"  WHERE " + DbSqlColumns.ARRIVE_TIME.getName() + " < " + DbSqlColumns.LEAVE_TIME.getName()  + /*this guarantees the order filled and ended (car left)*/
			" and " + DbSqlColumns.ARRIVE_TIME.getName() + " between ? and ?";


	
	
}
