package server.db;

// TODO: Auto-generated Javadoc
/**
 * The Class SqlColumns.
 */
public class SqlColumns {
	
	/**
	 * The Class ParkingTonnage.
	 */
	// current_cars_planed_being_in_parking columns
	public class ParkingTonnage { 
 /** The Constant ENTRANCE_ID. */
 // shorter name for current_cars_planed_being_in_parking
		public static final String ENTRANCE_ID = "entrance_id"; //int(11) PK
		
		/** The Constant CAR_ID. */
		public static final String CAR_ID = "car_id"; //varchar(10)
		
		/** The Constant ACCOUNT_ID. */
		public static final String ACCOUNT_ID = "account_id"; //int(11)
		
		/** The Constant LOT_ID. */
		public static final String LOT_ID = "lot_id"; //int(11)
		
		/** The Constant ORDER_TYPE. */
		public static final String ORDER_TYPE = "order_type"; //enum
		
		/** The Constant ARRIVE_PREDICTION. */
		public static final String ARRIVE_PREDICTION = "arrive_prediction"; //timestamp
		
		/** The Constant LEAVE_PREDICTION. */
		public static final String LEAVE_PREDICTION = "leave_prediction"; //timestamp
		
		/** The Constant ARRIVE_TIME. */
		public static final String ARRIVE_TIME = "arrive_time"; //timestamp
		
		/** The Constant LEAVE_TIME. */
		public static final String LEAVE_TIME = "leave_time"; //timestamp
		
		/** The Constant EMAIL. */
		public static final String EMAIL = "email"; //varchar(45)

	}
	
	/**
	 * The Class Account.
	 */
	public class Account{ /** The Constant ACCOUNT_ID. */
 //currently doesn't has a PK
		public static final String ACCOUNT_ID = "account_id"; //int(11)
		
		/** The Constant EMAIL. */
		public static final String EMAIL = "email"; //varchar(255)
		
		/** The Constant CAR_ID. */
		public static final String CAR_ID = "car_id"; //varchar(10)
		
		/** The Constant BALANCE. */
		public static final String BALANCE = "balance"; //double
		
		/** The Constant HAS_SUBSCRIPTION. */
		public static final String HAS_SUBSCRIPTION = "has_subscription"; //enum - true/false
	}
	
	/**
	 * The Class Cars.
	 */
	public class Cars{
		
		/** The Constant SUBSCRIPTION_ID. */
		public static final String SUBSCRIPTION_ID = "subscription_id"; //int(11)
		
		/** The Constant ACCOUNT_ID. */
		public static final String ACCOUNT_ID = "account_id"; //int(11)
		
		/** The Constant CAR_ID. */
		public static final String CAR_ID = "car_id"; //varchar(10) PK
	}
	
	/**
	 * The Class Complaints.
	 */
	public class Complaints{
		
		/** The Constant COMPLAINT_ID. */
		public static final String COMPLAINT_ID = "complaint_id"; //int(11) PK
		
		/** The Constant ACCOUNT_ID. */
		public static final String ACCOUNT_ID = "account_id"; //int(11)
		
		/** The Constant COMPLAINT_DESCRIPTION. */
		public static final String COMPLAINT_DESCRIPTION = "complaint_description"; //text
		
		/** The Constant COMPLAINT_CUSTOMER_SERVICE_RESPOND. */
		public static final String COMPLAINT_CUSTOMER_SERVICE_RESPOND = "customer_service_response"; //text
		
		/** The Constant COMPLAINT_FILLED. */
		public static final String COMPLAINT_FILLED = "filled"; //enum - true/false
		
		/** The Constant COMPLAINT_DATETIME. */
		public static final String COMPLAINT_DATETIME = "date_complaint"; //timestamp
		
		/** The Constant IS_ACCEPTED. */
		public static final String IS_ACCEPTED = "is_accepted"; //enum - true/false
	}
	
	/**
	 * The Class DailyStats.
	 */
	public class DailyStats{
		
		/** The Constant DAY_ID. */
		public static final String DAY_ID = "day_id"; //date PK
		
		/** The Constant LOT_ID. */
		public static final String LOT_ID = "lot_id"; // int(11) PK
		
		/** The Constant FILLED_RESERVATIONS. */
		public static final String FILLED_RESERVATIONS = "filled_reservations"; //int(11)
		
		/** The Constant CANCELED_ORDERS. */
		public static final String CANCELED_ORDERS = "canceled_reservations"; //int(11)
		
		/** The Constant LATING_PER_PARK. */
		public static final String LATING_PER_PARK = "latings_per_park"; //int(11)
	}
	
