/*
Constructor:
 * Define Airport object with name and number of ranways
Depart method (synchronized):
 * Received request for departure, check for free ranway and return its number. Otherwise put flight to wait mode
Land method (synchronized):
 * Received request for landing, check for free ranway and return its number. Otherwise put flight to wait mode
FreeRunway method (synchronized):
 * Get number of freed ranway, update it, and notify (wake up waiting flights)
 * 
P.S. All requests and permissions are printed
 */
public class Airport {
	private final String name;
	private int numOfRanways;
	private boolean[] busyRanway;
	
	//constructor
	public Airport(String name, int numOfRanways) {
		this.name = name;
		this.numOfRanways = numOfRanways;
		busyRanway = new boolean[numOfRanways];
	}
	
	//get number of flight and return number of ranway (1-3)
	public synchronized int depart(String flightNumber) {
		//check for free runway and return his number
		System.out.printf("Flignt number: %s ASKS for departing at %s Airport.\n", flightNumber, name);
		while(true) {
			for(int i=0; i<numOfRanways;i++) {
				if(busyRanway[i]==false) {
					busyRanway[i]=true;
					System.out.printf("Flignt number: %s RECEIVED permission to depart at %s Airport. Ranway: %d\n", flightNumber, name, i+1);
					return i;
				}
			}
			try {
				wait();
			} catch (InterruptedException e) {
				System.out.println("Interrupted while waiting");
				e.printStackTrace();
			}
		}
	}
	
	//get number of flight and return number of ranway (1-3)
	public synchronized int land(String flightNumber) {
		//check for free ranway and return its number
		System.out.printf("Flignt number: %s ASKS for landing at %s Airport.\n", flightNumber, name);
		while(true) {
			for(int i=0; i<numOfRanways;i++) {
				if(busyRanway[i]==false) {
					busyRanway[i]=true;
					System.out.printf("Flignt number: %s RECEIVED permission to land at %s Airport. Ranway: %d\n", flightNumber, name, i+1);
					return i;
				}
			}
			try {
				wait();
			} catch (InterruptedException e) {
				System.out.println("Interrupted while waiting");
				e.printStackTrace();
			}
		}
	}
	
	public synchronized void freeRunway(String flightNumber, int ranwayNumber) {
		busyRanway[ranwayNumber] = false;
		System.out.printf("Flight number: %s FREED ranway: %d at %s Airport.\n", flightNumber, ranwayNumber+1, name);
		notify();
	}
}
