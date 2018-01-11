package server.db.queries;

import server.db.DBConstants.SqlColumns;
import server.db.DBConstants.SqlTables;
import server.db.dbAPI.DBAPI;

public class WorkersQueries extends DBAPI {

	private static volatile WorkersQueries instance;
	private static Object mutex = new Object();

	private WorkersQueries() {
	}

	public static WorkersQueries getInstance() {
		WorkersQueries result = instance;
		if (result == null) {
			synchronized (mutex) {
				result = instance;
				if (result == null)
					instance = result = new WorkersQueries();
			}
		}
		return result;
	}
	

public final String select_worker_role = "SELECT " + SqlColumns.ROLE_ID.getName() + ", " + SqlColumns.LOT_ID.getName()  + 
		" FROM " + SqlTables.WORKERS.getName() +
		"  WHERE  " + SqlColumns.WORKER_ID.getName() + " = ?";
	
	
}
