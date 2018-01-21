package server.db;

import server.db.DBConnection.sqlTypeKind;

// TODO: Auto-generated Javadoc
/**
 * The Class DBConstants.
 */
// These constants must be the same as the column names in the DB 
public class DBConstants {
	
	/**
	 * The Enum DbSqlColumns.
	 */
	public enum DbSqlColumns{
		
		/** The entrance id. */
		ENTRANCE_ID("entrance_id", DBConnection.sqlTypeKind.INT),
		
		/** The leave time. */
		LEAVE_TIME("leave_time", DBConnection.sqlTypeKind.TIMESTAMP),
		
		/** The account id. */
		ACCOUNT_ID("account_id", DBConnection.sqlTypeKind.INT),
		
		/** The email. */
		EMAIL("email", DBConnection.sqlTypeKind.VARCHAR),		
		
		/** The leave prediction. */
		LEAVE_PREDICTION("leave_prediction", DBConnection.sqlTypeKind.TIMESTAMP),
		
		/** The order type. */
		ORDER_TYPE("order_type", DBConnection.sqlTypeKind.VARCHAR), 
 /** The arrive prediction. */
 //enum
		ARRIVE_PREDICTION("arrive_prediction", DBConnection.sqlTypeKind.VARCHAR),
		
		/** The arrive time. */
		ARRIVE_TIME("arrive_time", DBConnection.sqlTypeKind.VARCHAR),
		
		/** The car id. */
		CAR_ID("car_id", DBConnection.sqlTypeKind.INT),
		
		/** The cars num. */
		CARS_NUM("cars_num", DBConnection.sqlTypeKind.INT),
		
		/** The filled reservations. */
		FILLED_RESERVATIONS("filled_reservations", DBConnection.sqlTypeKind.INT),
		
		/** The lot id. */
		LOT_ID("lot_id", DBConnection.sqlTypeKind.INT),
		
		/** The number of columns in this lot. */
		NUMBER_OF_COLUMNS_IN_THIS_LOT("number_of_columns", DBConnection.sqlTypeKind.INT),
		
		/** The role id. */
		ROLE_ID("role_id", DBConnection.sqlTypeKind.VARCHAR), 
 /** The password. */
 //enum
		PASSWORD("password", DBConnection.sqlTypeKind.VARCHAR),
		
		/** The has subscription. */
		HAS_SUBSCRIPTION("has_subscription", DBConnection.sqlTypeKind.VARCHAR), 
 /** The subscription type. */
 //enum
		SUBSCRIPTION_TYPE("subscription_type", DBConnection.sqlTypeKind.VARCHAR), 
 /** The subscription id. */
 //enum
		SUBSCRIPTION_ID("subscription_id", DBConnection.sqlTypeKind.INT),
		
		/** The expired date. */
		EXPIRED_DATE("expired_date", DBConnection.sqlTypeKind.TIMESTAMP),
		
		/** The subscription start time. */
		SUBSCRIPTION_START_TIME("start_date", DBConnection.sqlTypeKind.VARCHAR),
		
		/** The day id. */
		DAY_ID("day_id", DBConnection.sqlTypeKind.DATE),
		
		/** The balance. */
		BALANCE("balance", DBConnection.sqlTypeKind.DOUBLE),
		
		/** The canceled orders. */
		CANCELED_ORDERS("canceled_reservations", DBConnection.sqlTypeKind.INT),
		
		/** The lating per park. */
		LATING_PER_PARK("latings_per_park", DBConnection.sqlTypeKind.INT),
		
		/** The complaint id. */
		COMPLAINT_ID("complaint_id", DBConnection.sqlTypeKind.INT),
		
		/** The complaint datetime. */
		COMPLAINT_DATETIME("date_complaint", DBConnection.sqlTypeKind.TIMESTAMP),
		
