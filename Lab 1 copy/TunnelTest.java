
public class TunnelTest {
	public static void main(String[] args) {
		Tunnel portTunnel = new Tunnel(10);
		
		for(int j = 1;j<= 15; j++) {
			new Thread(new Car(portTunnel, j)).start();
			try {
				Thread.sleep(200);
			} catch(InterruptedException e) {
				
			}
		}
	}
	
	
}

class Car implements Runnable {
	
	//properties
	private Tunnel t;
	private int num;
	
	//constructor
	public Car(Tunnel t1, int n) {
		t = t1;
		num = n;
	}
	
	public void run() {
		t.waitTunnel(num);
		
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			System.out.println("Car "+ num + " is crossing the tunnel");
		}
		
		
		t.signalTunnel(num);
	}
}

class Tunnel {
	
	//properties
	private int count;
	
	//constructor
	public Tunnel(int c) {
		count = c;
	}
	
	//critical resource
	public synchronized void waitTunnel(int n){
		// if count is zero the process must wait
		while(count == 0)
			try{
				wait();
			} catch(InterruptedException e){}

		 // otherwise the process can continue
		 count--;
		 System.out.println("Car " +n+ " has Entered. " +  count + " spaces left in tunnel.");

	}
	
	public synchronized void signalTunnel(int n) {
		 count++;
     	 System.out.println("Car "+n+" has left. " +  count + " spaces left in tunnel.");
     	 notify();

	}
}

