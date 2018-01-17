package core;

public class Rates {
	double occasionalRate;
	double oneTimeRate;
	double routineMonthlyRate;
	double routineMonthlyMultipleCarsRate;
	
	public Rates() {
		this.occasionalRate = 5;
		this.oneTimeRate = 4;
		this.routineMonthlyRate = 0;
		this.routineMonthlyMultipleCarsRate = 0;
	}
	public double getOccasionalRate() {
		return occasionalRate;
	}
	public double getOneTimeRate() {
		return oneTimeRate;
	}
	public double getRoutineMonthlyRate() {
		return routineMonthlyRate;
	}
	public double getRoutineMonthlyMultipleCarsRate() {
		return routineMonthlyMultipleCarsRate;
	}
	public void setOccasionalRate(double occasionalRate) {
		this.occasionalRate = occasionalRate;
	}
	public void setOneTimeRate(double oneTimeRate) {
		this.oneTimeRate = oneTimeRate;
	}
	public void setRoutineMonthlyRate(double routineMonthlyRate) {
		this.routineMonthlyRate = routineMonthlyRate;
	}
	public void setRoutineMonthlyMultipleCarsRate(double routineMonthlyMultipleCarsRate) {
		this.routineMonthlyMultipleCarsRate = routineMonthlyMultipleCarsRate;
	}

	
}
