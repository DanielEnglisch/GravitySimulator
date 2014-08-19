
package at.xer0.Support;

public class SObject
{

	private double xPos;
	private double yPos;

	private double mass;
	private double xVelocity;
	private double yVelocity;

	private double initTime;
	private double initxPos;
	private double inityPos;

	private SObjectColor color;

	public SObject(double initxPos, double inityPos, double mass, double vx, double vy, double initTime, SObjectColor c)
	{
		super();
		this.xPos = initxPos;
		this.yPos = inityPos;
		this.mass = mass;
		xVelocity = vx;
		yVelocity = vy;
		this.initTime = initTime;
		this.initxPos = initxPos;
		this.inityPos = inityPos;
		this.color = c;
	}

	public double getInitxPos()
	{
		return initxPos;
	}

	public double getInityPos()
	{
		return inityPos;
	}

	public double getxPos()
	{
		return xPos;
	}

	public void setxPos(double xPos)
	{
		this.xPos = xPos;
	}

	public double getyPos()
	{
		return yPos;
	}

	public void setyPos(double yPos)
	{
		this.yPos = yPos;
	}

	public double getMass()
	{
		return mass;
	}

	public void setMass(double mass)
	{
		this.mass = mass;
	}

	public double getxVelocity()
	{
		return xVelocity;
	}

	public void setVx(double vx)
	{
		xVelocity = vx;
	}

	public double getyVelocity()
	{
		return yVelocity;
	}

	public void setVy(double vy)
	{
		yVelocity = vy;
	}

	public double getInitTime()
	{
		return initTime;
	}

	public SObjectColor getColor()
	{
		return color;
	}

	public void setColor(SObjectColor color)
	{
		this.color = color;
	}

}
