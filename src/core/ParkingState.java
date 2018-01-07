package core;

public enum ParkingState {FREE("free"), PARKED("parked"), RESERVED("reserved"), BROKEN("broken"), MAINTENENCE("maintenence");
	private String value;
	private ParkingState(String value) {
	      this.value = value;
	}
    public String getValue() {
	      return value;
    }
    public static ParkingState convertStringToParkingMapEnum(String s) {
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
