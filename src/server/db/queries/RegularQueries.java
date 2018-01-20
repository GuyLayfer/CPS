package server.db.queries;

import server.db.DBConstants.DbSqlColumns;
import server.db.DBConstants.SqlTables;






public class RegularQueries {
	private static volatile RegularQueries instance;
	private static Object mutex = new Object();

	private RegularQueries() {
	}

	public static RegularQueries getInstance() {
		RegularQueries result = instance;
		if (result == null) {
			synchronized (mutex) {
				result = instance;
				if (result == null)
					instance = result = new RegularQueries();
			}
		}
		return result;
	}
	
	
//	paramsValues.add(customerId);
//	paramsValues.add(lotId);
//	paramsValues.add(complaintDescription);
//	paramsValues.add(entranceId);
//	paramsValues.add(TrueFalse.FALSE.getValue());
//	paramsValues.add(complaintTime);
	
	public final String insert_complaint = 
			"INSERT INTO " + SqlTables.COMPLAINTS.getName()+
			"(" +
			 DbSqlColumns.ACCOUNT_ID.getName() + ", " +
			 DbSqlColumns.COMPLAINT_DESCRIPTION.getName() + ", " + 
			 DbSqlColumns.COMPLAINT_FILLED.getName() + ", " +
			 DbSqlColumns.COMPLAINT_DATETIME.getName() +
			 " ) " +
			  " VALUES (?, ?, ?, ?);";
	
	
	public final String get_all_opened_complains =
			"SELECT * " +
			" FROM " + SqlTables.COMPLAINTS.getName() +
			" WHERE " + DbSqlColumns.COMPLAINT_FILLED.getName() + " = 'false'";
	
	public final String car_id_details = 
			"SELECT * " +
			"FROM " +  SqlTables.CARS.getName() + 
			"  WHERE " + DbSqlColumns.CAR_ID.getName() + " = ?";
	
	
	public final String select_complain_details =
			"SELECT * " +
			"FROM " +  SqlTables.COMPLAINTS.getName() +
			" WHERE " + DbSqlColumns.COMPLAINT_ID.getName() + " = ?";
	
	public final String update_complaint =
			"UPDATE " + SqlTables.COMPLAINTS.getName() +
			" SET " + DbSqlColumns.COMPLAINT_FILLED.getName() + " = 'true', " + 
			          DbSqlColumns.IS_ACCEPTED.getName() + " = ?, " +
			          DbSqlColumns.COMPLAINT_CUSTOMER_SERVICE_RESPOND.getName() + " = ? " +
			"WHERE " + DbSqlColumns.COMPLAINT_ID.getName() + " = ?";
	
	public final String update_customer_balance =
			" UPDATE  " + SqlTables.ACCOUNTS.getName() +
			" SET " + DbSqlColumns.BALANCE.getName() + " = " + DbSqlColumns.BALANCE.getName() + " + ? " +
			" WHERE " + DbSqlColumns.ACCOUNT_ID.getName() + " = ?";
	
	
	public final String update_arrive_time_current_cars_in_parking_by_car_id = 
			"UPDATE " + SqlTables.CURRENT_CARS_PLANED_BEING_IN_PARKING.getName() + 
			" SET " + DbSqlColumns.ARRIVE_TIME.getName() + " = ? " +
			" WHERE " + DbSqlColumns.CAR_ID.getName() + " = ? ";
	
	
	public final String update_arrive_time_logs_by_entrance_id = 
			"UPDATE " + SqlTables.CURRENT_CARS_PLANED_BEING_IN_PARKING_LOG.getName() + 
			" SET " + DbSqlColumns.ARRIVE_TIME.getName() + " = ? " +
			" WHERE " + DbSqlColumns.ENTRANCE_ID.getName() + " = ? ";
	
	public final String select_customer_id_by_entrance_id = 
			"SELECT " + DbSqlColumns.ACCOUNT_ID.getName()  +
			" FROM " + SqlTables.CURRENT_CARS_PLANED_BEING_IN_PARKING.getName()+ 
			"  WHERE " + DbSqlColumns.ENTRANCE_ID.getName() + " = ?";
	
	
	public final String insert_car_planed_being_in_parking_to_log = 
			"INSERT INTO " + SqlTables.CURRENT_CARS_PLANED_BEING_IN_PARKING_LOG.getName()+
			"(" +
			 DbSqlColumns.CAR_ID.getName() + ", " +
			DbSqlColumns.ACCOUNT_ID.getName() + ", " +
			 DbSqlColumns.LOT_ID.getName() + ", " + 
			 DbSqlColumns.ORDER_TYPE.getName() + ", " +
			 DbSqlColumns.ARRIVE_PREDICTION.getName() + ", " +
			 DbSqlColumns.LEAVE_PREDICTION.getName() + ", " +
			 DbSqlColumns.ARRIVE_TIME.getName() + ", " +
			  DbSqlColumns.LEAVE_TIME.getName() + 
			 " ) " +
			  " VALUES (?, ?, ?, ?, ?, ?, ?, ?);";
	
