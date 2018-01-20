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
		paramsValues.add(workerId);
		paramsValues.add(workerPassword);
		DBConnection.selectSql(workersQueriesInst.selectWorkerBtNameAndPassword, paramsValues, resultList);
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
			DBConnection.selectSql(workersQueriesInst.select_all_pending_lots_rates, null, resultList);
		else
			DBConnection.selectSql(workersQueriesInst.select_all_lots_rates, null, resultList);
	}

	
	
	public void selectFullSubscriptionRate(ArrayList<Map<String, Object>> resultList) throws SQLException {
		DBConnection.selectSql(workersQueriesInst.select_full_subscription_rate, null, resultList);
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
	public void insertRatesOfLotId(boolean insertIntoPending, int lotId, double oneTimeParking, double order, double subscriptionFull,
									double subscriptionOccasional, double subscriptionMultipleCarsPrice) throws SQLException {
		Queue<Object> paramsValues = new LinkedList<Object>(); // push all params to q. in order of SQL
		Queue<Object> paramsValuesApprovedFull = new LinkedList<Object>();
		
		if (insertIntoPending) {
			paramsValues.add(lotId);
			paramsValues.add(subscriptionOccasional);
			paramsValues.add(subscriptionFull);
			paramsValues.add(oneTimeParking);
			paramsValues.add(order);
			paramsValues.add(subscriptionMultipleCarsPrice);
			
			DBConnection.updateSql(workersQueriesInst.insert_into_pending_rates_of_lot_id, paramsValues);
		} else {
			paramsValues.add(lotId);
			paramsValues.add(subscriptionOccasional);
			paramsValues.add(oneTimeParking);
			paramsValues.add(order);
			paramsValues.add(subscriptionMultipleCarsPrice);
			
			paramsValuesApprovedFull.add(subscriptionFull);
			
			DBConnection.updateSql(workersQueriesInst.insert_rates_of_lot_id, paramsValues);
			DBConnection.updateSql(workersQueriesInst.change_full_subscription_rate, paramsValuesApprovedFull);
		}
	}
	
	
	
	
	
	


}

