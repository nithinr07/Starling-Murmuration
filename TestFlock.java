import flockbase.Bird;
import flockbase.Flock;
import sample.*;

//sample main (which will be replaced by an interactive front-end. 
// You can use the following to test your code. 
// We will assume we have a 1000x1000 2D space in which the birds fly

public class TestFlock {
	public static void main(String[] args) {
		Flock f = new Flock511(); // where FlockX is a concrete derived class of Flock

		// add a bunch of birds

		// repeat the above for the different derived classes of bird
		Bird b1 = new Bird511(); // where Bird511 is a derived concrete class of Bird
		b1.setPos(10,10);
		f.addBird(b1);
		
		// for(int i=0;i<3;i++){
			// }
			
			// Bird b2 = new Bird511();
			// b2.setPos(i*100,  10);
			// f.addBird(b2);
			
		Bird b3 = new Bird523();
		b3.setPos(100,  10);
		f.addBird(b3);		
		
		// Bird b4 = new Bird2017016("eric");
		// b4.setPos(200,  10);
		// f.addBird(b4);	
		
		Bird b5 = new Bird2017028();
		b5.setPos(200,  10);
		f.addBird(b5);
			
		Bird b2 = new Bird511();
		b2.setPos(500,  10);
		f.addBird(b2);

		
		Bird b6 = new Bird523();
		b6.setPos(300,  10);
		f.addBird(b6);	

		Bird b7 = new Bird043();
		b7.setPos(400,  10);
		f.addBird(b7);	
		
		FlockDisplay disp = new SwingDisplay();
		// FlockDisplay disp = new TextDisplay();
		
		App app = new App(disp);
		disp.setApp(app);
		app.init(f);
		
		app.setLeader(b1);
		app.setTarget(800,300);
	
		/*
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		app.setLeader(b2);
		app.setTarget(400, 200);
		
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		*/
		/*
		Flock f2 = f.split(2);
		Bird f2lead = f2.getLeader();
		f2lead.setTarget(10, 20);
		
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		f2.joinFlock(f);
		f.getLeader().setTarget(100, 900);
	
		*/
	}
}
