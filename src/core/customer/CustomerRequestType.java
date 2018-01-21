package core.customer;

// TODO: Auto-generated Javadoc
/**
 * The Enum CustomerRequestType.
 */
// Used also as response type
public enum CustomerRequestType {
	
	/** The pre ordered parking. */
	PRE_ORDERED_PARKING,
	
	/** The cancel order. */
	CANCEL_ORDER,
	
	/** The track order status. */
	TRACK_ORDER_STATUS,
	
	/** The order routine monthly subscription. */
	ORDER_ROUTINE_MONTHLY_SUBSCRIPTION,
	
	/** The order full monthly subscription. */
	ORDER_FULL_MONTHLY_SUBSCRIPTION,
	
	/** The subscription renewal. */
	SUBSCRIPTION_RENEWAL,
	
	/** The open complaint. */
	OPEN_COMPLAINT,
	
	/** The occasional parking. */
	// relevant only for kiosk clients
	OCCASIONAL_PARKING,
	
	/** The enter parking pre ordered. */
	ENTER_PARKING_PRE_ORDERED,
	
	/** The enter parking subscriber. */
	ENTER_PARKING_SUBSCRIBER,
	
	/** The exit parking. */
	EXIT_PARKING,
	
	/** The full parking lot. */
	// used only for sending messages from the server to the client
	FULL_PARKING_LOT,
	
	/** The parking lot names. */
	PARKING_LOT_NAMES,
	
	/** The bad request. */
	BAD_REQUEST, // Used only for responses. The client should not send this request
}
