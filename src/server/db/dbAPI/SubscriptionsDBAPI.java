package server.db.dbAPI;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

import server.db.DBConnection;
import server.db.DBConnection.sqlTypeKind;
import server.db.queries.SubscriptionsQueries;

public class SubscriptionsDBAPI extends DBAPI{

	

	public static void acvivateSubscriptionBeforeExpired(int subscriptionId, Date newExpiredDate, ArrayList<Map<String, Object>> resultList) throws SQLException {
		Queue<Object> params = new LinkedList<Object>(); // push all params to paramsValues. in order of SQL
		Queue<DBConnection.sqlTypeKind> paramTypes = new LinkedList<DBConnection.sqlTypeKind>(); // push all params to paramsValues. in order of SQL
		params.add(subscriptionId);
		paramTypes.add(DBConnection.sqlTypeKind.INT);
		params.add(newExpiredDate);
		paramTypes.add(DBConnection.sqlTypeKind.TIMESTAMP);
		DBConnection.updateSql(SubscriptionsQueries.update_subscription_expired_date, params, paramTypes);
	}
	
//	public static void acvivateSubscriptionAlreadyExpired(int subscriptionId, Date newExpiredDate, ArrayList<Map<String, Object>> resultList) throws SQLException {
//		Queue<Object> params = new LinkedList<Object>(); // push all params to paramsValues. in order of SQL
//		Queue<DBConnection.sqlTypeKind> paramTypes = new LinkedList<DBConnection.sqlTypeKind>(); // push all params to paramsValues. in order of SQL
//		params.add(subscriptionId);
//		paramTypes.add(sqlTypeKind.INT);
//		DBConnection.updateSql(QueriesSubscriptions.activate_subscription_already_expired, params, paramTypes);
//		DBConnection.updateSql(QueriesSubscriptions.delete_subscription_from_not_active, params, paramTypes);
//		params.clear();
//		paramTypes.clear();
//		params.add(newExpiredDate);
//		paramTypes.add(sqlTypeKind.DATE);
//		params.add(subscriptionId);
//		paramTypes.add(sqlTypeKind.INT);
//		DBConnection.updateSql(QueriesSubscriptions.update_expired_date, params, paramTypes);
//	}
	
	public static void selectSubscriptionDetails (int subscriptionId, ArrayList<Map<String, Object>> rs) throws SQLException {
		Queue<Object> params = new LinkedList<Object>(); // push all params to paramsValues. in order of SQL
		Queue<DBConnection.sqlTypeKind> paramTypes = new LinkedList<DBConnection.sqlTypeKind>(); // push all params to paramsValues. in order of SQL
		params.add(subscriptionId);
		paramTypes.add(sqlTypeKind.INT);
		
		DBConnection.selectSql(SubscriptionsQueries.select_subscription_details_by_id, params, paramTypes, rs);
	}
	
	
	
}
