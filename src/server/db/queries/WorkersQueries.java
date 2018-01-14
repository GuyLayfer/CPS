package server.db.queries;

import server.db.DBConstants.SqlTables;
import server.db.SqlColumns;
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
	

public final String selectWorkerBtNameAndPassword = "SELECT *"
		+ " FROM " + SqlTables.WORKERS.getName()
		+ "  WHERE " + SqlColumns.Workers.WORKER_ID
		+ " = ?"
		+ " AND " + SqlColumns.Workers.PASSWORD
		+ " = ?";
}
