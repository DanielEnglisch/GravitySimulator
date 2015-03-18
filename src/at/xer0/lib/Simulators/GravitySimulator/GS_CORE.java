package at.xer0.lib.Simulators.GravitySimulator;

public class GS_CORE {
	
	public static void main(String[] args) throws InterruptedException
	{
		GravitySimulator gs = new GravitySimulator(1);
		
		gs.addObject(new SObject(new Vec3D(0,0,0), new Vec3D(1,4,-3),3E30,"Sonne"));
		gs.addObject(new SObject(new Vec3D(0,1.5E11,0), new Vec3D(30000,0,0),6E24,"Erde"));

		
		gs.logObjects();
		
		gs.startSimulation();
		Thread.sleep(2000);
		gs.stopSimulation();

		gs.logObjects();

		gs.terminate();
		
		
	}
	
	public static void log(String s)
	{
		System.out.println(s);
	}

}
