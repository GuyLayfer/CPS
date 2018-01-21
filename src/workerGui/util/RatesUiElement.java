package workerGui.util;

import core.worker.Rates;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;

// TODO: Auto-generated Javadoc
/**
 * The Class RatesUiElement.
 */
public class RatesUiElement {
	
	/** The parking lot id. */
	private SimpleIntegerProperty parkingLotId = new SimpleIntegerProperty(this, "parkingLotId");
	
	/** The occasional parking rate. */
	private SimpleDoubleProperty occasionalParkingRate = new SimpleDoubleProperty(this, "occasionalParkingRate");
	
	/** The pre ordered parking rate. */
	private SimpleDoubleProperty preOrderedParkingRate = new SimpleDoubleProperty(this, "preOrderedParkingRate");
	
	/** The routine monthly subscription rate. */
	private SimpleDoubleProperty routineMonthlySubscriptionRate = new SimpleDoubleProperty(this, "routineMonthlySubscriptionRate");
	
	/** The routine monthly subscription multiple cars rate. */
	private SimpleDoubleProperty routineMonthlySubscriptionMultipleCarsRate = new SimpleDoubleProperty(this, "routineMonthlySubscriptionMultipleCarsRate");
	
	/** The full monthly subscription rate. */
	private SimpleDoubleProperty fullMonthlySubscriptionRate = new SimpleDoubleProperty(this, "fullMonthlySubscriptionRate");
	
	/** The rates. */
	private Rates rates;

	/**
	 * Instantiates a new rates ui element.
	 *
	 * @param rates the rates
	 */
	public RatesUiElement(Rates rates) {
		this.parkingLotId.set(rates.parkingLotId);
		this.occasionalParkingRate.set(rates.occasionalParkingRate);
		this.preOrderedParkingRate.set(rates.preOrderedParkingRate);
		this.routineMonthlySubscriptionRate.set(rates.routineMonthlySubscription);
		this.routineMonthlySubscriptionMultipleCarsRate.set(rates.routineMonthlySubscriptionMultipleCars);
		this.fullMonthlySubscriptionRate.set(rates.fullMonthlySubscription);
		this.rates = rates;
	}

	/**
	 * Gets the parking lot id.
	 *
	 * @return the parking lot id
	 */
	public int getParkingLotId() {
		return parkingLotId.get();
	}

	/**
	 * Gets the occasional parking rate.
	 *
	 * @return the occasional parking rate
	 */
	public double getOccasionalParkingRate() {
		return occasionalParkingRate.get();
	}

	/**
	 * Gets the pre ordered parking rate.
	 *
	 * @return the pre ordered parking rate
	 */
	public double getPreOrderedParkingRate() {
		return preOrderedParkingRate.get();
	}

	/**
	 * Gets the routine monthly subscription rate.
	 *
	 * @return the routine monthly subscription rate
	 */
	public double getRoutineMonthlySubscriptionRate() {
		return routineMonthlySubscriptionRate.get();
	}
	
	/**
	 * Gets the routine monthly subscription multiple cars rate.
	 *
	 * @return the routine monthly subscription multiple cars rate
	 */
	public double getRoutineMonthlySubscriptionMultipleCarsRate() {
		return routineMonthlySubscriptionMultipleCarsRate.get();
	}

	/**
	 * Gets the full monthly subscription rate.
	 *
	 * @return the full monthly subscription rate
	 */
	public double getFullMonthlySubscriptionRate() {
		return fullMonthlySubscriptionRate.get();
	}

	/**
	 * Sets the parking lot id.
	 *
	 * @param parkingLotId the new parking lot id
	 */
	public void setParkingLotId(int parkingLotId) {
		this.parkingLotId.set(parkingLotId);
	}

	/**
	 * Sets the occasional parking rate.
	 *
	 * @param occasionalParkingRate the new occasional parking rate
	 */
	public void setOccasionalParkingRate(double occasionalParkingRate) {
		this.occasionalParkingRate.set(occasionalParkingRate);
	}

	/**
	 * Sets the pre ordered parking rate.
	 *
	 * @param preOrderedParkingRate the new pre ordered parking rate
	 */
	public void setPreOrderedParkingRate(double preOrderedParkingRate) {
		this.preOrderedParkingRate.set(preOrderedParkingRate);
	}

	/**
	 * Sets the routine monthly subscription rate.
	 *
	 * @param routineMonthlySubscriptionRate the new routine monthly subscription rate
	 */
	public void setRoutineMonthlySubscriptionRate(double routineMonthlySubscriptionRate) {
		this.routineMonthlySubscriptionRate.set(routineMonthlySubscriptionRate);
	}
	
	/**
	 * Sets the routine monthly subscription multiple cars rate.
	 *
	 * @param routineMonthlySubscriptionMultipleCarsRate the new routine monthly subscription multiple cars rate
	 */
	public void setRoutineMonthlySubscriptionMultipleCarsRate(double routineMonthlySubscriptionMultipleCarsRate) {
		this.routineMonthlySubscriptionMultipleCarsRate.set(routineMonthlySubscriptionMultipleCarsRate);
	}

	/**
	 * Sets the full monthly subscription rate.
	 *
	 * @param fullMonthlySubscriptionRate the new full monthly subscription rate
	 */
	public void setFullMonthlySubscriptionRate(double fullMonthlySubscriptionRate) {
		this.fullMonthlySubscriptionRate.set(fullMonthlySubscriptionRate);
	}

	/**
	 * Gets the value.
	 *
	 * @return the value
	 */
	public Rates getValue() {
		return this.rates;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return Integer.toString(getParkingLotId());
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;

		RatesUiElement ratesElement = (RatesUiElement) o;

		return parkingLotId != null ? parkingLotId.equals(ratesElement.parkingLotId) : ratesElement.parkingLotId == null;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return parkingLotId != null ? parkingLotId.hashCode() : 0;
	}
}
