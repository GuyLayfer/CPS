package server.db.queries;

import server.db.DBConstants.DbSqlColumns;
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


	public final String select_all_lots_rates =
			"SELECT *"
					+ " FROM " + SqlTables.RATES.getName();
	
	public final String select_all_pending_lots_rates =
			" SELECT *"
					+ " FROM " + SqlTables.RATES_PENDING_FOR_APPROVAL.getName();
	
	public final String select_pending_rates_details =
			"SELECT * " +
					"FROM " +  SqlTables.RATES_PENDING_FOR_APPROVAL.getName() +
					" WHERE " + DbSqlColumns.LOT_ID.getName() + " = ?";

	public final String select_rates_details =
			"SELECT * " +
					"FROM " +  SqlTables.RATES.getName() +
					" WHERE " + DbSqlColumns.LOT_ID.getName() + " = ?";
	
	public final String select_full_subscription_rate = 
			" SELECT * FROM " + SqlTables.FULL_SUBSCRIPTION_RATE.getName();
	
	public final String selectWorkerBtNameAndPassword =
			"SELECT *"
					+ " FROM " + SqlTables.WORKERS.getName()
					+ "  WHERE " + SqlColumns.Workers.WORKER_ID
					+ " = ?"
					+ " AND " + SqlColumns.Workers.PASSWORD
					+ " = ?";


	public final String insert_rates_of_lot_id =
			" INSERT INTO " + SqlTables.RATES.getName() + 
			" VALUES (?,?,?,?,?)";

	public final String change_full_subscription_rate = 
			" UPDATE " + SqlTables.FULL_SUBSCRIPTION_RATE.getName() + 
			" SET rate = ?";

	public final String insert_into_pending_rates_of_lot_id =
			" INSERT INTO " + SqlTables.RATES_PENDING_FOR_APPROVAL.getName() + 
			" VALUES (?,?,?,?,?,?)";

	public final String delete_rate_from_pending_rate =
			"DELETE FROM " + SqlTables.RATES_PENDING_FOR_APPROVAL.getName()+
			" WHERE " + DbSqlColumns.LOT_ID.getName() + " = ?";	
	
	public final String update_existing_rate =
			"UPDATE " + SqlTables.RATES.getName() +
			" SET " + SqlColumns.Rates.PRE_ORDERED + " = ?, " +
			SqlColumns.Rates.OCCASIONAL + " = ?, " +
			SqlColumns.Rates.SUBSCRIPTION + " = ?, " +
			SqlColumns.Rates.SUBSCRIPTION_MULTIPLE_CARS + " = ? " +
			"WHERE " + SqlColumns.Rates.LOT_ID + " = ?";
}
