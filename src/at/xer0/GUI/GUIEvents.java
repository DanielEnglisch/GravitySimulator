
package at.xer0.GUI;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

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

	public static void loadConf()
	{

		JFileChooser fc = new JFileChooser();
		fc.setDialogTitle("Select a file");

		if (fc.showOpenDialog(null) == JFileChooser.APPROVE_OPTION)
		{

			loadConfigFromFile(fc.getSelectedFile());
		}

	}

	public static void reloadConfig()
	{
		loadConfigFromFile(Vars.lastFile);
	}

	private static void loadConfigFromFile(File f)
	{
		Vars.isResetRequested = true;

		try
		{
			BufferedReader in = new BufferedReader(new FileReader(f));

			while (in.ready())
			{
				String inText = in.readLine();

				if (inText.equals("forceRadius"))
				{
					Vars.mainFrame.cb_forceRadius.setSelected(true);
					GUIEvents.forceRadius(true);
				} else if (inText.substring(0, Math.min(inText.length(), 3)).equalsIgnoreCase("scZ"))
				{

					double d = Double.parseDouble(inText.split(":")[1]);

					Vars.scaling_ZoomFactor = d;
					Vars.mainFrame.lastMouseWheelState = (int) (1 / d);
					Vars.mainFrame.l_massstab.setText("Maßstab = 1:" + (int) (1 / Vars.scaling_ZoomFactor));

				} else if (inText.substring(0, Math.min(inText.length(), 4)).equalsIgnoreCase("scDX"))
				{

					Vars.scaling_Delta.setX(Double.parseDouble(inText.split(":")[1]));

				} else if (inText.substring(0, Math.min(inText.length(), 4)).equalsIgnoreCase("scDY"))
				{
					Vars.scaling_Delta.setY(Double.parseDouble(inText.split(":")[1]));

				} else if (inText.substring(0, Math.min(inText.length(), 8)).equalsIgnoreCase("timestep"))
				{
					Vars.timeStep = Double.parseDouble(inText.split(":")[1]);
					Vars.mainFrame.l_Timestep.setText("Timestep: " + Vars.timeStep);
					Vars.mainFrame.t_timestep.setText(Vars.timeStep + "");

				} else if (inText.substring(0, Math.min(inText.length(), 8)).equalsIgnoreCase("pathsize"))
				{
					
					Vars.pathSize = Integer.parseInt(inText.split(":")[1]);

					Vars.mainFrame.t_pathSize.setText("" + Vars.pathSize);
					Vars.mainFrame.l_pathsize.setText("Timestep: " + Vars.pathSize);



				} else if (inText.substring(0, Math.min(inText.length(), 2)).equalsIgnoreCase("o:"))
				{
					// Object:

					inText = inText.substring(2);

					String[] split = inText.split("#");

					double x = Double.parseDouble(split[0]);
					double y = Double.parseDouble(split[1]);

					double vx = Double.parseDouble(split[2]);
					double vy = Double.parseDouble(split[3]);

					double mass = Double.parseDouble(split[4]);

					String name = "";

					if (split.length >= 6)
					{
						name = split[5];
					}

					Obj o = new Obj(new Vec2D(x, y), new Vec2D(vx, vy), mass);

					o.setName(name);

					System.out.println("Parsed Object: " + o.toString());

					Vars.bufferedObjects.add(o);

				} else
				{
					System.out.println("Unknown Argurment: " + inText);
					JOptionPane.showMessageDialog(null, "Invalid file format!");
					in.close();
					return;
				}

			}

			in.close();

			System.out.println(Vars.scaling_ZoomFactor);

			Vars.lastFile = f;

		} catch (Exception e)
		{
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Invalid Fileformat!");

		}
	}

	public static void forceRadius(boolean force)
	{
		Vars.forceRadius = force;
	}

}
