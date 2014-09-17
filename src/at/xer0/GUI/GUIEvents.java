
package at.xer0.GUI;

import at.xer0.Support.Obj;
import at.xer0.Support.Vars;
import at.xer0.Support.Vec2D;

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

	public static void addObject(int x, int y)
	{
		Obj o = new Obj(new Vec2D(x, y), Vars.currentVelocityPreset, Vars.currentMassPreset, Vars.currentColorPreset);

		Vars.bufferedObjects.add(o);

		System.out.println("Added Object: " + o.toString());
	}

	public static void reverseTime()
	{
		Vars.isTimeReversed = !Vars.isTimeReversed;

		if (Vars.isTimeReversed)
		{
			Vars.mainFrame.b_ReverseTime.setText("Normalize Time");
			Vars.mainFrame.b_nextStep.setText("<");
			System.out.println("Reversed Time!");
		} else
		{
			Vars.mainFrame.b_ReverseTime.setText("Reverse Time");
			Vars.mainFrame.b_nextStep.setText(">");
			System.out.println("Normalized Time!");
		}

	}
	
	
	public static void editObject(int x, int y)
	{
		
		
		
		if(!Vars.isActive)
		{
			Obj chosen = null;
			
			for(Obj o : Vars.activeObjects)
			{
				if(x  < o.getPosition().getX() + 10 && x  > o.getPosition().getX() - 10 &&
						y  < o.getPosition().getY() + 10 && y  > o.getPosition().getY() - 10)
				{
					chosen = o;
				}
			}
			
			if(chosen != null)
			{
				EditObjectGUI os = new EditObjectGUI(chosen);
				os.setVisible(true);
			}
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
