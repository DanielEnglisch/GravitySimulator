
package at.xer0.GUI;

import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import at.xer0.Support.FileManager;
import at.xer0.Support.Obj;
import at.xer0.Support.Vars;
import at.xer0.Support.Vec2D;

public class GUIEvents
{

	public static void startStop()
	{

		Vars.isActive = !Vars.isActive;

		if (Vars.isActive)
		{
			Vars.mainFrame.b_StartStop.setText("Stop Simulation");
			System.out.println("Simulation: Started");

			// DisableStepMode:
			Vars.mainFrame.b_nextStep.setEnabled(false);

		} else
		{
			Vars.mainFrame.b_StartStop.setText("Start Simulation");
			System.out.println("Simulation: Stopped");

			// EnableStepMode:
			Vars.mainFrame.b_nextStep.setEnabled(true);
		}
	}

	public static void addObject(double x, double y)
	{

		Obj o = null;

		o = new Obj(new Vec2D(x, y), Vars.preset_Velocity, Vars.preset_Mass);

		Vars.bufferedObjects.add(o);

		System.out.println("Added Object: " + o.toString());
	}

	public static void addObject(int x, int y)
	{
		// Verschiebung:
		x -= Vars.scaling_Delta.getX() * Vars.scaling_ZoomFactor;
		y -= Vars.scaling_Delta.getY() * Vars.scaling_ZoomFactor;

		// Origin:
		x -= (Vars.mainFrame.renderPanel.getWidth() / 2);
		y -= (Vars.mainFrame.renderPanel.getHeight() / 2);

		Obj o = null;

		o = new Obj(new Vec2D(
		// Zoom:
		(x / Vars.scaling_ZoomFactor), (y / Vars.scaling_ZoomFactor)), Vars.preset_Velocity, Vars.preset_Mass);

		Vars.bufferedObjects.add(o);

		System.out.println("Added Object: " + o.toString());
	}

	public static void saveConf()
	{
		boolean restart = false;
		if (Vars.isActive)
		{
			Vars.isActive = false;
			restart = true;
		}

		JFileChooser fc = new JFileChooser();
		fc.setDialogTitle("Select an output file");

		File file = new File(".");

		int res = fc.showSaveDialog(Vars.mainFrame);

		if (res == JFileChooser.APPROVE_OPTION)
		{
			file = fc.getSelectedFile();
		}

		try
		{
			BufferedWriter out = new BufferedWriter(new FileWriter(file));

			// Scaling:
			out.write("scZ:" + Vars.scaling_ZoomFactor + "\n");
			out.write("scDX:" + Vars.scaling_Delta.getX() + "\n");
			out.write("scDY:" + Vars.scaling_Delta.getY() + "\n");

			// ProgrammData;
			out.write("timestep:" + Vars.timeStep + "\n");
			out.write("pathsize:" + Vars.pathSize + "\n");

			if (Vars.mainFrame.cb_forceRadius.isSelected())
			{
				out.write("forceRadius\n");
			}

			for (Obj o : Vars.activeObjects)
			{

				out.write("o:" + o.getPosition().getX() + "#" + o.getPosition().getY() + "#" + o.getVelocity().getX() + "#" + o.getVelocity().getY() + "#" + o.getMass() + "\n");
			}

			out.flush();
			out.close();

			if (res == JFileChooser.APPROVE_OPTION)
			{
				JOptionPane.showMessageDialog(null, "Successfully saved configuration!");

			}

		} catch (Exception e)
		{
			e.printStackTrace();
		}

		if (restart)
		{
			Vars.isActive = true;
		}

	}
	
	public static void takeScreenShot()
	{
		BufferedImage bi = new BufferedImage(Vars.mainFrame.renderPanel.getWidth(), Vars.mainFrame.renderPanel.getHeight(), BufferedImage.TYPE_3BYTE_BGR);
		Vars.mainFrame.renderPanel.paint(bi.getGraphics());

		File outputfile = null;

		JFileChooser fc = new JFileChooser();
		fc.setDialogTitle("Select a file");

		if (fc.showOpenDialog(null) == JFileChooser.APPROVE_OPTION)
		{
			outputfile = fc.getSelectedFile();
		}

		try
		{
			ImageIO.write(bi, "jpg", outputfile);
		} catch (IOException e)
		{
			e.printStackTrace();
		}

	}

	public static void loadConf()
	{

		JFileChooser fc = new JFileChooser();
		fc.setDialogTitle("Select a file");

		if (fc.showOpenDialog(null) == JFileChooser.APPROVE_OPTION)
		{

			FileManager.loadConfigFromFile(fc.getSelectedFile());
		}

	}

	public static void reloadConfig()
	{
		FileManager.loadConfigFromFile(Vars.lastFile);
	}

	public static void forceRadius(boolean force)
	{
		Vars.forceRadius = force;
	}

}
