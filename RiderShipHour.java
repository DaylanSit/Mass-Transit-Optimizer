package ca.queensu.cics124;

/** This class contains the total number of riders by hour
 * 
 */
public class RiderShipHour {
	
	//hour of the day
	private int hour;
	//number of riders 
	private float count;
	
	/** Constructor for RiderShipHour
	 * @param hour int hour of the day 
	 */
	public RiderShipHour( int hour ) {
		this.hour = hour;
		this.count = 0;
	}
	
	//accessors and mutators 
	public int getHour() {
		return hour;
	}
	
	public void setHour(int hour) {
		this.hour = hour;
	}

	public float getCount() {
		return count;
	}

	public void setCount(float count) {
		this.count = count;
	}

	
	/**
	 * Adds rider object to the hour of the day 
	 * @param rider --> rider object, rider's space that they take up changes based on age 
	 */
	public void addRider(Rider rider) {
		// TODO Auto-generated method stub
		this.count += rider.getCapacity();		
	}

	@Override
	//returns number of riders at the hour of the day 
	public String toString() {
		return "RiderShipHour [hour=" + hour + ", count=" + count + "]\r\n";
	}
	
	
}
