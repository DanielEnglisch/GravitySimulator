package at.xer0.lib.Simulators.GravitySimulator;

import java.util.ArrayList;

public class GravitySimulator{
	
	ArrayList<SObject> activeObjects = new ArrayList<SObject>();
	
	boolean isCloseRequested = false;
	boolean isActive = false;
	
	 View view = null;
	
	 public View getView() {
		return view;
	}


	double time = 0;
	 double timeStep = 0;
	 int operations = 0;
	 
	 double rExponent = 2;
	 
	
	private Thread logic_thread = null;

	 int quequedOps = 0;
	 
	 double G = 6.67384 * Math.pow(10, -11);
	

	
	public double getG() {
		return G;
	}
	
	public void enableView()
	{
		this.view = new View(this.activeObjects);
		new Thread(this.view).start();
	}

	public void setG(double g) {
		G = g;
	}
	
	 public double getrExponent() {
			return rExponent;
		}

		public void setrExponent(double rExponent) {
			this.rExponent = rExponent;
		}


	public GravitySimulator(double timeS)
	{
		this.timeStep = timeS;
		
		logic_thread = new Thread(new Logic(this));
		logic_thread.start();
		
	}
	
	public void reset()
	{
		this.time = 0;
		this.operations = 0;
		this.activeObjects.clear();
	}
	
		
	public void doOperations(int ops)
	{
		if(!isActive){
			//TODO: Exception handeling (<0)
			this.quequedOps = ops;
		}
	}
	
	public void setTimestep(double t)
	{
		this.timeStep = t;
	}
	
	public int getOperations()
	{
		return this.operations;
	}
	
	public double getTimestep()
	{
		return this.timeStep;
	}
	
	public double getTime()
	{
		return this.time ;
	}
	
	public void addObject(SObject o)
	{
		activeObjects.add(o);
	}
	
	public void startSimulation()
	{
		this.isActive = true;
	}
	
	public void stopSimulation()
	{
		this.isActive = false;
	}
	
	public void logObjects()
	{
		System.out.println("####################");
		
		System.out.println("Simulation state at " + time + "[ts: " + timeStep + "] (" + operations + " ops):");

		
		for(int i = 0; i < activeObjects.size(); i++)
		{
			System.out.println(activeObjects.get(i));
		}
		
		System.out.println("####################");

	}
	
	public void terminate()
	{
		this.isCloseRequested = true;
		logic_thread.interrupt();
	}

}

class Logic implements Runnable
{

	private GravitySimulator gs;
	
	public Logic(GravitySimulator g)
	{
		this.gs = g;
	}
	
	@Override
	public void run() {
		
		while(!gs.isCloseRequested)
		{
			if(gs.isActive)
			{
				logicTick();
			}
			else
			{
				if(gs.quequedOps != 0)
				{
					
					//Step mode
					for(int i = 0; i < gs.quequedOps; i++)
					{
						logicTick();

					}
					
					System.out.println("Done " + gs.quequedOps + " operations");

					gs.quequedOps = 0;
				}
				
				
			}
			
			if(gs.view != null)
			gs.view.getContentPane().repaint();
		}
		
	}
	
	private void logicTick()
	{
		
		double deltaT = gs.timeStep;
		double G = gs.G;
		double rExponent = gs.rExponent + 1;


		// #1:Beschleunigung-Schleife:
		for (SObject o1 : gs.activeObjects)
		{
			double ax = 0;
			double ay = 0;
			double az = 0;


			for (SObject o2 : gs.activeObjects)
			{
				if (o1 != o2)
				{

					Vec3D delta = o1.getDeltaXYZ(o2);
					double r = o1.getDistanceTo(o2);

					ax += (G * o2.getMass() * delta.getX()) / Math.pow(r, rExponent);
					ay += (G * o2.getMass() * delta.getY()) / Math.pow(r, rExponent);
					az += (G * o2.getMass() * delta.getZ()) / Math.pow(r, rExponent);

				}
			}

			o1.setAcceleration(new Vec3D(ax, ay, az));
						

		}

		// #2:Geschwindigkeit-Schleife:
		for (SObject o : gs.activeObjects)
		{
			o.setVelocity(new Vec3D(o.getVelocity().getX() + deltaT * o.getAcceleration().getX(),
					o.getVelocity().getY() + deltaT * o.getAcceleration().getY(),
					o.getVelocity().getZ() + deltaT * o.getAcceleration().getZ()));
		}

		// #3:Position-Schleife:
		for (SObject o : gs.activeObjects)
		{
			o.setPosition(
					new Vec3D(
							o.getPosition().getX() + (deltaT * o.getVelocity().getX()) + ((1 / 2) * o.getAcceleration().getX() * Math.pow(deltaT, 2)),
							o.getPosition().getY() + (deltaT * o.getVelocity().getY()) + ((1 / 2) * o.getAcceleration().getY() * Math.pow(deltaT, 2)),
							o.getPosition().getZ() + (deltaT * o.getVelocity().getZ()) + ((1 / 2) * o.getAcceleration().getZ() * Math.pow(deltaT, 2))
							)
					);
		}

		// Nur für die Zeit Anzeige Relevant:
		gs.time += deltaT;
		gs.operations++;
	}
	
	
	
}
