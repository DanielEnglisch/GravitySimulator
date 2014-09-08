
package at.xer0.Support;

import java.awt.Color;

public class Obj
{

	private Vec2D position; //Wird im Constructor initiiert.
	private Vec2D velocity; //Wird im Constructor initiiert.
	private Vec2D acceleration = new Vec2D(0, 0);
	private double mass; //Wird im Constructor initiiert.

	private Color color; //Wird im Constructor initiiert.

	public Obj(Vec2D iPos, Vec2D iVel, double mass, double itime, Color c)
	{
		super();

		this.position = iPos;
		this.velocity = iVel;

		this.mass = mass;

		this.color = c;

	}

	@Override
	public String toString()
	{
		return "Obj [position=" + position + ", velocity=" + velocity + ", acceleration=" + acceleration + ", mass=" + mass + ", color=" + color + "]";
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

	public double distanceTo(Obj b)
	{
		double dx = b.getPosition().getX() - position.getX();
		double dy = b.getPosition().getY() - position.getY();
		return Math.sqrt(dx * dx + dy * dy);
	}

}
