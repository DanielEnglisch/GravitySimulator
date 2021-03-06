package org.xeroserver.GravitySimulator.Objects;

public class Point {

	@Override
	public String toString() {
		return "Point [x=" + x + ", y=" + y + "]";
	}

	private int x, y;

	/**
	 * @param x
	 * @param y
	 */
	public Point(int x, int y) {
		super();
		this.x = x;
		this.y = y;
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

	public boolean isIdenticalTo(Point p) {

		if (this.x == p.getX() && this.y == p.getY()) {
			return true;
		}
		return false;
	}

}
