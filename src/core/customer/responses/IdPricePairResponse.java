package core.customer.responses;

public class IdPricePairResponse extends CustomerBaseResponse {
	public int id;
	public double price;

	public IdPricePairResponse(int id, double price) {
		this.id = id;
		this.price = price;
	}

	@Override
	public String toString() {
		return "Your order number is " + id
				+ ".\nYour account will be charged with " + price
				+ "NIS.\nThank you for choosing CPS.";
	}
}
