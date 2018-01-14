package server.workerServer;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;

import com.google.gson.Gson;

import core.CpsGson;
import core.worker.WorkerRequestType;
import core.worker.requests.*;
import server.workerServer.workerRequestsHandlers.*;

public class WorkerRequestsTypesMapper {
	private static Gson gson = new CpsGson().GetGson();

	public static Map<WorkerRequestType, Function<String, BaseRequest>> CreateRequestsConverterMap() {
		Map<WorkerRequestType, Function<String, BaseRequest>> converterMap = new HashMap<WorkerRequestType, Function<String, BaseRequest>>();
		converterMap.put(WorkerRequestType.ACQUIT_OR_CHARGE_ACCOUNT, (gsonString) -> {
			return gson.fromJson((String) gsonString, AcquitOrChargeAccountRequest.class);
		});
		converterMap.put(WorkerRequestType.CANCEL_CUSTOMER_ORDER, (gsonString) -> {
			return gson.fromJson((String) gsonString, CancelCustomerOrderRequest.class);
		});
		converterMap.put(WorkerRequestType.INITIALIZE_PARKING_LOT, (gsonString) -> {
			return gson.fromJson((String) gsonString, InitializeParkingLotRequest.class);
		});
		converterMap.put(WorkerRequestType.PARKING_LOT_FULL, (gsonString) -> {
			return gson.fromJson((String) gsonString, ParkingLotFullRequest.class);
		});
		converterMap.put(WorkerRequestType.RESERVE_PARKING_SPACE, (gsonString) -> {
			return gson.fromJson((String) gsonString, ReserveParkingSpaceRequest.class);
		});
		converterMap.put(WorkerRequestType.OUT_OF_ORDER, (gsonString) -> {
			return gson.fromJson((String) gsonString, OutOfOrderRequest.class);
		});
		converterMap.put(WorkerRequestType.UPDATE_RATES, (gsonString) -> {
			return gson.fromJson((String) gsonString, UpdateRatesRequest.class);
		});
		converterMap.put(WorkerRequestType.REQUEST_COMPLAINTS_FOR_REVIEW, (gsonString) -> {
			return gson.fromJson((String) gsonString, ComplaintsForReviewRequest.class);
		});
		converterMap.put(WorkerRequestType.REQUEST_RATES_FOR_REVIEW, (gsonString) -> {
			return gson.fromJson((String) gsonString, RatesForReviewRequest.class);
		});
		converterMap.put(WorkerRequestType.DECIDE_ON_RATES, (gsonString) -> {
			return gson.fromJson((String) gsonString, DecideOnRateRequest.class);
		});
		converterMap.put(WorkerRequestType.DECIDE_ON_COMPLAINTS, (gsonString) -> {
			return gson.fromJson((String) gsonString, DecideOnComplaintRequest.class);
		});
		converterMap.put(WorkerRequestType.REQUEST_PERMISSIONS, (gsonString) -> {
			return gson.fromJson((String) gsonString, PermissionsRequest.class);
		});
		converterMap.put(WorkerRequestType.PARKING_LOT_NAMES, (gsonString) -> {
			return gson.fromJson((String) gsonString, ParkingLotsNamesRequest.class);
		});

		return converterMap;
	};

	public static Set<IRequestsHandler> CreateRequestsHandlers() {
		Set<IRequestsHandler> requestsHandlers = new HashSet<IRequestsHandler>();
		requestsHandlers.add(new AcquitOrChargeAccountRequestsHandler());
		requestsHandlers.add(new CancelRequestsHandler());
		requestsHandlers.add(new DecideOnComplaintsRequestsHandler());
		requestsHandlers.add(new DecideOnRatesRequestsHandler());
		requestsHandlers.add(new InitializeParkingLotRequestsHandler());
		requestsHandlers.add(new OutOfOrderRequestsHandler());
		requestsHandlers.add(new ParkingLotFullRequestsHandler());
		requestsHandlers.add(new ParkingLotNamesRequestsHandler());
		requestsHandlers.add(new PermissionsRequestsHandler());
		requestsHandlers.add(new RequestComplaintsForReviewRequestsHandler());
		requestsHandlers.add(new RequestRatesForReviewRequestsHandler());
		requestsHandlers.add(new ReserveParkingSpaceRequestsHandler());
		requestsHandlers.add(new UpdateRatesRequestsHandler());
		// TODO: Add reports handlers
		return requestsHandlers;
	}
}
