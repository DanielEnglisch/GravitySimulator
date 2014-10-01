
package at.xer0.Support;

import java.awt.Color;
import java.util.ArrayList;

public class Obj
{

	private Vec2D position,
			velocity,
			acceleration = new Vec2D(0, 0);
	private double mass;
	private Color color;
public int id = 0;
	public boolean isStatic = false;


	public ArrayList<Point> points = new ArrayList<Point>();

	/**
	 * @param position
	 *            Init position of Object
	 * @param velocity
	 *            Init velocity of Object
	 * @param mass
	 *            Mass of Object
	 * @param color
	 *            Color of Object
	 */
	public Obj(Vec2D iPos, Vec2D iVel, double mass, Color c, boolean isStat)
	{
		super();
		
		id = Vars.ObjectID;
		Vars.ObjectID++;

		this.position = iPos;
		this.velocity = iVel;
		this.mass = mass;
		this.color = c;


		this.isStatic = isStat;

		if (isStat)
		{
			this.velocity = new Vec2D(0, 0);
		}

	}

	public void setMass(double mass)
	{
		this.mass = mass;

		

	}

	@Override
	public String toString()
	{
		return "Obj [position=" + position + ", velocity=" + velocity + ", mass=" + mass + ", isStatic=" + isStatic + "]";
	}

	public Vec2D getPosition()
	{
		return position;
	}

	public void setPosition(Vec2D position)
	{
		this.position = position;
	}

	public Vec2D getVelocity()
	{
		return velocity;
	}

	public void setVelocity(Vec2D velocity)
	{
		this.velocity = velocity;
	}

	public Vec2D getAcceleration()
	{
		return acceleration;
	}

	public void setAcceleration(Vec2D acceleration)
	{
		this.acceleration = acceleration;
	}

	public double getMass()
	{
		return mass;
	}

	public Color getColor()
	{
		return color;
	}

	public Vec2D getDeltaXY(Obj o2)
	{
		double dx = o2.getPosition().getX() - position.getX();
		double dy = o2.getPosition().getY() - position.getY();
		return new Vec2D(dx, dy);
	}

	public double getDistanceTo(Obj o2)
	{
		double dx = o2.getPosition().getX() - position.getX();
		double dy = o2.getPosition().getY() - position.getY();
		return Math.sqrt(Math.pow(dx, 2) + Math.pow(dy, 2));
	}

	public void addPoint(Point p)
	{
		this.points.add(p);
	}

	public void clearPoints()
	{
		this.points.clear();
	}

}
