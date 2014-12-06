
package at.xer0.Support;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import javax.swing.JOptionPane;

import at.xer0.GUI.GUIEvents;

public class FileManager
{

	public static void loadConfigFromFile(File f)
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
					Vars.mainFrame.t_massstabInput.setText(""+(int) (1 / Vars.scaling_ZoomFactor));


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
					Vars.mainFrame.l_pathsize.setText("Pathsize: " + Vars.pathSize);

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
}
