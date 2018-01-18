package server.db.dbAPI;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import server.db.DBConnection;
import server.db.DBConnection.sqlTypeKind;
import server.db.DBConstants.TrueFalse;
import server.db.queries.SubscriptionsQueries;

// TODO: Auto-generated Javadoc
/**
 * The Class SubscriptionsDBAPI.
 */
public class SubscriptionsDBAPI extends DBAPI{

	//	private static Object mutex = new Object();
	private static volatile SubscriptionsDBAPI instance;
	private SubscriptionsQueries subscriptionsQueriesInst;


	private SubscriptionsDBAPI() {
		subscriptionsQueriesInst = SubscriptionsQueries.getInstance();
	}

	public static SubscriptionsDBAPI getInstance() {
		SubscriptionsDBAPI result = instance;
		if (result == null) {
			synchronized (mutex) {
				result = instance;
				if (result == null)
					instance = result = new SubscriptionsDBAPI();
			}
		}
		return result;
	}


	/**
	 * Gets the number of subscriptions has more than one cars.
	 *
	 * @return the number of subscriptions has more than one car.
	 * @throws SQLException the SQL exception
	 */
	public  int getNumberOfSubscriptionsHasMoreThanOneCar() throws SQLException {
		ArrayList<Map<String, Object>> rs = new ArrayList<Map<String, Object>>();
		selectNumberOfCarsOfOneSubscriptionGroupedBySubscriptionId(rs);
		return rs.size();
	}

	/**
	 * Checks if is subscription active.
	 * compares the expired date entity and today.
	 * @param subscriptionId the subscription id
	 * @return true, if is subscription active
	 * @throws SQLException the SQL exception
	 */
	public boolean isSubscriptionActive(int subscriptionId) throws SQLException {
		ArrayList<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
		selectSubscriptionDetails(subscriptionId, resultList);
		Map<String, Object> map = (Map<String, Object>) resultList.get(0);
		Date expiredDate = (Date)( map.get("expired_date"));
		long  expiredDateInMillis = expiredDate.getTime();
		if (expiredDateInMillis > System.currentTimeMillis()) {
			return true;			
		}
		else
			return false;
	}


	// 	TODO: test
	/**
	 * Update subscription expired date.
	 *
	 * @param subscriptionId the subscription id
	 * @param newExpiredDate the new expired date
	 * @throws SQLException the SQL exception
	 */
	public void updateSubscriptionExpiredDate(int subscriptionId, Date newExpiredDate) throws SQLException {
		Queue<Object> params = new LinkedList<Object>(); // push all params to paramsValues. in order of SQL
		Queue<DBConnection.sqlTypeKind> paramTypes = new LinkedList<DBConnection.sqlTypeKind>(); // push all params to paramsValues. in order of SQL
		params.add(subscriptionId);
		paramTypes.add(DBConnection.sqlTypeKind.INT);
		params.add(newExpiredDate);
		paramTypes.add(DBConnection.sqlTypeKind.TIMESTAMP);
		DBConnection.updateSql(subscriptionsQueriesInst.update_subscription_expired_date, params, paramTypes);
	}


	//	TODO: test
	/**
	 * Select subscription details.
	 *
	 * @param subscriptionId the subscription id
	 * @param rs the arrayList contains the details of this subscriptionId.
	 * @throws SQLException the SQL exception
	 */
	public void selectSubscriptionDetails (int subscriptionId, ArrayList<Map<String, Object>> rs) throws SQLException {
		Queue<Object> params = new LinkedList<Object>(); // push all params to paramsValues. in order of SQL
		Queue<DBConnection.sqlTypeKind> paramTypes = new LinkedList<DBConnection.sqlTypeKind>(); // push all params to paramsValues. in order of SQL
		params.add(subscriptionId);
		paramTypes.add(sqlTypeKind.INT);

		DBConnection.selectSql(subscriptionsQueriesInst.select_subscription_details_by_id, params, paramTypes, rs);
	}

