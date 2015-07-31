package at.xer0.lib.Simulators.GravitySimulator;

import java.awt.Color;
import java.util.Random;

public class SObject {

	public Color color = randomColor();

	public static Color randomColor() {
		Random rand = new Random();
		float r = rand.nextFloat();
		float g = rand.nextFloat();
		float b = rand.nextFloat();

		return new Color(r, g, b);
	}

	private Vec3D position = new Vec3D(0, 0, 0), velocity = new Vec3D(0, 0, 0),
			acceleration = new Vec3D(0, 0, 0);

	@Override
	public String toString() {
		return "-" + name + "- [position=" + position + ", velocity="
				+ velocity + ", acceleration=" + acceleration + ", mass="
				+ mass + "]";
	}

	private double mass = 0;
	private String name = "";

	public SObject(Vec3D pos, Vec3D vel, double m, String s) {
		this.position = pos;
		this.velocity = vel;
		this.mass = m;
		this.name = s;
	}

	public Vec3D getPosition() {
		return position;
	}

	public void setPosition(Vec3D position) {
		this.position = position;
	}

	public Vec3D getVelocity() {
		return velocity;
	}

	public void setVelocity(Vec3D velocity) {
		this.velocity = velocity;
	}

	public Vec3D getAcceleration() {
		return acceleration;
	}

	public void setAcceleration(Vec3D acceleration) {
		this.acceleration = acceleration;
	}

	public double getMass() {
		return mass;
	}

	public void setMass(double mass) {
		this.mass = mass;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Vec3D getDeltaXYZ(SObject o2) {
		double dx = o2.getPosition().getX() - position.getX();
		double dy = o2.getPosition().getY() - position.getY();
		double dz = o2.getPosition().getZ() - position.getZ();

		return new Vec3D(dx, dy, dz);
	}

	public double getDistanceTo(SObject o2) {
		Vec3D delta = getDeltaXYZ(o2);

		return Math.sqrt(Math.pow(delta.getX(), 2) + Math.pow(delta.getY(), 2)
				+ Math.pow(delta.getZ(), 2));
	}

}
