package server.db.dbAPI;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


import server.db.DBConstants;
import server.db.DBConstants.DbSqlColumns;

// TODO: Auto-generated Javadoc
/**
 * The Class ServerUtils.
 */
public class ServerUtils {

//	TODO: test this function
	/**
	 * Db parking map to 1 D array.
	 * converts map array to 1 dimension array.
	 * @param parkingMapAsArrList the parking map as arr list
	 * @return the DB constants.parking map[]
	 */
	public static DBConstants.ParkingMap [] dbParkingMapTo1DArray(ArrayList<Map<String, Object>> parkingMapAsArrList) {

		
//		int countRows = 0;
//		int x = 0, y = 0, z = 0;
		
		int numberOfParkings = ((parkingMapAsArrList.get(0)).entrySet()).size();
		DBConstants.ParkingMap arr1d[] = new DBConstants.ParkingMap[numberOfParkings];

		Map<String, Object> parkingMapMap = parkingMapAsArrList.get(0);
		for (int i = 1; i <= arr1d.length; i++) {
			String currKey = "c" + i;
			String currValueString = (String) parkingMapMap.get(currKey);
			arr1d[i-1] = DBConstants.ParkingMap.convertStringToParkingMapEnum(currValueString);

		}
		return arr1d;
	}
	
//	TODO: not usable for now.
	/**
	 * Db parking map to 1 D array.
	 *
	 * @param parkingMap3D the parking map 3 D
	 * @param parkingMap1D the parking map 1 D
	 */
	public static void dbParkingMapTo1DArray(DBConstants.ParkingMap [][][] parkingMap3D, DBConstants.ParkingMap [] parkingMap1D) {

		int oneDIndex = 0;
		int x = parkingMap3D.length;
		int y = parkingMap3D[1].length;
		int z = parkingMap3D[0][1].length;
		for (int i = 0; i < x ; i++) {
			for (int j = 0; j < y; j++) {
				for (int k = 0; k < z; k++) {
					parkingMap1D[oneDIndex] = parkingMap3D[i][j][k];
				}
			}
		}
	}
	
	
	/**
	 * Calc 1 D to 3 D.
	 *
	 * @param oneDIndex the index to be converted to 3D indices.
	 * @param dimy the dimension of y (number of columns in the calculated index of the parkingLot).
	 * @return the map - map: { x: idxX, y: idxY, z: idxZ }
	 */
	public static Map<Character, Integer> calc1IndexDTo3DIndices(int oneDIndex, int dimy) {
		//TODO:  check
//		int size = dimx * dimy * dimz;
//		int dimz = 3;
		int dimx = 3;
		int z = (int) Math.ceil( oneDIndex / (dimx * dimy));
		int normalizeFloor = Math.abs(((z-1) * dimx * dimy) - oneDIndex);
		int x = (int)Math.ceil(normalizeFloor / dimy);
		int y = Math.abs(((x-1) * dimy) - normalizeFloor);
		Map<Character, Integer> lotDimensions3D = new HashMap<Character, Integer>();
		lotDimensions3D.put('x', x);
		lotDimensions3D.put('y', y);
		lotDimensions3D.put('z', z);
		return lotDimensions3D;
	}
	
	
//	TODO: test this
	/**
	 * Calc 3 D to 1 D.
	 *
	 * @param x the x index
	 * @param y the y index
	 * @param z the z index
	 * @param dimy the number of columns in the current lotId
	 * @return the int
	 */
	public static int calc3DTo1D(int x, int y, int z, int dimy) {
		//TODO:  check
//		int dimz = 3;
		int dimx = 3;
		int res = (z * (dimx * dimy)/*this takes me to the right floor*/) + (x * dimy /*this takes me to the right row*/) + y;
		return res;
	}
	
	
	/**
	 * Yesterday.
	 *	return the date of yesterday.
	 * @return the java.sql. date
	 */
	public static java.sql.Date yesterday() {
	    final Calendar cal = Calendar.getInstance();
	    cal.add(Calendar.DATE, -1);
		java.sql.Date yesterday = new java.sql.Date(cal.getTimeInMillis());
	    return yesterday;
	}
	
	
	/**
	 * Gets the yesterday date string.
	 *
	 * @return the yesterday date string
	 */
	public static String getYesterdayDateString() {
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		return dateFormat.format(yesterday());
	}

