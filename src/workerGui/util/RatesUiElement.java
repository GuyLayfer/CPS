package workerGui.util;

import core.worker.Rates;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class RatesUiElement {
	private SimpleIntegerProperty parkingLotId = new SimpleIntegerProperty(this, "parkingLotId");
	private SimpleDoubleProperty occasionalParkingRate = new SimpleDoubleProperty(this, "occasionalParkingRate");
	private SimpleDoubleProperty preOrderedParkingRate = new SimpleDoubleProperty(this, "preOrderedParkingRate");
	private SimpleDoubleProperty routineMonthlySubscriptionRate = new SimpleDoubleProperty(this, "routineMonthlySubscriptionRate");
	private SimpleDoubleProperty fullMonthlySubscriptionRate = new SimpleDoubleProperty(this, "fullMonthlySubscriptionRate");
	private Rates rates;

	public RatesUiElement(Rates rates) {
		this.parkingLotId.set(rates.parkingLotId);
		this.occasionalParkingRate.set(rates.occasionalParkingRate);
		this.preOrderedParkingRate.set(rates.preOrderedParkingRate);
		this.routineMonthlySubscriptionRate.set(rates.routineMonthlySubscription);
		this.fullMonthlySubscriptionRate.set(rates.fullMonthlySubscription);
		this.rates = rates;
	}

	public int getParkingLotId() {
		return parkingLotId.get();
	}

	public double getOccasionalParkingRate() {
		return occasionalParkingRate.get();
	}

	public double getPreOrderedParkingRate() {
		return preOrderedParkingRate.get();
	}

	public double getRoutineMonthlySubscriptionRate() {
		return routineMonthlySubscriptionRate.get();
	}

	public double getFullMonthlySubscriptionRate() {
		return fullMonthlySubscriptionRate.get();
	}

	public void setParkingLotId(int parkingLotId) {
		this.parkingLotId.set(parkingLotId);
	}

	public void setOccasionalParkingRate(double occasionalParkingRate) {
		this.occasionalParkingRate.set(occasionalParkingRate);
	}

	public void setPreOrderedParkingRate(double preOrderedParkingRate) {
		this.preOrderedParkingRate.set(preOrderedParkingRate);
	}

	public void setRoutineMonthlySubscriptionRate(double routineMonthlySubscriptionRate) {
		this.routineMonthlySubscriptionRate.set(routineMonthlySubscriptionRate);
	}

	public void setFullMonthlySubscriptionRate(double fullMonthlySubscriptionRate) {
		this.fullMonthlySubscriptionRate.set(fullMonthlySubscriptionRate);
	}

	public Rates getValue() {
		return this.rates;
	}

	@Override
	public String toString() {
		return Integer.toString(getParkingLotId());
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;

		RatesUiElement ratesElement = (RatesUiElement) o;

		return parkingLotId != null ? parkingLotId.equals(ratesElement.parkingLotId) : ratesElement.parkingLotId == null;
	}

	@Override
	public int hashCode() {
		return parkingLotId != null ? parkingLotId.hashCode() : 0;
	}
}
