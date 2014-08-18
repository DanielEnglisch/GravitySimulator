package at.xer0.GUI;

import at.xer0.Support.SObject;
import at.xer0.Support.Vars;

public class GUIEvents {
	
	public static void startStop()
	{
		Vars.isActive = !Vars.isActive;

		if(Vars.isActive)
		{
			Vars.mainFrame.b_StartStop.setText("Stop Simulation");
			System.out.println("Simulation: Started");
		}else
		{
			Vars.mainFrame.b_StartStop.setText("Start Simulation");
			System.out.println("Simulation: Stopped");
		}
	}
	
	public static void clearSimulation()
	{
		Vars.isActive = false;
		Vars.sObjects.clear();
		Vars.timeStep = 0.000001;
		Vars.time = 0;
		
		System.out.println("Simulation cleared!");
	}
	
	public static void addObject(int x, int y)
	{
		Vars.sObjects.add(new SObject(x-(Vars.currentMassPreset/2),y-(Vars.currentMassPreset/2),Vars.currentMassPreset,Vars.currentxVelocityPreset,Vars.currentyVelocityPreset,Vars.time,Vars.currentSObjectColorPreset));
		System.out.println("Added Object at " + x+ "/" + y);
	}
	
	public static void reverseTime()
	{
		Vars.isTimeReversed = !Vars.isTimeReversed;
		
		
		if(Vars.isTimeReversed)
		{
			Vars.mainFrame.b_ReverseTime.setText("Normalize Time");
			System.out.println("Reversed Time!");
		}else
		{
			Vars.mainFrame.b_ReverseTime.setText("Reverse Time");
			System.out.println("Normalized Time!");
		}
		
	}

}
