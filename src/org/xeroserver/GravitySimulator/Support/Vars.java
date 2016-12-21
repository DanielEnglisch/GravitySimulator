package org.xeroserver.GravitySimulator.Support;

import java.io.File;
import java.util.ArrayList;

import org.xeroserver.GravitySimulator.GUI.MainFrame;
import org.xeroserver.GravitySimulator.Objects.Obj;
import org.xeroserver.GravitySimulator.Objects.Vec2D;
import org.xeroserver.x0library.log.Logger;

public class Vars {

	public static MainFrame mainFrame = null;
	public static String version = "1.53";

	public static Logger logger = new Logger("GravitySimulator");

	public static boolean renderRequest = false;

	public static boolean isActive = false;
	public static boolean nextStep = false;
	public static boolean isResetRequested = false;

	public static boolean clearPoints = false;

	public static File lastFile = null;

	public static int steps = 1;

	// ActiveObjects:
	public static ArrayList<Obj> activeObjects = new ArrayList<Obj>();
	public static ArrayList<Obj> bufferedObjects = new ArrayList<Obj>();
	public static ArrayList<Obj> objectToDelete = new ArrayList<Obj>();

	// Time (only for rendering on GUI)
	public static double time = 0;

	// Current timestep
	public static double timeStep = 0.00001;

	public static Vec2D preset_Velocity = new Vec2D(0, 0);
	public static Vec2D preset_Position = new Vec2D(0, 0);
	public static double preset_Mass = Double.parseDouble("6E24");
	public static String preset_Name = "";

	public static double G = 6.67384 * Math.pow(10, -11);
	public static double rExponent = 2;

	public static double scaling_ZoomFactor = 1;
	public static Vec2D scaling_Delta = new Vec2D(0, 0);

	public static int pathSize = 300;

}
