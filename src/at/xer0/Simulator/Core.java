package at.xer0.Simulator;

import java.awt.Color;
import java.awt.Graphics;

import at.xer0.GUI.MainFrame;
import at.xer0.Support.SObject;
import at.xer0.Support.Vars;



public class Core {

	private static MainFrame mf;

	public static void main(String[] args) throws InterruptedException
	{
		mf = new MainFrame();
		new Thread(mf).start();
		
		Vars.sObjects.add(new SObject(100,100,30,3,10,Vars.time));
				
		while(true)
		{
			if(Vars.isActive)
			{
				Vars.time += Vars.timeStep;
				logic();
			}
			
			//Render:
			mf.renderPanel.repaint();
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
		mf.l_Time.setText("Time: " + String.format("%.5f", Vars.time));
		mf.l_Objects.setText("Objects: " + Vars.sObjects.size());
	}
	
	public static void render(Graphics g)
	{
		//Actual Render Logic
		
		g.setColor(Color.GREEN);
		
		for(SObject obj : Vars.sObjects)
		{
			g.fillOval((int)obj.getxPos(), (int)obj.getyPos(), (int)obj.getMass(), (int)obj.getMass());
		}
	}
		

}
