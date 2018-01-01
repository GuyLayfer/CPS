package core.Customer;

import core.AbstractResponse;
import core.ResponseStatus;

/*
 * // Client usage example (not a working code):
 * 
 * void handleMessageFromServer(Object msg) {
 * 		WebCustomerServerResponse response = gson.fromJson((String)msg, WebCustomerServerResponse.class);
 * 		switch (response.responseType) {
 *		case ORDER_ONE_TIME_PARKING:
 *			if (response.status == OK) {
 *				IdPricePair responseData = gson.fromJson(response.jsonData, IdPricePair.class);
 *			} else {
 *				//handle error according to the status
 *			}
 *			break;
 *		case TRACK_ORDER_STATUS:
 *			if (response.status == OK) {
 *				TrackOrderServerResponseData orderData = gson.fromJson(response.jsonData, TrackOrderServerResponseData.class);
 *			} else {
 *				//handle error according to the status
 *			}
 *			break;
 *		//...
 * }
 */

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
 */
public class CustomerResponse extends AbstractResponse {
	// requestType has a valid value only if status is OK or REQUEST_DENIED or UNSUPPORTED_FEATURE
	public CustomerRequestType requestType;
	
	// General constructor
	public CustomerResponse(ResponseStatus status, String statusDescription, 
							CustomerRequestType requestType, String jsonData) {
		this.status = status;
		this.statusDescription = statusDescription;
		this.requestType = requestType;
		this.jsonData = jsonData;
	}
	
	// Used in cases in which status is OK
	public CustomerResponse(CustomerRequestType requestType, String jsonData) {
		this.status = ResponseStatus.OK;
		this.statusDescription = null;
		this.requestType = requestType;
		this.jsonData = jsonData;
	}
	
	// Used in cases in which status is BAD_REQUEST or SERVER_FAILLURE
	public CustomerResponse(ResponseStatus status, String statusDescription) {
		this.status = status;
		this.statusDescription = statusDescription;
	}

	@Override
	public String toString() {
		return status != ResponseStatus.OK ? super.toString() : "Request: " + requestType;
	}
}
