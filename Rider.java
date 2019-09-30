package ca.queensu.cics124;

/**
 * Rider class to create rider objects 
 * Contains methods that check for the validity of the input data from the ridership.txt file 
 * Calculates how much space the rider takes up depending on their age 
 */
public class Rider {
	
	//constants of rider attributes from ridership.txt file 
	public static final String CHILD = "C";
	public static final String ADULT = "A";
	public static final String SENIOR = "S";
	
	public static final String TM_SUBWAY = "S";
	public static final String TM_STREETCAR = "X";
	public static final String TM_GOTRAIN = "G";
	public static final String TM_BUS = "C";
	public static final String TM_GOBUS = "D";
	
	
	//rider attributes 
	private String identification;
	private String ageGroup;
	private String transportModality;
	private String hourOfDay;
	private String date;
	
	
	//accessors and mutators for rider attributes 
	public String getIdentification() {
		return identification;
	}
	public void setIdentification(String identification) {
		this.identification = identification;
	}
	public String getAgeGroup() {
		return ageGroup;
	}
	public void setAgeGroup(String ageGroup) {
		this.ageGroup = ageGroup;
	}
	public String getTransportModality() {
		return transportModality;
	}
	public void setTransportModality(String transportModality) {
		this.transportModality = transportModality;
	}
	public String getHourOfDay() {
		return hourOfDay;
	}
	public void setHourOfDay(String hourOfDay) {
		this.hourOfDay = hourOfDay;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	
	
	/**
	 * Rider object constructor
	 * @param identification String ID of rider
	 * @param ageGroup String age of rider 
	 * @param transportModality String transportation 
	 * @param hourOfDay String hour of transportation 
	 * @param date String date of transportation 
	 */
	Rider( String identification, String ageGroup, String transportModality, String hourOfDay, String date ) {
		this.identification = identification;
		this.ageGroup = ageGroup;
		this.transportModality = transportModality;
		this.hourOfDay = hourOfDay;
		this.date = date;
	}
	
	/** //Returns true if the personal identification of the rider is correct
	 *  @param id is the string of the rider's ID
	 *  @returns boolean of whether it is valid
	 */
	public static boolean isValidPid( String id ) {
		
		boolean isValid = false;
		
		if (id != null && !id.isEmpty() ) {
			if (id.equalsIgnoreCase("*")) {
				isValid = true;
			}
			// check of it is a 7-digit card id
			else if ((id.length() == 7) && 
					(id.matches("\\d+"))) {  // regular expression matching 1 or more digits
				isValid = true;
			} else if ( id.length() == 16 && id.startsWith("T")) { // check for monthly pass ticket code "Tyymmddnnnnnnn"
				isValid = true;
			}
		}
		return isValid;
	}
	
	/** //Returns true if the age of the rider is correct (must be either A C or S (Adult Child or Senior)
	 *  @param ageGroup is the string of the rider's age
	 *  @return boolean of whether it is valid
	 */
	public static boolean isValidAgeGroup ( String ageGroup ) {
		
		boolean isValid = false;
		
		if ((ageGroup != null) && !ageGroup.isEmpty()) {
			isValid = ageGroup.equals(ADULT) || ageGroup.equals(CHILD) || ageGroup.equals(SENIOR); 					
		}
		return isValid;		
	}
	
	/** //Returns true if the modality of the rider is correct 
	 *  @param transMod is the string of the rider's modality 
	 *  @return boolean of whether it is valid
	 */
	public static boolean isValidTransMod ( String transMod ) {
		
		boolean isValid = false;
		
		if ((transMod != null) && !transMod.isEmpty()) {
			isValid = transMod.equals(TM_SUBWAY) || transMod.equals(TM_STREETCAR) || 
					transMod.equals(TM_GOTRAIN) || transMod.equals(TM_GOBUS) || transMod.equals(TM_BUS); 					
		}
		return isValid;		
	}

	/** //Returns true if the hour of the rider is correct 
	 *  @param hour is the string of the rider's hour they travel 
	 *  @return boolean of whether it is valid
	 */
	public static boolean isValidHour ( String hour ) {
		
		boolean isValid = false;
		
		if (hour != null && !hour.isEmpty()) {
			try {
				int hr = Integer.parseInt(hour);
				if (hr >=1 && hr <=24) {
					isValid = true;
				}
			} catch (NumberFormatException e) {
			}
		}
	
		return isValid;
	}
	
	/** //Returns true if the day of the rider is correct 
	 *  @param day is the string of the rider's day they travel 
	 *  @return boolean of whether it is valid
	 */
	public static boolean isValidDay ( String day ) {
		
		boolean isValid = false;
		if (day != null && !day.isEmpty()) {
			try {
				int d = Integer.parseInt(day);
				if (d >=1 && d <=31) {
					isValid = true;
				}
			} catch (NumberFormatException e) {
			} 
		}
		return isValid;
	}
	
	/** //Returns true if the month of the rider is correct 
	 *  @param month is the string of the rider's month they travel 
	 *  @return boolean of whether it is valid
	 */
	public static boolean isValidMonth ( String month ) {
		
		boolean isValid = false;
		if (month != null && !month.isEmpty()) {
			try {
				int m = Integer.parseInt(month);
				if (m >=1 && m <=12) {
					isValid = true;
				}
			} catch (NumberFormatException e) {
			}
		}
		return isValid;
	}
	
	/** //Returns true if the year of the rider is correct 
	 *  @param year is the string of the rider's year they travel 
	 *  @return boolean of whether it is valid
	 */
	public static boolean isValidYear ( String year ) {
		
		boolean isValid = false;
		if (year != null && !year.isEmpty()) {
	
			try {
				int y = Integer.parseInt(year);
				if (y == 2018 ||  y == 2019) {
					isValid = true;
				}
			} catch (NumberFormatException e) {
			}
		}
		return isValid;
	}
	
	/** check date string yyyymmdd, where yyyy is 2018 or 2019, mm is in the range 01-12, and dd is in the range 00-31.
	 * @param date string 
	 * @return boolean of whether it is valid
	*/
	public static boolean isValidDate ( String date ) { 
		
		boolean isValid = false;
			
		if (date != null && date.length() == 8 ) {
			String year = date.substring(0, 4);
			String month = date.substring(4, 6);
			String day = date.substring(6);
			isValid = isValidYear(year) && isValidMonth(month) && isValidDay(day);
		}
		return isValid;
	}
	
	
	//returns boolean of whether rider is child, adult, or senior
	public boolean isChild() {
		return this.ageGroup == CHILD;
	}

	public boolean isAdult() {
		return this.ageGroup == ADULT;
	}

	public boolean isSenior() {
		return this.ageGroup == SENIOR;
	}

	/**gets how much room rider takes up depending on their age 
	 * @return float of rider's capacity 
	 */
	public float getCapacity() {
		float capacity = 1.0f; // defaults to adult;
		if (isChild())
			capacity = 0.75f;
		else if (isAdult())
			capacity = 1.00f;
		else if (isSenior())
			capacity = 1.25f;
		return capacity;
		
	}
	
}
