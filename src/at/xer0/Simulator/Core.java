
package at.xer0.Simulator;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

import at.xer0.GUI.MainFrame;
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

		// Hauptschleife
		while (true)
		{
			// Wenn die Simulation läuft:
			if (Vars.isActive)
			{
				// Logik
				logic();

			} else
			{
				// Stepping Mode Active:

				// Wenn der SingleStep Button gedrückt wurde,
				if (Vars.nextStep)
				{
					// Die Logic so oft ausführen, wie gewünscht:

					for (int i = 0; i <= Vars.steps; i++)
					{
						logic();
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

	public static void logic()
	{

		double G = Math.pow(6.67384 * 10, -11);
		double deltaT;

		//Wenn die Zeit rückwerts laufen soll
		if (Vars.isTimeReversed)
		{
			//Soll DeltaT negativ sein.
			deltaT = Vars.timeStep * (-1);
		} else
		{
			//Sonnst positiv
			deltaT = Vars.timeStep;
		}

		//#1:Beschleunigung-Schleife:
		for (Obj o1 : Vars.activeObjects)
		{
			o1.setAcceleration(new Vec2D(0, 0));

			for (Obj o2 : Vars.activeObjects)
			{
				if (o1 != o2)
				{

					double r = o1.distanceTo(o2);

					o1.setAcceleration(new Vec2D(
							(-G * o1.getPosition().getX() * o2.getMass()) / (Math.pow(r, 3)) + (o1.getPosition().getX()), //X-Komponente
							(-G * o1.getPosition().getY() * o2.getMass()) / (Math.pow(r, 3) + (o1.getPosition().getY()) //Y-Komponente
					)));

				}
			}
		}

		//#2:Geschwindigkeit-Schleife:
		for (Obj o1 : Vars.activeObjects)
		{

			for (Obj o2 : Vars.activeObjects)
			{
				if (o1 != o2)
				{

					o1.setVelocity(new Vec2D(
							o1.getVelocity().getX() + deltaT * o1.getAcceleration().getX(), //X-Komponente
							o1.getVelocity().getY() + deltaT * o1.getAcceleration().getY() //Y-Komponente
					));

				}
			}
		}

		//#3:Position-Schleife:
		for (Obj o1 : Vars.activeObjects)
		{

			for (Obj o2 : Vars.activeObjects)
			{
				if (o1 != o2)
				{

					o1.setPosition(new Vec2D(
							o1.getPosition().getX() + deltaT * o1.getVelocity().getX(), //X-Komponente
							o1.getPosition().getY() + deltaT * o1.getVelocity().getY() //Y-Komponente
					));

				}
			}
		}

		// Nur für die Zeit Anzeige Relevant:
		Vars.time += deltaT;

	}

	public static void updateGUIVars()
	{
		Vars.mainFrame.l_Time.setText("Time: " + String.format("%.5f", Vars.time));
		Vars.mainFrame.l_Objects.setText("Objects: " + Vars.activeObjects.size());
	}

	public static void render(Graphics g)
	{
		// Actual Render Logic

		for (Obj obj : Vars.activeObjects)
		{
			g.setColor(obj.getColor());

			int x = (int) obj.getPosition().getX();
			int y = (int) obj.getPosition().getY();

			int radius = (int) obj.getMass() / 10;
			int r_x = x - (radius / 2);
			int r_y = y - (radius / 2);
			int r_xVel = (int) obj.getVelocity().getX() / 2;
			int r_yVel = (int) obj.getVelocity().getY() / 2;

			g.fillOval(r_x, r_y, radius, radius);

			if (Vars.mainFrame.cb_speedVec.isSelected())
			{
				g.setColor(Color.BLACK);
				g.drawLine(x, y, x + r_xVel, y + r_yVel);

			}
		}
	}

	public static int randInt(int min, int max)
	{
		Random r = new java.util.Random();
		int i1 = r.nextInt(max - min + 1) + min;
		return i1;
	}

}