	//	TODO: change the name of the function
	/**
	 * Select number of cars of one subscription grouped by subscription id.
	 *
	 * @param resultList the result list contains: the number of cars owned by each subscription.
	 * @throws SQLException the SQL exception
	 */
	public  void selectNumberOfCarsOfOneSubscriptionGroupedBySubscriptionId(ArrayList<Map<String, Object>> resultList) throws SQLException {
		DBConnection.selectSql(subscriptionsQueriesInst.select_counts_of_cars_of_one_subscription_grouped_by_subs_id, null, null, resultList);
	}

//	/**
//	 * Gets the subscription id by car id.
//	 *
//	 * @param subscription_id the subscription id
//	 * @param resultList the result list
//	 * @return the subscription id by car id
//	 * @throws SQLException the SQL exception
//	 */
//	public void getSubscriptionIdByCarId(int subscription_id, ArrayList<Map<String, Object>> resultList) throws SQLException {
//		/*return if customer has subscription*/	
//		Queue<Object> paramsValues = new LinkedList<Object>(); // push all params to q. in order of SQL
//		Queue<DBConnection.sqlTypeKind> paramTypes = new LinkedList<DBConnection.sqlTypeKind>(); // push all params to q. in order of SQL
//		paramsValues.add(subscription_id);
//		paramTypes.add(DBConnection.sqlTypeKind.INT);
//		DBConnection.selectSql(subscriptionsQueriesInst.select_subscriptioin_id_by_car_id, paramsValues, paramTypes, resultList);
//	}

	/**
	 * Insert new subscription.
	 *
	 * @param customerId the customer id
	 * @param listOfCarsForThisSubscriptioin the list of cars for this subscription
	 * @return the subscription ID.
	 * @throws SQLException 
	 */
	//TODO: implement
	public int insertNewSubscription(int customerId, int lotId, TrueFalse occaional, Date expiredDate, ArrayList<String> listOfCarsForThisSubscription) throws SQLException {
		
		Queue<Object> params = new LinkedList<Object>(); // push all params to paramsValues. in order of SQL
		Queue<DBConnection.sqlTypeKind> paramTypes = new LinkedList<DBConnection.sqlTypeKind>(); // push all params to paramsValues. in order of SQL
		params.add(customerId);
		paramTypes.add(DBConnection.sqlTypeKind.INT);
		params.add(lotId);
		paramTypes.add(DBConnection.sqlTypeKind.INT);
		params.add(occaional.getValue());
		paramTypes.add(DBConnection.sqlTypeKind.VARCHAR);
		params.add(expiredDate);
		paramTypes.add(DBConnection.sqlTypeKind.TIMESTAMP);
		int subscriptionId = DBConnection.updateSql(subscriptionsQueriesInst.insert_subscription, params, paramTypes);
		//insert each car in this subscription to cars 
		Iterator<String> iterator = listOfCarsForThisSubscription.iterator();
		while (iterator.hasNext()) {
			String curCarId = (String) iterator.next();
			insertCarToCarsTable(curCarId, customerId, subscriptionId);
		}
		
		return subscriptionId;
	}
	
	public void insertCarToCarsTable(String carId, int customerId, int subscriptionId) throws SQLException {
		Queue<Object> params = new LinkedList<Object>(); // push all params to paramsValues. in order of SQL
		Queue<DBConnection.sqlTypeKind> types = new LinkedList<DBConnection.sqlTypeKind>(); // push all params to paramsValues. in order of SQL

		params.add(subscriptionId);
		types.add(DBConnection.sqlTypeKind.INT);
		params.add(customerId);
		types.add(DBConnection.sqlTypeKind.INT);
		params.add(carId);
		types.add(DBConnection.sqlTypeKind.VARCHAR);
		
		DBConnection.updateSql(subscriptionsQueriesInst.insert_car_to_cars, params, types);
	}
	
	public void selectCarsOfSubscriptionId(int subscriptionId,ArrayList<Map<String, Object>> rs) throws SQLException {
		Queue<Object> params = new LinkedList<Object>(); // push all params to paramsValues. in order of SQL
		Queue<DBConnection.sqlTypeKind> types = new LinkedList<DBConnection.sqlTypeKind>(); // push all params to paramsValues. in order of SQL
		params.add(subscriptionId);
		types.add(DBConnection.sqlTypeKind.INT);
		
		DBConnection.selectSql(subscriptionsQueriesInst.select_cars_of_subscription_id, params, types, rs);
		
	}






}
