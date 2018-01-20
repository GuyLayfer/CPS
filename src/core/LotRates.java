package core;
import core.worker.Rates;

public class LotRates {
	private double occasionalParkingRate;
	private double preOrderedParkingRate;
	private double routineMonthlySubscription;
	private double routineMonthlySubscriptionMultipleCars;
	private static Double fullMonthlySubscription;
	
	final public double DEFAULT_OCCASIONAL_RATE = 5.0;
	final public double DEFAULT_ONE_TIME_RATE = 4.0;
	final public double DEFAULT_ROUTINE_MONTHLY_RATE = 240.0;
	final public double DEFAULT_ROUTINE_MONTHLY_MULTIPLE_CARS_RATE = 216.0;
	final public double DEFAULT_FULL_MONTHLY_RATE = 288.0;
	
	public LotRates() {
		this.occasionalParkingRate = DEFAULT_OCCASIONAL_RATE;
		this.preOrderedParkingRate = DEFAULT_ONE_TIME_RATE;
		this.routineMonthlySubscription = DEFAULT_ROUTINE_MONTHLY_RATE;
		this.routineMonthlySubscriptionMultipleCars = DEFAULT_ROUTINE_MONTHLY_MULTIPLE_CARS_RATE;
		//this.fullMonthlySubscription = (Double)DEFAULT_FULL_MONTHLY_RATE;
	}
	public LotRates(Rates rates) {
		this.occasionalParkingRate = rates.occasionalParkingRate;
		this.preOrderedParkingRate = rates.preOrderedParkingRate;
		this.routineMonthlySubscription = rates.routineMonthlySubscription;
		this.routineMonthlySubscriptionMultipleCars = rates.routineMonthlySubscriptionMultipleCars;
	}
	public LotRates(double occasional, double preOrdered, double monthly, double monthlyMultipleCars) {
		this.occasionalParkingRate = occasional;
		this.preOrderedParkingRate = preOrdered;
		this.routineMonthlySubscription = monthly;
		this.routineMonthlySubscriptionMultipleCars = monthlyMultipleCars;
	}
	
	synchronized public double getOccasionalParkingRate() {
		return occasionalParkingRate;
	}
	synchronized public double getPreOrderedParkingRate() {
		return preOrderedParkingRate;
	}
	synchronized public double getRoutineMonthlySubscription() {
		return routineMonthlySubscription;
	}
	synchronized public double getRoutineMonthlySubscriptionMultipleCars() {
		return routineMonthlySubscriptionMultipleCars;
	}
	public static Double getFullMonthlySubscription() {
		synchronized(fullMonthlySubscription) {
			return fullMonthlySubscription;
		}
	}
	synchronized public void setOccasionalParkingRate(double newOccasionalParkingRate) {
		this.occasionalParkingRate = newOccasionalParkingRate;
	}
	synchronized public void setPreOrderedParkingRate(double newPreOrderedParkingRate) {
		this.preOrderedParkingRate = newPreOrderedParkingRate;
	}
	synchronized public void setRoutineMonthlySubscription(double newRoutineMonthlySubscription) {
		this.routineMonthlySubscription = newRoutineMonthlySubscription;
	}
	synchronized public void setRoutineMonthlySubscriptionMultipleCars(double newRoutineMonthlySubscriptionMultipleCars) {
		this.routineMonthlySubscriptionMultipleCars = newRoutineMonthlySubscriptionMultipleCars;
	}
	public static void setFullMonthlySubscription(Double fullMonthlySubscription) {
		synchronized(fullMonthlySubscription) {
			LotRates.fullMonthlySubscription = fullMonthlySubscription;
		}
	}
}
