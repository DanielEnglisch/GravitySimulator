package at.xer0.Simulator;

import java.awt.Color;
import java.awt.Graphics;

import at.xer0.GUI.MainFrame;
import at.xer0.Support.SObject;
import at.xer0.Support.Vars;



public class Core {

	public static void main(String[] args) throws InterruptedException
	{
		Vars.mainFrame = new MainFrame();
		new Thread(Vars.mainFrame).start();
						
		while(true)
		{
			if(Vars.isActive)
			{
			
				
				
				if(Vars.isTimeReversed)
				{
					Vars.time -= Vars.timeStep;
				}
				else
				{
					Vars.time += Vars.timeStep;
				}
				
				logic();
				
				
				if(Vars.time <= 0)
				{
					Vars.isTimeReversed = false;
					Vars.isActive = false;
					Vars.mainFrame.b_StartStop.setText("Start Simulation");
					Vars.mainFrame.b_ReverseTime.setText("Reverse Time");

				}
			}
			
			//Render:
			Vars.mainFrame.renderPanel.repaint();
			//
			
			//GuiVars
			updateGUIVars();

		}
				
		
	}
	
	public static  void logic()
	{
		for(SObject o : Vars.sObjects)
		{
		
			//Moves Object
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
		//Actual Render Logic
		
		
		for(SObject obj : Vars.sObjects)
		{
			//	Magenta,Light_Gray,Gray,Dark_Gray,Black,Red,Pink,Orange,Yellow,Green,Cyan,Blue

			switch(obj.getColor())
			{
				case Magenta: g.setColor(Color.MAGENTA);
				break; 
				case Light_Gray: g.setColor(Color.LIGHT_GRAY);
				break;
				case Gray: g.setColor(Color.GRAY);
				break;
				case Dark_Gray: g.setColor(Color.DARK_GRAY);
				break;
				case Black: g.setColor(Color.BLACK);
				break;
				case Red: g.setColor(Color.RED);
				break;
				case Pink: g.setColor(Color.PINK);
				break;
				case Orange: g.setColor(Color.ORANGE);
				break;
				case Yellow: g.setColor(Color.YELLOW);
				break;
				case Green: g.setColor(Color.GREEN);
				break;
				case Cyan: g.setColor(Color.CYAN);
				break;
				case Blue: g.setColor(Color.BLUE);
				break;
				
			default: g.setColor(Color.MAGENTA);
				break;
			}
			
			g.fillOval((int)obj.getxPos(), (int)obj.getyPos(), (int)obj.getMass(), (int)obj.getMass());
		}
	}
		
	public static int randInt(int min, int max) {
		java.util.Random r = new java.util.Random();
		int i1 = r.nextInt(max - min + 1) + min;
		return i1;
	}

}
