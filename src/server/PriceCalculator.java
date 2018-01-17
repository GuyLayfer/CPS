package server;

import java.sql.SQLException;

import server.RatesManager;

public class PriceCalculator {
	
	private static volatile PriceCalculator instance = null;
	
	final private RatesManager ratesManager;
	
	private PriceCalculator() throws SQLException {
		this.ratesManager = RatesManager.getInstance();
	}
	
	/**
	 * Initializer - used only once in CPSMain.
	 *
	 * @throws SQLException the SQL exception
	 */
	public static void initialize() throws SQLException {
		if (instance == null) {
			instance = new PriceCalculator();
		}
	}
	
	/**
	 * Gets the single instance of RatesManager.
	 *
	 * @return single instance of RatesManager
	 */
	public static PriceCalculator getInstance() {
		return instance;
	}
}
