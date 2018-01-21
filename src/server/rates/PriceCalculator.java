package server.rates;

import java.sql.SQLException;
import java.util.Date;

import server.db.DBConstants.OrderType;
import server.db.SqlColumns;

// TODO: Auto-generated Javadoc
/**
 * The Class PriceCalculator.
 */
public class PriceCalculator {
	
	/** The instance. */
	private static volatile PriceCalculator instance = null;
	
	/** The rates manager. */
	final private RatesManager ratesManager;
	
	/**
	 * Instantiates a new price calculator.
	 *
	 * @throws SQLException the SQL exception
	 */
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
	
	/**
	 * Calculate occasional.
	 *
	 * @param lotId the lot id
	 * @param arrivalTime the arrival time
	 * @param estimatedDepartureTime the estimated departure time
	 * @return the double
	 */
	public double calculateOccasional(int lotId, Date arrivalTime, Date estimatedDepartureTime) {
		double rate = ratesManager.getOccasionalParkingRate(lotId);
		// parkTimeInMilli / parkTimeInHours is the total estimated parking time
		long parkTimeInMilli = estimatedDepartureTime.getTime() - arrivalTime.getTime();
		double parkTimeInHours = (parkTimeInMilli / 60000) / 60;
		return rate*parkTimeInHours;
	}
	
	/**
	 * Calculate pre ordered.
	 *
	 * @param lotId the lot id
	 * @param arrivalTime the arrival time
	 * @param estimatedDepartureTime the estimated departure time
	 * @return the double
	 */
	public double calculatePreOrdered(int lotId, Date arrivalTime, Date estimatedDepartureTime) {
		double rate = ratesManager.getPreOrderedParkingRate(lotId);
		// parkTimeInMilli / parkTimeInHours is the total estimated parking time
		long parkTimeInMilli = estimatedDepartureTime.getTime() - arrivalTime.getTime();
		double parkTimeInHours = (parkTimeInMilli / 60000) / 60;
		return rate*parkTimeInHours;
	}
	
	/**
	 * Calculate cancel refund.
	 *
	 * @param lotId the lot id
	 * @param estimatedArrivalTime the estimated arrival time
	 * @param estimatedDepartureTime the estimated departure time
	 * @return the double
	 */
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
	
	/**
	 * Calculate monthly.
	 *
	 * @param lotId the lot id
	 * @return the double
	 */
	public double calculateMonthly(int lotId) {
		return ratesManager.getRoutineMonthlySubscription(lotId);
	}
	
	/**
	 * Calculate monthly multiple cars.
	 *
	 * @param lotId the lot id
	 * @param carsNum the cars num
	 * @return the double
	 */
	public double calculateMonthlyMultipleCars(int lotId, int carsNum) {
		return ratesManager.getRoutineMonthlySubscriptionMultipleCars(lotId) * carsNum;
	}
	
	/**
	 * Calculate full monthly.
	 *
	 * @return the double
	 */
	public double calculateFullMonthly() {
		return ratesManager.getFullMonthlySubscription();
	}
	
	/**
	 * Calculate fine.
	 *
	 * @param orderType the order type
	 * @param lotId the lot id
	 * @param miliLate the mili late
	 * @return the double
	 */
	public double calculateFine(OrderType orderType, int lotId, long miliLate) {
		double rate;
		switch (orderType) {
		case ORDER:
			rate = ratesManager.getOccasionalParkingRate(lotId);
			return rate * (miliLate / 60000) / 60;
		case ONE_TIME:
			rate = ratesManager.getPreOrderedParkingRate(lotId);
			return rate * (miliLate / 60000) / 60;
		default:
			return 0.0;	
		}
	}
}
