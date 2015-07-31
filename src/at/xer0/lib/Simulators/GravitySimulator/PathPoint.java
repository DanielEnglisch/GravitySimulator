package at.xer0.lib.Simulators.GravitySimulator;

public class PathPoint {

	private int x;
	private int y;

	public PathPoint(int i, int ii) {
		x = i;
		y = ii;
	}

	public PathPoint(Vec2D v) {
		x = (int) v.getX();
		y = (int) v.getY();

	}

	public PathPoint(Vec3D v) {
		x = (int) v.getX();
		y = (int) v.getY();

	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

}
