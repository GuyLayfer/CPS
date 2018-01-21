package core.parkingLot;

// TODO: Auto-generated Javadoc
/**
 * The Enum ParkingStatus.
 */
public enum ParkingStatus {/** The free. */
FREE("free"), /** The parked. */
 PARKED("parked"), /** The reserved. */
 RESERVED("reserved"), /** The broken. */
 BROKEN("broken");
	
	/** The value. */
	private String value;
	
	/**
	 * Instantiates a new parking status.
	 *
	 * @param value the value
	 */
	private ParkingStatus(String value) {
	      this.value = value;
	}
    
    /**
     * Gets the value.
     *
     * @return the value
     */
    public String getValue() {
	      return value;
    }
    
    /**
     * Convert string to parking map enum.
     *
     * @param s the s
     * @return the parking status
     */
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
