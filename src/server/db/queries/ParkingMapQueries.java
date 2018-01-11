package server.db.queries;

import server.db.DBConstants.DbSqlColumns;
import server.db.DBConstants.SqlTables;

public class ParkingMapQueries {
	
	private static volatile ParkingMapQueries instance;
	private static Object mutex = new Object();

	private ParkingMapQueries() {
	}

	public static ParkingMapQueries getInstance() {
		ParkingMapQueries result = instance;
		if (result == null) {
			synchronized (mutex) {
				result = instance;
				if (result == null)
					instance = result = new ParkingMapQueries();
			}
		}
		return result;
	}
	
	
	
	public int numOfLots = 2;
	
	
	public final String query_lot_1 =
			" (lot_id,c1,c2,c3,c4,c5,c6,c7,c8,c9,c10,c11,c12,c13,c14,c15,c16,c17,c18,c19,c20,c21,c22,c23,c24,c25,"+
			"c26,c27,c28,c29,c30,c31,c32,c33,c34,c35,c36) values " + "(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) "; //36 + 1 for lot_id;

	
	public final String query_lot_2 = 
			" (lot_id,c1,c2,c3,c4,c5,c6,c7,c8,c9,c10,c11,c12,c13,c14,c15,c16,c17,c18,c19,c20,c21,c22,c23,c24,c25,"+
			"c26,c27,c28,c29,c30,,c31,c32,c33,c34,c35,c36,c37,c38,c39,c40,c41,c42,c43,c44,45 ) values " +
			"(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) "; //45 + 1 for lot_id;
			
	public final String insert_parking_map_lot_1 =
			"INSERT INTO " + SqlTables.PARKING_MAP.getName() + " " + 
			query_lot_1;
	
	public final String insert_parking_map_lot_2 = 
			"INSERT INTO " + SqlTables.PARKING_MAP.getName() + " " +
			query_lot_2;
	
	
	public  String [] parkingMapInsertQueriesIdxByLotId = {insert_parking_map_lot_1, insert_parking_map_lot_2}; 

	
	
	public final String select_parking_map_by_lot_id =
			"SELECT * " + 
			" FROM " + SqlTables.PARKING_MAP.getName() + 
			" WHERE " + DbSqlColumns.LOT_ID.getName() + " = ?";


	
	public final String delete_parking_map_of_lot_id =
			"DELETE FROM " + SqlTables.PARKING_MAP.getName()+
			" WHERE " + DbSqlColumns.LOT_ID.getName() + " = ?";	
	
	
}
