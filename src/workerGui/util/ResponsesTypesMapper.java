package workerGui.util;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import com.google.gson.Gson;

import core.CpsGson;
import core.worker.WorkerRequestType;
import core.worker.responses.*;

public class ResponsesTypesMapper {
private static Gson gson = CpsGson.GetGson();
	
	public static Map<WorkerRequestType, Function<String, WorkerBaseResponse>> CreateResponsesConverterMap() {
		Map<WorkerRequestType, Function<String, WorkerBaseResponse>> converterMap = new HashMap<WorkerRequestType, Function<String, WorkerBaseResponse>>();
		converterMap.put(WorkerRequestType.ACQUIT_OR_CHARGE_ACCOUNT, (gsonString) -> {
			return gson.fromJson((String) gsonString, NotificationResponse.class);
		});
		converterMap.put(WorkerRequestType.CANCEL_CUSTOMER_ORDER, (gsonString) -> {
			return gson.fromJson((String) gsonString, NotificationResponse.class);
		});
		converterMap.put(WorkerRequestType.INITIALIZE_PARKING_LOT, (gsonString) -> {
			return gson.fromJson((String) gsonString, NotificationResponse.class);
		});
		converterMap.put(WorkerRequestType.PARKING_LOT_FULL, (gsonString) -> {
			return gson.fromJson((String) gsonString, NotificationResponse.class);
		});
		converterMap.put(WorkerRequestType.RESERVE_PARKING_SPACE, (gsonString) -> {
			return gson.fromJson((String) gsonString, NotificationResponse.class);
		});
		converterMap.put(WorkerRequestType.OUT_OF_ORDER, (gsonString) -> {
			return gson.fromJson((String) gsonString, NotificationResponse.class);
		});
		converterMap.put(WorkerRequestType.UPDATE_RATES, (gsonString) -> {
			return gson.fromJson((String) gsonString, NotificationResponse.class);
		});
		converterMap.put(WorkerRequestType.REQUEST_COMPLAINTS_FOR_REVIEW, (gsonString) -> {
			return gson.fromJson((String) gsonString, ComplaintsForReviewResponse.class);
		});
		converterMap.put(WorkerRequestType.REQUEST_RATES_FOR_REVIEW, (gsonString) -> {
			return gson.fromJson((String) gsonString, RatesForReviewResponse.class);
		});
		converterMap.put(WorkerRequestType.DECIDE_ON_RATES, (gsonString) -> {
			return gson.fromJson((String) gsonString, NotificationResponse.class);
		});
		converterMap.put(WorkerRequestType.DECIDE_ON_COMPLAINTS, (gsonString) -> {
			return gson.fromJson((String) gsonString, NotificationResponse.class);
		});
		converterMap.put(WorkerRequestType.REQUEST_PERMISSIONS, (gsonString) -> {
			return gson.fromJson((String) gsonString, PermissionsResponse.class);
		});
		converterMap.put(WorkerRequestType.PARKING_LOT_NAMES, (gsonString) -> {
			return gson.fromJson((String) gsonString, ParkingLotsNamesResponse.class);
		});
		converterMap.put(WorkerRequestType.BAD_REQUEST, (gsonString) -> {
			return gson.fromJson((String) gsonString, BadResponse.class);
		});
		converterMap.put(WorkerRequestType.OUT_OF_ORDER_REPORT, (gsonString) -> {
			return gson.fromJson((String) gsonString, ReportResponse.class);
		});
		converterMap.put(WorkerRequestType.ORDERS_REPORT, (gsonString) -> {
			return gson.fromJson((String) gsonString, ReportResponse.class);
		});
		converterMap.put(WorkerRequestType.COMPLAINTS_REPORT, (gsonString) -> {
			return gson.fromJson((String) gsonString, ReportResponse.class);
		});
		converterMap.put(WorkerRequestType.LOT_SPACES_REPORT, (gsonString) -> {
			return gson.fromJson((String) gsonString, ReportResponse.class);
		});
		converterMap.put(WorkerRequestType.CURRENT_SUBSCRIBERS_REPORT, (gsonString) -> {
			return gson.fromJson((String) gsonString, ReportResponse.class);
		});
		converterMap.put(WorkerRequestType.OPERATIONS_REPORT, (gsonString) -> {
			return gson.fromJson((String) gsonString, ReportResponse.class);
		});
		converterMap.put(WorkerRequestType.PERFORMENCE_REPORT, (gsonString) -> {
			return gson.fromJson((String) gsonString, ReportResponse.class);
		});
		converterMap.put(WorkerRequestType.PARKING_LOT_INFO, (gsonString) -> {
			return gson.fromJson((String) gsonString, ParkingLotInfoResponse.class);
		});

		return converterMap;
	};
}
