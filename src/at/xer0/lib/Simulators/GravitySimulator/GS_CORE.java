package at.xer0.lib.Simulators.GravitySimulator;

public class GS_CORE {
	
	public static void main(String[] args) throws InterruptedException
	{
		//Create GravitySimulator Object
		GravitySimulator gs = new GravitySimulator(0.001);
		
		//Add Objects to simulate
		gs.addObject(new SObject(new Vec3D(0,0,0), new Vec3D(0,0,0),1E12,"o1"));
		gs.addObject(new SObject(new Vec3D(0,200,0), new Vec3D(0.6,0,0),10,"o2"));
		gs.addObject(new SObject(new Vec3D(0,300,0), new Vec3D(.3,0,0),10,"o3"));

		//Log existing objects
		gs.logObjects();

		//Enable GUI
		gs.enableView();
		Thread.sleep(2000);

		//Start Simulation
		gs.startSimulation();
			

		
	}
	
	public static void log(String s)
	{
		System.out.println(s);
	}

}
