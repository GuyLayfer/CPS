package core.worker.requests;

public class AcquitOrChargeAccountRequest extends BaseRequest {
	
	public int accountId;
	
	public double amount; // Negative value to charge account, positive value to acquit.
}
