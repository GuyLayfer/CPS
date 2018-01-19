package server.rates;

import java.sql.SQLException;
import java.util.Date;

import server.db.DBConstants.OrderType;

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
	public double calculateOccasional(int lotId, Date arrivalTime, Date estimatedDepartureTime) {
		double rate = ratesManager.getOccasionalParkingRate(lotId);
		// parkTimeInMilli / parkTimeInHours is the total estimated parking time
		long parkTimeInMilli = estimatedDepartureTime.getTime() - arrivalTime.getTime();
		double parkTimeInHours = (parkTimeInMilli / 60000) / 60;
		return rate*parkTimeInHours;
	}
	public double calculatePreOrdered(int lotId, Date arrivalTime, Date estimatedDepartureTime) {
		double rate = ratesManager.getPreOrderedParkingRate(lotId);
		// parkTimeInMilli / parkTimeInHours is the total estimated parking time
		long parkTimeInMilli = estimatedDepartureTime.getTime() - arrivalTime.getTime();
		double parkTimeInHours = (parkTimeInMilli / 60000) / 60;
		return rate*parkTimeInHours;
	}
	public double calculateCancelRefund(int lotId, Date estimatedArrivalTime, Date estimatedDepartureTime) {
		// Only for preOrdered parking!!
		double rate = ratesManager.getPreOrderedParkingRate(lotId);
		// parkTimeInMilli / parkTimeInHours is the total estimated parking time
		long parkTimeInMilli = estimatedDepartureTime.getTime() - estimatedArrivalTime.getTime();
		double parkTimeInHours = (parkTimeInMilli / 60000) / 60;
		double originalPrice = rate*parkTimeInHours;
		Date rightNow = new Date();
		long timeFromArrivalInMilli = estimatedArrivalTime.getTime() - rightNow.getTime();
		double timeFromArrivalInHours = (timeFromArrivalInMilli / 60000) / 60;
		// in case that more than 3 hours before parking - 90% refund
		if (timeFromArrivalInHours > 3) {
			return originalPrice*0.9;
		}
		// in case that more than 1 hour before parking - 50% refund
		if (timeFromArrivalInHours > 1) {
			return originalPrice*0.5;
		}
		// less than 1 hour before parking - no refund!
		return 0;
	}
	public double calculateMonthly(int lotId) {
		return ratesManager.getRoutineMonthlySubscription(lotId);
	}
	public double calculateMonthlyMultipleCars(int lotId, int carsNum) {
		return ratesManager.getRoutineMonthlySubscriptionMultipleCars(lotId) * carsNum;
	}
	public double calculateFullMonthly() {
		return ratesManager.getFullMonthlySubscription();
	}
	public double calculateExit() {
		// TODO
		return 0.0;
	}
}
