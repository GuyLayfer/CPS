package core.customer.responses;

// TODO: Auto-generated Javadoc
/**
 * The Class IdPricePairResponse.
 */
public class IdPricePairResponse extends CustomerBaseResponse {
	
	/** The id. */
	public int id;
	
	/** The price. */
	public double price;

	/**
	 * Instantiates a new id price pair response.
	 *
	 * @param id the id
	 * @param price the price
	 */
	public IdPricePairResponse(int id, double price) {
		this.id = id;
		this.price = price;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Your order number is " + id
				+ ".\nYour account will be charged with " + price
				+ "NIS.\nThank you for choosing CPS.";
	}
}
