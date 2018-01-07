package server.db.queries;

import server.db.DBConstants.sqlColumns;
import server.db.DBConstants.sqlTables;

public class RegularQueries {

	
	public static String get_last_day_complains =
			"SELECT * " +
			"FROM " + sqlTables.COMPLAINTS.getName() + 
			"  WHERE " + sqlColumns.COMPLAINT_DATETIME.getName()  +
			" BETWEEN ? AND ?";
	
	public static String car_id_details = 
			"SELECT * " +
			"FROM " +  sqlTables.CARS.getName() + 
			"  WHERE " + sqlColumns.CAR_ID.getName() + " = ?";
	
	
	public static String select_complain_details =
			"SELECT * " +
			"FROM " +  sqlTables.COMPLAINTS.getName() +
			" WHERE " + sqlColumns.COMPLAINT_ID.getName() + " = ?";

	public static String select_complaints_last_day = 
			"SELECT * " +
			" FROM " +  sqlTables.COMPLAINTS.getName() +
			" WHERE " + sqlColumns.COMPLAINT_DATETIME.getName() +
			" BETWEEN ? and ?";
	
	public static String update_customer_balance =
			" UPDATE  " + sqlTables.ACCOUNTS.getName() +
			" SET " + sqlColumns.BALANCE.getName() + " = " + sqlColumns.BALANCE.getName() + " + ? " +
			" WHERE " + sqlColumns.ACCOUNT_ID.getName() + " = ?";
	
	public static String select_customer_id_by_entrance_id = 
			"SELECT " + sqlColumns.ACCOUNT_ID.getName()  +
			" FROM " + sqlTables.CURRENT_CARS_PLANED_BEING_IN_PARKING.getName()+ 
			"  WHERE " + sqlColumns.ENTRANCE_ID.getName() + " = ?";
	
	
	public static final String insert_car_planed_being_in_parking_to_log = 
			"INSERT INTO " + sqlTables.CURRENT_CARS_PLANED_BEING_IN_PARKING_LOG.getName()+
			"(" +
			 sqlColumns.CAR_ID.getName() + ", " +
			sqlColumns.ACCOUNT_ID.getName() + ", " +
			 sqlColumns.LOT_ID.getName() + ", " + 
			 sqlColumns.ORDER_TYPE.getName() + ", " +
			 sqlColumns.ARRIVE_PREDICTION.getName() + ", " +
			 sqlColumns.LEAVE_PREDICTION.getName() + ", " +
			 sqlColumns.ARRIVE_TIME.getName() + ", " +
			  sqlColumns.LEAVE_TIME.getName() + 
			 " ) " +
			  " VALUES (?, ?, ?, ?, ?, ?, ?, ?);";
	
	public static final String insert_car_planed_being_in_parking =
			"INSERT INTO " + sqlTables.CURRENT_CARS_PLANED_BEING_IN_PARKING.getName() + 
			" VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?);";

	
	public static final String car_left_parking_update_time_left = 
			" UPDATE  " + sqlTables.CURRENT_CARS_PLANED_BEING_IN_PARKING_LOG.getName() + 
			" SET " + sqlColumns.LEAVE_TIME.getName() + " = ? "
			+ " WHERE " + sqlColumns.ENTRANCE_ID.getName() + " = ?";
	
	public static final String delete_entrance_from_car_planed_being_in_parking = 
			"DELETE FROM " + sqlTables.CURRENT_CARS_PLANED_BEING_IN_PARKING.getName()+
			" WHERE " + sqlColumns.ENTRANCE_ID.getName() + " = ?";
	
	public static final String select_subscriptioin_id_by_car_id = 
			"SELECT " + sqlColumns.CAR_ID.getName() + 
			" FROM " + sqlTables.SUBSCRIPTIONS.getName() + 
			"  WHERE " + sqlColumns.SUBSCRIPTION_ID.getName() + " = ? ";

	public static final String track_order =
			"SELECT * " +
			 "	FROM " + sqlTables.CURRENT_CARS_PLANED_BEING_IN_PARKING.getName()+
			 "  WHERE " + sqlColumns.ENTRANCE_ID.getName() + " = ?";
	

	public static final String insert_new_account = 
			"INSERT INTO " + sqlTables.ACCOUNTS.getName() +
			" VALUES (?, ?, ?, ?, ?) ";
	
	public static final String select_account_details = 
			"SELECT * " +
			"FROM  " + sqlTables.ACCOUNTS.getName() +
			" WHERE " + sqlColumns.ACCOUNT_ID.getName() + " = ?";
	
	
	public static String select_all_cars = 
			"SELECT * " +
			"FROM " + sqlTables.CARS.getName();
	
	
	/*select all cars in parking (including broken, order etc..*/ //TODO: fix this
	public static final String select_all_cars_in_parking =
			"SELECT * " +
			" FROM " + sqlTables.CURRENT_CARS_PLANED_BEING_IN_PARKING.getName();
	

	
	
}
