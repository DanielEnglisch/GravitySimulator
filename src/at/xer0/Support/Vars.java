
package at.xer0.Support;

import java.awt.Color;
import java.io.File;
import java.util.ArrayList;

import at.xer0.GUI.MainFrame;

public class Vars
{

	// Standart Werte
	public static String defxVelocityPreset = "0";
	public static String defyVelocityPreset = "0";
	public static String defMassPreset = "6E24";
	public static Color defColorPreset = ColorEnum.randomColor();
	//

	public static MainFrame mainFrame = null;
	public static String version = "0.94a";

	public static boolean isActive = false;
	public static boolean isTimeReversed = false;
	public static boolean nextStep = false;
	public static boolean isResetRequested = false;
	public static boolean isRenderPaused = false;
	public static boolean customObjects = false;

	public static boolean clearPoints = false;

	public static File lastFile = null;

	// Steps to do in SingleStepMode (by default 1)
	public static int steps = 1;

	// ActiveObjects:
	public static ArrayList<Obj> activeObjects = new ArrayList<Obj>();
	public static ArrayList<Obj> bufferedObjects = new ArrayList<Obj>();
	public static ArrayList<Obj> objectToDelete = new ArrayList<Obj>();

	// Time (only for rendering on GUI)
	public static double time = 0;

	// Current timestep
	public static double timeStep = 0.00001;

	public static Vec2D currentVelocityPreset = new Vec2D(Double.parseDouble(defxVelocityPreset), Double.parseDouble(defyVelocityPreset));
	public static double currentMassPreset = Double.parseDouble(defMassPreset);
	public static Color currentColorPreset = defColorPreset;

	public static double G = 6.67384 * Math.pow(10, -11);

	public static double scaling_ZoomFactor = 1;

	public static Vec2D scaling_Delta = new Vec2D(0, 0);

	public static boolean forceRadius = false;
	
	public static int pathSize = 300;

}
