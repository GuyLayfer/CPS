package core;

// Used as response data type when the server needs to send order/subscription id and a price
public class IdPricePair {
	public int id;
	public double price;
	
	public IdPricePair(int id, double price) {
		this.id = id;
		this.price = price;
	}
}
