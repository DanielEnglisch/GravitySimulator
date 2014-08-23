
package at.xer0.Support;

import java.awt.Color;

public class SObject
{

	private SVector2D initPosition;
	private SVector2D position;
	private SVector2D initVelocity;
	private SVector2D velocity;

	private double mass;

	private double initTime;

	private SVector2D acceleration = new SVector2D(0, 0);

	private Color color;

	public SObject(SVector2D iPos, SVector2D iVel, double mass, double itime, Color c)
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
		return "SObject [initPosition=" + initPosition + ", position=" + position + ", initVelocity=" + initVelocity + ", velocity=" + velocity + ", mass=" + mass + ", initTime=" + initTime + ", acceleration=" + acceleration + ", color=" + color + "]";
	}

	public SVector2D getPosition()
	{
		return position;
	}

	public void setPosition(SVector2D position)
	{
		this.position = position;
	}

	public SVector2D getVelocity()
	{
		return velocity;
	}

	public void setVelocity(SVector2D velocity)
	{
		this.velocity = velocity;
	}

	public SVector2D getAcceleration()
	{
		return acceleration;
	}

	public void setAcceleration(SVector2D acceleration)
	{
		this.acceleration = acceleration;
	}

	public SVector2D getInitPosition()
	{
		return initPosition;
	}

	public SVector2D getInitVelocity()
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
