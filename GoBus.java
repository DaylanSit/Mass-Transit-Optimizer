package ca.queensu.cics124;


/**
 * 
 * 
 */
public class GoBus extends MTOTransit{

	/**
	 * Constructor via parent class
	 * @param unitNumber
	 * @param identification
	 * @param capacity
	 */
	public GoBus( String unitNumber, String identification, int capacity) {
		super( unitNumber, identification,  GOBUS,  capacity );
						
	}
	
	/**
	 * Creates goBus objects from the line read from file 
	 * @param line
	 */
	public GoBus( String line ) {
		  super(line);		
  		  type = GOBUS;
	  	  
	}
	
	/**
	 * Constructor via goBus object
	 * @param bus
	 */
	public GoBus(GoBus bus) {
		  super();
		  this.unitNumber = bus.unitNumber;
		  this.identification = bus.identification;
		  this.capacity = bus.capacity;
		  type = BUS;
	  	  
	}
	

}
