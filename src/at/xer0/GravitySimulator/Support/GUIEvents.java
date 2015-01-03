
package at.xer0.GravitySimulator.Support;

import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFileChooser;

import at.xer0.GravitySimulator.Objects.Obj;
import at.xer0.GravitySimulator.Objects.Vec2D;
import at.xer0.GravitySimulator.Simulator.Core;

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

		o = new Obj(new Vec2D(x, y), Vars.preset_Velocity, Vars.preset_Mass, Vars.preset_Name);

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
		(x / Vars.scaling_ZoomFactor), (y / Vars.scaling_ZoomFactor)), Vars.preset_Velocity, Vars.preset_Mass,  Vars.preset_Name);

		Vars.bufferedObjects.add(o);

		System.out.println("Added Object: " + o.toString());
	}

	
	public static void takeScreenShot(boolean quickSave)
	{
		BufferedImage bi = new BufferedImage(Vars.mainFrame.renderPanel.getWidth(), Vars.mainFrame.renderPanel.getHeight(), BufferedImage.TYPE_INT_RGB);
		Vars.mainFrame.renderPanel.paint(bi.getGraphics());

		File outputfile = null;

		if (quickSave)
		{
			try
			{
				File f = new File(System.getProperty("java.class.path"));
				File dir = f.getAbsoluteFile().getParentFile();
				String path = dir.toString();

				outputfile = new File(path, "" + Core.randInt(0, 10000000) + ".png");

				System.out.println("FILE: " + outputfile.getAbsolutePath());

			} catch (Exception e)
			{
				e.printStackTrace();
			}
		} else
		{
			JFileChooser fc = new JFileChooser();
			fc.setDialogTitle("Select a file");

			if (fc.showOpenDialog(null) == JFileChooser.APPROVE_OPTION)
			{
				outputfile = fc.getSelectedFile();
			}
		}

		try
		{
			ImageIO.write(bi, "png", outputfile);
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

	public static void initGlobalKeyEvents()
	{
		KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(new KeyEventDispatcher()
		{

			@Override
			public boolean dispatchKeyEvent(KeyEvent arg0)
			{

				if (arg0.getID() != KeyEvent.KEY_PRESSED)
				{
					return false;
				}

				if (arg0.getKeyCode() == KeyEvent.VK_R && arg0.isControlDown())
				{
					GUIEvents.reloadConfig();
				} else

				if (arg0.getKeyCode() == KeyEvent.VK_S)
				{
					System.out.println("QuickScreenshot");
					GUIEvents.takeScreenShot(true);
				}

				return false;
			}
		});
	}

	public static void reloadConfig()
	{
		if (Vars.lastFile == null)
		{
			loadConf();
			return;
		}

		FileManager.loadConfigFromFile(Vars.lastFile);
	}



}
