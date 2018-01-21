package core.worker.requests;

import core.worker.Rates;

// TODO: Auto-generated Javadoc
/**
 * The Class DecideOnRateRequest.
 */
public class DecideOnRateRequest extends BaseRequest {
	
	/** The rates to decide. */
	public Rates ratesToDecide;
	
	/** The is new rate approved. */
	public Boolean isNewRateApproved;
}
