package server.db.dbAPI;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import server.db.DBConnection;
import server.db.DBConstants.DbSqlColumns;
import server.db.DBConstants.SqlTables;
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
	 * Select pending rates details.
	 *
	 * @param parkingLotId  the lot ID for the pending rate
	 * @param resultList the result list
	 * @throws SQLException the SQL exception
	 */
	public void selectPendingRatesDetails(int parkingLotId, ArrayList<Map<String, Object>> resultList) throws SQLException {
		Queue<Object> params = new LinkedList<Object>();
		params.add(parkingLotId);
		DBConnection.selectSql(workersQueriesInst.select_pending_rates_details, params, resultList);
	}
	
	/**
	 * Select rates details.
	 *
	 * @param parkingLotId  the lot ID for the pending rate
	 * @param resultList the result list
	 * @throws SQLException the SQL exception
	 */
	public void selectRatesDetails(int parkingLotId, ArrayList<Map<String, Object>> resultList) throws SQLException {
		Queue<Object> params = new LinkedList<Object>();
		params.add(parkingLotId);
		DBConnection.selectSql(workersQueriesInst.select_rates_details, params, resultList);
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
	 * @param preOrdered the one time parking
	 * @param occasional the order
	 * @param subscriptionFull the subscription full
	 * @param rutineSubscription the subscription occasional
	 * @throws SQLException the SQL exception
	 */
	public void insertRatesOfLotId(boolean insertIntoPending, int lotId, double preOrdered, double occasional, double subscriptionFull,
									double rutineSubscription, double subscriptionMultipleCarsPrice) throws SQLException {
		Queue<Object> paramsValues = new LinkedList<Object>();
		
		if (insertIntoPending) {
			paramsValues.add(lotId);
			paramsValues.add(rutineSubscription);
			paramsValues.add(subscriptionFull);
			paramsValues.add(preOrdered);
			paramsValues.add(occasional);
			paramsValues.add(subscriptionMultipleCarsPrice);
			
			DBConnection.updateSql(workersQueriesInst.insert_into_pending_rates_of_lot_id, paramsValues);
		} else {
			paramsValues.add(lotId);
			paramsValues.add(rutineSubscription);
			paramsValues.add(preOrdered);
			paramsValues.add(occasional);
			paramsValues.add(subscriptionMultipleCarsPrice);
						
			DBConnection.updateSql(workersQueriesInst.insert_rates_of_lot_id, paramsValues);
		}
	}
	
	public void updateFullSubscriptionRate(double subscriptionFull) throws SQLException {
		Queue<Object> paramsValues = new LinkedList<Object>();
		paramsValues.add(subscriptionFull);
		DBConnection.updateSql(workersQueriesInst.change_full_subscription_rate, paramsValues);
	}
	
	public void updateExistingRates(int lotId, double preOrdered, double occasional,
			double rutineSubscription, double subscriptionMultipleCarsPrice) throws SQLException {
		Queue<Object> paramsValues = new LinkedList<Object>();
		paramsValues.add(preOrdered);
		paramsValues.add(occasional);
		paramsValues.add(rutineSubscription);
		paramsValues.add(subscriptionMultipleCarsPrice);
		paramsValues.add(lotId);
		DBConnection.updateSql(workersQueriesInst.update_existing_rate, paramsValues);
	}
	
	public void deletePendingRate(int lotId) throws SQLException {
		Queue<Object> paramsValues = new LinkedList<Object>();
		paramsValues.add(lotId);
		DBConnection.updateSql(workersQueriesInst.delete_rate_from_pending_rate, paramsValues);
	}
}

