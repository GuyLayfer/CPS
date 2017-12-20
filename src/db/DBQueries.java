package db;
public class DBQueries {

	public static String insert_car_entered = "INSERT INTO current_cars_in_parking " + 
			"VALUES (?, ?, ?, ?, ?);";
	
	public static String select_all = "SELECT *" + 
			"FROM $current_cars_in_parking";
	
	public static String create_table_current_cars_in_parking = "CREATE TABLE current_cars_in_parking (" + 
			"	car_id int," + 
			"	entrance_id int," + 
			"	prediction_arrive TIMESTAMP," + 
			"	prediction_leave TIMESTAMP," + 
			"	kind int " + 
			");";
	
	
	public static String track_order = "SELECT car_id, prediction_arrive, prediction_leave, kind "
									+ "	 FROM current_cars_in_parking "
									+ " WHERE entrance_id = ?";
	
	public static String new_order = "INSERT INTO current_cars_in_parking " +
									" VALUES (?, ?, ?, ?, ?)";
	
	

	
	
	
	
	
}
