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
		workersDBAPI.selectAllLotsRates(false, resultList);
		this.ratesMap = new ConcurrentHashMap<Integer, LotRates>();
		for (int i = 0; i < resultList.size(); i++) {
			int lotId = (int)resultList.get(i).get(SqlColumns.Rates.LOT_ID);
			double occasional = (double)resultList.get(i).get(SqlColumns.Rates.OCCASIONAL);
			double preOrdered = (double)resultList.get(i).get(SqlColumns.Rates.PRE_ORDERED);
			double monthly = (double)resultList.get(i).get(SqlColumns.Rates.SUBSCRIPTION);
			double monthlyMultipleCars = (double)resultList.get(i).get(SqlColumns.Rates.SUBSCRIPTION_MULTIPLE_CARS);
			LotRates newLotRates = new LotRates(occasional, preOrdered, monthly, monthlyMultipleCars);
			this.ratesMap.put(lotId, newLotRates);
		}
		resultList.clear();
		workersDBAPI.selectFullSubscriptionRate(resultList);
		double fullSubscriptionRate = (double)resultList.get(0).get(SqlColumns.FullSubscriptionRate.RATE);
		LotRates.setFullMonthlySubscription(fullSubscriptionRate);
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
		return LotRates.getFullMonthlySubscription();
	}
	public void addRates(int lotId) throws SQLException {
		LotRates toBeAddedLotRates = new LotRates();
		double occasionalRate = toBeAddedLotRates.getOccasionalParkingRate();
		double oneTimRate = toBeAddedLotRates.getPreOrderedParkingRate();
		double monthlylRate = toBeAddedLotRates.getRoutineMonthlySubscription();
		double monthlyMultipleRate = toBeAddedLotRates.getRoutineMonthlySubscriptionMultipleCars();
		double monthlyFull = LotRates.getFullMonthlySubscription();
		workersDBAPI.insertRatesOfLotId(false, lotId, occasionalRate, oneTimRate, monthlyFull,
				monthlylRate, monthlyMultipleRate);
		this.ratesMap.put(lotId, toBeAddedLotRates);
	}
	public void deleteRates(int lotId) {
		//TODO: something with DB
		this.ratesMap.remove(lotId);
	}
	public void insertRatesRequest(Rates newRates) throws SQLException {
		workersDBAPI.insertRatesOfLotId(true, newRates.parkingLotId, newRates.preOrderedParkingRate, newRates.occasionalParkingRate, newRates.fullMonthlySubscription,
				newRates.routineMonthlySubscription, newRates.routineMonthlySubscriptionMultipleCars);
	}
	public void updateApprovedRates(Rates newRates) throws SQLException {
		workersDBAPI.insertRatesOfLotId(false, newRates.parkingLotId, newRates.preOrderedParkingRate, newRates.occasionalParkingRate, newRates.fullMonthlySubscription,
				newRates.routineMonthlySubscription, newRates.routineMonthlySubscriptionMultipleCars);
		workersDBAPI.updateFullSubscriptionRate(newRates.fullMonthlySubscription);
		LotRates newLotRates = new LotRates(newRates);
		this.ratesMap.put(newRates.parkingLotId,newLotRates);
	}
}
