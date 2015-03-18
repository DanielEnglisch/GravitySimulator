package at.xer0.lib.Simulators.GravitySimulator;

public class Vec3D {
	
	private double x,y,z;

	@Override
	public String toString() {
		return "Vec3D [x=" + x + ", y=" + y + ", z=" + z + "]";
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

	public double getZ() {
		return z;
	}

	public void setZ(double z) {
		this.z = z;
	}

	public Vec3D(double x, double y, double z) {
		super();
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	

}
