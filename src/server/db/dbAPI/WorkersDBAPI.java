package server.db.dbAPI;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import server.db.DBConnection;
import server.db.DBConnection.sqlTypeKind;
import server.db.queries.WorkersQueries;

// TODO: Auto-generated Javadoc
/**
 * The Class WorkersDBAPI.
 */
public class WorkersDBAPI extends DBAPI{

	/** The instance. */
	private static volatile WorkersDBAPI instance;
	
	/** The workers queries inst. */
	//	private static Object mutex = new Object();
	private WorkersQueries workersQueriesInst;


	/**
	 * Instantiates a new workers DBAPI.
	 */
	private WorkersDBAPI() {
		workersQueriesInst = WorkersQueries.getInstance();
	}

	/**
	 * Gets the single instance of WorkersDBAPI.
	 *
	 * @return single instance of WorkersDBAPI
	 */
	public static WorkersDBAPI getInstance() {
		WorkersDBAPI result = instance;
		if (result == null) {
			synchronized (mutex) {
				result = instance;
				if (result == null)
					instance = result = new WorkersDBAPI();
			}
		}
		return result;
	}


	//TODO: fix this. also to change names. not tested yet.
	/**
	 * Gets the worker by name and password.
	 *
	 * @param workerId the worker id
	 * @param workerPassword the worker password
	 * @param resultList the result list
	 * @return the workers role
	 * @throws SQLException the SQL exception
	 */
	public void getWorkerByNameAndPassword(int workerId, String workerPassword, ArrayList<Map<String, Object>> resultList) throws SQLException {
		Queue<Object> paramsValues = new LinkedList<Object>(); // push all params to q. in order of SQL
		Queue<DBConnection.sqlTypeKind> paramTypes = new LinkedList<DBConnection.sqlTypeKind>(); // push all params to q. in order of SQL
		paramsValues.add(workerId);
		paramTypes.add(DBConnection.sqlTypeKind.INT);
		paramsValues.add(workerPassword);
		paramTypes.add(DBConnection.sqlTypeKind.VARCHAR);
		DBConnection.selectSql(workersQueriesInst.selectWorkerBtNameAndPassword, paramsValues, paramTypes, resultList);
	}


	/**
	 * Select all lots rates.
	 *
	 * @param selectPendingRates  select all pending rates
	 * @param resultList the result list
	 * @throws SQLException the SQL exception
	 */
	public void selectAllLotsRates(boolean selectPendingRates, ArrayList<Map<String, Object>> resultList) throws SQLException {
		if (selectPendingRates)
			DBConnection.selectSql(workersQueriesInst.select_all_pending_lots_rates, null, null, resultList);
		else
			DBConnection.selectSql(workersQueriesInst.select_all_lots_rates, null, null, resultList);
	}

	/**
	 * Insert rates of lot id.
	 *
	 * @param insertIntoPending TRUE is this lot rates have not been approved yet.
	 * @param lotId the lot id
	 * @param oneTimeParking the one time parking
	 * @param order the order
	 * @param subscriptionFull the subscription full
	 * @param subscriptionOccasional the subscription occasional
	 * @throws SQLException the SQL exception
	 */
	public void insertRatesOfLotId(boolean insertIntoPending, int lotId, double oneTimeParking, double order, double subscriptionFull, double subscriptionOccasional) throws SQLException {
		Queue<Object> paramsValues = new LinkedList<Object>(); // push all params to q. in order of SQL
		Queue<DBConnection.sqlTypeKind> paramTypes = new LinkedList<DBConnection.sqlTypeKind>(); // push all params to q. in order of SQL

		paramsValues.add(lotId);
		paramTypes.add(sqlTypeKind.INT);
		paramsValues.add(subscriptionOccasional);
		paramTypes.add(sqlTypeKind.DOUBLE);
		paramsValues.add(subscriptionFull);
		paramTypes.add(sqlTypeKind.DOUBLE);		
		paramsValues.add(oneTimeParking);
		paramTypes.add(sqlTypeKind.DOUBLE);
		paramsValues.add(order);
		paramTypes.add(sqlTypeKind.DOUBLE);

		if (insertIntoPending)
			DBConnection.updateSql(workersQueriesInst.insert_into_pending_rates_of_lot_id, paramsValues, paramTypes);
		else
			DBConnection.updateSql(workersQueriesInst.insert_rates_of_lot_id, paramsValues, paramTypes);

	}
	
	
	
	
	
	


}

