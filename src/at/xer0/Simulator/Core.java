
package at.xer0.Simulator;

import java.awt.Color;
import java.awt.Graphics;

import at.xer0.GUI.MainFrame;
import at.xer0.Support.SObject;
import at.xer0.Support.Vars;

public class Core
{

	public static void main(String[] args) throws InterruptedException
	{
		Vars.mainFrame = new MainFrame();
		System.out.println("MainFrame initialized!");
		new Thread(Vars.mainFrame).start();

		while (true)
		{
			if (Vars.isActive)
			{

				if (Vars.isTimeReversed)
				{
					Vars.time -= Vars.timeStep;
				} else
				{
					Vars.time += Vars.timeStep;
				}

				logic();

				if (Vars.time <= 0)
				{
					Vars.isTimeReversed = false;
					Vars.isActive = false;
					Vars.mainFrame.b_StartStop.setText("Start Simulation");
					Vars.mainFrame.b_ReverseTime.setText("Reverse Time");

				}
			}

			// Render:
			Vars.mainFrame.renderPanel.repaint();
			//

			// GuiVars
			updateGUIVars();

		}

	}

	public static void logic()
	{
		for (SObject o : Vars.sObjects)
		{

			// Moves Object
			double deltaT = Vars.time - o.getInitTime();
			o.setxPos(o.getInitxPos() + (o.getxVelocity() * deltaT));
			o.setyPos(o.getInityPos() + (o.getyVelocity() * deltaT));

		}
	}

	public static void updateGUIVars()
	{
		Vars.mainFrame.l_Time.setText("Time: " + String.format("%.5f", Vars.time));
		Vars.mainFrame.l_Objects.setText("Objects: " + Vars.sObjects.size());
	}

	public static void render(Graphics g)
	{
		// Actual Render Logic

		for (SObject obj : Vars.sObjects)
		{
			g.setColor(obj.getColor());

			int x = (int)obj.getxPos();
			int y = (int)obj.getyPos();
			
			int radius = (int)obj.getMass()/10;
			int r_x = (int)obj.getxPos() - (radius/2);
			int r_y = (int)obj.getyPos() - (radius/2);
			int r_xVel = (int)obj.getxVelocity()/10;
			int r_yVel = (int)obj.getyVelocity()/10;

			
			g.fillOval(r_x,r_y, radius,radius);

			if (Vars.mainFrame.cb_speedVec.isSelected())
			{
				g.setColor(Color.BLACK);
				g.drawLine(x,y,x + r_xVel, y + r_yVel);
			}
		}
	}

	public static int randInt(int min, int max)
	{
		java.util.Random r = new java.util.Random();
		int i1 = r.nextInt(max - min + 1) + min;
		return i1;
	}

}
