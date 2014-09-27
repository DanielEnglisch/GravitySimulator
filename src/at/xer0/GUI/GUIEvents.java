
package at.xer0.GUI;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import at.xer0.Support.ColorEnum;
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

	public static void addObject(int x, int y)
	{
		x -= (Vars.mainFrame.renderPanel.getWidth()/2);
		y -= (Vars.mainFrame.renderPanel.getHeight()/2);

		Obj o = null;
		
		o = new Obj(new Vec2D(x, y), Vars.currentVelocityPreset, Vars.currentMassPreset, Vars.currentColorPreset,Vars.mainFrame.cb_static.isSelected() );



		Vars.bufferedObjects.add(o);

		System.out.println("Added Object: " + o.toString());
	}

	public static void reverseTime()
	{
		Vars.isTimeReversed = !Vars.isTimeReversed;

		if (Vars.isTimeReversed)
		{
			Vars.mainFrame.b_ReverseTime.setText("Normalize Time");
			Vars.mainFrame.b_nextStep.setText("<");
			System.out.println("Reversed Time!");
		} else
		{
			Vars.mainFrame.b_ReverseTime.setText("Reverse Time");
			Vars.mainFrame.b_nextStep.setText(">");
			System.out.println("Normalized Time!");
		}

	}
	
	
	public static void editObject(int x, int y)
	{
		x -= (Vars.mainFrame.renderPanel.getWidth()/2);
		y -= (Vars.mainFrame.renderPanel.getHeight()/2);
		
		if(!Vars.isActive)
		{
			Obj chosen = null;
			
			for(Obj o : Vars.activeObjects)
			{
				if(x  < o.getPosition().getX() + 10 && x  > o.getPosition().getX() - 10 &&
						y  < o.getPosition().getY() + 10 && y  > o.getPosition().getY() - 10)
				{
					chosen = o;
				}
			}
			
			if(chosen != null)
			{
				EditObjectGUI os = new EditObjectGUI(chosen);
				os.setVisible(true);
			}
		}
	}

	public static void saveConf()
	{
		boolean restart  = false;
		if(Vars.isActive){Vars.isActive = false; restart = true;}
		
		JFileChooser fc  = new JFileChooser();
		fc.setDialogTitle("Select an output file");
		
		File file = new File(".");
		
		int res = fc.showSaveDialog(Vars.mainFrame);
		
		if ( res == JFileChooser.APPROVE_OPTION)
		{ 
			file = fc.getSelectedFile();
		}
		
		try
		{
			BufferedWriter out = new BufferedWriter(new FileWriter(file));
			
			for(Obj o : Vars.activeObjects)
			{
				String stat = "false";
				if(o.isStatic){stat = "true";}
				
				out.write(o.getPosition().getX() + "#" + o.getPosition().getY() + "#" + o.getVelocity().getX() + "#" + o.getVelocity().getY() + "#" + o.getMass() + "#" + stat + "\n");
			}
			
			out.flush();
			out.close();
			
			if ( res == JFileChooser.APPROVE_OPTION)
			{
				JOptionPane.showMessageDialog(null, "Successfully saved configuration!");

			}

			
		} catch (Exception e)
		{
			e.printStackTrace();
		}

		
		if(restart){Vars.isActive = true;}

	}
	
	
	public static void loadConf()
	{
		
		JFileChooser fc  = new JFileChooser();
		fc.setDialogTitle("Select a file");
		
		 if (fc.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
			 
				Vars.isResetRequested = true;

			 
			 File selectedFile = fc.getSelectedFile();
			 
			 try
			 {
				 BufferedReader in = new BufferedReader(new FileReader(selectedFile));
				 
				 while(in.ready())
				 {
					 String inText = in.readLine();
					 
					 String[] split = inText.split("#");
					 
					 double x = Double.parseDouble(split[0]);
					 double y = Double.parseDouble(split[1]);
					 
					 double vx = Double.parseDouble(split[2]);
					 double vy = Double.parseDouble(split[3]);
					 
					 double mass = Double.parseDouble(split[4]);
					 
					 boolean stat = Boolean.parseBoolean(split[5]);

					Obj o = new Obj(new Vec2D(x,y), new Vec2D(vx,vy), mass, ColorEnum.randomColor(),stat);
					
					System.out.println("Parsed Object: " + o.toString());
					

					Vars.bufferedObjects.add(o);
				 }
				 
				 in.close();
				 
				 Vars.lastFile = selectedFile;
				 
			 }
			 catch(Exception e)
			 {
				 e.printStackTrace();
				JOptionPane.showMessageDialog(null, "Invalid Fileformat!");

			 }
		 }
		     
		 
		 
	}

	public static void loadConf(File f)
	{

		Vars.isResetRequested = true;

		
		try
			 {
				 BufferedReader in = new BufferedReader(new FileReader(f));
				 
				 while(in.ready())
				 {
					 String inText = in.readLine();
					 
					 String[] split = inText.split("#");
					 
					 double x = Double.parseDouble(split[0]);
					 double y = Double.parseDouble(split[1]);
					 
					 double vx = Double.parseDouble(split[2]);
					 double vy = Double.parseDouble(split[3]);
					 
					 double mass = Double.parseDouble(split[4]);
					 
					 boolean stat = Boolean.parseBoolean(split[5]);

					Obj o = new Obj(new Vec2D(x,y), new Vec2D(vx,vy), mass, ColorEnum.randomColor(),stat);
					
					System.out.println("Parsed Object: " + o.toString());
										
					Vars.bufferedObjects.add(o);
				 }
				 
				 in.close();
				 
			 }
			 catch(Exception e)
			 {
				 e.printStackTrace();
				JOptionPane.showMessageDialog(null, "Invalid Fileformat!");
			 }
		 
		     
		 
		 
	}
	
	
	public static void forceRadius(boolean force)
	{
		if(force)
		{
			for(Obj o : Vars.activeObjects)
			{
				o.setRadius(30);
			}
		}
		else
		{
			for(Obj o : Vars.activeObjects)
			{
				o.setRadius((int)(o.getMass() * Vars.G));
			}
		}
	}
	
	public static void updateTimestep(int timestepfactor)
	{

		switch (timestepfactor)
		{
		case 0:
		{
			Vars.timeStep = 1;
		}
			break;
		case 1:
		{
			Vars.timeStep = 0.1;
		}
			break;
		case 2:
		{
			Vars.timeStep = 0.01;
		}
			break;
		case 3:
		{
			Vars.timeStep = 0.001;
		}
			break;
		case 4:
		{
			Vars.timeStep = 0.0001;
		}
			break;
		case 5:
		{
			Vars.timeStep = 0.00001;
		}
			break;
		case 6:
		{
			Vars.timeStep = 0.000001;
		}
			break;
		case 7:
		{
			Vars.timeStep = 0.0000001;
		}
			break;
		case 8:
		{
			Vars.timeStep = 0.00000001;
		}
			break;
		case 9:
		{
			Vars.timeStep = 0.000000001;
		}
			break;
		case 10:
		{
			Vars.timeStep = 0.0000000001;
		}
			break;

		}

		Vars.mainFrame.l_timestep.setText("" + String.format("%." + timestepfactor + "f", Vars.timeStep));

	}

}
