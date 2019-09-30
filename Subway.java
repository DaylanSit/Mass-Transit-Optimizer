package ca.queensu.cics124;


/**
 * Creates Subway objects 
 *
 */
public class Subway extends MTOTransit {
	
	//Constants from file 
	public static final String STATUS_AVAILABLE = "A" ;
	public static final String STATUS_UNAVAILABLE = "U" ;
	public static final String STATUS_UNKNOWN = "*" ;
	
	//number of cars per train
	private int numberOfCars;
	
	//Date when the train operational status is valid, in the format yyyymmdd
	private String statusDate;
	
	//A 2-digit number indicating a car’s maximum passenger capacity
	private int passengersPerCar;
	
	/**
	 * Constructor for subway object
	 * 
	 * @param unitNumber
	 * @param identification
	 * @param numberOfCars     A 1-digit indicating the number of cars per train.
	 * @param passengersPerCar A 2-digit number indicating a car’s maximum passenger
	 *                         capacity.
	 * @param status           A 1-character field indicating whether train is
	 *                         available or not on the date indicated in the
	 *                         Operation Date field: Available (A), Unavailable (U),
	 *                         Unknown(*).
	 * @param statusDate       Date when the train operational status is valid, in
	 *                         the format yyyymmdd.
	 */
	public Subway(String unitNumber, String identification, int numberOfCars, int passengersPerCar, String status, String statusDate ) {
		super(unitNumber , identification, SUBWAY);
		this.numberOfCars = numberOfCars;
		this.status = status;
		this.statusDate = statusDate;
		this.passengersPerCar = passengersPerCar;
		this.capacity = getCapacity();
	}
	
	/**
	 * Creates subway objects from the line read from file 
	 * @param line String line read from file
	 */
	public Subway(String line) {
		  super();
		  String s[] = line.split(",");		  
		  if (s[0] != null && !s[0].isEmpty() ) {
			  unitNumber = s[0].trim();
		  }
		  if (s[1] != null && !s[1].isEmpty() ) {
			  identification = s[1].trim();
		  }
		  if (s[2] != null && !s[2].isEmpty() ) {
			  numberOfCars = Integer.parseInt(s[2].trim());
		  }
		  if (s[3] != null && !s[3].isEmpty() ) {
			  passengersPerCar = Integer.parseInt(s[3].trim());
		  }
		  if (s[4] != null && !s[4].isEmpty() ) {
			  status  = s[4].trim();
		  }
		  if (s[5] != null && !s[5].isEmpty() ) {
			  statusDate  = s[5].trim();
		  }
		  this.capacity = getCapacity();
		  
		  type = SUBWAY;
	  	  
	}
	
	/**
	 * Separate subway constructor passing through subway object
	 * @param subway
	 */
	public Subway(Subway subway) {
		  super();
		  this.unitNumber = subway.unitNumber;
		  this.identification = subway.identification;
		  this.numberOfCars = subway.numberOfCars;
		  this.passengersPerCar = subway.passengersPerCar;
		  this.status = subway.status;
		  this.statusDate = subway.statusDate;		 
		  this.capacity = subway.capacity;
		  type = SUBWAY;
	  	  
	}
	
	
	
	//accessors and mutators
	public int getNumberOfCars() {
		return numberOfCars;
	}

	public void setNumberOfCars(int numberOfCars) {
		this.numberOfCars = numberOfCars;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getStatusDate() {
		return statusDate;
	}

	public void setStatusDate(String statusDate) {
		this.statusDate = statusDate;
	}

	public int getPassengersPerCar() {
		return passengersPerCar;
	}

	public void setPassengersPerCar(int passengersPerCar) {
		this.passengersPerCar = passengersPerCar;
	}

	//capacity of a subway is the number of cars * passengers per car
	public int getCapacity() {
		// TODO Auto-generated method stub
		int capacity = numberOfCars * passengersPerCar;
		return capacity;
	}

	/**
	 * Print subway object info
	 */
	public String print() {
		String buf = String.format("%s %s %s %d %d %s %s\r\n" , getType(), getUnitNumber(), getIdentification(), getNumberOfCars(), getPassengersPerCar(), getStatus(), getStatusDate() );
		return buf;
	}
}
