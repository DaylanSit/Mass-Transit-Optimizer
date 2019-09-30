/* 
MTO Optimizer 
Name: Daylan Sit
Student Number: 10179650
Date: March 28, 2019

MTO Optimizer is a software prototype tool used to explore rough fleet size optimizations in 
the hourly deployment of the vehicles used in the different public transportation modalities 
(i.e., buses, subways, trains) in the city of Toronto.

The tool outputs hourly estimates of the optimal number of vehicles, for each means of transportation, 
that should be in operation, in a 24-hour period, to satisfy the projected demand indicated by the 
processed sample.
 */


package ca.queensu.cics124;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;



//Main class 
public class MTOptimizer {

	private static final String riderShipFile = "ridership.txt";
	private static final String goTransFile = "gotrains.txt";
	private static final String subwaysFile = "subways.txt";
	private static final String streetcarsFile = "streetcars.txt";
	private static final String busesFile = "buses.txt";
	private static final String goBusesFile = "gobuses.txt";
	private static final String errorFile = "errorLog.txt";
	private static final String outPutFile = "InOperationFleets.txt";
	
	private static List<Rider> riders = new ArrayList<Rider> ();
	
	
	private static FleetSchedule busFleet = null;
	private static FleetSchedule goBusFleet = null;
	private static FleetSchedule goTrainFleet = null;
	private static FleetSchedule streetCarFleet = null;
	private static FleetSchedule subwayFleet = null;
	
	//Array of the number of hours in a day for each mode of transportation 
	private static RiderShipHour [] ridershipHourBus = new RiderShipHour [24];
	private static RiderShipHour [] ridershipHourStreetCar = new RiderShipHour [24];
	private static RiderShipHour [] ridershipHourGoTrain = new RiderShipHour [24];
	private static RiderShipHour [] ridershipHourSubway = new RiderShipHour [24];
	private static RiderShipHour [] ridershipHourGoBus = new RiderShipHour [24];
	
	private static FileWriter errorFileWriter = null;
	
