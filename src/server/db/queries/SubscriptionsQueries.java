package server.db.queries;

import server.db.DBConstants.sqlColumns;
import server.db.DBConstants.sqlTables;

public class SubscriptionsQueries {

	public static String insert_subscription =
			"INSERT INTO " + sqlTables.SUBSCRIPTIONS.getName() + 
			" VALUES (?, ?, ?, ?, ?, ?, ?)";/*subscription_id, customer_id, car_id,
			 								shigrati_or_full, already_came_today, lot_id, expired_date*/


public static String update_subscription_expired_date = 
			" UPDATE  " +sqlTables.SUBSCRIPTIONS.getName() + " SET "+ sqlColumns.EXPIRED_DATE.getName() + " = ? " +
			"   WHERE  " + sqlColumns.SUBSCRIPTION_ID.getName() + " = ?";


public static String select_subscription_details_by_id =
		"SELECT * " +
		" FROM " + sqlTables.SUBSCRIPTIONS.getName() +
		"   WHERE  " + sqlColumns.SUBSCRIPTION_ID.getName() + " = ?";

public static String select_all_active_subscriptions = 
		"SELECT * " +
		" FROM " + sqlTables.SUBSCRIPTIONS.getName();
//public static String activate_subscription_already_expired = "INSERT INTO subscriptions_active " +
//"SELECT *  FROM subscriptions_not_active " +
//"  WHERE  subscription_id = ?";



//public static String activate_subscription = " UPDATE  subscriptions " +
//"SET expired_date = ?" +
//"  WHERE  subscription_id = ?";
}
