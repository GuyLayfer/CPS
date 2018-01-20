package server.requestHandlers.worker;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;

import com.google.gson.Gson;

import core.CpsGson;
import core.worker.WorkerRequestType;
import core.worker.requests.*;
import server.requestHandlers.worker.handlers.*;

public class WorkerRequestsTypesMapper {
	private static Gson gson = CpsGson.GetGson();

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
		converterMap.put(WorkerRequestType.COMPLAINTS_REPORT, (gsonString) -> {
			return gson.fromJson((String) gsonString, PeriodicReportRequest.class);
		});
		converterMap.put(WorkerRequestType.CURRENT_SUBSCRIBERS_REPORT, (gsonString) -> {
			return gson.fromJson((String) gsonString, ReportRequest.class);
		});
		converterMap.put(WorkerRequestType.LOT_SPACES_REPORT, (gsonString) -> {
			return gson.fromJson((String) gsonString, ReportRequest.class);
		});
		converterMap.put(WorkerRequestType.OPERATIONS_REPORT, (gsonString) -> {
			return gson.fromJson((String) gsonString, PeriodicReportRequest.class);
		});
		converterMap.put(WorkerRequestType.ORDERS_REPORT, (gsonString) -> {
			return gson.fromJson((String) gsonString, PeriodicReportRequest.class);
		});
		converterMap.put(WorkerRequestType.OUT_OF_ORDER_REPORT, (gsonString) -> {
			return gson.fromJson((String) gsonString, PeriodicReportRequest.class);
		});
		converterMap.put(WorkerRequestType.PERFORMENCE_REPORT, (gsonString) -> {
			return gson.fromJson((String) gsonString, PeriodicReportRequest.class);
		});

		return converterMap;
	};

	public static Set<IRequestsHandler> CreateRequestsHandlers(IProvideConnectionsToClient server) {
		Set<IRequestsHandler> requestsHandlers = new HashSet<IRequestsHandler>();
		requestsHandlers.add(new AcquitOrChargeAccountRequestsHandler(server));
		requestsHandlers.add(new CancelRequestsHandler(server));
		requestsHandlers.add(new DecideOnComplaintsRequestsHandler(server));
		requestsHandlers.add(new DecideOnRatesRequestsHandler(server));
		requestsHandlers.add(new InitializeParkingLotRequestsHandler(server));
		requestsHandlers.add(new OutOfOrderRequestsHandler(server));
		requestsHandlers.add(new ParkingLotFullRequestsHandler(server));
		requestsHandlers.add(new ParkingLotNamesRequestsHandler(server));
		requestsHandlers.add(new PermissionsRequestsHandler(server));
		requestsHandlers.add(new RequestComplaintsForReviewRequestsHandler(server));
		requestsHandlers.add(new RequestRatesForReviewRequestsHandler(server));
		requestsHandlers.add(new ReserveParkingSpaceRequestsHandler(server));
		requestsHandlers.add(new ComplaintsReportRequestsHandler(server));
		requestsHandlers.add(new CurrentSubscribersReportRequestsHandler(server));
		requestsHandlers.add(new LotSpacesReportRequestsHandler(server));
		requestsHandlers.add(new OperationsReportRequestsHandler(server));
		requestsHandlers.add(new OrdersReportRequestsHandler(server));
		requestsHandlers.add(new OutOfOrderReportRequestHandler(server));
		requestsHandlers.add(new PerformanceReportRequestsHandler(server));
		return requestsHandlers;
	}
}
