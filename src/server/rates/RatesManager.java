package server.rates;
import server.db.dbAPI.WorkersDBAPI;
import server.db.SqlColumns;
import core.LotRates;
import core.worker.Rates;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 *  A Singleton which provides all the required functions for managing and changing the rates.
 */
public class RatesManager {
	private static volatile RatesManager instance = null;
	
	private ConcurrentHashMap<Integer, LotRates> ratesMap;
	final private WorkersDBAPI workersDBAPI = WorkersDBAPI.getInstance();
	
	private RatesManager() throws SQLException {
		ArrayList<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
		/*//workersDBAPI.selectAllLotsRates(false, resultList);
		for (int i = 0; i < resultList.size(); i++) {
			int lotId = resultList.get(i).get(SqlColumns.Rates.LOT_ID);
			double occasional = resultList.get(i).get(SqlColumns.Rates.OCCASIONAL);
			double preOrdered = resultList.get(i).get(SqlColumns.Rates.PRE_ORDERED);
			double monthly = resultList.get(i).get(SqlColumns.Rates.SUBSCRIPTION);
			// TODO: double monthlyMultipleCars = resultList.get(i).get(SqlColumns.Rates.SOMTHING);
			LotRates newLotRates = new LotRates(occasional, preOrdered, monthly, monthlyMultipleCars);
			this.ratesMap.put(lotId, newLotRates);
		}*/
	}
	
	/**
	 * Initializer - used only once in CPSMain.
	 *
	 * @throws SQLException the SQL exception
	 */
	public static void initialize() throws SQLException {
		if (instance == null) {
			instance = new RatesManager();
		}
	}
	
	/**
	 * Gets the single instance of RatesManager.
	 *
	 * @return single instance of RatesManager
	 */
	public static RatesManager getInstance() {
		return instance;
	}
	//public LotRates convertRatesToLotRates(Rates toBeConverted) {
	//	LotRates newLotRates = new LotRates
	//}
	/**
	 * Gets a specific Rate object according to the LotId. 
	 *
	 * @return the Rate object of the specific lot
	 */
	public LotRates getLotRates(int lotId) {
		return this.ratesMap.get(lotId);
	}
	public double getOccasionalParkingRate(int lotId) {
		return this.ratesMap.get(lotId).getOccasionalParkingRate();
	}
	public double getPreOrderedParkingRate(int lotId) {
		return this.ratesMap.get(lotId).getPreOrderedParkingRate();
	}
	public double getRoutineMonthlySubscription(int lotId) {
		return this.ratesMap.get(lotId).getRoutineMonthlySubscription();
	}
	public double getRoutineMonthlySubscriptionMultipleCars(int lotId) {
		return this.ratesMap.get(lotId).getRoutineMonthlySubscriptionMultipleCars();
	}
	public Double getFullMonthlySubscription() {
		return this.getFullMonthlySubscription();
	}
	public void addRates(Rates toBeAddedRates) {
		// TODO: something with DB
		LotRates toBeAddedLotRates = new LotRates(toBeAddedRates);
		this.ratesMap.put(toBeAddedRates.parkingLotId, toBeAddedLotRates);
	}
	public void deleteRates(int lotId) {
		//TODO: something with DB
		this.ratesMap.remove(lotId);
	}
	public void insertRatesRequest(Rates newRates) {
		// TODO: something with DB
	}
	public void updateApprovedRates(Rates newRates) {
		// TODO: something with DB
		LotRates newLotRates = new LotRates(newRates);
		this.ratesMap.put(newRates.parkingLotId,newLotRates);
	}
}