	/**
	 * Prints the all in result set.
	 *
	 * @param resultList the result list
	 */
	public static void printAllInResultSet(ArrayList<Map<String, Object>> resultList) {
		Iterator<Map<String, Object>> iterator = resultList.iterator();
		while (iterator.hasNext()) {
			Map<String, Object> row = (Map<String, Object>) iterator.next();
			for (Map.Entry<String, Object> column : row.entrySet()) {
				System.out.println(column.getKey() + "/" + column.getValue());
			}
		}
	}

	/**
	 * Gets the time stamp of date.
	 *
	 * @param date the date
	 * @return the time stamp of date
	 */
	public static Timestamp getTimeStampOfDate(Date date) {
		
		Timestamp ts = new Timestamp((date).getTime());
		String dateString = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(ts);
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		Date parsedDate;
		try {
			parsedDate = dateFormat.parse(dateString);
			Timestamp timestamp = new java.sql.Timestamp(parsedDate.getTime());
			return timestamp;
		} catch (ParseException e) {
			e.printStackTrace();
			System.out.println("You tried to convert some object to TimeStamp. Check your query types.");
		}

		return null;
//		return new java.sql.Timestamp(parsedDate.getTime());
		
	}
	
	/**
	 * Gets the today.
	 *
	 * @return the today
	 */
	public static java.sql.Date getToday () {
		Calendar calendar = new GregorianCalendar();
		return new java.sql.Date(calendar.getTimeInMillis());
	}
	
	/**
	 * Gets the last week.
	 *
	 * @return the last week
	 */
	public static java.sql.Date getLastWeek () {
		Calendar calendar = new GregorianCalendar();
		java.sql.Date today = new java.sql.Date(calendar.getTimeInMillis());
		calendar.add(Calendar.DATE, -7); //get a week back
		java.sql.Date lastWeekDate = new java.sql.Date(calendar.getTimeInMillis());
		return lastWeekDate;
	}
	
	/**
	 * Gets the last day.
	 *
	 * @return the last day
	 */
	public static java.sql.Date getLastDay () {
		Calendar calendar = new GregorianCalendar();
		java.sql.Date today = new java.sql.Date(calendar.getTimeInMillis());
		calendar.add(Calendar.DATE, -1); //get a week back
		java.sql.Date lastWeekDate = new java.sql.Date(calendar.getTimeInMillis());
		return lastWeekDate;
	}	
	
	
	/**
	 * Calc mean.
	 *
	 * @param resultList the result list
	 * @param columnName the column name
	 * @return the int
	 */
	public static int calcMean (ArrayList<Map<String, Object>> resultList, String columnName ) {
		ArrayList<Integer> list = new ArrayList<Integer>();
		Iterator<Map<String, Object>> iterator = resultList.iterator();
		while (iterator.hasNext()) {
			Map<String, Object> row = (Map<String, Object>) iterator.next();
			list.add((Integer)row.get(columnName));
		}
		Collections.sort(list);
		return list.get(3);
	}
	
	/**
	 * Calc avg.
	 *
	 * @param resultList the result list
	 * @param columnName the column name
	 * @return the int
	 */
	public static int calcAvg (ArrayList<Map<String, Object>> resultList, String columnName ) {
		int sum = 0;
		ArrayList<Integer> list = new ArrayList<Integer>();
		Iterator<Map<String, Object>> iterator = resultList.iterator();
		while (iterator.hasNext()) {
			Map<String, Object> row = (Map<String, Object>) iterator.next();
			list.add((Integer)row.get(columnName));
			sum += (Integer)row.get(columnName);
		}
		return sum / resultList.size();
	}
	
	/**
	 * Calc std.
	 *
	 * @param resultList the result list
	 * @param columnName the column name
	 * @return the double
	 */
	public static double calcStd (ArrayList<Map<String, Object>> resultList, String columnName ) {
		ArrayList<Integer> list = new ArrayList<Integer>();
		Iterator<Map<String, Object>> iterator = resultList.iterator();
		while (iterator.hasNext()) {
			Map<String, Object> row = (Map<String, Object>) iterator.next();
			list.add((Integer)row.get(columnName));
		}
		Collections.sort(list);
		int max = list.get(6);
		int min = list.get(0);
		int mean = list.get(3);
		if (max - mean > mean - min)
			return Math.sqrt(max - mean);
		else 
			return Math.sqrt(mean - min);
	}	

	
	

	
}
