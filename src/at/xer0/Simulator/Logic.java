package at.xer0.Simulator;

import at.xer0.Support.Obj;
import at.xer0.Support.Point;
import at.xer0.Support.Vars;
import at.xer0.Support.Vec2D;


public class Logic
{
	public static void tick()
	{
		//checkCollision();
		simpleAlgorithm();
		handlePath();
	}
	
	public static void simpleAlgorithm()
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
					double r = o1.getDistanceTo(o2);

					o1.setAcceleration(new Vec2D(

					(G * o2.getMass() * (deltaX / r)), (G * o2.getMass() * (deltaY / r))

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

					o1.setVelocity(new Vec2D(o1.getVelocity().getX() + deltaT * o1.getAcceleration().getX(), // X-Komponente
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
					o1.setPosition(new Vec2D(o1.getPosition().getX() + deltaT * o1.getVelocity().getX(), // X-Komponente
					o1.getPosition().getY() + deltaT * o1.getVelocity().getY() // Y-Komponente
					));


				}
			}
		}

		// Nur für die Zeit Anzeige Relevant:
		Vars.time += deltaT;
	}
	
	private static void handlePath()
	{
		
		for(Obj o1 : Vars.activeObjects)
		{
			// Path:
			if (Vars.mainFrame.cb_drawPath.isSelected())
			{
				Point p = new Point( (int) o1.getPosition().getX(), (int) o1.getPosition().getY());

			if (p.getX() > -(Vars.mainFrame.renderPanel.getWidth()/2) - 200 &&
					p.getX() < (Vars.mainFrame.renderPanel.getWidth()/2) + 200 &&
					p.getY() > -(Vars.mainFrame.renderPanel.getHeight()/2) - 200 &&
					p.getY() < (Vars.mainFrame.renderPanel.getHeight()/2) + 200)
				{
					boolean add = true;

					for (Point pp : o1.points)
					{
						if (pp.isIdenticalTo(p))
						{
							add = false;
						}
					}

					if (add)
					{
						o1.addPoint(p);
					}
				}

			} else
			{
				o1.clearPoints();
			}
			
		}
		
		
	}
	
	public static void checkCollision()
	{
		for(Obj o1 : Vars.activeObjects)
		{
			for(Obj o2 : Vars.activeObjects)
			{
				if(o1 != o2)
				{
					int distance = (int)o1.getDistanceTo(o2);
					
					System.out.println(distance + " - " + o1.getRadius());
					
					
					if(o1.getRadius() + o2.getRadius() > distance)
					{
						if(o1.getRadius() <= o2.getRadius())
						{
							Vars.objectToDelete.add(o1);
						}
						else
						{
							Vars.objectToDelete.add(o2);
						}
					}
				}
			}
		}
	}
}
