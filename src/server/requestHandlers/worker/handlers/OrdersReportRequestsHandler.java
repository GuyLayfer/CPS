package server.requestHandlers.worker.handlers;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import core.worker.ReportItem;
import core.worker.WorkerRequestType;
import core.worker.requests.BaseRequest;
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

		 List<ReportItem> reportItems = new ArrayList<ReportItem>(); 
		
		
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
	public void generateReportsDataOfLotId(String reservationsFilledCanceledLatings, 
			int lotId, ArrayList<ReportItem> reportItems) throws SQLException {
		ArrayList<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
		
		// here select what data you would like to fetch
		if (reservationsFilledCanceledLatings.equals("reservations")){
			ReportsDBAPI.getInstance().getNumberOfReservationsOfLastWeekGroupedByOrderOfLotId(resultList, lotId);
		} else if (reservationsFilledCanceledLatings.equals("filled")){
			ReportsDBAPI.getInstance().getNumberOfFilledOfLastWeekGroupedByOrderOfLotId(resultList, lotId);
		} else if (reservationsFilledCanceledLatings.equals("canceled")){
			ReportsDBAPI.getInstance().getNumberOfCanceledOfLastWeekGroupedByOrderOfLotId(resultList, lotId);
		} else if (reservationsFilledCanceledLatings.equals("lating")){
			ReportsDBAPI.getInstance().getNumberOfLatingOfLastWeekGroupedByOrderOfLotId(resultList, lotId);
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

		
//		reportItems.add(new ReportItem("countOneTimeOrder", Double.toString((long)countOneTimeOrder)));
//		reportItems.add(new ReportItem("orderPercents", Double.toString(orderPercents)));
//		reportItems.add(new ReportItem("subsFullPercents", Double.toString(subsFullPercents)));
//		reportItems.add(new ReportItem("subsOccPercents", Double.toString(subsOccPercents)));
		
		reportItems.add(new ReportItem("oneTimeOrderPercents", Double.toString(oneTimeOrderPercents)));
		reportItems.add(new ReportItem("orderPercents", Double.toString(orderPercents)));
		reportItems.add(new ReportItem("subsFullPercents", Double.toString(subsFullPercents)));
		reportItems.add(new ReportItem("subsOccPercents", Double.toString(subsOccPercents)));
		
		reportItems.add(new ReportItem("dailyAvgOneTimeOrder", Double.toString(dailyAvgOneTimeOrder)));
		reportItems.add(new ReportItem("dailyAvgOrder", Double.toString(dailyAvgOrder)));
		reportItems.add(new ReportItem("dailyAvgSubsOccOrder", Double.toString(dailyAvgSubsOccOrder)));
		reportItems.add(new ReportItem("dailyAvgSubsFullOrder", Double.toString(dailyAvgSubsFullOrder)));
		
		
	}
	
	
	
	
	
}
