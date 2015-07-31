package at.xer0.lib.Simulators.GravitySimulator;

public class Vec2D {

	private double x, y;

	@Override
	public String toString() {
		return "Vec2D [x=" + x + ", y=" + y + "]";
	}

	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}

	public Vec2D(double x, double y) {
		super();
		this.x = x;
		this.y = y;
	}

}
