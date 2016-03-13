package org.xeroserver.GravitySimulator.Support;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import org.xeroserver.GravitySimulator.Objects.Obj;

public class FileManager {

	public static void loadConfigFromFile(File f) {
		Vars.isResetRequested = true;

		try {
			BufferedReader in = new BufferedReader(new FileReader(f));

			while (in.ready()) {
				parseLine(in.readLine());
			}

			in.close();

			Vars.lastFile = f;

		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Invalid Fileformat!");

		}
	}

	public static void loadConfigFromInputStream(InputStream is) {
		Vars.isResetRequested = true;

		try {
			BufferedReader in = new BufferedReader(new InputStreamReader(is));

			while (in.ready()) {
				parseLine(in.readLine());
			}

			in.close();

		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Invalid Fileformat!");

		}
	}

	public static void parseLine(String line) {
		String inText = line;

		if (inText.substring(0, Math.min(inText.length(), 3)).equalsIgnoreCase("scZ")) {

			double d = Double.parseDouble(inText.split(":")[1]);

			Vars.scaling_ZoomFactor = d;
			Vars.mainFrame.lastMouseWheelState = (int) (1 / d);
			Vars.mainFrame.rl_massstab.setText("Scale = 1:" + (int) (1 / Vars.scaling_ZoomFactor));
			Vars.mainFrame.t_massstabInput.setText("" + (int) (1 / Vars.scaling_ZoomFactor));

		} else if (inText.substring(0, Math.min(inText.length(), 4)).equalsIgnoreCase("scDX")) {

			Vars.scaling_Delta.setX(Double.parseDouble(inText.split(":")[1]));

		} else if (inText.substring(0, Math.min(inText.length(), 4)).equalsIgnoreCase("scDY")) {
			Vars.scaling_Delta.setY(Double.parseDouble(inText.split(":")[1]));

		} else if (inText.substring(0, Math.min(inText.length(), 8)).equalsIgnoreCase("timestep")) {
			Vars.timeStep = Double.parseDouble(inText.split(":")[1]);
			Vars.mainFrame.t_timestep.setText(Vars.timeStep + "");

		} else if (inText.substring(0, Math.min(inText.length(), 8)).equalsIgnoreCase("pathsize")) {

			Vars.pathSize = Integer.parseInt(inText.split(":")[1]);

			Vars.mainFrame.t_pathSize.setText("" + Vars.pathSize);

		} else if (inText.substring(0, Math.min(inText.length(), 2)).equalsIgnoreCase("o:")) {
			// Object:

			inText = inText.substring(2);

			Obj o = parseObject(inText);

			Vars.logger.info("Parsed Object: " + o.toString());

			Vars.bufferedObjects.add(o);

		} else {
			JOptionPane.showMessageDialog(null, "There was an error parsing the file!\nUnknown argurment: " + inText);

		}

	}

	public static Obj parseObject(String line) {

		// x-22#y-22#vx-44#vy-44#m-66#n-NAME

		String[] args = line.split("#");

		double x = 0, y = 0, vx = 0, vy = 0, m = 0;

		String name = "";

		for (String s : args) {
			Vars.logger.info("Obj Argurment: " + s);

			String[] spt = s.split(",");
			String att = spt[0];
			String key = spt[1];

			if (att.equals("x")) {
				x = Double.parseDouble(key);
			} else if (att.equals("y")) {
				y = Double.parseDouble(key);
			} else if (att.equals("vx")) {
				vx = Double.parseDouble(key);
			} else if (att.equals("vy")) {
				vy = Double.parseDouble(key);
			} else if (att.equals("m")) {
				m = Double.parseDouble(key);
			} else if (att.equals("n")) {
				name = key;
			}
		}

		return new Obj(x, y, vx, vy, m, name);
	}

	public static void saveConfiguration() {
		boolean restart = false;
		if (Vars.isActive) {
			Vars.isActive = false;
			restart = true;
		}

		JFileChooser fc = new JFileChooser();
		fc.setDialogTitle("Select an output file");

		File file = new File(".");
		fc.setCurrentDirectory(file);

		int res = fc.showSaveDialog(Vars.mainFrame);

		if (res == JFileChooser.APPROVE_OPTION) {
			file = fc.getSelectedFile();
		}

		try {
			BufferedWriter out = new BufferedWriter(new FileWriter(file));

			// Scaling:
			out.write("scZ:" + Vars.scaling_ZoomFactor + "\n");
			out.write("scDX:" + Vars.scaling_Delta.getX() + "\n");
			out.write("scDY:" + Vars.scaling_Delta.getY() + "\n");

			// ProgrammData;
			out.write("timestep:" + Vars.timeStep + "\n");
			out.write("pathsize:" + Vars.pathSize + "\n");

			for (Obj o : Vars.activeObjects) {
				out.write("o:x," + o.getPosition().getX() + "#y," + o.getPosition().getY() + "#vx,"
						+ o.getVelocity().getX() + "#vy," + o.getVelocity().getY() + "#m," + o.getMass());
				if (!o.getName().equals("")) {
					out.write("#n," + o.getName());
				}
				out.write("\n");
			}

			out.flush();
			out.close();

			if (res == JFileChooser.APPROVE_OPTION) {
				JOptionPane.showMessageDialog(null, "Successfully saved configuration!");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		if (restart) {
			Vars.isActive = true;
		}

	}

}
