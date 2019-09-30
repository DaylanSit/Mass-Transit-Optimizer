package ca.queensu.cics124;

/**
 * Creates Streetcar objects 
 *
 */
public class StreetCar extends MTOTransit  {
	
	String carType;
	
	/**
	 * Constructor by assignment 
	 * @param unitNumber
	 * @param identification
	 * @param carType
	 */
	public StreetCar(String unitNumber, String identification, String carType ) {
		super(unitNumber , identification, STREETCAR);		
		this.carType = carType;
		this.capacity = getCapacity();
	}
	
	/**
	 * Constructor by reading string line from file 
	 * @param line
	 */
	public StreetCar(String line) {
		  super();
		  String s[] = line.split(",");		  
		  if (s[0] != null && !s[0].isEmpty() ) {
			  unitNumber = s[0].trim();
		  }
		  if (s[1] != null && !s[1].isEmpty() ) {
			  identification = s[1].trim();
		  }
		  if (s[2] != null && !s[2].isEmpty() ) {
			  carType = s[2].trim();
		  }
		  
		  this.capacity = getCapacity();
		  type = STREETCAR;	  	  
	}
	
	/**
	 * Constructor via streetcar object 
	 * @param car
	 */
	public StreetCar(StreetCar car) {
		  super();
		  this.unitNumber = car.unitNumber;
		  this.identification = car.identification;
		  this.carType = car.carType;
		  this.capacity = car.capacity;
		  type = STREETCAR;
	  	  
	}
	
	
	/**
	 * Capacity is either a single street car or double street car 
	 */
	public int getCapacity() {

		int capacity = 40;
		if (carType.equalsIgnoreCase(STREETCAR_DOUBLE)) {
			capacity *= 2;
		}
		return capacity;
	}


	/**
	 * Print streetcar object info
	 */
	public String print() {
		String buf = String.format("%s %s %s %s\r\n" , getType(), getUnitNumber(), getIdentification(), getCarType());
		return buf;
	}

	public String getCarType() {
		return carType;
	}

	public void setCarType(String carType) {
		this.carType = carType;
	}

}
