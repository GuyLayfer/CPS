package core.worker.requests;

import core.worker.Rates;

public class DecideOnRateRequest extends BaseRequest {
	
	public Rates ratesToDecide;
	
	public Boolean isNewRateApproved;
}
