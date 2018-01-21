package core.worker;

// TODO: Auto-generated Javadoc
/**
 * The Enum WorkerRequestType.
 */
public enum WorkerRequestType {
	
	/** The acquit or charge account. */
	ACQUIT_OR_CHARGE_ACCOUNT,
	
	/** The cancel customer order. */
	CANCEL_CUSTOMER_ORDER,
	
	/** The initialize parking lot. */
	INITIALIZE_PARKING_LOT,
	
	/** The parking lot full. */
	PARKING_LOT_FULL,
	
	/** The reserve parking space. */
	RESERVE_PARKING_SPACE,
	
	/** The out of order. */
	OUT_OF_ORDER,
	
	/** The update rates. */
	UPDATE_RATES,
	
	/** The request complaints for review. */
	REQUEST_COMPLAINTS_FOR_REVIEW,
	
	/** The request rates for review. */
	REQUEST_RATES_FOR_REVIEW,
	
	/** The decide on rates. */
	DECIDE_ON_RATES,
	
	/** The decide on complaints. */
	DECIDE_ON_COMPLAINTS,
	
	/** The parking lot names. */
	PARKING_LOT_NAMES,
	
	/** The request permissions. */
	REQUEST_PERMISSIONS,
	
	/** The parking lot info. */
	PARKING_LOT_INFO,
	
	/** The bad request. */
	BAD_REQUEST, 
 /** The out of order report. */
 // Used only for responses. The client should not send this request
	OUT_OF_ORDER_REPORT,
	
	/** The orders report. */
	ORDERS_REPORT,
	
	/** The complaints report. */
	COMPLAINTS_REPORT,
	
	/** The lot spaces report. */
	LOT_SPACES_REPORT,
	
	/** The current subscribers report. */
	CURRENT_SUBSCRIBERS_REPORT,
	
	/** The operations report. */
	OPERATIONS_REPORT,
	
	/** The performence report. */
	PERFORMENCE_REPORT,
}