		/** The complaint customer service respond. */
		COMPLAINT_CUSTOMER_SERVICE_RESPOND("customer_service_response", DBConnection.sqlTypeKind.VARCHAR), 
 /** The complaint description. */
 //text
		COMPLAINT_DESCRIPTION("complaint_description", DBConnection.sqlTypeKind.VARCHAR),
		
		/** The complaint filled. */
		COMPLAINT_FILLED("filled", DBConnection.sqlTypeKind.VARCHAR), 
 /** The is accepted. */
 //enum
		IS_ACCEPTED("is_accepted", DBConnection.sqlTypeKind.VARCHAR), 
 /** The worker id. */
 //enum
		WORKER_ID("worker_id", DBConnection.sqlTypeKind.INT),
		
		/** The info. */
		INFO("info", DBConnection.sqlTypeKind.TEXT), 
		
		/** The row. */
		ROW("row", DBConnection.sqlTypeKind.INT),
		
		/** The column. */
		COLUMN("coloumn", DBConnection.sqlTypeKind.INT),
		
		/** The floor. */
		FLOOR("floor", DBConnection.sqlTypeKind.INT)
		;
		
		/** The column name. */
		String columnName;
		
		/** The type. */
		sqlTypeKind type;
		
		/**
		 * Instantiates a new db sql columns.
		 *
		 * @param columnName the column name
		 * @param columnType the column type
		 */
		DbSqlColumns(String columnName, sqlTypeKind columnType){
			 this.columnName = columnName; 
			 this.type = columnType;
		}
		
		/**
		 * Gets the name.
		 *
		 * @return the name
		 */
		public String getName(){
			return columnName;
		}
		
		/**
		 * Gets the type.
		 *
		 * @return the type
		 */
		public sqlTypeKind getType(){
			return type;
		}
	};
	
	/**
	 * The Enum SqlTables.
	 */
	public enum SqlTables {
		
		/** The complaints. */
		COMPLAINTS("complaints"),
		
		/** The current cars planed being in parking. */
		CURRENT_CARS_PLANED_BEING_IN_PARKING("current_cars_planed_being_in_parking"),
		
		/** The current cars planed being in parking log. */
		CURRENT_CARS_PLANED_BEING_IN_PARKING_LOG("current_cars_planed_being_in_parking_log"),
		
		/** The accounts. */
		ACCOUNTS("accounts"),
		
		/** The cars. */
		CARS("cars"),
		
		/** The workers. */
		WORKERS("workers"),
		
		/** The subscriptions. */
		SUBSCRIPTIONS("subscriptions"),
		
		/** The roles. */
		ROLES(""),
		
		/** The rates. */
		RATES("rates"),
		
		/** The full subscription rate. */
		FULL_SUBSCRIPTION_RATE("full_subscription_rate"),
		
		/** The rates pending for approval. */
		RATES_PENDING_FOR_APPROVAL("rates_pending"),
		
		/** The parking map. */
		PARKING_MAP("parking_map"),
		
		/** The permissions. */
		PERMISSIONS(""),
		
		/** The daily stats. */
		DAILY_STATS("daily_stats"),
		
		/** The lot dimensions. */
		LOT_DIMENSIONS("lot_num_of_columns"),
		
		/** The weekly stats. */
		WEEKLY_STATS("weekly_stats"),
		
		/** The server info. */
		SERVER_INFO("server_info"),
		
		/** The out of orders parkings. */
		OUT_OF_ORDERS_PARKINGS("out_of_orders_parkings");
		
		/** The table name. */
		private String tableName;
		
		/**
		 * Instantiates a new sql tables.
		 *
		 * @param tableName the table name
		 */
		SqlTables(String tableName){
			this.tableName = tableName;
		}
		
		/**
		 * Gets the name.
		 *
		 * @return the name
		 */
		public String getName() {
			return tableName;
		}
		
	};
	

	/**
	 * The Enum ParkingMap.
	 */
	public enum ParkingMap {/** The free. */
FREE("free"), /** The parked. */
 PARKED("parked"), /** The reserved. */
 RESERVED("reserved"), /** The broken. */
 BROKEN("broken"), /** The maintenence. */
 MAINTENENCE("maintenence");
		
