package at.xer0.Support;

import java.util.ArrayList;


public class Vars {
	
	
	//Standart Wertks
	public static String defxVelocityPreset = "47.42";
	public static String defyVelocityPreset = "29.128";
	public static String defMassPreset = "45";
	//
	
	public static boolean isActive = false;
	public static ArrayList<SObject> sObjects = new ArrayList<SObject>();
	public static double time = 0;
	public static double timeStep = 0.000001;
	
	public static double currentxVelocityPreset = Double.parseDouble(defxVelocityPreset);
	public static double currentyVelocityPreset = Double.parseDouble(defyVelocityPreset);
	public static double currentMassPreset = Double.parseDouble(defMassPreset);
	




}
