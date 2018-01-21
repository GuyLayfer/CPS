package core;
import core.worker.Rates;

// TODO: Auto-generated Javadoc
/**
 * The Class LotRates.
 */
public class LotRates {
	
	/** The occasional parking rate. */
	private double occasionalParkingRate;
	
	/** The pre ordered parking rate. */
	private double preOrderedParkingRate;
	
	/** The routine monthly subscription. */
	private double routineMonthlySubscription;
	
	/** The routine monthly subscription multiple cars. */
	private double routineMonthlySubscriptionMultipleCars;
	
	/** The full monthly subscription. */
	private static Double fullMonthlySubscription;
	
	/** The default occasional rate. */
	final public double DEFAULT_OCCASIONAL_RATE = 5.0;
	
	/** The default one time rate. */
	final public double DEFAULT_ONE_TIME_RATE = 4.0;
	
	/** The default routine monthly rate. */
	final public double DEFAULT_ROUTINE_MONTHLY_RATE = 240.0;
	
	/** The default routine monthly multiple cars rate. */
	final public double DEFAULT_ROUTINE_MONTHLY_MULTIPLE_CARS_RATE = 216.0;
	
	/** The default full monthly rate. */
	final public double DEFAULT_FULL_MONTHLY_RATE = 288.0;
	
	/**
	 * Instantiates a new lot rates.
	 */
	public LotRates() {
		this.occasionalParkingRate = DEFAULT_OCCASIONAL_RATE;
		this.preOrderedParkingRate = DEFAULT_ONE_TIME_RATE;
		this.routineMonthlySubscription = DEFAULT_ROUTINE_MONTHLY_RATE;
		this.routineMonthlySubscriptionMultipleCars = DEFAULT_ROUTINE_MONTHLY_MULTIPLE_CARS_RATE;
		//this.fullMonthlySubscription = (Double)DEFAULT_FULL_MONTHLY_RATE;
	}
	
	/**
	 * Instantiates a new lot rates.
	 *
	 * @param rates the rates
	 */
	public LotRates(Rates rates) {
		this.occasionalParkingRate = rates.occasionalParkingRate;
		this.preOrderedParkingRate = rates.preOrderedParkingRate;
		this.routineMonthlySubscription = rates.routineMonthlySubscription;
		this.routineMonthlySubscriptionMultipleCars = rates.routineMonthlySubscriptionMultipleCars;
	}
	
	/**
	 * Instantiates a new lot rates.
	 *
	 * @param occasional the occasional
	 * @param preOrdered the pre ordered
	 * @param monthly the monthly
	 * @param monthlyMultipleCars the monthly multiple cars
	 */
	public LotRates(double occasional, double preOrdered, double monthly, double monthlyMultipleCars) {
		this.occasionalParkingRate = occasional;
		this.preOrderedParkingRate = preOrdered;
		this.routineMonthlySubscription = monthly;
		this.routineMonthlySubscriptionMultipleCars = monthlyMultipleCars;
	}
	
	/**
	 * Gets the occasional parking rate.
	 *
	 * @return the occasional parking rate
	 */
	synchronized public double getOccasionalParkingRate() {
		return occasionalParkingRate;
	}
	
	/**
	 * Gets the pre ordered parking rate.
	 *
	 * @return the pre ordered parking rate
	 */
	synchronized public double getPreOrderedParkingRate() {
		return preOrderedParkingRate;
	}
	
	/**
	 * Gets the routine monthly subscription.
	 *
	 * @return the routine monthly subscription
	 */
	synchronized public double getRoutineMonthlySubscription() {
		return routineMonthlySubscription;
	}
	
	/**
	 * Gets the routine monthly subscription multiple cars.
	 *
	 * @return the routine monthly subscription multiple cars
	 */
	synchronized public double getRoutineMonthlySubscriptionMultipleCars() {
		return routineMonthlySubscriptionMultipleCars;
	}
	
	/**
	 * Gets the full monthly subscription.
	 *
	 * @return the full monthly subscription
	 */
	public static Double getFullMonthlySubscription() {
		synchronized(fullMonthlySubscription) {
			return fullMonthlySubscription;
		}
	}
	
	/**
	 * Sets the occasional parking rate.
	 *
	 * @param newOccasionalParkingRate the new occasional parking rate
	 */
	synchronized public void setOccasionalParkingRate(double newOccasionalParkingRate) {
		this.occasionalParkingRate = newOccasionalParkingRate;
	}
	
	/**
	 * Sets the pre ordered parking rate.
	 *
	 * @param newPreOrderedParkingRate the new pre ordered parking rate
	 */
	synchronized public void setPreOrderedParkingRate(double newPreOrderedParkingRate) {
		this.preOrderedParkingRate = newPreOrderedParkingRate;
	}
	
	/**
	 * Sets the routine monthly subscription.
	 *
	 * @param newRoutineMonthlySubscription the new routine monthly subscription
	 */
	synchronized public void setRoutineMonthlySubscription(double newRoutineMonthlySubscription) {
		this.routineMonthlySubscription = newRoutineMonthlySubscription;
	}
	
	/**
	 * Sets the routine monthly subscription multiple cars.
	 *
	 * @param newRoutineMonthlySubscriptionMultipleCars the new routine monthly subscription multiple cars
	 */
	synchronized public void setRoutineMonthlySubscriptionMultipleCars(double newRoutineMonthlySubscriptionMultipleCars) {
		this.routineMonthlySubscriptionMultipleCars = newRoutineMonthlySubscriptionMultipleCars;
	}
	
	/**
	 * Sets the full monthly subscription.
	 *
	 * @param fullMonthlySubscription the new full monthly subscription
	 */
	public static void setFullMonthlySubscription(Double fullMonthlySubscription) {
		synchronized(fullMonthlySubscription) {
			LotRates.fullMonthlySubscription = fullMonthlySubscription;
		}
	}
}