	/**
	 * The Class LotNumOfColumns.
	 */
	public class LotNumOfColumns{
		
		/** The Constant LOT_ID. */
		public static final String LOT_ID = "lot_id"; //int(11) PK
		
		/** The Constant NUM_OF_COLS. */
		public static final String NUM_OF_COLS = "num_of_cols"; //int(11)
	}
	
	/**
	 * The Class Rates.
	 */
	public class Rates{
		
		/** The Constant LOT_ID. */
		public static final String LOT_ID = "lot_id"; //int(11) PK
		
		/** The Constant SUBSCRIPTION. */
		public static final String SUBSCRIPTION = "subscription_regular"; //double
		
		/** The Constant SUBSCRIPTION_FULL. */
		public static final String SUBSCRIPTION_FULL = "subscription_full"; //double
		
		/** The Constant OCCASIONAL. */
		public static final String OCCASIONAL = "order_regular"; //double
		
		/** The Constant PRE_ORDERED. */
		public static final String PRE_ORDERED = "order_one_time"; //double
		
		/** The Constant SUBSCRIPTION_MULTIPLE_CARS. */
		public static final String SUBSCRIPTION_MULTIPLE_CARS= "subscription_multiple_cars";
	}
	
	/**
	 * The Class FullSubscriptionRate.
	 */
	public class FullSubscriptionRate{
		
		/** The Constant KEY. */
		public static final String KEY = "key"; //int(11) PK
		
		/** The Constant RATE. */
		public static final String RATE = "rate"; //double
	}
	
	/**
	 * The Class Subscriptions.
	 */
	public class Subscriptions{
		
		/** The Constant SUBSCRIPTION_ID. */
		public static final String SUBSCRIPTION_ID = "subscription_id"; //int(11) PK
		
		/** The Constant ACCOUNT_ID. */
		public static final String ACCOUNT_ID = "account_id"; //int(11)
		
		/** The Constant CAR_ID. */
		public static final String CAR_ID = "car_id"; //varchar(10)
		
		/** The Constant CARS_NUM. */
		public static final String CARS_NUM = "cars_num"; //int
		
		/** The Constant OCCASIONAL. */
		public static final String OCCASIONAL = "occasional"; //enum - true/false
		
		/** The Constant CAME_IN_TODAY. */
		public static final String CAME_IN_TODAY = "came_in_today"; //enum - true/false
		
		/** The Constant LOT_ID. */
		public static final String LOT_ID = "lot_id"; //int(11)
		
		/** The Constant EXPIRED_DATE. */
		public static final String EXPIRED_DATE = "expired_date"; // timestamp
		
		/** The Constant START_DATE. */
		public static final String START_DATE = "start_date"; // timestamp
		
		/** The Constant SUBSCRIPTION_TYPE. */
		public static final String SUBSCRIPTION_TYPE = "subscription_type"; //enum - ROUTINE_MULTIPLE_CARS/ROUTINE/FULL
		
		/** The Constant HAS_SUBSCRIPTION. */
		public static final String HAS_SUBSCRIPTION = "has_subscription"; //enum - true/false
	}
	
	/**
	 * The Class Workers.
	 */
	public class Workers{
		
		/** The Constant WORKER_ID. */
		public static final String WORKER_ID = "worker_id"; //int(11) PK
		
		/** The Constant PASSWORD. */
		public static final String PASSWORD = "password"; //varchar(255)
		
		/** The Constant ROLE_TYPE. */
		public static final String ROLE_TYPE = "role_type"; //enum
		
		/** The Constant CUSTOMER_SERVICE_RESPONSE. */
		public static final String CUSTOMER_SERVICE_RESPONSE = "customer_service_response"; //text
		
		/** The Constant LOT_ID. */
		public static final String LOT_ID = "lot_id"; // int(11)
	}
	
	/**
	 * The Class outOfOrderParkings.
	 */
	public class outOfOrderParkings{
		
		/** The Constant LOT_ID. */
		public static final String LOT_ID = "lot_id";
		
		/** The Constant ARRIVE_TIME. */
		public static final String ARRIVE_TIME = "arrive_time";
		
		/** The Constant LEAVE_TIME. */
		public static final String LEAVE_TIME = "leave_time";
		
		/** The Constant ROW. */
		public static final String ROW = "row";
		
		/** The Constant COLOUMN. */
		public static final String COLOUMN = "coloumn";
		
		/** The Constant FLOOR. */
		public static final String FLOOR = "floor";
	}
}