	public final String insert_car_planed_being_in_parking =
			"INSERT INTO " + SqlTables.CURRENT_CARS_PLANED_BEING_IN_PARKING.getName() + 
			" VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?);";

	
	public final String car_left_parking_update_time_left = 
			" UPDATE  " + SqlTables.CURRENT_CARS_PLANED_BEING_IN_PARKING_LOG.getName() + 
			" SET " + DbSqlColumns.LEAVE_TIME.getName() + " = ? "
			+ " WHERE " + DbSqlColumns.ENTRANCE_ID.getName() + " = ?";
	
	public final String update_time_car_left_parking_in_logs = 
			" UPDATE  " + SqlTables.CURRENT_CARS_PLANED_BEING_IN_PARKING_LOG.getName() + 
			" SET " + DbSqlColumns.LEAVE_TIME.getName() + " = ? "
			+ " WHERE " + DbSqlColumns.LOT_ID.getName() + " = ? and "
						+ DbSqlColumns.CAR_ID.getName() + " = ?";
	
	
	public final String delete_entrance_from_car_planed_being_in_parking_by_entrance_id = 
			"DELETE FROM " + SqlTables.CURRENT_CARS_PLANED_BEING_IN_PARKING.getName()+
			" WHERE " + DbSqlColumns.ENTRANCE_ID.getName() + " = ?";
	
	public final String delete_entrance_from_car_planed_being_in_parking_by_car_id = 
			"DELETE FROM " + SqlTables.CURRENT_CARS_PLANED_BEING_IN_PARKING.getName()+
			" WHERE " + DbSqlColumns.CAR_ID.getName() + " = ?";
	

	public final String track_order =
			"SELECT * " +
			 "	FROM " + SqlTables.CURRENT_CARS_PLANED_BEING_IN_PARKING.getName()+
			 "  WHERE " + DbSqlColumns.ENTRANCE_ID.getName() + " = ?";
	

	
	public final String select_all_details_by_car_id_of_car_in_parking = 
			" SELECT * " +
			" FROM " + SqlTables.CURRENT_CARS_PLANED_BEING_IN_PARKING_LOG.getName() + 
			" WHERE " + DbSqlColumns.CAR_ID.getName() + " = ?";
			
			;
	public final String insert_new_account = 
			"INSERT INTO " + SqlTables.ACCOUNTS.getName() +
			" VALUES (?, ?, ?, ?, ?) ";
	
	public final String select_account_details = 
			"SELECT * " +
			"FROM  " + SqlTables.ACCOUNTS.getName() +
			" WHERE " + DbSqlColumns.ACCOUNT_ID.getName() + " = ?";
	
	
	public final String select_all_cars = 
			"SELECT * " +
			"FROM " + SqlTables.CARS.getName();
	
	
	/*select all cars in parking (including broken, order etc..*/ //TODO: fix this
	public final String select_all_cars_in_parking =
			"SELECT * " +
			" FROM " + SqlTables.CURRENT_CARS_PLANED_BEING_IN_PARKING.getName();
	
	public final String select_server_info =
			"SELECT * " +
			" FROM " + SqlTables.SERVER_INFO.getName();

	public final String insert_new_server_info =
			"INSERT INTO "  + SqlTables.SERVER_INFO.getName() +
			"(" + DbSqlColumns.INFO.getName() + ") values(?)";
	
	public final String update_server_info_of_lot_id =
			" UPDATE "  + SqlTables.SERVER_INFO.getName() +
			" SET " + DbSqlColumns.INFO.getName() + " = ? " +
			" WHERE " + DbSqlColumns.LOT_ID.getName() + " = ? ";
			
	
	public final String delete_server_info_of_lot_id =
			" DELETE FROM " + SqlTables.SERVER_INFO.getName() +
			" WHERE " + DbSqlColumns.LOT_ID.getName() + " = ? ";
	
	
	public final String select_all_lot_id_from_rates = 
			"SELECT * FROM " + SqlTables.RATES.getName();
	
}
