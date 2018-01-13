package core.worker.requests;

import core.worker.Complaint;

public class DecideOnComplaintRequest extends BaseRequest {

	public Complaint complaintToDecide;

	public Boolean isComplaintApproved;

	public double amountToAcquit; // Only applicable when isComplaintApproved == true.
}
