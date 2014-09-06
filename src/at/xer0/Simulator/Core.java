
package at.xer0.Simulator;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

import at.xer0.GUI.GUIEvents;
import at.xer0.GUI.MainFrame;
import at.xer0.Support.Obj;
import at.xer0.Support.Vec2D;
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

				logic();
				
				if (Vars.isTimeReversed)
				{
					Vars.time -= Vars.timeStep;
				}

				else
				{
					Vars.time += Vars.timeStep;
				}

				

				if (Vars.time <= 0)
				{
					Vars.isTimeReversed = false;
					GUIEvents.startStop();
					Vars.mainFrame.b_ReverseTime.setText("Reverse Time");
					Vars.time = 0;

				}
			} 
			else 
				
				
				if (Vars.time <= 0)
				{
					Vars.time = 0;
					Vars.previousStep = false;
				}
			
			
			if(Vars.nextStep)
			{
				for(int i = 0;i<Vars.steps;i++)
				{
					Vars.time += Vars.timeStep;
					logic();
				}
				
				Vars.nextStep = false;

	
			}
			else if(Vars.previousStep)
			{
				for(int i = 0;i<Vars.steps;i++)
				{
					Vars.time -= Vars.timeStep;
					logic();
				}
				
				Vars.previousStep = false;
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

		for (Obj o1 : Vars.sObjects)
		{
			double deltaT = Vars.time - o1.getInitTime();
			o1.setPosition(new Vec2D(o1.getInitPosition().getX() + (deltaT * o1.getVelocity().getX()), o1.getInitPosition().getY() + (deltaT * o1.getVelocity().getY())));
		}

		// return;
	}

	public static void updateGUIVars()
	{
		Vars.mainFrame.l_Time.setText("Time: " + String.format("%.5f", Vars.time));
		Vars.mainFrame.l_Objects.setText("Objects: " + Vars.sObjects.size());
	}

	public static void render(Graphics g)
	{
		// Actual Render Logic

		for (Obj obj : Vars.sObjects)
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
