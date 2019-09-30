package ca.queensu.cics124;


//Class that creates transit objects to place in the fleet 
public class MTOTransit implements IMTOTransit 
{

	// protected so that it can be accessed by subclass
	protected String type;  
	protected String unitNumber;
	protected String identification;
	protected int capacity = 0;
	protected String status = AVAILABLE;
	
	
	public boolean isAvailable() {
		return status.equals(AVAILABLE);
	}
	
	public String getUnitNumber() {
		return unitNumber;
	}
	public void setUnitNumber(String unitNumber) {
		this.unitNumber = unitNumber;
	}

	//riders currently in the vehicle
	private float riderCount = 0.0f;
	
	public  MTOTransit ( String unitNumber, String identification,  String type, int capacity) {
		this.unitNumber = unitNumber;
		this.identification = identification;
		this.capacity = capacity;
		this.type = type;
	}
	

	public  MTOTransit ( String unitNumber, String identification,  String type) {
		this.unitNumber = unitNumber;
		this.identification = identification;
		this.type = type;
	}
	
	/**
	 * Constructor used to add the information from a line read from a file 
	 * @param line String line read from file 
	 */
	public MTOTransit(String line) {
		  String s[] = line.split(",");		  
		  if (s[0] != null && !s[0].isEmpty() ) {
			  unitNumber = s[0].trim();
		  }
		  if (s[1] != null && !s[1].isEmpty() ) {
			  identification = s[1].trim();
		  }
		  if (s[2] != null && !s[2].isEmpty() ) {
			  capacity = Integer.parseInt(s[2].trim());
		  }	  	  
	}
	/**
	 * Default constructor 
	 */
	public MTOTransit() 
	{
	}
	

	
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getIdentification() {
		return identification;
	}
	public void setIdentification(String identification) {
		this.identification = identification;
	}
	public int getCapacity() {
		return capacity;
	}
	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}
	public float getRiderCount() {
		return riderCount;
	}
	public void setRiderCount(float riderCount) {
		this.riderCount = riderCount;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	
	/**
	 * Returns boolean of whether a rider can be added to the transit vehicle 
	 */
	public boolean canAddRider( Rider rider ) {
		
		float riderCapacity = rider.getCapacity();
		if (riderCapacity + riderCount > getCapacity() ) {
			return false;
		}
		
		return true;
	}
	
	/**
	 * Adds the rider to the vehicle if vehicle has space 
	 * Increases the riderCount by how much space the rider takes up 
	 */
	public boolean addRider( Rider rider ) {
		
		if (canAddRider( rider)) {
			riderCount += rider.getCapacity();
			return true;
		}
		return false;
		
	}
	
	/**print vehicle information
	 * 
	 */
	public String print() {

		String buf = String.format("%s %s %s %s\r\n" , getType(), getUnitNumber(), getIdentification(), getCapacity());
		return buf;
	}
	
	/**
	 *  * To sort an Object by its property, must make the Object 
	 *  implement the Comparable interface and override the compareTo() method.
	 *  Now able to sort transits by least capacity to most capacity 
	 */
	public int compareTo(IMTOTransit compareTransit) {
		
		
		int compareCapacity = compareTransit.getCapacity();
				 
		// We need ascending order i.e the least capacity comes first
		return ( this.getCapacity() - compareCapacity );

	}
			
			
			
}
