package ca.queensu.cics124;



public class Bus extends MTOTransit {

	public Bus( String unitNumber, String identification, int capacity) {
		super( unitNumber, identification,  BUS,  capacity );
						
	}
	
	public Bus(String line) {
		  super(line);		 
  		  type = BUS;
	  	  
	}
	
	public Bus(Bus bus) {
		  super();
		  this.unitNumber = bus.unitNumber;
		  this.identification = bus.identification;
		  this.capacity = bus.capacity;
		  type = BUS;
	  	  
	}
	
	
		
}
