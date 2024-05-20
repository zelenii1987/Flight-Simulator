/*
Main method:
 * Creates two Airport objects with 3 ranways
 * Create and execute 10 flights (threads) with random flight numbers and destinations
 */
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class FlightSimulation {
	private static Random rand = new Random();
	private final static int numOfFlights =10;
	
	public static void main(String[] args) {
		
		System.out.println("Flight simulation started\n");

		//create 2 airports with 3 ranways
		Airport[] airportArray = {new Airport("Ben Gurion", 3), new Airport("Eilat Ramon", 3)};
		
		//create 10 flights
		ExecutorService executor = Executors.newFixedThreadPool(numOfFlights);
		for(int i=0; i<numOfFlights; i++) {
			int depAirport = rand.nextInt(2);
			//chose departing place and define landing place
			executor.execute(new Flight(getFlightNumber(), 
								airportArray[depAirport], 
								airportArray[(depAirport+1)%2]));
		}
		executor.shutdown();
		while(!executor.isTerminated())
			try {
				executor.awaitTermination(500, TimeUnit.MILLISECONDS);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		System.out.println("\nFlight simulation is over");
	}
	
	//return flight number generated randomaly in format [AA1111]
	private static String getFlightNumber() {
		return String.format("%c%c%04d", 
				(char)rand.nextInt(26)+'A',
				(char)rand.nextInt(26)+'A',
				rand.nextInt(10000));
	}

}
