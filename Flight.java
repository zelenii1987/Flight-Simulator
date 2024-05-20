/*
Constructor:
 * Define Flight object (Runnable) with flight number, departure and landing places
Run:
 * Asks for departure. When received permission, launch "depart simulation" (sleep) 2-5 seconds
 * Report about freed ranway, and launch "flying simulation" (sleep) 10-15 seconds
 * Asks for landing. When received permission, launch "landing simulation" (sleep) 2-5 seconds
 * In the end report about freed ranway
Delay method:
 * Generate random delay (sleeping time) in seconds.
 */
import java.util.Random;

public class Flight implements Runnable{
	private final String flightNumber;
	private final Airport departure, landing;
	private final int second = 1000;
	
	//constructor
	public Flight(String flightNumber, Airport departure, Airport landing) {
		this.flightNumber = flightNumber;
		this.departure = departure;
		this.landing = landing;
	}
	@Override
	public void run() {
		//asks for departure
		int ranwayNumber = departure.depart(flightNumber);
		//depart simulation 2-5 seconds
		delay(2,5);
		//report on the release of the ranway
		departure.freeRunway(flightNumber, ranwayNumber);
		
		//flying simulation 10-15 seconds
		delay(10,15);
		
		//asks for landing
		ranwayNumber = landing.land(flightNumber);
		
		//landing simulation 2-5 seconds
		delay(2,5);
		
		//report on the release of the ranway
		landing.freeRunway(flightNumber, ranwayNumber);
	}
	
	//delay simulation for departure, landing and flying
	private void delay(int from, int until) {
		Random rand = new Random();
		int range = until-from+1;
		try {
			Thread.sleep((rand.nextInt(range)+from)*second); //sleep in miliseconds
		}catch (InterruptedException e) {
			System.out.println("Interrupted while waiting");
			e.printStackTrace();
		}
	}

}
