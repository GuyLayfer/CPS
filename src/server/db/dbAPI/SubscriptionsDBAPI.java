package server.db.dbAPI;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

import server.db.DBConnection;
import server.db.DBConnection.sqlTypeKind;
import server.db.queries.RegularQueries;
import server.db.queries.ReportsQueries;
import server.db.queries.SubscriptionsQueries;

// TODO: Auto-generated Javadoc
/**
 * The Class SubscriptionsDBAPI.
 */
public class SubscriptionsDBAPI extends DBAPI{


	// 	TODO: test
	/**
	 * Update subscription expired date.
	 *
	 * @param subscriptionId the subscription id
	 * @param newExpiredDate the new expired date
	 * @throws SQLException the SQL exception
	 */
	public static void updateSubscriptionExpiredDate(int subscriptionId, Date newExpiredDate) throws SQLException {
		Queue<Object> params = new LinkedList<Object>(); // push all params to paramsValues. in order of SQL
		Queue<DBConnection.sqlTypeKind> paramTypes = new LinkedList<DBConnection.sqlTypeKind>(); // push all params to paramsValues. in order of SQL
		params.add(subscriptionId);
		paramTypes.add(DBConnection.sqlTypeKind.INT);
		params.add(newExpiredDate);
		paramTypes.add(DBConnection.sqlTypeKind.TIMESTAMP);
		DBConnection.updateSql(SubscriptionsQueries.update_subscription_expired_date, params, paramTypes);
	}


	//	TODO: test
	/**
	 * Select subscription details.
	 *
	 * @param subscriptionId the subscription id
	 * @param rs the arrayList contains the details of this subscriptionId.
	 * @throws SQLException the SQL exception
	 */
	public static void selectSubscriptionDetails (int subscriptionId, ArrayList<Map<String, Object>> rs) throws SQLException {
		Queue<Object> params = new LinkedList<Object>(); // push all params to paramsValues. in order of SQL
		Queue<DBConnection.sqlTypeKind> paramTypes = new LinkedList<DBConnection.sqlTypeKind>(); // push all params to paramsValues. in order of SQL
		params.add(subscriptionId);
		paramTypes.add(sqlTypeKind.INT);

		DBConnection.selectSql(SubscriptionsQueries.select_subscription_details_by_id, params, paramTypes, rs);
	}

	/**
	 * Gets the subscription id by car id.
	 *
	 * @param subscription_id the subscription id
	 * @param resultList the result list
	 * @return the subscription id by car id
	 * @throws SQLException the SQL exception
	 */
	public static void getSubscriptionIdByCarId(int subscription_id, ArrayList<Map<String, Object>> resultList) throws SQLException {
		/*return if customer has subscription*/	
		Queue<Object> paramsValues = new LinkedList<Object>(); // push all params to q. in order of SQL
		Queue<DBConnection.sqlTypeKind> paramTypes = new LinkedList<DBConnection.sqlTypeKind>(); // push all params to q. in order of SQL
		paramsValues.add(subscription_id);
		paramTypes.add(DBConnection.sqlTypeKind.INT);
		DBConnection.selectSql(RegularQueries.select_subscriptioin_id_by_car_id, paramsValues, paramTypes, resultList);
	}
	
//	TODO: change the name of the function
	/**
	 * Select number of cars of one subscription grouped by subscription id.
	 *
	 * @param resultList the result list contains: the number of cars owned by each subscription.
	 * @throws SQLException the SQL exception
	 */
	public static void selectNumberOfCarsOfOneSubscriptionGroupedBySubscriptionId(ArrayList<Map<String, Object>> resultList) throws SQLException {
		DBConnection.selectSql(ReportsQueries.select_counts_of_cars_of_one_subscription_grouped_by_subs_id, null, null, resultList);
	}

	
	/**
	 * Gets the number of subscriptions has more than one cars.
	 *
	 * @return the number of subscriptions has more than one car.
	 * @throws SQLException the SQL exception
	 */
	public static int getNumberOfSubscriptionsHasMoreThanOneCar() throws SQLException {
		ArrayList<Map<String, Object>> rs = new ArrayList<Map<String, Object>>();
		selectNumberOfCarsOfOneSubscriptionGroupedBySubscriptionId(rs);
		return rs.size();
	}



}
