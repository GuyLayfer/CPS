package core.customer.responses;

import core.customer.CustomerRequestType;

/* responseType to jsonData-type map:
 * 		ORDER_ONE_TIME_PARKING 				- IdPricePair (orderID, price)
 *		CANCEL_ORDER						- double (refund price)
 *		TRACK_ORDER_STATUS					- TrackOrderResponseData
 *		ORDER_ROUTINE_MONTHLY_SUBSCRIPTION	- IdPricePair (subscriptionID, price)
 *		ORDER_FULL_MONTHLY_SUBSCRIPTION		- IdPricePair (subscriptionID, price)
 *		SUBSCRIPTION_RENEWAL				- IdPricePair (subscriptionID, price)
 *		OPEN_COMPLAINT						- int (complaintID)
 *	relevant only for kiosk client:
 *		OCCASIONAL_PARKING					- IdPricePair (orderID, price)
 *		ENTER_PARKING						- TODO
 *		EXIT_PARKING						- TODO
 *  used only for sending messages from the server to the client:
 *		FULL_PARKING_LOT					- TODO
 */
public class CustomerResponse {
	public CustomerRequestType requestType;
	
	public String jsonData;
	
	public CustomerResponse(CustomerRequestType requestType, String jsonData) {
		this.requestType = requestType;
		this.jsonData = jsonData;
	}
}
