
package at.xer0.GUI;

import at.xer0.Support.Obj;
import at.xer0.Support.Vec2D;
import at.xer0.Support.Vars;

public class GUIEvents
{

	public static void startStop()
	{
		Vars.isActive = !Vars.isActive;

		if (Vars.isActive)
		{
			Vars.mainFrame.b_StartStop.setText("Stop Simulation");
			System.out.println("Simulation: Started");

			// DisableStepMode:
			Vars.mainFrame.b_nextStep.setEnabled(false);

		} else
		{
			Vars.mainFrame.b_StartStop.setText("Start Simulation");
			System.out.println("Simulation: Stopped");

			// EnableStepMode:
			Vars.mainFrame.b_nextStep.setEnabled(true);

		}
	}

	public static void resetSimulation()
	{
		Vars.activeObjects.clear();
		Vars.time = 0;
		Vars.isActive = false;
		Vars.mainFrame.b_StartStop.setText("Start Simulation");
		Vars.mainFrame.b_nextStep.setEnabled(true);
		Vars.isTimeReversed = false;
		Vars.mainFrame.b_ReverseTime.setText("Reverse Time");
		Vars.mainFrame.l_Time.setText("Time: " + String.format("%.5f", Vars.time));

	}

	public static void addObject(int x, int y)
	{
		Obj o = new Obj(new Vec2D(x, y), Vars.currentVelocityPreset, Vars.currentMassPreset, Vars.time, Vars.currentColorPreset);
		Vars.activeObjects.add(o);

		System.out.println("Added Object: " + o.toString());
	}

	public static void reverseTime()
	{
		Vars.isTimeReversed = !Vars.isTimeReversed;

		if (Vars.isTimeReversed)
		{
			Vars.mainFrame.b_ReverseTime.setText("Normalize Time");
			System.out.println("Reversed Time!");
		} else
		{
			Vars.mainFrame.b_ReverseTime.setText("Reverse Time");
			System.out.println("Normalized Time!");
		}

	}

	public static void updateTimestep(int timestepfactor)
	{

		switch (timestepfactor)
		{
		case 0:
		{
			Vars.timeStep = 1;
		}
			break;
		case 1:
		{
			Vars.timeStep = 0.1;
		}
			break;
		case 2:
		{
			Vars.timeStep = 0.01;
		}
			break;
		case 3:
		{
			Vars.timeStep = 0.001;
		}
			break;
		case 4:
		{
			Vars.timeStep = 0.0001;
		}
			break;
		case 5:
		{
			Vars.timeStep = 0.00001;
		}
			break;
		case 6:
		{
			Vars.timeStep = 0.000001;
		}
			break;
		case 7:
		{
			Vars.timeStep = 0.0000001;
		}
			break;
		case 8:
		{
			Vars.timeStep = 0.00000001;
		}
			break;
		case 9:
		{
			Vars.timeStep = 0.000000001;
		}
			break;
		case 10:
		{
			Vars.timeStep = 0.0000000001;
		}
			break;

		}

		Vars.mainFrame.l_timestep.setText("" + String.format("%." + timestepfactor + "f", Vars.timeStep));

	}

}
