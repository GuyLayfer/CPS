/*This class is only to demonstrate implemantion of the DBAPI*/

package server.db.dbAPI;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import core.parkingLot.ParkingState;
import core.parkingLot.ParkingStatus;
import server.db.DBConstants;

public class MainDBAPI {


	public static void main(String[] args) {
		// TODO: Who uses this code? Why there's main here?

/*
		RegularDBAPI regularDBAPIInst = RegularDBAPI.getInstance();
		int lot1Size = 36;
		int lotId = 1;
		String[] parkingMapArrForSelectQuery = new String[lot1Size];
		List<ParkingState> parkingMap = null;

		try {
			regularDBAPIInst.selectParkingMapByLotId(lotId, parkingMapArrForSelectQuery);
			parkingMap = Arrays.stream(parkingMapArrForSelectQuery).map(lotStatus -> new ParkingState(ParkingStatus.convertStringToParkingMapEnum(lotStatus), ""))
					.collect(Collectors.toList());
		} catch (SQLException e) {
			e.printStackTrace();
		}

		for (ParkingState parkingState : parkingMap) {
			System.out.println(parkingState);
		}

		String[] parkingMapArrForInsertQuery = new String[lot1Size];
		for (int i = 0; i < parkingMapArrForInsertQuery.length; i++) {
			parkingMapArrForInsertQuery[i] = DBConstants.ParkingMap.FREE.getValue();

		}
*/
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		// int x = 3;
		// int y = 4;
		// int z = 3;
		// int oneDIndex = 0;
		// int [][][] parkingMap3D = new int [z][x][y];
		//
		// int[] parkingMap1D = new int[x*y*z];
		// for (int i = 0; i < z ; i++) {
		// for (int j = 0; j < x; j++) {
		// for (int k = 0; k < y; k++) {
		// parkingMap3D[i][j][k] = ServerUtils.calc3DTo1D(j,k,i,y);
		// }
		// }
		// }
		//
		// for (int i = 0; i < z ; i++) {
		// for (int j = 0; j < x; j++) {
		// for (int k = 0; k < y; k++) {
		// parkingMap1D[oneDIndex] = parkingMap3D[i][j][k];
		// System.out.format("[%d,%d,%d] = %d ",i, j, k, parkingMap1D[oneDIndex]);
		// oneDIndex++;
		//
		// }
		// }
		// }

		
		
		
		//
		//
		//
		//
		//// DBAPIRegular dbapiRegular = new DBAPIRegular();
		//// DBAPIReports dbapiReports = new DBAPIReports();
		//// DBAPISubscriptions dbapiSubscriptions = new DBAPISubscriptions();
		//
		// Date dateArrive = new Date(System.currentTimeMillis());
		// Calendar calendarLeft = new GregorianCalendar();
		//// set(int year, int month, int date, int hourOfDay, int minute)
		// calendarLeft.set(2017, 11, 31, 23, 59, 01);
		//
		//
		// Date dateLeave = calendarLeft.getTime();
		// System.out.println(dateLeave);
		//
		//
		//// public static void updateSql(String stmtString, Queue<Object> params, Queue<sqlTypeKind> types) {
		//// updateSql("current_cars_in_parking", create_table_current_cars_in_parking, null, null);
		//
		//
		// //EXAMPLE of usage
		// int entranceId = 0;
		// try {
		// System.out.println("!!!!!" + DBConstants.orderType.ORDER.getValue());
		// Date timeArrive = new Date(0);
		// Date timeLeave = new Date(0);
		// entranceId = RegularDBAPI.insertParkingReservaion(Integer.toString(51)/*carId*/,4/*accountId*/,/* 2 entranceId,*/
		// 1/*lotId*/, dateArrive, dateLeave, timeArrive, timeLeave, DBConstants.orderType.ORDER/*order*/);
		// }
		// catch (SQLException e) {
		// System.out.println("ERRORR");
		// e.printStackTrace();
		// }
		//
		//
		// ArrayList<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
		//// selectSql("current_cars_in_parking", select_all, null, null, resultList);
		//
		// try {
		// RegularDBAPI.trackOrderStatus(entranceId, resultList);
		// RegularDBAPI.createNewAccount(123, "asdas@jsdflkj.com", Integer.toString(123456), DBConstants.trueFalse.FALSE);
		// } catch (SQLException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		//
		// int countRows = 0;
		//
		// for (Iterator iterator = resultList.iterator(); iterator.hasNext();) {
		// System.out.println("next row " + countRows);
		// Map<String, Object> row = (Map<String, Object>) iterator.next();
		// countRows++;
		// for (Map.Entry<String, Object> column : row.entrySet()) {
		// System.out.println(column.getKey() + "/" + column.getValue());
		// }
		//
		// }
		//
		//
		//// for (Iterator iterator = resultList.iterator(); iterator.hasNext();) {
		//// System.out.println("next row " + countRows);
		//// Map<String, Object> row = (Map<String, Object>) iterator.next();
		//// countRows++;
		//// for (Map.Entry<String, Object> column : row.entrySet()) {
		//// System.out.println(column.getKey() + "/" + column.getValue());
		//// }
		////
		//// }
		//
	}
}
