
package at.xer0.Support;

import java.awt.Color;
import java.util.ArrayList;

import at.xer0.GUI.MainFrame;

public class Vars
{

	// Standart Werte
	public static String defxVelocityPreset = "10.32";
	public static String defyVelocityPreset = "-24.3";
	public static String defMassPreset = "523";
	public static Color defColorPreset = Color.MAGENTA;
	//

	public static MainFrame mainFrame = null;

	public static String version = "0.7a";

	public static boolean isActive = false;
	public static boolean isTimeReversed = false;
	public static boolean nextStep = false;

	//Steps to do in SingleStepMode (by default 10)
	public static int steps = 10;

	//ActiveObjects:
	public static ArrayList<Obj> activeObjects = new ArrayList<Obj>();
	
	//Time (only for rendering on GUI)
	public static double time = 0;
	
	//Current timestep
	public static double timeStep = 0.00001;

	public static Vec2D currentVelocityPreset = new Vec2D(Double.parseDouble(defxVelocityPreset), Double.parseDouble(defyVelocityPreset));
	public static double currentMassPreset = Double.parseDouble(defMassPreset);
	public static Color currentColorPreset = defColorPreset;

}
