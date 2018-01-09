package server.db.queries;

import server.db.DBConstants.sqlColumns;
import server.db.DBConstants.sqlTables;

public class WorkersQueries {


public static final String select_worker_role = "SELECT " + sqlColumns.ROLE_ID.getName() + ", " + sqlColumns.LOT_ID.getName()  + 
		" FROM " + sqlTables.WORKERS.getName() +
		"  WHERE  " + sqlColumns.WORKER_ID.getName() + " = ?";
	
	
}
