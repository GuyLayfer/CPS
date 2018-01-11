package server.db.queries;

import server.db.DBConstants.DbSqlColumns;
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
	

public final String select_worker_role = "SELECT " + DbSqlColumns.ROLE_ID.getName() + ", " + DbSqlColumns.LOT_ID.getName()  + 
		" FROM " + SqlTables.WORKERS.getName() +
		"  WHERE  " + DbSqlColumns.WORKER_ID.getName() + " = ?";
	
	
}
