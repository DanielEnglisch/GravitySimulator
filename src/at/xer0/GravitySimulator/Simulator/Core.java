package at.xer0.GravitySimulator.Simulator;

import java.util.Random;

import at.xer0.GravitySimulator.GUI.MainFrame;
import at.xer0.GravitySimulator.Objects.Obj;
import at.xer0.GravitySimulator.Objects.Vec2D;
import at.xer0.GravitySimulator.Support.Vars;
import at.xer0.x0_Library.net.AppUpdater;

public class Core {
	
	

	public static void main(String[] args) throws InterruptedException {
		
		//Updater:
		AppUpdater up = new AppUpdater	(	"http://xeroserver.org/api/gravsim/cs_gravsim.php",
											"http://xeroserver.org/api/gravsim/GravitySimulator.jar",
											"GravitySimulator.jar"
										);
		up.checkForUpdate();
		
				
		
		// Ladet und startet die GUI
		Vars.mainFrame = new MainFrame();
		new Thread(Vars.mainFrame).start();

		resetSimulation();

		System.out.println("GravitySimulator initialized!");

		// Hauptschleife
		while (true) {

			if (Vars.isResetRequested) {
				resetSimulation();
				Vars.isResetRequested = false;
			}

			// Delete requested Object:
			if (Vars.objectToDelete.size() != 0) {
				for (Obj no : Vars.objectToDelete) {
					Vars.activeObjects.remove(no);
				}

				Vars.objectToDelete.clear();
			}

			// Add buffered Objects:
			if (Vars.bufferedObjects.size() != 0) {
				for (Obj no : Vars.bufferedObjects) {
					Vars.activeObjects.add(no);
				}

				Vars.bufferedObjects.clear();
			}

			// Wenn die Simulation läuft:
			if (Vars.isActive) {
				// Logik
				Logic.tick();

			} else {
				// Stepping Mode Active:

				// Wenn der SingleStep Button gedrückt wurde,
				if (Vars.nextStep) {
					// Die Logic so oft ausführen, wie gewünscht:

					for (int i = 0; i < Vars.steps; i++) {
						Logic.tick();
					}

					// Setze den SingleStep Button Request wieder auf false.
					Vars.nextStep = false;
				}

			}

			// Render:
			Vars.mainFrame.renderPanel.repaint();
			//

			// GuiVars
			updateGUIVars();
			//

		}

	}

	public static void resetSimulation() {

		Vars.isActive = false;

		// Darstellungsvars:
		Vars.scaling_ZoomFactor = 0.001;
		Vars.mainFrame.lastMouseWheelState = 1000;
		Vars.scaling_Delta = new Vec2D(0, 0);
		Vars.mainFrame.l_massstab.setText("Scale = 1:1000m");
		Vars.mainFrame.t_massstabInput.setText("1000");
		// -----------------

		Vars.mainFrame.t_timestep.setText("0.00001");
		Vars.timeStep = 0.00001;

		Vars.mainFrame.t_pathSize.setText("300");
		Vars.pathSize = 300;

		Vars.mainFrame.t_steps.setText("1");
		Vars.steps = 1;
		// -------------------------------

		Vars.activeObjects.clear();
		Vars.time = 0;

		Vars.mainFrame.cb_drawPath.setSelected(true);

		Vars.mainFrame.b_StartStop.setText("Start Simulation");
		Vars.mainFrame.b_nextStep.setEnabled(true);
		Vars.mainFrame.r_Time.setText("Time: "
				+ String.format("%.5f", Vars.time));

		Vars.mainFrame.mouseClickPos = new Vec2D(0, 0);
		Vars.mainFrame.mouseReleasePos = new Vec2D(0, 0);

	}

	public static void updateGUIVars() {
		Vars.mainFrame.r_Time.setText("Time: " + fmt(Vars.time) + "s");
		Vars.mainFrame.r_Timestep.setText("Timestep: " + fmt(Vars.timeStep)
				+ "s");

		Vars.mainFrame.r_Objects.setText("Objects: "
				+ Vars.activeObjects.size());
		Vars.mainFrame.l_massstab.setText("Scale = 1:"
				+ String.format("%.0f", (1 / Vars.scaling_ZoomFactor)) + "m");

	}

	public static int randInt(int min, int max) {
		Random r = new java.util.Random();
		int i1 = r.nextInt(max - min + 1) + min;
		return i1;
	}

	public static String fmt(double d) {
		if (d == (long) d)
			return String.format("%d", (long) d);
		else
			return String.format("%s", d);
	}

}
