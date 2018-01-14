package server.db.queries;

import server.db.DBConstants.DbSqlColumns;
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


//	public final String select_subscriptioin_id_by_car_id = 
//			"SELECT " + DbSqlColumns.CAR_ID.getName() + 
//			" FROM " + SqlTables.SUBSCRIPTIONS.getName() + 
//			"  WHERE " + DbSqlColumns.SUBSCRIPTION_ID.getName() + " = ? ";
	
//	TODO: change the name of the query
	public final String select_counts_of_cars_of_one_subscription_grouped_by_subs_id = 
			"SELECT " +
			DbSqlColumns.SUBSCRIPTION_ID.getName() +
			", count(" + DbSqlColumns.CAR_ID.getName() + ") " +
			" FROM " + SqlTables.CARS.getName() +
			" GROUP BY " + DbSqlColumns.SUBSCRIPTION_ID.getName() +
			" having (count(" + DbSqlColumns.CAR_ID.getName() + ") > 1)" ;

	public final String select_cars_of_subscription_id = 
			"SELECT " + DbSqlColumns.CAR_ID.getName() + 
			" FROM " + SqlTables.CARS.getName() + 
			"  WHERE " + DbSqlColumns.SUBSCRIPTION_ID.getName() + " = ? ";
	
	
	
	public final String insert_subscription =
			"INSERT INTO " + SqlTables.SUBSCRIPTIONS.getName() + 
			"(" +
			 DbSqlColumns.ACCOUNT_ID.getName() + ", " +
			 DbSqlColumns.LOT_ID.getName() + ", " + 
			 DbSqlColumns.OCCASIONAL_OR_FULL.getName() + ", " +
			 DbSqlColumns.EXPIRED_DATE.getName() +
			 " ) " +
			" VALUES (?, ?, ?, ?)";/*subscription_id auto incremented, customer_id, lot_id, 
				shigrati_or_full, expired_date*/

	
	public final String insert_car_to_cars = 
			"INSERT INTO " + SqlTables.CARS.getName() +
			" values (?,?,? )" ; 

	public final String update_subscription_expired_date = 
			" UPDATE  " +SqlTables.SUBSCRIPTIONS.getName() + " SET "+ DbSqlColumns.EXPIRED_DATE.getName() + " = ? " +
					"   WHERE  " + DbSqlColumns.SUBSCRIPTION_ID.getName() + " = ?";


	public final String select_subscription_details_by_id =
			"SELECT * " +
					" FROM " + SqlTables.SUBSCRIPTIONS.getName() +
					"   WHERE  " + DbSqlColumns.SUBSCRIPTION_ID.getName() + " = ?";

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
