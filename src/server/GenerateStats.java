package server;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.Map;
import java.util.TimerTask;
import core.worker.ReportItem;
import server.db.DBConstants.DbSqlColumns;
import server.db.dbAPI.RegularDBAPI;
import server.db.dbAPI.ReportsDBAPI;
import server.db.dbAPI.ServerUtils;
import server.db.queries.ReportsQueries;

// TODO: Auto-generated Javadoc
/**
 * The Class GenerateStats.
 */
public class GenerateStats extends TimerTask{
	
	/** The reservations filled canceled latings list. */
	private ArrayList<String> reservationsFilledCanceledLatingsList;
	
	/**
	 * Instantiates a new generate stats.
	 */
	GenerateStats(){
		super();
		reservationsFilledCanceledLatingsList = new ArrayList<String>(); 
		reservationsFilledCanceledLatingsList.add("reservation");
		reservationsFilledCanceledLatingsList.add("filled");
		reservationsFilledCanceledLatingsList.add("cancelled");
		reservationsFilledCanceledLatingsList.add("lating");
	}
	
	/* (non-Javadoc)
	 * @see java.util.TimerTask#run()
	 */
	@Override
	public void run() {

		ArrayList<ReportItem> reportItems = new ArrayList<ReportItem>();
//		java.sql.Date first = ServerUtils.getLastWeek();
//		java.sql.Date second = ServerUtils.getToday();
		ArrayList<Integer> listOfLotId = new ArrayList<Integer>();
		int countFilled = 0;
		int countCanceled = 0;
		int countLating = 0;
		try {
			RegularDBAPI.getInstance().selectAllParkingLotsId(listOfLotId);
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		for (Iterator iterator = listOfLotId.iterator(); iterator.hasNext();) {
			java.sql.Date second = ServerUtils.getToday();
			Calendar cal = new GregorianCalendar();
			cal.add(Calendar.DATE, -1); //get a week back
			java.sql.Date first = new java.sql.Date(cal.getTimeInMillis());
			Integer curLotId = (Integer) iterator.next();
			for (int i = 0; i < 7; i++) {
				
			try {
				ArrayList<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>(); 
				ReportsDBAPI.getInstance().getNumberOfFilledBetween2DatesGroupedByOrderOfLotId(resultList, curLotId, first, second);
				Iterator<Map<String, Object>> iterator2 = resultList.iterator();
				while (iterator2.hasNext()) {
					Map<String, Object> row = (Map<String, Object>) iterator2.next();
					countFilled += ((Long)row.get("count(entrance_id)")).intValue();
				}
				resultList.clear();
				
				ReportsDBAPI.getInstance().getNumberOfCanceledBetween2DatesGroupedByOrderOfLotId(resultList, curLotId, first, second);
				Iterator<Map<String, Object>> iterator3 = resultList.iterator();
				while (iterator3.hasNext()) {
					Map<String, Object> row = (Map<String, Object>) iterator3.next();
					countCanceled += ((Long)row.get("count(entrance_id)")).intValue();
				}
				resultList.clear();
				
				ReportsDBAPI.getInstance().getNumberOfLatingBetween2DatesGroupedByOrderOfLotId(resultList, curLotId, first, second);
				Iterator<Map<String, Object>> iterator4 = resultList.iterator();
				while (iterator4.hasNext()) {
					Map<String, Object> row = (Map<String, Object>) iterator4.next();
					countLating += ((Long)row.get("count(entrance_id)")).intValue();
				}
				resultList.clear();
				
				ReportsDBAPI.getInstance().insertIntoDailyStats(curLotId, countFilled, countCanceled, countLating, first);
				
				//	this was originally made to insert to DB also stats.			
//				for (Iterator<String> iterator2 = reservationsFilledCanceledLatingsList.iterator(); iterator2.hasNext();) {
//					String reservationsFilledCanceledLatings = (String) iterator2.next();
//					
//					OrdersReportRequestsHandler.generateReportsDataBetween2DatesOfLotId(reservationsFilledCanceledLatings,
//							curLotId, reportItems,  first, second);
//				}

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("1111111");
			
			second = first;
			cal.add(Calendar.DATE, -1); //get a week back
			first = new java.sql.Date(cal.getTimeInMillis());
		}
			
	}
		for (Iterator iterator = listOfLotId.iterator(); iterator.hasNext();) {
			Integer curLotId = (Integer) iterator.next();
			ArrayList<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>(); 
			Calendar cal = new GregorianCalendar();
			cal.add(Calendar.DATE, -7); //get a week back
			java.sql.Date first = new java.sql.Date(cal.getTimeInMillis());
			java.sql.Date second = ServerUtils.getToday();
			try {
				ReportsDBAPI.getInstance().selectBetween2DatesQueryOfLotId(ReportsQueries.getInstance().select_filled_reservations_between_2_dates_of_lot_id, first, second, resultList, curLotId);
				int filledMean = ServerUtils.calcMean(resultList, DbSqlColumns.FILLED_RESERVATIONS.getName());
				double filledStd = ServerUtils.calcStd(resultList, DbSqlColumns.FILLED_RESERVATIONS.getName());
				double filledAvg = ServerUtils.calcAvg(resultList, DbSqlColumns.FILLED_RESERVATIONS.getName());
				resultList.clear();
				ReportsDBAPI.getInstance().selectBetween2DatesQueryOfLotId(ReportsQueries.getInstance().select_canceled_reservations_between_2_dates_of_lot_id, first, second, resultList, curLotId);
				int canceledMean = ServerUtils.calcMean(resultList, DbSqlColumns.CANCELED_ORDERS.getName());
				double canceledStd = ServerUtils.calcStd(resultList, DbSqlColumns.CANCELED_ORDERS.getName());
				double canceledAvg = ServerUtils.calcAvg(resultList, DbSqlColumns.CANCELED_ORDERS.getName());
				resultList.clear();
				ReportsDBAPI.getInstance().selectBetween2DatesQueryOfLotId(ReportsQueries.getInstance().select_lating_reservations_between_2_dates_of_lot_id, first, second, resultList, curLotId);
				int latingMean = ServerUtils.calcMean(resultList, DbSqlColumns.LATING_PER_PARK.getName());
				double latingStd = ServerUtils.calcStd(resultList, DbSqlColumns.LATING_PER_PARK.getName());
				double latingAvg = ServerUtils.calcAvg(resultList, DbSqlColumns.LATING_PER_PARK.getName());
				resultList.clear();
				
				ReportsDBAPI.getInstance().insertIntoWeeklyStats(curLotId, filledMean, canceledMean, latingMean,
						filledStd, canceledStd, latingStd,
						filledAvg, canceledAvg, latingAvg, first);
			
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			
			
			
			

		}
		
		
		
	}

}
