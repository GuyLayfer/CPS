package server.db.queries;

import server.db.DBConstants.SqlColumns;
import server.db.DBConstants.SqlTables;

public class SubscriptionsQueries {

	private static volatile SubscriptionsQueries instance;
	private static Object mutex = new Object();

	private SubscriptionsQueries() {
	}

	public static SubscriptionsQueries getInstance() {
		SubscriptionsQueries result = instance;
		if (result == null) {
			synchronized (mutex) {
				result = instance;
				if (result == null)
					instance = result = new SubscriptionsQueries();
			}
		}
		return result;
	}


	public final String select_subscriptioin_id_by_car_id = 
			"SELECT " + SqlColumns.CAR_ID.getName() + 
			" FROM " + SqlTables.SUBSCRIPTIONS.getName() + 
			"  WHERE " + SqlColumns.SUBSCRIPTION_ID.getName() + " = ? ";
	
//	TODO: change the name of the query
	public final String select_counts_of_cars_of_one_subscription_grouped_by_subs_id = 
			"SELECT " +
			SqlColumns.SUBSCRIPTION_ID.getName() +
			", count(" + SqlColumns.CAR_ID.getName() + ") " +
			" FROM " + SqlTables.CARS.getName() +
			" GROUP BY " + SqlColumns.SUBSCRIPTION_ID.getName() +
			" having (count(" + SqlColumns.CAR_ID.getName() + ") > 1)" ;

	public final String insert_subscription =
			"INSERT INTO " + SqlTables.SUBSCRIPTIONS.getName() + 
			" VALUES (?, ?, ?, ?, ?, ?, ?)";/*subscription_id, customer_id, car_id,
			 								shigrati_or_full, already_came_today, lot_id, expired_date*/


	public final String update_subscription_expired_date = 
			" UPDATE  " +SqlTables.SUBSCRIPTIONS.getName() + " SET "+ SqlColumns.EXPIRED_DATE.getName() + " = ? " +
					"   WHERE  " + SqlColumns.SUBSCRIPTION_ID.getName() + " = ?";


	public final String select_subscription_details_by_id =
			"SELECT * " +
					" FROM " + SqlTables.SUBSCRIPTIONS.getName() +
					"   WHERE  " + SqlColumns.SUBSCRIPTION_ID.getName() + " = ?";

	public final String select_all_active_subscriptions = 
			"SELECT * " +
					" FROM " + SqlTables.SUBSCRIPTIONS.getName();
	//public final String activate_subscription_already_expired = "INSERT INTO subscriptions_active " +
	//"SELECT *  FROM subscriptions_not_active " +
	//"  WHERE  subscription_id = ?";



	//public final String activate_subscription = " UPDATE  subscriptions " +
	//"SET expired_date = ?" +
	//"  WHERE  subscription_id = ?";
}
