package server;
import server.db.dbAPI.RegularDBAPI;
import core.Rates;

import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;
import java.util.Map;


/**
 *  A Singleton which provides all the required functions for managing and changing the rates.
 */
public class RatesManager {
	private static volatile RatesManager instance = null;
	
	private ConcurrentHashMap<Integer, Rates> ratesMap;
	final private RegularDBAPI regularDBAPI = RegularDBAPI.getInstance();
	
	private RatesManager() throws SQLException {
		HashMap<Integer, String> rateJsons = new HashMap<Integer, String>();
		regularDBAPI.selectAllRates(rateJsons);
		//TODO: implement
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
	public Rates getRate(int LotId) {
		return this.ratesMap.get(LotId);
	}
	
	public double getOccasionalRate(int LotId) {
		return this.ratesMap.get(LotId).getOccasionalRate();
	}
	public double getOneTimeRate(int LotId) {
		return this.ratesMap.get(LotId).getOneTimeRate();
	}
	public double getRoutineMonthlyRate(int LotId) {
		return this.ratesMap.get(LotId).getRoutineMonthlyRate();
	}
	public double getRoutineMonthlyMultipleCarsRate(int LotId) {
		return this.ratesMap.get(LotId).getRoutineMonthlyMultipleCarsRate();
	}
}
