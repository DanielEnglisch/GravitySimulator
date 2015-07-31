package at.xer0.lib.Simulators.GravitySimulator;

public class GS_CORE {

	public static void main(String[] args) throws InterruptedException {
		// Create GravitySimulator Object
		GravitySimulator gs1 = new GravitySimulator(0.001);

		// Add Objects to simulate
		gs1.addObject(new SObject(new Vec3D(0, 0, 0), new Vec3D(0, 0, 0), 1E12, "o1"));
		gs1.addObject(new SObject(new Vec3D(0, 200, 0), new Vec3D(0.6, 0, 0),10, "o2"));
		gs1.addObject(new SObject(new Vec3D(0, 300, 0), new Vec3D(.3, 0, 0),10, "o3"));


		gs1.startSimulation();
		// Enable GUI
		 gs1.enableView();
		 
		 Thread.sleep(3000);
			gs1.logObjects();




	}

	public static void log(String s) {
		System.out.println(s);
	}

}
