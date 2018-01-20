package server.requestHandlers.worker.handlers;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import core.worker.ReportItem;
import core.worker.WorkerRequestType;
import core.worker.requests.BaseRequest;
import core.worker.requests.PeriodicReportRequest;
import core.worker.requests.PermissionsRequest;
import core.worker.responses.WorkerBaseResponse;
import core.worker.responses.WorkerResponse;
import ocsf.server.ConnectionToClient;
import server.db.DBConstants;
import server.db.DBConstants.DbSqlColumns;
import server.db.dbAPI.ReportsDBAPI;
import server.requestHandlers.worker.IProvideConnectionsToClient;
import server.requestHandlers.worker.WorkerResponseFactory;

public class OrdersReportRequestsHandler extends BaseRequestsHandler {

	public OrdersReportRequestsHandler(IProvideConnectionsToClient connectionsToClientProvider) {
		super(connectionsToClientProvider);
	}

	@Override
	protected WorkerRequestType getHandlerRequestsType() {
		return WorkerRequestType.ORDERS_REPORT;
	}

	@Override
	protected WorkerResponse HandleSpecificRequest(BaseRequest specificRequest, ConnectionToClient client) throws SQLException {
//		return createUnsupportedFeatureResponse();
		
		PeriodicReportRequest periodicReportRequest = (PeriodicReportRequest) specificRequest;
		ArrayList<ReportItem> reportItems = new ArrayList<ReportItem>(); 
		
		 System.out.println("123");
		
//		int curLotId = periodicReportRequest.lotId;
		
		
		java.sql.Date first = new java.sql.Date(periodicReportRequest.startDate.getTime());
		java.sql.Date second = new java.sql.Date(periodicReportRequest.endDate.getTime());

		 generateReportsDataBetween2DatesOfLotId("reservations", 4, reportItems, first, second);
		 WorkerBaseResponse response = WorkerResponseFactory.CreateReportResponse(reportItems, getHandlerRequestsType());
		 return CreateWorkerResponse(response);
	}
	
	//TODO: floating point double = long / long - gives no floating point. need to make if for at least 2 digits after point.
	/**
	 * Generate reports data of lot id.
	 * this function should be broken that could return the values calculated in the end of it.
	 * 
	 * @param reservationsFilledCanceledLatings the reservations filled canceled latings
	 * @param lotId the lot id
	 * @throws SQLException the SQL exception
	 */
	public static void generateReportsDataBetween2DatesOfLotId(String reservationsFilledCanceledLatings, 
			int lotId, ArrayList<ReportItem> reportItems, java.sql.Date first, java.sql.Date second) throws SQLException {
		ArrayList<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
		
		// here select what data you would like to fetch
		if (reservationsFilledCanceledLatings.equals("reservations")){
			ReportsDBAPI.getInstance().getNumberOfReservationsBetween2DatesGroupedByOrderOfLotId(resultList, lotId, first, second);
		} else if (reservationsFilledCanceledLatings.equals("filled")){
			ReportsDBAPI.getInstance().getNumberOfFilledBetween2DatesGroupedByOrderOfLotId(resultList, lotId, first, second);
		} else if (reservationsFilledCanceledLatings.equals("canceled")){
			ReportsDBAPI.getInstance().getNumberOfCanceledBetween2DatesGroupedByOrderOfLotId(resultList, lotId, first, second);
		} else if (reservationsFilledCanceledLatings.equals("lating")){
			ReportsDBAPI.getInstance().getNumberOfLatingBetween2DatesGroupedByOrderOfLotId(resultList, lotId, first, second);
		}
		// number of reservations of the 'reservationsFilledCanceledLatings' category
		long countOneTimeOrder = 0;
		long countOrder = 0;
		long countSubsFull = 0;
		long countSubsOcc = 0;
		long totalReservations = 0;
		Iterator<Map<String, Object>> iterator = resultList.iterator();
		// iterate over the 'count' entries (have only one for each order type) and get data.
		while (iterator.hasNext()) {
			Map<String, Object> row = (Map<String, Object>) iterator.next();
			if(row.get(DbSqlColumns.ORDER_TYPE.getName()).equals(DBConstants.OrderType.ONE_TIME.getValue())){
				countOneTimeOrder = (long) row.get("count(entrance_id)");
			}
			else if (row.get(DbSqlColumns.ORDER_TYPE.getName()).equals(DBConstants.OrderType.ORDER.getValue())){ 
				System.out.println(row.get("count(entrance_id)"));
				countOrder =  (long) (row.get("count(entrance_id)"));
			}
			else if (row.get(DbSqlColumns.ORDER_TYPE.getName()).equals(DBConstants.OrderType.SUBSCRIPTION.getValue())){
				countSubsOcc  = (long) row.get("count(entrance_id)");
			}
			else if (row.get(DbSqlColumns.ORDER_TYPE.getName()).equals(DBConstants.OrderType.SUBSCRIPTION_FULL.getValue())){
				countSubsFull  = (long) row.get("count(entrance_id)");
			}
		}
		//total number of reservations is the sum of all possebilities
		totalReservations = countOneTimeOrder + countOrder + countSubsFull + countSubsOcc;
		
		// how many order types were in last week in percenteges.
		double oneTimeOrderPercents =  countOneTimeOrder / totalReservations;
		double orderPercents =  countOrder / totalReservations;
		double subsFullPercents =  countSubsFull / totalReservations;
		double subsOccPercents =  countSubsOcc / totalReservations;
		
		// weekly average of each order type
		double dailyAvgOneTimeOrder = countOneTimeOrder / 7; 
		double dailyAvgOrder = countOrder / 7; 
		double dailyAvgSubsOccOrder = countSubsOcc / 7; 
		double dailyAvgSubsFullOrder = countSubsFull / 7; 

		
		reportItems.add(new ReportItem(reservationsFilledCanceledLatings + "_countOneTimeOrder", Double.toString((long)countOneTimeOrder)));
		reportItems.add(new ReportItem(reservationsFilledCanceledLatings + "_orderPercents", Double.toString((long)countOrder)));
		reportItems.add(new ReportItem(reservationsFilledCanceledLatings + "_subsFullPercents", Double.toString((long)countSubsOcc)));
		reportItems.add(new ReportItem(reservationsFilledCanceledLatings + "_subsOccPercents", Double.toString((long)countSubsFull)));
		
		reportItems.add(new ReportItem(reservationsFilledCanceledLatings + "_oneTimeOrderPercents", Double.toString(oneTimeOrderPercents)));
		reportItems.add(new ReportItem(reservationsFilledCanceledLatings + "_orderPercents", Double.toString(orderPercents)));
		reportItems.add(new ReportItem(reservationsFilledCanceledLatings + "_subsFullPercents", Double.toString(subsFullPercents)));
		reportItems.add(new ReportItem(reservationsFilledCanceledLatings + "_subsOccPercents", Double.toString(subsOccPercents)));
		
		reportItems.add(new ReportItem(reservationsFilledCanceledLatings + "_dailyAvgOneTimeOrder", Double.toString(dailyAvgOneTimeOrder)));
		reportItems.add(new ReportItem(reservationsFilledCanceledLatings + "_dailyAvgOrder", Double.toString(dailyAvgOrder)));
		reportItems.add(new ReportItem(reservationsFilledCanceledLatings + "_dailyAvgSubsOccOrder", Double.toString(dailyAvgSubsOccOrder)));
		reportItems.add(new ReportItem(reservationsFilledCanceledLatings + "_dailyAvgSubsFullOrder", Double.toString(dailyAvgSubsFullOrder)));
		
		
	}
	
	
	
	
	
}
