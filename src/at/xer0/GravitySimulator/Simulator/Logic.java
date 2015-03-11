
package at.xer0.GravitySimulator.Simulator;

import at.xer0.GravitySimulator.Objects.Obj;
import at.xer0.GravitySimulator.Objects.Point;
import at.xer0.GravitySimulator.Objects.Vec2D;
import at.xer0.GravitySimulator.Support.Vars;

public class Logic
{

	public static void tick()
	{
		simpleAlgorithm();
		handlePath();
	}

	public static void simpleAlgorithm()
	{
		
		double deltaT = Vars.timeStep;
		double G = Vars.G;
		double rExponent = Vars.rExponent + 1;


		// #1:Beschleunigung-Schleife:
		for (Obj o1 : Vars.activeObjects)
		{
			double ax = 0;
			double ay = 0;

			for (Obj o2 : Vars.activeObjects)
			{
				if (o1 != o2)
				{

					double deltaX = o1.getDeltaXY(o2).getX();
					double deltaY = o1.getDeltaXY(o2).getY();
					double r = o1.getDistanceTo(o2);

					ax += (G * o2.getMass() * deltaX) / Math.pow(r, rExponent);
					ay += (G * o2.getMass() * deltaY) / Math.pow(r, rExponent);
				}
			}

			o1.setAcceleration(new Vec2D(ax, ay));
						

		}

		// #2:Geschwindigkeit-Schleife:
		for (Obj o : Vars.activeObjects)
		{
			o.setVelocity(new Vec2D(o.getVelocity().getX() + deltaT * o.getAcceleration().getX(),
					o.getVelocity().getY() + deltaT * o.getAcceleration().getY()));
		}

		// #3:Position-Schleife:
		for (Obj o : Vars.activeObjects)
		{
			o.setPosition(
					new Vec2D(
							o.getPosition().getX() + (deltaT * o.getVelocity().getX()) + ((1 / 2) * o.getAcceleration().getX() * Math.pow(deltaT, 2)),
							o.getPosition().getY() + (deltaT * o.getVelocity().getY()) + ((1 / 2) * o.getAcceleration().getY() * Math.pow(deltaT, 2))
							)
					);
		}

		// Nur für die Zeit Anzeige Relevant:
		Vars.time += deltaT;
		
		
	}

	private static void handlePath()
	{

		if (Vars.clearPoints)
		{
			for (Obj o1 : Vars.activeObjects)
			{
				o1.clearPoints();
			}

			Vars.clearPoints = false;

		}

		for (Obj o1 : Vars.activeObjects)
		{
			if (Vars.mainFrame.cb_drawPath.isSelected())
			{

				int pathSize = Vars.pathSize;

				try
				{

					if (pathSize < 0)
					{
						pathSize = 0;
					}
				} catch (Exception ex)
				{

				}

				if (pathSize == 0)
				{
					o1.clearPoints();
					return;
				}

				if (o1.points.size() > pathSize)
				{
					if (o1.points.size() != 0)
					{
						for (int i = 0; i < (o1.points.size() - pathSize); i++)
						{
							o1.points.remove(i);
						}

					}

				}

				// Zoom:
				Point p = new Point((int) (o1.getPosition().getX() * Vars.scaling_ZoomFactor), (int) (o1.getPosition().getY() * Vars.scaling_ZoomFactor));

				// Verschiebung:

				p.setX((int) (p.getX() + Vars.scaling_Delta.getX() * Vars.scaling_ZoomFactor));
				p.setY((int) (p.getY() + Vars.scaling_Delta.getY() * Vars.scaling_ZoomFactor));

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

		}

	}

	public static void checkCollision()
	{
		for (Obj o1 : Vars.activeObjects)
		{
			for (Obj o2 : Vars.activeObjects)
			{
				if (o1 != o2)
				{
					int distance = (int) o1.getDistanceTo(o2);

					if (distance <= 10)
					{
						// System.out.println("COLLISION");

					}

				}
			}
		}
	}
}
