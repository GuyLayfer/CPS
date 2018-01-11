package core;

public enum ParkingStatus {FREE("free"), PARKED("parked"), RESERVED("reserved"), BROKEN("broken"), MAINTENENCE("maintenence");
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
         case "maintenence":
        	 return MAINTENENCE;
         default:
             throw new IllegalArgumentException("Invalid value: " + s);
        }
    }
}
