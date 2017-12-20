package db;
public class DBQueries {

	public static final String insert_car_planed_being_in_parking = "INSERT INTO current_cars_planed_being_in_parking " + 
			"VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?);";
	
	public static final String select_all = "SELECT *" + 
			"FROM $current_cars_in_parking";
	

	public static final String track_order = "SELECT * "
									+ "	 FROM current_cars_planed_being_in_parking "
									+ " WHERE entrance_id = ?";
	

	public static final String create_new_account = "INSERT INTO accounts " +
												"VALUES (?, ?, ?, ?, ?) ";
	
	public static final String get_account_details = "SELECT * " +
												"FROM accounts " +
												"WHERE account_id = ?";
	

//	public static String create_table_current_cars_in_parking = "CREATE TABLE current_cars_planed_being_in_parking (" + 
//	"	car_id int," + 
//	"	entrance_id int," + 
//	"	prediction_arrive TIMESTAMP," + 
//	"	prediction_leave TIMESTAMP," + 
//	"	kind int " + 
//	");";	
	
	
	
	
}
