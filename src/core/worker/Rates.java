package core.worker;

// TODO: Auto-generated Javadoc
/**
 * The Class Rates.
 */
public class Rates {

	/**
	 * Instantiates a new rates.
	 *
	 * @param parkingLotId the parking lot id
	 * @param occasionalParkingRate the occasional parking rate
	 * @param preOrderedParkingRate the pre ordered parking rate
	 * @param routineMonthlySubscription the routine monthly subscription
	 * @param routineMonthlySubscriptionMultipleCars the routine monthly subscription multiple cars
	 * @param fullMonthlySubscription the full monthly subscription
	 */
	public Rates(
			int parkingLotId,
			double occasionalParkingRate,
			double preOrderedParkingRate,
			double routineMonthlySubscription,
			double routineMonthlySubscriptionMultipleCars,
			double fullMonthlySubscription) {
		this.parkingLotId = parkingLotId;
		this.occasionalParkingRate = occasionalParkingRate;
		this.preOrderedParkingRate = preOrderedParkingRate;
		this.routineMonthlySubscription = routineMonthlySubscription;
		this.routineMonthlySubscriptionMultipleCars = routineMonthlySubscriptionMultipleCars;
		this.fullMonthlySubscription = fullMonthlySubscription;
	}

	/** The parking lot id. */
	public int parkingLotId;

	/** The occasional parking rate. */
	public double occasionalParkingRate;

	/** The pre ordered parking rate. */
	public double preOrderedParkingRate;

	/** The routine monthly subscription. */
	public double routineMonthlySubscription;
	
	/** The routine monthly subscription multiple cars. */
	public double routineMonthlySubscriptionMultipleCars;

	/** The full monthly subscription. */
	public double fullMonthlySubscription;
}
