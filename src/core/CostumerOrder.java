package core;

public class CostumerOrder {
//	Should be added to the classes draft?
//	Used in requests between the web GUI and the server
	public CostumerRequestType costumerRequestType;
	public String name;
	public String email;
	public String id;
	public String liscencePlate;
//	Should add  more possible parameters...
	
	@Override
	public String toString(){
		return "CostumerOrder: " + name + ", " + id + ", " + liscencePlate + ", " + costumerRequestType + ".";
	}
}
