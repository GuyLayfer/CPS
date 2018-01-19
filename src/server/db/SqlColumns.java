package server.db;

public class SqlColumns {
	// current_cars_planed_being_in_parking columns
	public class ParkingTonnage { // shorter name for current_cars_planed_being_in_parking
		public static final String ENTRANCE_ID = "entrance_id"; //int(11) PK
		public static final String CAR_ID = "car_id"; //varchar(10)
		public static final String ACCOUNT_ID = "account_id"; //int(11)
		public static final String LOT_ID = "lot_id"; //int(11)
		public static final String ORDER_TYPE = "order_type"; //enum
		public static final String ARRIVE_PREDICTION = "arrive_prediction"; //timestamp
		public static final String LEAVE_PREDICTION = "leave_prediction"; //timestamp
		public static final String ARRIVE_TIME = "arrive_time"; //timestamp
		public static final String LEAVE_TIME = "leave_time"; //timestamp
	}
	
	public class Account{ //currently doesn't has a PK
		public static final String ACCOUNT_ID = "account_id"; //int(11)
		public static final String EMAIL = "email"; //varchar(255)
		public static final String CAR_ID = "car_id"; //varchar(10)
		public static final String BALANCE = "balance"; //double
		public static final String HAS_SUBSCRIPTION = "has_subscription"; //enum - true/false
	}
	
	public class Cars{
		public static final String SUBSCRIPTION_ID = "subscription_id"; //int(11)
		public static final String ACCOUNT_ID = "account_id"; //int(11)
		public static final String CAR_ID = "car_id"; //varchar(10) PK
	}
	
	public class Complaints{
		public static final String COMPLAINT_ID = "complaint_id"; //int(11) PK
		public static final String ACCOUNT_ID = "account_id"; //int(11)
		public static final String COMPLAINT_DESCRIPTION = "complaint_description"; //text
		public static final String COMPLAINT_CUSTOMER_SERVICE_RESPOND = "customer_service_response"; //text
		public static final String ENTRANCE_ID = "entrance_id"; //int(11)
		public static final String LOT_ID = "lot_id"; //int(11)
		public static final String COMPLAINT_FILLED = "filled"; //enum - true/false
		public static final String COMPLAINT_RESULT = "complaint_result"; //text
		public static final String COMPLAINT_DATETIME = "date_complaint"; //timestamp
	}
	
	public class DailyStats{
		public static final String DAY_ID = "day_id"; //date PK
		public static final String LOT_ID = "lot_id"; // int(11) PK
		public static final String FILLED_RESERVATIONS = "filled_reservations"; //int(11)
		public static final String CANCELED_ORDERS = "canceled_reservations"; //int(11)
		public static final String LATING_PER_PARK = "latings_per_park"; //int(11)
	}
	
	public class LotNumOfColumns{
		public static final String LOT_ID = "lot_id"; //int(11) PK
		public static final String NUM_OF_COLS = "num_of_cols"; //int(11)
	}
	
	public class Rates{
		public static final String LOT_ID = "lot_id"; //int(11) PK
		public static final String SUBSCRIPTION = "subscription_regular"; //double
		public static final String SUBSCRIPTION_FULL = "subscription_full"; //double
		public static final String OCCASIONAL = "order_one_time"; //double
		public static final String PRE_ORDERED = "order_regular"; //double
	}
	
	public class Subscriptions{
		public static final String SUBSCRIPTION_ID = "subscription_id"; //int(11) PK
		public static final String ACCOUNT_ID = "account_id"; //int(11)
		public static final String CAR_ID = "car_id"; //varchar(10)
		public static final String OCCASIONAL = "occasional"; //enum - true/false
		public static final String CAME_IN_TODAY = "came_in_today"; //enum - true/false
		public static final String LOT_ID = "lot_id"; //int(11)
		public static final String EXPIRED_DATE = "expired_date"; // timestamp
		public static final String START_DATE = "start_date"; // timestamp
		public static final String SUBSCRIPTION_TYPE = "subscription_type"; //enum - ROUTINE_MULTIPLE_CARS/ROUTINE/FULL
		public static final String HAS_SUBSCRIPTION = "has_subscription"; //enum - true/false
	}
	
	public class Workers{
		public static final String WORKER_ID = "worker_id"; //int(11) PK
		public static final String PASSWORD = "password"; //varchar(255)
		public static final String ROLE_TYPE = "role_type"; //enum
		public static final String CUSTOMER_SERVICE_RESPONSE = "customer_service_response"; //text
		public static final String LOT_ID = "lot_id"; // int(11)
	}
}
