package core.customer;

// Used also as response type
public enum CustomerRequestType {
	ORDER_ONE_TIME_PARKING,
	CANCEL_ORDER,
	TRACK_ORDER_STATUS,
	ORDER_ROUTINE_MONTHLY_SUBSCRIPTION,
	ORDER_FULL_MONTHLY_SUBSCRIPTION,
	SUBSCRIPTION_RENEWAL,
	OPEN_COMPLAINT,
	
	// relevant only for kiosk clients
	OCCASIONAL_PARKING,
	ENTER_PARKING_PRE_ORDERED,
	ENTER_PARKING_SUBSCRIBER,
	EXIT_PARKING,
	
	// used only for sending messages from the server to the client
	FULL_PARKING_LOT,
	PARKING_LOT_NAMES,
	
	BAD_REQUEST, // Used only for responses. The client should not send this request
}
