
package at.xer0.Support;

import java.awt.Color;

public class Obj
{

	private Vec2D initPosition;
	private Vec2D initVelocity;
	private double initTime;

	private Vec2D position;
	private Vec2D velocity;
	private Vec2D acceleration = new Vec2D(0, 0);
	private double mass;

	private Color color;

	public Obj(Vec2D iPos, Vec2D iVel, double mass, double itime, Color c)
	{
		super();

		this.initPosition = iPos;
		this.position = this.initPosition;

		this.initVelocity = iVel;
		this.velocity = this.initVelocity;

		this.initTime = itime;

		this.mass = mass;

		this.color = c;

	}

	@Override
	public String toString()
	{
		return "Objekt [initPosition=" + initPosition + ", position=" + position + ", initVelocity=" + initVelocity + ", velocity=" + velocity + ", mass=" + mass + ", initTime=" + initTime + ", acceleration=" + acceleration + ", color=" + color + "]";
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

	public Vec2D getInitPosition()
	{
		return initPosition;
	}

	public Vec2D getInitVelocity()
	{
		return initVelocity;
	}

	public double getMass()
	{
		return mass;
	}

	public double getInitTime()
	{
		return initTime;
	}

	public Color getColor()
	{
		return color;
	}

}
