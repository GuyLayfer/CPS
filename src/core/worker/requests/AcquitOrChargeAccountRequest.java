package core.worker.requests;

// TODO: Auto-generated Javadoc
/**
 * The Class AcquitOrChargeAccountRequest.
 */
public class AcquitOrChargeAccountRequest extends BaseRequest {
	
	/** The account id. */
	public int accountId;
	
	/** The amount. */
	public double amount; // Negative value to charge account, positive value to acquit.
}