	/** Creates rider objects and their attributes by reading the ridership.txt file 
	 * Also logs errors from the ridership.txt file for missing or faulty values in an errorlog.txt file
	 * 
	 * @param filename String of the file's name
	 * @param errorFilename String of the error log file 
	 */
	private static void createRiders(String filename, String errorFilename) {

		String line;
		int lines = 0;
		
		try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
			
			
			while ((line = br.readLine()) != null) {

				String id="";
				String transMod="";
				String ageGroup="";
				
				String hour="";
				String date="";
				String errors = "";

				String s[] = line.split(",");

				lines++;
				
				// check for correct number of fields
				if (s.length != 5) {
					
					//if wrong number of fields add error string and write to error log file
					errors += "<Incorrect number of fields: " + s.length + ">";
					writeToRiderErrorFile(lines, line, errors, errorFilename);
					continue;
				}
				
				//Store ID of rider, otherwise add error to log if missing 
				if (s[0] != null && !s[0].trim().isEmpty()) {
					id = s[0];
				} else {
					errors += "<Missing personal identification>";
				}
				
				//Store transportation modality of rider, otherwise add error to log if missing 
				if (s[1] != null && !s[1].trim().isEmpty()) {
					transMod = s[1];
				} else {
					errors += "<Missing transportaion modality>";
				}
				
				//Store age of rider, otherwise add error to log if missing 
				if (s[2] != null && !s[2].trim().isEmpty()) {
					ageGroup = s[2];
				} else {
					errors += "<Missing Age group>";
				}
				
				//Store hour of day rider used transportation, otherwise add error to log if missing 
				if (s[3] != null && !s[3].trim().isEmpty()) {
					hour = s[3];
				} else {
					errors += "<Missing hour of day>";
				}
				
				//Store date rider used transportation, otherwise add error to log if missing 
				if (s[4] != null && !s[4].trim().isEmpty()) {
					date = s[4];
				} else {
					errors += "<Missing date>";
				}
				
				//Add the error string to the error log file 
				if (!errors.isEmpty()) {
					writeToRiderErrorFile(lines, line, errors, errorFilename);
					continue;
				}
				
				// Now validate format
				if (!Rider.isValidPid(id)) {
					errors += "<Invalid personal identification: " + id + "> ";
				}
				if (!Rider.isValidAgeGroup(ageGroup)) {
					errors += "<Invalid age group: " + ageGroup + "> ";
				}
				if (!Rider.isValidTransMod(transMod)) {
					errors += "<Invalid transportation modality : " + transMod
							+ "> ";
				}
				if (!Rider.isValidHour(hour)) {
					errors += "<Invalid hour of the day : " + hour + "> ";
				}
				if (!Rider.isValidDate(date)) {
					errors += "<Invalid date : " + date + "> ";
				}

				if (!errors.isEmpty()) {
					writeToRiderErrorFile(lines, line, errors, errorFilename);
				} else {
					
					//create rider object and add to riders array list
					Rider rider = new Rider(id, ageGroup, transMod, hour, date);
					riders.add(rider);
				}
			}
			br.close();
			System.out.println("Rider lines processed:" + lines);
			
		} catch (IOException e) {
			System.err.println("Error reading file");
		}
	}
	
	/** This method writes the errors from the ridership file to the errorlog.txt file
	 * @param lineNum int of the line number of the error
	 * @param errors String of the error 
	 * @errorFilename String of the errorlog file name 
	 */
	private static void writeToRiderErrorFile(int lineNum, String line, String errors, String errorFilename) {
		 
		try {
			errorFileWriter = new FileWriter(errorFilename,
					errorFileWriter == null ? false : true); // append true if
																// errorFileWriter
																// is not null
			BufferedWriter bw = new BufferedWriter(errorFileWriter);
			
			
			String buf = "Error(s) detected in line " + lineNum +": " + line + ": " + errors + "\r\n";
			
			bw.write(buf);
			bw.close();
			
		} catch (IOException e) {
			System.err.println("Error writing error to file");
		}

	}

	
	/**
	 * Optimizes the number of each mode of transportation 
	 */
	public static void optimizeFleets() {
		
		subwayFleet.allocateRiderShipHour( ridershipHourSubway);
		busFleet.allocateRiderShipHour( ridershipHourBus);
		goBusFleet.allocateRiderShipHour( ridershipHourGoBus);
		streetCarFleet.allocateRiderShipHour( ridershipHourStreetCar);
		goTrainFleet.allocateRiderShipHour( ridershipHourGoTrain);
		
	}

	
	/** This method creates the total number of riders that are traveling each hour based on their
	 *  specific mode of transportation 
	 * 
	 */
	private static void createRiderShipHours() {

		
		//For each hour of the day for each transportation modality, store the number of riders that 
		//are traveling each hour --> there are 24 hours of transportation, each with diff number of riders
		for( int i=0; i< 24 ; i++) {
			ridershipHourBus[i] = new RiderShipHour(i);
			ridershipHourStreetCar[i] = new RiderShipHour(i);
			ridershipHourGoBus[i] = new RiderShipHour(i);
			ridershipHourSubway[i] = new RiderShipHour(i);
			ridershipHourGoTrain[i] = new RiderShipHour(i);
		}
		
		// total all the riders by hour
		// for each rider in the array list of riders, 
		for( Rider rider: riders) {
			
			//stores the hour the rider rides 
			int hour = Integer.parseInt(rider.getHourOfDay()) - 1; // 1 am is index 0
			
			//declare new riderShipHour variable --> reference for a ridershipHour for a specific hour
			//and transportation modality 
			RiderShipHour riderShipHour = null;
			
			//assign the  ridership hour array at the specific hour that the rider travels
			//to the ridership hour variable depending on the 
			//transportation modality of the rider 
			switch (rider.getTransportModality()) 
			{
				case Rider.TM_BUS:
					riderShipHour = ridershipHourBus[hour];
					break;
					
				case Rider.TM_GOBUS:
					riderShipHour = ridershipHourGoBus[hour];
					break;
					
				case Rider.TM_SUBWAY:
					riderShipHour = ridershipHourSubway[hour];
					break;
					
				case Rider.TM_GOTRAIN:
					riderShipHour = ridershipHourGoTrain[hour];
					break;
					
				case Rider.TM_STREETCAR:
					riderShipHour = ridershipHourStreetCar[hour];
					break;
					
				default:
					System.err.println("Unknow transport mode!");
					break;
			}
			
			if (riderShipHour!=null) 
			{
				//adds the rider to the hour that they ride in the array list for the specific transportation
				//modality --> increases the count in the ridershipHour 
				riderShipHour.addRider( rider );
			} else {
				System.err.println("Unknown rider!");
			}
		}
		
	}



	/**
	 * Write optimized fleet to the file 
	 * @param filename String name of the file 
	 */
	private static void createOutPut(String filename) {
		// TODO Auto-generated method stub
		try {
			errorFileWriter = new FileWriter(filename, false); 
			BufferedWriter bw = new BufferedWriter(errorFileWriter);
			
			
			String buf = busFleet.printOptimizedSchedule();
			buf+="\r\n";
			
			buf += goBusFleet.printOptimizedSchedule();
			buf+="\r\n";
			
			buf += streetCarFleet.printOptimizedSchedule();
			buf+="\r\n";
			
			buf += subwayFleet.printOptimizedSchedule();
			buf+="\r\n";

			buf += goTrainFleet.printOptimizedSchedule();
			
			
			bw.write(buf);
			bw.close();
		} catch (IOException e) {
			System.err.println("Error writing error to file");
		}
		
	}
	
	/**
	 * This method creates the buses that transport the riders from the buses.txt file
	 * @param filename is the file name that describes fleet of buses in Toronto's metropolitan area 
	 */
	private static void createBusFleet( String filename) {

		String line;
		int lines = 0;
		try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
			
			// create array list of type IMTOTransit (interface) to create an array list of
			// buses
			// list of MTO transit objects that implements the interface 
			List<IMTOTransit> busList = new ArrayList<IMTOTransit>();

			while ((line = br.readLine()) != null) {
				
				// create new bus object and add it to the array list of buses using the
				// features in the line read from the file
				Bus bus = new Bus(line);
				
				//able to add Bus object to busList Interface array list because 
				//Bus extends MTOTransit which implements Interface 
				busList.add(bus);
				
				lines++;
			}

		  	br.close();	
		  	System.out.println("Bus lines processed:" + lines);
		  	
		  	//adds the array list of buses to the fleet object 
		  	busFleet = new FleetSchedule( "Buses", busList );
		  	
		} catch ( IOException  e) {
			 System.err.println("Error reading bus file");
		}		
	}


	/**
	 * This method creates the GoBuses that transport the riders from the buses.txt file
	 * @param filename is the file name that describes fleet of GoBuses in Toronto's metropolitan area 
	 */
	private static void createGoBusFleet(String filename) {

		String line;
		int lines = 0;
		try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
			
			List<IMTOTransit> busList = new ArrayList<IMTOTransit>();
			
		    while ((line = br.readLine()) != null ) {

		      GoBus bus = new GoBus(line);
		      busList.add(bus);
		  	  lines++;
		    }
		  	br.close();	
		  	System.out.println("Go Bus lines processed:" + lines);
		  	goBusFleet = new FleetSchedule( "GoBuses", busList );
		  	
		} catch ( IOException  e) {
			 System.err.println("Error reading go bus file");
		}		
	}
	
	/**
	 * This method creates the GoTrains that transport the riders from the buses.txt file
	 * @param filename is the file name that describes fleet of GoTrains in Toronto's metropolitan area 
	 */
	private static void createGoTrainFleet(String filename) {

		String line;
		int lines = 0;
		try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
			
			List<IMTOTransit> trainList = new ArrayList<IMTOTransit>();
			
		    while ((line = br.readLine()) != null ) {

		      GoTrain train = new GoTrain(line);
		      trainList.add(train);
		  	  lines++;
		    }
		  	br.close();	
		  	System.out.println("Go train lines processed:" + lines);
		  	goTrainFleet = new FleetSchedule( "GoTrains", trainList );
		  	
		} catch ( IOException  e) {
			 System.err.println("Error reading Go train file");
		}		
	}

	/**
	 * This method creates the StreetCars that transport the riders from the buses.txt file
	 * @param filename is the file name that describes fleet of StreetCars in Toronto's metropolitan area 
	 */
	private static void createStreetCarFleet(String filename) {

		String line;
		int lines = 0;
		try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
			
			List<IMTOTransit> carList = new ArrayList<IMTOTransit>();
			
		    while ((line = br.readLine()) != null ) {

		      StreetCar car = new StreetCar(line);
		      carList.add(car);
		  	  lines++;
		    }
		  	br.close();	
		  	System.out.println("Street Car lines processed:" + lines);
		  	streetCarFleet = new FleetSchedule( "StreetCars", carList );
		  	
		} catch ( IOException  e) {
			 System.err.println("Error reading street car file");
		}		
	}

	/**
	 * This method creates the Subways that transport the riders from the buses.txt file
	 * @param filename is the file name that describes fleet of Subways in Toronto's metropolitan area 
	 */
	private static void createSubwayFleet(String filename) {
		// TODO Auto-generated method stub
		String line;
		int lines = 0;
		try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
			
			List<IMTOTransit> carList = new ArrayList<IMTOTransit>();
			
		    while ((line = br.readLine()) != null ) {

		      Subway car = new Subway(line);
		      carList.add(car);
		  	  lines++;
		    }
		  	br.close();	
		  	System.out.println("Subway Car lines processed:" + lines);
		  	subwayFleet = new FleetSchedule( "Subways", carList );
		  	
		} catch ( IOException  e) {
			 System.err.println("Error reading Subways file");
		}		
	}

	
	public static void main(String[] args) {
		
		createRiders( riderShipFile, errorFile );
		createRiderShipHours();
		
		createBusFleet(  busesFile );
		createGoBusFleet(  goBusesFile );
		createSubwayFleet( subwaysFile );
		createStreetCarFleet(  streetcarsFile );
		createGoTrainFleet(  goTransFile );
		
		optimizeFleets();
		
		createOutPut(outPutFile);

	}

}
