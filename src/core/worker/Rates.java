package core.worker;

public class Rates {

	public Rates(
			int parkingLotId,
			double occasionalParkingRate,
			double preOrderedParkingRate,
			double routineMonthlySubscription,
			double fullMonthlySubscription) {
		this.parkingLotId = parkingLotId;
		this.occasionalParkingRate = occasionalParkingRate;
		this.preOrderedParkingRate = preOrderedParkingRate;
		this.routineMonthlySubscription = routineMonthlySubscription;
		this.fullMonthlySubscription = fullMonthlySubscription;
	}

	public int parkingLotId;

	public double occasionalParkingRate;

	public double preOrderedParkingRate;

	public double routineMonthlySubscription;

	public double fullMonthlySubscription;
}
