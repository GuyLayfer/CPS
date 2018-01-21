package core.worker.requests;

import core.worker.Complaint;

// TODO: Auto-generated Javadoc
/**
 * The Class DecideOnComplaintRequest.
 */
public class DecideOnComplaintRequest extends BaseRequest {

	/** The complaint to decide. */
	public Complaint complaintToDecide;

	/** The is complaint approved. */
	public Boolean isComplaintApproved;

	/** The amount to acquit. */
	public double amountToAcquit; // Only applicable when isComplaintApproved == true.
}
