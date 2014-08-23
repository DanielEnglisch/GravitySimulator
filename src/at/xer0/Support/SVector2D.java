
package at.xer0.Support;

public class SVector2D
{

	private double x;

	/**
	 * @param xComponent
	 *            xComponent of SVector2D
	 * @param yComponent
	 *            yComponent of SVector2D
	 */
	public SVector2D(double xComponent, double yComponent)
	{
		super();
		this.x = xComponent;
		this.y = yComponent;
	}

	@Override
	public String toString()
	{
		return "SVector2D [x=" + x + ", y=" + y + "]";
	}

	private double y;

	public double getX()
	{
		return x;
	}

	public void setX(double xComponent)
	{
		this.x = xComponent;
	}

	public double getY()
	{
		return y;
	}

	public void setY(double yComponent)
	{
		this.y = yComponent;
	}

}
