
package at.xer0.Simulator;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

import at.xer0.GUI.MainFrame;
import at.xer0.Support.MassPreset;
import at.xer0.Support.Obj;
import at.xer0.Support.Point;
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
		
		
		//Custom Objects:
		
		//Vars.activeObjects.add(new Obj(new Vec2D(350,350), new Vec2D(0,0), MassPreset.SUN,Color.ORANGE)); //Earth Mass Obj
		Vars.activeObjects.add(new Obj(new Vec2D(100,300), new Vec2D(0,-10), MassPreset.EARTH,Color.BLUE)); //Sun Mass Obj
		Vars.activeObjects.add(new Obj(new Vec2D(500,200), new Vec2D(-10,100), MassPreset.EARTH/2,Color.RED)); //Sun Mass Obj
		Vars.activeObjects.add(new Obj(new Vec2D(300,100), new Vec2D(0,-32), MassPreset.EARTH,Color.GREEN)); //Sun Mass Obj

		
		
		// Hauptschleife
		while (true)
		{
			
			if(Vars.isResetRequested)
			{
				resetSimulation();
				Vars.isResetRequested = false;
			}
			
			//Add buffered Objects:
			if(Vars.bufferedObjects.size() != 0)
			{
				for(Obj no : Vars.bufferedObjects)
				{
					Vars.activeObjects.add(no);
				}
				
				Vars.bufferedObjects.clear();
			}
			
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

					for (int i = 0; i < Vars.steps; i++)
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
	
	public static void logic()
	{

		double deltaT;
		double G = Vars.G;

		// Wenn die Zeit rückwerts laufen soll
		if (Vars.isTimeReversed)
		{
			// Soll DeltaT negativ sein.
			deltaT = Vars.timeStep * (-1);
		} else
		{
			// Sonnst positiv
			deltaT = Vars.timeStep;
		}

		// #1:Beschleunigung-Schleife:
		for (Obj o1 : Vars.activeObjects)
		{
			o1.setAcceleration(new Vec2D(0, 0));

			for (Obj o2 : Vars.activeObjects)
			{
				if (o1 != o2)
				{

					double deltaX = o1.getDeltaXY(o2).getX();
					double deltaY = o1.getDeltaXY(o2).getY();
					double r = o1.getRto(o2);

					//[-G] * m * (y/r)
					
					//ax = (-G * deltaX * m2 )/ r³
					
					o1.setAcceleration(new Vec2D(
							
							(G * o2.getMass() * (deltaX/r)),
							(G * o2.getMass() * (deltaY/r))
							
					));
					

				}
			}
		}

		// #2:Geschwindigkeit-Schleife:
		for (Obj o1 : Vars.activeObjects)
		{

			for (Obj o2 : Vars.activeObjects)
			{
				if (o1 != o2)
				{

					o1.setVelocity(new Vec2D(
					o1.getVelocity().getX() + deltaT * o1.getAcceleration().getX(), // X-Komponente
					o1.getVelocity().getY() + deltaT * o1.getAcceleration().getY() // Y-Komponente
					));

				}
			}
		}

		// #3:Position-Schleife:
		for (Obj o1 : Vars.activeObjects)
		{

			for (Obj o2 : Vars.activeObjects)
			{
				if (o1 != o2)
				{

					o1.setPosition(new Vec2D(
					o1.getPosition().getX() + deltaT * o1.getVelocity().getX(), // X-Komponente
					o1.getPosition().getY() + deltaT * o1.getVelocity().getY() // Y-Komponente
					));
					
					
					//Path:
					if(Vars.mainFrame.cb_drawPath.isSelected())
					{
						Point p = new Point((int)o1.getPosition().getX(),(int)o1.getPosition().getY());
						
						if(p.getX()<Vars.mainFrame.renderPanel.getWidth() && p.getY()<Vars.mainFrame.renderPanel.getHeight())
						{
							boolean add = true;
							
							for(Point pp : o1.points)
							{
								if(pp.isIdenticalTo(p))
								{
									add = false;
								}
							}
							
							if(add)
							{
								o1.addPoint(p);
							}
						}
						
						
						
					}else
					{
						o1.clearPoints();
					}
			
					//

				}
			}
		}
		
		
		
		
		/*if(Vars.centerOfMassMode)
		{
			//(m*v = pges // gesM) for o - v
			//center of mass feature
			
			Vec2D Pges = new Vec2D(0,0);
			
			for(Obj o : Vars.activeObjects)
			{
				Pges.setX(Pges.getX() +( o.getMass() * o.getVelocity().getX()));
				Pges.setY(Pges.getY() +( o.getMass() * o.getVelocity().getY()));
			}
			
			for(Obj o : Vars.activeObjects)
			{
				o.getVelocity().setX(o.getVelocity().getX() - Pges.getX());
				o.getVelocity().setY(o.getVelocity().getY() - Pges.getY());
			}
		}*/
		
		

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

			int radius = (int) (obj.getMass() * Vars.G);
			
			int r_x = x - (radius / 2);
			int r_y = y - (radius / 2);
			int r_xVel = ((int) obj.getVelocity().getX());
			int r_yVel = ((int) obj.getVelocity().getY());

			//Render Object:
			g.fillOval(r_x, r_y, radius, radius);
			//
			
	
			
			//Render Path:
			if(Vars.mainFrame.cb_drawPath.isSelected())
			{
				g.setColor(obj.getColor());

				for(int i = 0; i<obj.points.size();i++)
				{
					try{
						Point p1 = obj.points.get(i);
						Point p2 = obj.points.get(i+1);

						g.drawLine(p1.getX(),p1.getY(),p2.getX(),p2.getY());
						
						i++;
					}
					catch(Exception exx){}
				
				}
			}
			
			//
			
			//Velocity Vector:
			if (Vars.mainFrame.cb_speedVec.isSelected())
			{
				g.setColor(Color.BLACK);
				g.drawLine(x, y, x + r_xVel, y + r_yVel);
			}
			//
		}
	}

	public static int randInt(int min, int max)
	{
		Random r = new java.util.Random();
		int i1 = r.nextInt(max - min + 1) + min;
		return i1;
	}

}
