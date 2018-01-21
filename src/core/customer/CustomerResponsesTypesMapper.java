package core.customer;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import com.google.gson.Gson;

import core.CpsGson;
import core.customer.responses.BadCustomerResponse;
import core.customer.responses.CustomerBaseResponse;
import core.customer.responses.CustomerNotificationResponse;
import core.customer.responses.IdPricePairResponse;
import core.customer.responses.ParkingLotsNamesForCustomerResponse;
import core.customer.responses.TrackOrderResponse;

// TODO: Auto-generated Javadoc
/**
 * The Class CustomerResponsesTypesMapper.
 */
public class CustomerResponsesTypesMapper {
	
	/** The Constant gson. */
	private static final Gson gson = CpsGson.GetGson();
	
	/**
	 * Creates the response converter map.
	 *
	 * @return the map
	 */
	public static Map<CustomerRequestType, Function<String, CustomerBaseResponse>> CreateResponseConverterMap() {
		Map<CustomerRequestType, Function<String, CustomerBaseResponse>> converterMap = new HashMap<CustomerRequestType, Function<String, CustomerBaseResponse>>();
		converterMap.put(CustomerRequestType.PRE_ORDERED_PARKING, (gsonString) -> {
			return gson.fromJson((String) gsonString, IdPricePairResponse.class);
		});
		converterMap.put(CustomerRequestType.CANCEL_ORDER, (gsonString) -> {
			return gson.fromJson((String) gsonString, CustomerNotificationResponse.class);
		});
		converterMap.put(CustomerRequestType.TRACK_ORDER_STATUS, (gsonString) -> {
			return gson.fromJson((String) gsonString, TrackOrderResponse.class);
		});
		converterMap.put(CustomerRequestType.ORDER_ROUTINE_MONTHLY_SUBSCRIPTION, (gsonString) -> {
			return gson.fromJson((String) gsonString, IdPricePairResponse.class);
		});
		converterMap.put(CustomerRequestType.ORDER_FULL_MONTHLY_SUBSCRIPTION, (gsonString) -> {
			return gson.fromJson((String) gsonString, IdPricePairResponse.class);
		});
		converterMap.put(CustomerRequestType.SUBSCRIPTION_RENEWAL, (gsonString) -> {
			return gson.fromJson((String) gsonString, IdPricePairResponse.class);
		});
		converterMap.put(CustomerRequestType.OPEN_COMPLAINT, (gsonString) -> {
			return gson.fromJson((String) gsonString, CustomerNotificationResponse.class);
		});
		converterMap.put(CustomerRequestType.OCCASIONAL_PARKING, (gsonString) -> {
			return gson.fromJson((String) gsonString, IdPricePairResponse.class);
		});
		converterMap.put(CustomerRequestType.ENTER_PARKING_PRE_ORDERED, (gsonString) -> {
			return gson.fromJson((String) gsonString, CustomerNotificationResponse.class);
		});
		converterMap.put(CustomerRequestType.ENTER_PARKING_SUBSCRIBER, (gsonString) -> {
			return gson.fromJson((String) gsonString, CustomerNotificationResponse.class);
		});
		converterMap.put(CustomerRequestType.EXIT_PARKING, (gsonString) -> {
			return gson.fromJson((String) gsonString, CustomerNotificationResponse.class);
		});
		converterMap.put(CustomerRequestType.FULL_PARKING_LOT, (gsonString) -> {
			return gson.fromJson((String) gsonString, CustomerNotificationResponse.class);
		});
		converterMap.put(CustomerRequestType.PARKING_LOT_NAMES, (gsonString) -> {
			return gson.fromJson((String) gsonString, ParkingLotsNamesForCustomerResponse.class);
		});
		converterMap.put(CustomerRequestType.BAD_REQUEST, (gsonString) -> {
			return gson.fromJson((String) gsonString, BadCustomerResponse.class);
		});
		return converterMap;
	};
}
