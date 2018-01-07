package server.db.dbAPI;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import server.db.DBConstants;

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
	public static DBConstants.parkingMap [] dbParkingMapTo1DArray(ArrayList<Map<String, Object>> parkingMapAsArrList) {
		
//		int countRows = 0;
//		int x = 0, y = 0, z = 0;
		
		int numberOfParkings = ((parkingMapAsArrList.get(0)).entrySet()).size();
		DBConstants.parkingMap arr1d[] = new DBConstants.parkingMap[numberOfParkings];
		Map<String, Object> parkingMapMap = parkingMapAsArrList.get(0);
		for (int i = 1; i <= arr1d.length; i++) {
			String currKey = "c" + i;
			String currValueString = (String) parkingMapMap.get(currKey);
			arr1d[i-1] = DBConstants.parkingMap.convertStringToParkingMapEnum(currValueString);
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
	public static void dbParkingMapTo1DArray(DBConstants.parkingMap [][][] parkingMap3D, DBConstants.parkingMap [] parkingMap1D) {
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
	 * Checks if is subscription active.
	 * compares the expired date entity and today.
	 * @param subscriptionId the subscription id
	 * @return true, if is subscription active
	 * @throws SQLException the SQL exception
	 */
	public static boolean isSubscriptionActive(int subscriptionId) throws SQLException {
		ArrayList<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
		SubscriptionsDBAPI.selectSubscriptionDetails(subscriptionId, resultList);
		Map<String, Object> map = (Map<String, Object>) resultList.get(0);
		Date expiredDate = (Date) map.get("expired_date");
	    long  expiredDateInMillis = expiredDate.getTime();
		if (expiredDateInMillis > System.currentTimeMillis()) {
			return true;			
		}
		else
			return false;
	}
	
}
