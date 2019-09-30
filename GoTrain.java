package ca.queensu.cics124;

/**
 * Creates GoTrain objects 
*
*/
public class GoTrain extends MTOTransit {
	
	/**
	 * Constructor via parent class
	 * @param unitNumber
	 * @param identification
	 * @param capacity
	 */
	public GoTrain( String unitNumber, String identification, int capacity) {
		super( unitNumber, identification,  GOTRAIN,  capacity );
						
	}
	
	/**
	 * Creates goTrain objects from the line read from file 
	 * @param line
	 */
	public GoTrain(String line) {
		  super(line);
		  type = GOTRAIN;
	  	  
	}
	
	/**
	 * Constructor via train object
	 * @param train
	 */
	public GoTrain(GoTrain train) {
		  super();
		  this.unitNumber = train.unitNumber;
		  this.identification = train.identification;
		  this.capacity = train.capacity;
		  type = GOTRAIN;
	  	  
	}
	

	
}