		/** The value. */
		String value;
		
		/**
		 * Instantiates a new parking map.
		 *
		 * @param value the value
		 */
		ParkingMap(String value) {
		      this.value = value;
		}
	    
    	/**
    	 * Gets the value.
    	 *
    	 * @return the value
    	 */
    	public String getValue() {
		      return value;
	    }
	    
    	/**
    	 * Convert string to parking map enum.
    	 *
    	 * @param s the s
    	 * @return the parking map
    	 */
    	public static ParkingMap convertStringToParkingMapEnum(String s) {
	        switch (s) {
	         case "free":
	 	    	return FREE;
	         case "parked":
	        	 return PARKED;
	         case "reserved":
	        	 return RESERVED;
	         case "broken":
	             return BROKEN;
	         case "maintenence":
	        	 return MAINTENENCE;
	         default:
	             throw new IllegalArgumentException("Invalid value: " + s);
	        }
	    }
	};
	
	/**
	 * The Enum OrderType.
	 */
	public enum OrderType {/** The one time. */
ONE_TIME("oneTime"), /** The order. */
 ORDER("order"), /** The subscription. */
 SUBSCRIPTION("subscriptionRegular"), /** The subscription full. */
 SUBSCRIPTION_FULL("subscriptionFull");

		/** The value. */
		String value;
		
		/**
		 * Instantiates a new order type.
		 *
		 * @param value the value
		 */
		OrderType(String value) {
		      this.value = value;
	   }
		
		/**
		 * Gets the value.
		 *
		 * @return the value
		 */
		public String getValue() {
		      return value;
		}
	};
	
	
	
	/**
	 * The Enum SubscriptionType.
	 */
	public enum SubscriptionType {/** The routine multiple cars. */
ROUTINE_MULTIPLE_CARS("routine_muliple_cars"), /** The routine. */
 ROUTINE("routine"), /** The full. */
 FULL("full") ;
		
		/** The value. */
		String value;
		
		/**
		 * Instantiates a new subscription type.
		 *
		 * @param value the value
		 */
		SubscriptionType(String value){
			this.value = value;
		}
		
		/**
		 * Gets the value.
		 *
		 * @return the value
		 */
		public String getValue() {
		      return value;
		}
	}
	
	
	/**
	 * The Enum TrueFalse.
	 */
	public enum TrueFalse {/** The true. */
TRUE("true"), /** The false. */
 FALSE("false") ;
		
		/** The value. */
		String value;
		
		/**
		 * Instantiates a new true false.
		 *
		 * @param value the value
		 */
		TrueFalse(String value){
			this.value = value;
		}
		
		/**
		 * Gets the value.
		 *
		 * @return the value
		 */
		public String getValue() {
		      return value;
		}
	}
	
	// current_cars_planed_being_in_parking columns
//	final public static String ENTRANCE_ID = "entrance_id";
//	final public static String LEAVE_TIME = "leave_time";
//	final public static String ACCOUNT_ID = "account_id";
//	final public static String EMAIL = "email";
//	final public static String LEAVE_PREDICTION = "leave_prediction";
//	final public static String LOT_ID = "lot_id";
//	final public static String KIND = "kind";
//	final public static String ARRIVE_PREDICTION = "arrive_prediction";
//	final public static String CAR_ID = "car_id";
//	final public static String ARRIVE_TIME = "arrive_time";
//	final public static String FILLED_RESERVATIONS = "filled_reservations";
//	final public static String COMPLAINT_DESCRIPTION = "complaint_description";
//	final public static String COMPLAINT_FILLED = "complaint_filled";
//	final public static String COMPLAINT_CS_RESPONSE = "complaint_customer_service_response";
//	final public static String COMPLAINT_RESOLUTION = "complaint_resolution";
//	final public static String ROLE_ID = "role_id";
//	final public static String PASSWORD = "pwd";

}
