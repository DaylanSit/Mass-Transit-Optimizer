package ca.queensu.cics124;

/*
 * Interface with all abstracted transit functionality 
 * Contract between MTOptimizer and MTOTransit 
 * Limits the access that MTOptimizer has to MTOTransit objects 
 * 
 * To sort an Object by its property, must make the Object 
 * implement the Comparable interface and override the compareTo() method.
 */
public interface IMTOTransit extends Comparable<IMTOTransit>{

	public static final String SUBWAY = "subway";
	public static final String STREETCAR = "streetcar";
	public static final String BUS = "bus";
	public static final String GOBUS = "gobus";
	public static final String GOTRAIN = "gotrain";
	
	public static final String AVAILABLE = "A";
	public static final String UNAVALIABLE = "U";
	public static final String UNKNOWN = "*";
	
	public static final String STREETCAR_DOUBLE = "D";
	public static final String STREETCAR_SINGLE = "S";
	

	public String getUnitNumber();
	public String getIdentification();
	public String getType();
	public String getStatus();
	public int getCapacity();
	public boolean canAddRider( Rider rider );
	public boolean addRider(Rider rider);
	public float getRiderCount();
	public String print();
	public boolean isAvailable();
	
	int compareTo(IMTOTransit compareTransit);
	
}
