package server;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.TimerTask;
import core.worker.ReportItem;
import server.db.dbAPI.RegularDBAPI;
import server.db.dbAPI.ReportsDBAPI;
import server.db.dbAPI.ServerUtils;

public class GenerateStats extends TimerTask{
	private ArrayList<String> reservationsFilledCanceledLatingsList;
	
	GenerateStats(){
		super();
		reservationsFilledCanceledLatingsList = new ArrayList<String>(); 
		reservationsFilledCanceledLatingsList.add("reservation");
		reservationsFilledCanceledLatingsList.add("filled");
		reservationsFilledCanceledLatingsList.add("cancelled");
		reservationsFilledCanceledLatingsList.add("lating");
	}
	
	@Override
	public void run() {

		ArrayList<ReportItem> reportItems = new ArrayList<ReportItem>();
		java.sql.Date first = ServerUtils.getLastWeek();
		java.sql.Date second = ServerUtils.getToday();
		ArrayList<Integer> listOfLotId = new ArrayList<Integer>();
		long countFilled = 0;
		long countCanceled = 0;
		long countLating = 0;
		try {
			RegularDBAPI.getInstance().selectAllParkingLotsId(listOfLotId);
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		for (Iterator iterator = listOfLotId.iterator(); iterator.hasNext();) {
			Integer curLotId = (Integer) iterator.next();
			try {
				ArrayList<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>(); 
				ReportsDBAPI.getInstance().getNumberOfFilledBetween2DatesGroupedByOrderOfLotId(resultList, curLotId, first, second);
				Iterator<Map<String, Object>> iterator2 = resultList.iterator();
				while (iterator2.hasNext()) {
					Map<String, Object> row = (Map<String, Object>) iterator2.next();
					countFilled += (long)row.get("count(entrance_id");
				}
				resultList.clear();
				
				Iterator<Map<String, Object>> iterator3 = resultList.iterator();
				while (iterator3.hasNext()) {
					Map<String, Object> row = (Map<String, Object>) iterator3.next();
					countCanceled += (long)row.get("count(entrance_id");
				}
				resultList.clear();
				
				Iterator<Map<String, Object>> iterator4 = resultList.iterator();
				while (iterator4.hasNext()) {
					Map<String, Object> row = (Map<String, Object>) iterator4.next();
					countLating += (long)row.get("count(entrance_id");
				}
				resultList.clear();
				
				ReportsDBAPI.getInstance().insertIntoDailyStats(curLotId, countFilled, countCanceled, countLating);
				
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
		}
	}

}
