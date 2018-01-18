package core.parkingLot;

public enum ParkingStatus {FREE("free"), PARKED("parked"), RESERVED("reserved"), BROKEN("broken");
	private String value;
	private ParkingStatus(String value) {
	      this.value = value;
	}
    public String getValue() {
	      return value;
    }
    public static ParkingStatus convertStringToParkingMapEnum(String s) {
        switch (s) {
         case "free":
 	    	return FREE;
         case "parked":
        	 return PARKED;
         case "reserved":
        	 return RESERVED;
         case "broken":
             return BROKEN;
         default:
             throw new IllegalArgumentException("Invalid value: " + s);
        }
    }
}
