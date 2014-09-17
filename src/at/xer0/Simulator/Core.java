
package at.xer0.Simulator;

import java.awt.Color;
import java.util.Random;

import at.xer0.GUI.MainFrame;
import at.xer0.Support.MassPreset;
import at.xer0.Support.Obj;
import at.xer0.Support.Vars;
import at.xer0.Support.Vec2D;

public class Core
{

	public static void main(String[] args) throws InterruptedException
	{
		// Ladet und startet die GUI
		Vars.mainFrame = new MainFrame();
		System.out.println("MainFrame initialized!");
		new Thread(Vars.mainFrame).start();

		// Custom Objects:
		Vars.activeObjects.add(new Obj(new Vec2D(100, 300), new Vec2D(0, -10), MassPreset.EARTH, Color.BLUE));
		Vars.activeObjects.add(new Obj(new Vec2D(500, 200), new Vec2D(-10, 100), MassPreset.EARTH / 2, Color.RED));
		Vars.activeObjects.add(new Obj(new Vec2D(300, 100), new Vec2D(0, -32), MassPreset.EARTH, Color.GREEN));

		// Hauptschleife
		while (true)
		{

			if (Vars.isResetRequested)
			{
				resetSimulation();
				Vars.isResetRequested = false;
			}

			//Delete requested Object:
			if (Vars.objectToDelete.size() != 0)
			{
				for (Obj no : Vars.objectToDelete)
				{
					Vars.activeObjects.remove(no);
				}

				Vars.objectToDelete.clear();
			}
			
			// Add buffered Objects:
			if (Vars.bufferedObjects.size() != 0)
			{
				for (Obj no : Vars.bufferedObjects)
				{
					Vars.activeObjects.add(no);
				}

				Vars.bufferedObjects.clear();
			}

			// Wenn die Simulation läuft:
			if (Vars.isActive)
			{
				// Logik
				Logic.simpleAlgorithm();

			} else
			{
				// Stepping Mode Active:

				// Wenn der SingleStep Button gedrückt wurde,
				if (Vars.nextStep)
				{
					// Die Logic so oft ausführen, wie gewünscht:

					for (int i = 0; i < Vars.steps; i++)
					{
						Logic.simpleAlgorithm();
					}

					// Setze den SingleStep Button Request wieder auf false.
					Vars.nextStep = false;
				}

			}

			// Render:
			Vars.mainFrame.renderPanel.repaint();
			//

			// GuiVars
			updateGUIVars();
			//

		}

	}

	public static void resetSimulation()
	{
		Vars.isActive = false;

		Vars.activeObjects.clear();
		Vars.time = 0;
		Vars.mainFrame.b_StartStop.setText("Start Simulation");
		Vars.mainFrame.b_nextStep.setEnabled(true);
		Vars.isTimeReversed = false;
		Vars.mainFrame.b_ReverseTime.setText("Reverse Time");
		Vars.mainFrame.l_Time.setText("Time: " + String.format("%.5f", Vars.time));

	}

	public static void updateGUIVars()
	{
		Vars.mainFrame.l_Time.setText("Time: " + String.format("%.5f", Vars.time));
		Vars.mainFrame.l_Objects.setText("Objects: " + Vars.activeObjects.size());
	}



	public static int randInt(int min, int max)
	{
		Random r = new java.util.Random();
		int i1 = r.nextInt(max - min + 1) + min;
		return i1;
	}

}
