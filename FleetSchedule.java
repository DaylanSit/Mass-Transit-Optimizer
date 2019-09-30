package ca.queensu.cics124;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * This class creates fleet objects of each transportation modality using a list
 * of the available transporation modalites in the Toronto metropolitan area 
 * and the name of the transportation modality 
 */
public class FleetSchedule {

	private String fleetName;
	
	//list of total mtoTransits available in the fleet
	private  List<IMTOTransit> mtoTransits;
	
	// 24 hour arrays of number of transportation modalities available each hour of the day 
	private  List<IMTOTransit> []  mtoTransitSchdeule = (List<IMTOTransit>[]) new ArrayList [24] ;  

	/**
	 * Creates Fleet objects
	 * 
	 * @param fleetName   Name of the transportation modality
	 * @param mtoTransits List of transportation objects that are available
	 *                    (buses,streetcars,gobuses, etc.)
	 */
	FleetSchedule(String fleetName, List<IMTOTransit> mtoTransits) {

		this.fleetName = fleetName;
		this.mtoTransits = mtoTransits;

		// sort the objects in the fleet by capacity (ascending order --> Least capacity to Most Capacity)
		Collections.sort(this.mtoTransits);

		
		for (int i = 0; i < 24; i++) {
			
			List<IMTOTransit> mtoTransitList = new ArrayList<IMTOTransit>();
			
			//assigns the list of transportation modality objects to each hour of the day 
			mtoTransitSchdeule[i] = mtoTransitList;
		}

	}
	

	/**
	 * Allocates the number of transit modalities available to an array list of transits 
	 * and the number of hours to a single ridership hour object 
	 * Finds the optimal number of transit objects depending on the number of riders per hour 
	 * @param riderShipHours hour object which contains the number of riders per hour 
	 */
	public void allocateRiderShipHour ( RiderShipHour[] riderShipHours ) {
		
		for ( int i =0; i < 24; i++) {
			
			//gets the riders for each hour of the day 
			RiderShipHour riderShipHour = riderShipHours[i];
			
			//gets the number of transportation modalities available per hour
			List<IMTOTransit> mtoTransitsHour = mtoTransitSchdeule[i];
			
			//finds the optimal-sized fleet 
			findOptimalSize( riderShipHour.getCount(), mtoTransitsHour );			 
		}
		
		
		
	}
	
	/**
	 * This function optimizes the transit required based on the riderShip count
	 * @param count number of riders for a specific hour 
	 * @param mtoTransitsHour Number of transits available each hour 
	 */
	// 1. It tried to minimize the number of transits required.
	// 2. It tried to find the most cost effective transit (The smallest that will fit)
	private void findOptimalSize(float count, List<IMTOTransit> mtoTransitsHour) {

		boolean done = false;
		float currentCount = count;
		
		IMTOTransit mtoTransit = null;
		
		//While not done and still riders to be assigned 
		while (!done && currentCount > 0) {

			
			//find the transit object with the smallest capacity that fits the rider count
			//will only return a vehicle if the total current rider count can be accommodated 
			//by one vehicle 
			mtoTransit = findSmallestFitNotInList(currentCount, mtoTransitsHour);
			
			
			if (mtoTransit != null) 
			{
				//add the vehicle that fits all riders to the vehicles assigned for the hour 
				mtoTransitsHour.add(mtoTransit);
				done = true; // found the last transit
			}
			
			
			//if one vehicle cannot fit all of the current riders that need be allocated, 
			if (!done) 
			{
				//Find the largest capacity vehicle
				mtoTransit = findLargestFitNotInList(mtoTransitsHour);
				
				if (mtoTransit != null) 
				{
					
					//allocates the vehicle to be used in the hour 
					mtoTransitsHour.add(mtoTransit);
					
					//reduce the number of riders that need to be allocated by the vehicle's 
					//capacity as they have now been allocated
					currentCount -= mtoTransit.getCapacity();
				}
				
				//not enough vehicles for the riders 
				else 
				{
					System.out.println("No more vehicles to be assigned - too many riders");
				}
			}
		}
	}
	
	/**
	 *  This function returns the largest capacity non-allocated vehicle
	 * @param mtoTransitsHour list of vehicles that have been already allocated to the hour 
	 * @return the largest capacity non-allocated vehicle
	 */ 
	private IMTOTransit findLargestFitNotInList( List<IMTOTransit> mtoTransitsHour) {
		
		//loop through the fleet from the LARGEST capacity vehicle to the smallest capacity vehicle
		for( int i=mtoTransits.size()-1; i>=0; i-- ) {
			
			
			IMTOTransit mtoTransit = mtoTransits.get(i);
			
			//if the vehicle has not yet been assigned to the hour and the vehicle status is available, return 
			//the vehicle 
			if (!mtoTransitsHour.contains(mtoTransit) && mtoTransit.isAvailable()) {
				
				return mtoTransit;
			}
		}
		return null;
	}
		
	
	/** This function returns the smallest capacity non-allocated transit that will accommodate the rider count	
	 * 
	 * @param count
	 * @param mtoTransitsHour number of transportation modalities available for a certain hour
	 * @return transit object interface containing the smallest capacity non-allocated transit 
	 */
	private IMTOTransit findSmallestFitNotInList( float count, List<IMTOTransit> mtoTransitsHour) {
		
		//for each transit object in the fleet (loops through smallest capacity to largest capacity vehicles)
		for (  IMTOTransit mtoTransit : mtoTransits) {
			
			//if the transit object has not already been allocated and it is currently available (for subways)
			if (!mtoTransitsHour.contains(mtoTransit) && mtoTransit.isAvailable()) {
				
				//if the count is less than the transit object's capacity (transit can fit the number of riders)
				//return the transit object 
				if (count <= mtoTransit.getCapacity()) {
					return (mtoTransit);
				}
			}
		}
		return null;
		
	}
	

	/**
	 * Prints the optimal number of transits for the fleet 
	 * @return String of output to write to file
	 */
	public String printOptimizedSchedule() {
		
		String buf = "[" + fleetName + "]\r\n";

		for( int  i=0; i< 24; i++) {
			List<IMTOTransit> mtoTransitList = mtoTransitSchdeule[i];
			
			buf += String.format("[Hour=%02d]\r\n", i+1);
			
			int deployedCount = 0;
			
			for ( IMTOTransit mtoTransit: mtoTransitList) {
					buf += mtoTransit.print();	
					deployedCount++;
			}
			buf += String.format("Count = %d\r\n", deployedCount);
			
		}
		
		return buf;
		
	}
	
}
